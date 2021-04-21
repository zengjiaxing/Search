package com.cap.controller;

import com.cap.dto.SearchData;
import com.cap.form.InfoDataForm;
import com.cap.pojo.User;
import com.cap.service.impl.SearchServiceImpl;
import com.cap.util.HtmlParseUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjx
 * @since 2020-07-01
 */
@RestController
public class SearchController {
    @Autowired
    HtmlParseUtil htmlParseUtil;
    @Autowired
    SearchServiceImpl searchService;//es操作相关封装类






    @GetMapping("/insertNewSearchData")
    public void insertNewSearchData() throws IOException {
        searchService.insertTest();
    }
    @GetMapping("/changeSearchData")
    public void changeSearchData() throws IOException {
        SearchData searchData = new SearchData("1","233","233","233","233");
        searchService.changeSearchData(searchData);
    }













    /**
     * 通过给定的name来匹配es中的数据，返回数据
     * @param keyword 需要匹配的关键字
     * @param pageNo 从哪开始匹配数据
     * @param pageSize 匹配多少数据
     * @return String 匹配到的数据
     * */
    @ApiOperation("通过给定的name来匹配es中的数据，返回数据")
    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public String search(@PathVariable("keyword") String keyword,
                         @PathVariable("pageNo") int pageNo,
                         @PathVariable("pageSize") int pageSize
    ) throws IOException{
        if(keyword.equals("1")){
            return "";
        }
        String infoJson = searchService.searchByName(keyword, pageNo, pageSize);
        System.out.println(infoJson);
        return infoJson;
    }
    /**
     * 通过给定的name来匹配es中的数据，返回数据的数量
     * @param keyword 需要匹配的关键字
     * @return int 匹配到的数据的数量
     * */
    @ApiOperation("通过给定的name来匹配es中的数据，返回数据的数量")
    @GetMapping("/searchNum/{keyword}")
    public int searchNum(@PathVariable("keyword") String keyword) throws IOException {
        if(keyword.equals("1")){
            return 0;
        }
        int num = searchService.searchNum(keyword);
        System.out.println("yy"+num);
        return num;
    }



























    /**
     * 通过给定的keyword解析百度网页获取词条数据并插入es
     * @param keyword 需要解析的关键字
     * */
    /*@GetMapping("/parse/{keyword}")
    public boolean parse(@PathVariable("keyword") String keyword) throws IOException {
        System.out.println(JSON.toJSONString(htmlParseUtil.parseBaiDu(keyword)));
        return searchService.parseKeyword(keyword);
    }*/
}
