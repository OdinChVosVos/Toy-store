package ru.ds.education.testspringboot.api.job;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ParseTask {


    public String parseProducts(String category){
        try {
            String url = String.format("https://www.detmir.ru/catalog/index/name/%s/", category);
            url = "https://www.detmir.ru/catalog/index/name/mashiny/";
            Document doc = Jsoup.connect(url)
                    .userAgent("Yandex")
                    .get();
            System.out.println(doc.toString());
//            Elements products = doc.getElementsByClass("tl to tp");
            Elements products = doc.select("h4.sU");
//            Elements products = doc.select(".sU");
            StringBuilder res = new StringBuilder();
            for (Element el : products){
                res.append(el.ownText());
                System.out.println("ve");
                System.out.println(el.ownText());
            }
            return res.toString();

        } catch (IOException e) {
            System.out.println("dfjevjkrf");
        }
        return "er";
    }
}
