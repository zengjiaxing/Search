package com.cap.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cap.pojo.Info;
import com.cap.util.HtmlParseUtil;
import org.apache.http.nio.entity.NFileEntity;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
/**
 * 获取词条信息
 * @Author zengjiaxing
 */
@Service
public class InfoService {
    @Autowired
    private RestHighLevelClient restHighLevelClient;//ES操作api
    /**
     * 解析位于特定位置的json文件，插入es
     * @return boolean 是否解析是否成功
     * */
    public Boolean parseJson() throws IOException {
        List<Info> infoList = new ArrayList<>();
        //ClassPathResource classPathResource = new ClassPathResource("D://jsonData.json");
        File file = new File("C:\\BaikeData0.json");
        InputStream inputStream = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String str = new String(sb.toString());
        infoList = JSON.parseArray(str, Info.class);
        System.out.println(infoList);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("600s");
        //把查询的数据放入es中
        for (int i = 0; i < infoList.size(); i++) {
            bulkRequest.add(new IndexRequest("info").source(JSON.toJSONString(infoList.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    /**
     * 通过给定的keyword解析百度网页获取词条数据并插入es
     * @param keyword 需要解析的关键字
     * @return boolean 解析是否成功
     * */
    public Boolean parseKeyword(String keyword) throws IOException {
        Info info = new HtmlParseUtil().parseBaiDu(keyword);
        if (info.getContent().equals("") || info.getContent().isEmpty()) {
            return false;
        }
        List<Info> infoList = new ArrayList<>();
        infoList.add(info);
        //把查询的数据放入es中
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("600s");
        for (int i = 0; i < infoList.size(); i++) {
            bulkRequest.add(new IndexRequest("info").source(JSON.toJSONString(infoList.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    /**
     * 通过给定的name来匹配es中的数据，返回数据
     * @param name 需要匹配的关键字
     * @param pageNo 从哪开始匹配数据
     * @param pageSize 匹配多少数据
     * @return String 匹配到的数据
     * */
    public String searchByName(String name, int pageNo, int pageSize) throws IOException {
        int i = 0;
        List<String> id = new ArrayList<>();
        if (pageNo <= 0) {
            pageNo = 0;
        }
        //条件搜索
        SearchRequest searchRequest = new SearchRequest("info");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.highlighter();
        //分页
        searchSourceBuilder.from(pageNo);
        searchSourceBuilder.size(pageSize);
        //匹配
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(name, "cnName", "enName", "abbreviation","tags"));
        searchSourceBuilder.timeout(new TimeValue(600, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Map<String, Object>> infos = new ArrayList<>();
        /*System.out.println(searchResponse.getHits().getHits().length);*/
        //System.out.println(JSON.toJSONString(searchResponse.getHits()));
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            /*System.out.println(searchHit);*/
            id.add(searchHit.getId());
            infos.add(searchHit.getSourceAsMap());
        }

        List<Info> infoList = new ArrayList<>();
        for (Map<String, Object> map : infos) {
            String jsonString = JSON.toJSONString(map);
            infoList.add(JSON.parseObject(jsonString, Info.class));
            infoList.get(i++).setId(id.get(i - 1));

        }
        String infoJson = JSON.toJSONString(infoList);
        return infoJson;
    }
    /**
     * 通过给定的name来匹配es中的数据，返回数据的数量
     * @param name 需要匹配的关键字
     * @return int 匹配到的数据的数量
     * */
    public int searchNum(String name) throws IOException {
        int i = 0;
        List<String> id = new ArrayList<>();
        
        //条件搜索
        SearchRequest searchRequest = new SearchRequest("info");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.highlighter();
        //分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(10000);
        //匹配
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(name, "cnName", "enName", "abbreviation","tags"));
        searchSourceBuilder.timeout(new TimeValue(600, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        /*System.out.println(searchResponse.getHits().getHits().length);*/
        return searchResponse.getHits().getHits().length;
    }
    /**
     * 通过给定的id列表来匹配es中的数据，返回数据
     * @param list 需要匹配的id列表
     * @return String 匹配到的数据
     * */
    public String searchById(List<String> list) throws IOException {
        //1.bulk
        MultiGetResponse response = null;
        int j = 0;
        List<String> id = new ArrayList<>();
        MultiGetRequest request = new MultiGetRequest();
        for (int i = 0; i < list.size(); i++){
            request.add(new MultiGetRequest.Item("info", "_doc", list.get(i)));
        }
        try{
            response = restHighLevelClient.mget(request, RequestOptions.DEFAULT);
        }catch (Exception e){
            return "";
        }
        //MultiGetResponse response = restHighLevelClient.mget(request, RequestOptions.DEFAULT);
        List<Map<String, Object>> infos = new ArrayList<>();
        for (MultiGetItemResponse item : response.getResponses()) {
            GetResponse getResponse = item.getResponse();
            if (getResponse.isExists()) {
                id.add(getResponse.getId());
                infos.add(getResponse.getSourceAsMap());
            } else {
                System.out.println("没有查询到相应文档");
            }
        }
        List<Info> infoList = new ArrayList<>();
        for (Map<String, Object> map : infos) {
            String jsonString = JSON.toJSONString(map);
            infoList.add(JSON.parseObject(jsonString, Info.class));
            infoList.get(j++).setId(id.get(j - 1));
        }
        String infoJson = JSON.toJSONString(infoList);
        return infoJson;
    }
}