package com.cap.service.impl;

import com.alibaba.fastjson.JSON;
import com.cap.dto.SearchData;
import com.cap.form.InfoDataForm;
import com.cap.mapper.InfoContentMapper;
import com.cap.mapper.InfoMapper;
import com.cap.pojo.Info;
import com.cap.pojo.InfoContent;
import com.cap.pojo.User;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
/**
 * 获取词条信息
 * @Author zengjiaxing
 */
@Service
public class SearchServiceImpl {
    @Autowired
    private RestHighLevelClient restHighLevelClient;//ES操作api
    @Autowired
    private InfoServiceImpl infoService;

    @Autowired
    private InfoMapper infoMapper;
    @Autowired
    private InfoContentMapper infoContentMapper;

    public List<InfoDataForm> getList() throws IOException {
        List<InfoDataForm> infoDataForms = new ArrayList<>();
        File file = new File("C:\\Users\\34732\\Desktop\\毕业设计\\info.txt");
        InputStream inputStream = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String str = new String(sb.toString());
        infoDataForms = JSON.parseArray(str, InfoDataForm.class);
        return infoDataForms;
    }
    public void insertTest() throws IOException {
        List<InfoDataForm> infoDataForms = getList();
        Iterator<InfoDataForm> infoDataFormIterator = infoDataForms.iterator();
        while (infoDataFormIterator.hasNext()) {
            InfoDataForm infoDataForm = infoDataFormIterator.next();
            insertNewSearchData(infoDataForm,1l);
        }
    }

    public Boolean insertNewSearchData(InfoDataForm infoDataForm, Long userId) {
        Info info = new Info(null,infoDataForm.getCnName(),infoDataForm.getEnName(),infoDataForm.getSlug(),null);
        infoMapper.insert(info);
        InfoContent infoContent = new InfoContent(null,info.getInfoId(),userId,infoDataForm.getContent(),0);
        infoContentMapper.insert(infoContent);
        info.setInfoContentId(infoContent.getInfoContentId());
        infoService.updateById(info);
        IndexRequest request = new IndexRequest("info","_doc",info.getInfoId().toString());
        request.source(JSON.toJSONString(infoDataForm),XContentType.JSON);
        IndexResponse indexResponse = null;
        try{
            indexResponse = restHighLevelClient.index(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(indexResponse);
        return true;
    }

    public Boolean changeSearchData(SearchData searchData) {
        UpdateRequest request = new UpdateRequest("info","_doc",searchData.getEsId());
        request.doc(JSON.toJSONString(searchData),XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = restHighLevelClient.update(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(updateResponse);
        return true;
    }

    /**
     * 通过给定的name来匹配es中的数据，返回数据
     * @param name 需要匹配的关键字
     * @param pageNo 从哪开始匹配数据
     * @param pageSize 匹配多少数据
     * @return String 匹配到的数据
     * */
    public String searchByName(String name, int pageNo, int pageSize) {
        if (name.equals("test")) {
            List<SearchData> list = new ArrayList<>();

            while (pageSize >= 0) {
                SearchData searchData = new SearchData("1","wedcat","1","1","1");
                list.add(searchData);

                pageSize -= 1;
            }

            String infoJson = JSON.toJSONString(list);
            return infoJson;
        }
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
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(name, "cnName", "enName"));
        searchSourceBuilder.timeout(new TimeValue(600, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Map<String, Object>> infos = new ArrayList<>();
        //System.out.println(searchResponse.getHits().getHits().length);
        //System.out.println(JSON.toJSONString(searchResponse.getHits()));
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            //System.out.println(searchHit);
            id.add(searchHit.getId());
            infos.add(searchHit.getSourceAsMap());
        }

        List<SearchData> infoList = new ArrayList<>();
        for (Map<String, Object> map : infos) {
            String jsonString = JSON.toJSONString(map);
            infoList.add(JSON.parseObject(jsonString, SearchData.class));
            infoList.get(i++).setEsId(id.get(i - 1));
        }
        String infoJson = JSON.toJSONString(infoList);
        return infoJson;
    }
    /**
     * 通过给定的name来匹配es中的数据，返回数据的数量
     * @param name 需要匹配的关键字
     * @return int 匹配到的数据的数量
     * */
    public int searchNum(String name) {
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
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(name, "cnName", "enName"));
        searchSourceBuilder.timeout(new TimeValue(600, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //System.out.println(searchResponse.getHits().getHits().length);
        return searchResponse.getHits().getHits().length;
    }
    /**
     * 通过给定的id列表来匹配es中的数据，返回数据
     * @param list 需要匹配的id列表
     * @return String 匹配到的数据
     * */
    public String searchById(List<String> list) {
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
        List<SearchData> infoList = new ArrayList<>();
        for (Map<String, Object> map : infos) {
            String jsonString = JSON.toJSONString(map);
            infoList.add(JSON.parseObject(jsonString, SearchData.class));
            infoList.get(j++).setEsId(id.get(j - 1));
        }
        String infoJson = JSON.toJSONString(infoList);
        return infoJson;
    }











































/**
 * 解析位于特定位置的json文件，插入es
 * @return boolean 是否解析是否成功
 * */
    /*public Boolean parseJson() throws IOException {
        List<SearchData> SearchDataList = new ArrayList<>();
        //ClassPathResource classPathResource = new ClassPathResource("D://jsonData.json");
        File file = new File("C:\\User\\34732\\Desktop\\毕业设计\\info.txt");
        InputStream inputStream = new FileInputStream(file);
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String str = new String(sb.toString());
        SearchDataList = JSON.parseArray(str, SearchData.class);
        System.out.println(SearchDataList);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("600s");
        //把查询的数据放入es中
        for (int i = 0; i < SearchDataList.size(); i++) {
            bulkRequest.add(new IndexRequest("info").source(JSON.toJSONString(SearchDataList.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }*/








    /**
     * 通过给定的keyword解析百度网页获取词条数据并插入es
     * @param keyword 需要解析的关键字
     * @return boolean 解析是否成功
     * */
    /*public Boolean parseKeyword(String keyword) throws IOException {
        SearchData info = new HtmlParseUtil().parseBaiDu(keyword);
        if (info.getContent().equals("") || info.getContent().isEmpty()) {
            return false;
        }
        List<SearchData> infoList = new ArrayList<>();
        infoList.add(info);
        //把查询的数据放入es中
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("600s");
        for (int i = 0; i < infoList.size(); i++) {
            bulkRequest.add(new IndexRequest("searchall").source(JSON.toJSONString(infoList.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }*/
}
