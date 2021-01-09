package com.cap.service;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    /**
     * 解析位于特定位置的json文件，插入es
     * @return boolean 是否解析是否成功
     * */
    public Boolean parseJson(String path) throws IOException;
    /**
     * 通过给定的keyword解析百度网页获取词条数据并插入es
     * @param keyword 需要解析的关键字
     * @return boolean 解析是否成功
     * */
    public Boolean parseKeyword(String keyword) throws IOException;
    /**
     * 通过给定的name来匹配es中的数据，返回数据
     * @param name 需要匹配的关键字
     * @param pageNo 从哪开始匹配数据
     * @param pageSize 匹配多少数据
     * @return String 匹配到的数据
     * */
    public String searchByName(String name, int pageNo, int pageSize) throws IOException;
    /**
     * 通过给定的name来匹配es中的数据，返回数据的数量
     * @param name 需要匹配的关键字
     * @return int 匹配到的数据的数量
     * */
    public int searchNum(String name) throws IOException;
    /**
     * 通过给定的id列表来匹配es中的数据，返回数据
     * @param list 需要匹配的id列表
     * @return String 匹配到的数据
     * */
    public String searchById(List<String> list) throws IOException;
}
