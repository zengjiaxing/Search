package com.cap.util;

import com.cap.dto.SearchData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class HtmlParseUtil {
    public SearchData parseBaiDu(String keywords) throws IOException {
        //获取请求
        //需要联网
        String url = "https://baike.baidu.com/item/"+keywords;
        System.out.println(url);
        //解析网页(Jsoup返回Document就是浏览器Document对象)
        Document document = Jsoup.parse(new URL(url),30000);

        //所有在js中可以使用的方法，这里都能用！
        Elements elements = document.getElementsByClass("lemma-summary");
        SearchData info = new SearchData();
        String text = "";
        for (Element el : elements)
        {
            text = text+el.text();
            System.out.println("=========================");
        }
        info.setContent(text);//设置正文内容




        Elements elementsOne = document.getElementsByClass("basicInfo-block");//获取中英文名称和缩写
        //System.out.println(elementsOne);
        for (Element el : elementsOne)
        {
            Elements elementsDt = el.getElementsByTag("dt");
            Elements elementsDd = el.getElementsByTag("dd");
            for(int i = 0; i < elementsDt.size(); i++)
            {
                if(elementsDt.eq(i).text().equals("中文名"))
                {
                    info.setCnName(elementsDd.eq(i).text());
                    System.out.println(elementsDd.eq(i).text());
                }
                if(elementsDt.eq(i).text().equals("外文名"))
                {
                    info.setEnName(elementsDd.eq(i).text());
                    System.out.println(elementsDd.eq(i).text());
                }
                if(elementsDt.eq(i).text().startsWith("缩"))
                {
                    info.setSlug(elementsDd.eq(i).text());
                    System.out.println(elementsDd.eq(i).text());
                }
            }
        }

        if (info.getCnName() == null)
        {
            info.setCnName("");
        }
        if (info.getEnName() == null)
        {
            info.setEnName("");
        }
        if (info.getSlug() == null)
        {
            info.setSlug("");
        }
        if (info.getContent() == null)
        {
            info.setContent("");
        }


        return info;
    }
    public SearchData parseWiKi(String keywords) throws IOException {
        //获取请求
        //需要联网
        String url = "https://zh.wikipedia.org/wiki/"+keywords;
        System.out.println(url);
        //解析网页(Jsoup返回Document就是浏览器Document对象)
        Document document = Jsoup.parse(new URL(url),30000);

        //所有在js中可以使用的方法，这里都能用！
        Elements elements = document.getElementsByClass("lemma-summary");
        SearchData info = new SearchData();
        String text = "";
        for (Element el : elements)
        {
            text = text+el.text();
            System.out.println("=========================");
        }
        info.setContent(text);//设置正文内容




        Elements elementsOne = document.getElementsByClass("basicInfo-block");//获取中英文名称和缩写
        //System.out.println(elementsOne);
        for (Element el : elementsOne)
        {
            Elements elementsDt = el.getElementsByTag("dt");
            Elements elementsDd = el.getElementsByTag("dd");
            for(int i = 0; i < elementsDt.size(); i++)
            {
                if(elementsDt.eq(i).text().equals("中文名"))
                {
                    info.setCnName(elementsDd.eq(i).text());
                    System.out.println(elementsDd.eq(i).text());
                }
                if(elementsDt.eq(i).text().equals("外文名"))
                {
                    info.setEnName(elementsDd.eq(i).text());
                    System.out.println(elementsDd.eq(i).text());
                }
            }
        }
        if (info.getCnName() == null)
        {
            info.setCnName("");
        }
        if (info.getEnName() == null)
        {
            info.setEnName("");
        }
        if (info.getContent() == null)
        {
            info.setContent("");
        }
        return info;
    }
}
