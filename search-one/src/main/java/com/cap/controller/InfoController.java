package com.cap.controller;

import com.alibaba.fastjson.JSON;
import com.cap.pojo.Info;
import com.cap.service.impl.InfoService;
import com.cap.util.HtmlParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class InfoController {
    @Autowired
    HtmlParseUtil htmlParseUtil;
    @Autowired
    InfoService infoService;//es操作相关封装类
    /**
     * 通过给定的keyword解析百度网页获取词条数据并插入es
     * @param keyword 需要解析的关键字
     * */
    @GetMapping("/parse/{keyword}")
    public boolean parse(@PathVariable("keyword") String keyword) throws IOException {
        System.out.println(JSON.toJSONString(htmlParseUtil.parseBaiDu(keyword)));
        return infoService.parseKeyword(keyword);
    }


    /**
     * 解析位于特定位置的json文件，插入es
     * */
    @GetMapping("/parseJson/{path}")
    public void parseJson(@PathVariable("path") String path) throws IOException {
        System.out.println(path);
        infoService.parseJson("D:\\json\\" + path + ".json");
    }

    /**
     * 通过给定的name来匹配es中的数据，返回数据
     * @param keyword 需要匹配的关键字
     * @param pageNo 从哪开始匹配数据
     * @param pageSize 匹配多少数据
     * @return String 匹配到的数据
     * */
    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public String search(@PathVariable("keyword") String keyword,
                         @PathVariable("pageNo") int pageNo,
                         @PathVariable("pageSize") int pageSize
                         ) throws IOException{
        if(keyword.equals("1")){
            return "";
        }
        String infoJson = infoService.searchByName(keyword, pageNo, pageSize);
        System.out.println(infoJson);
        return infoJson;
    }
    /**
     * 通过给定的name来匹配es中的数据，返回数据的数量
     * @param keyword 需要匹配的关键字
     * @return int 匹配到的数据的数量
     * */
    @GetMapping("/searchNum/{keyword}")
    public int searchNum(@PathVariable("keyword") String keyword) throws IOException{
        if(keyword.equals("1")){
            return 0;
        }
        int num = infoService.searchNum(keyword);
        System.out.println("yy"+num);
        return num;
    }
}
