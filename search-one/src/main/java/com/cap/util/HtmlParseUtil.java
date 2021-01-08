package com.cap.util;

import com.cap.pojo.Info;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
@Service
public class HtmlParseUtil {
    public Info parseBaiDu(String keywords) throws IOException {
        //获取请求
        //需要联网
        String url = "https://baike.baidu.com/item/"+keywords;
        System.out.println(url);
        //解析网页(Jsoup返回Document就是浏览器Document对象)
        Document document = Jsoup.parse(new URL(url),30000);

        //所有在js中可以使用的方法，这里都能用！
        Elements elements = document.getElementsByClass("lemma-summary");
        Info info = new Info();
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



        /*Elements elementsTwo = document.getElementsByClass("reference-item reference-item--type1 ");
        //System.out.println(elementsTwo);
        List<Literature> literatureList = new ArrayList<>();
        for (Element el : elementsTwo) {
            Elements elementsText = el.getElementsByClass("text");
            String bookName = elementsText.eq(0).text();
            String bookUrl = "https://baike.baidu.com"+elementsText.eq(0).attr("href");
            System.out.println(bookName);
            System.out.println(bookUrl);
            Elements elementsAuthor = el.getElementsByClass("site");
            String author = elementsAuthor.text().substring(1);
            System.out.println(author);
            Literature literature = new Literature(bookName,author,bookUrl);
            literatureList.add(literature);
        }*/
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
        /*if (literatureList == null)
        {
            Literature temp = new Literature("","","");
            literatureList.add(temp);
        }*/
        //tags
        /*List<String> tags = new ArrayList<>();
        tags.add("");
        info.setTags(tags);
        info.setLiteratureList(literatureList);*/
        return info;
    }
    public Info parseWiKi(String keywords) throws IOException {
        //获取请求
        //需要联网
        String url = "https://zh.wikipedia.org/wiki/"+keywords;
        System.out.println(url);
        //解析网页(Jsoup返回Document就是浏览器Document对象)
        Document document = Jsoup.parse(new URL(url),30000);

        //所有在js中可以使用的方法，这里都能用！
        Elements elements = document.getElementsByClass("lemma-summary");
        Info info = new Info();
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
