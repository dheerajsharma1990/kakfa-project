package com.kafka.project.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class MobilesGrabber {

    //doc.body().select("#main-con").select("div.large-12.medium-12.small-12.columns").select("#NextPrevSelector").select("ul.pagination").first().select("li").select("a.1NPSa")
    public static void main(String[] args) throws Exception {
        String baseUrl = "http://www.gsmchoice.com";

        String brandUrl = "/en/catalogue/samsung";

        Set<String> allUrls = new HashSet<>();
        while (true) {
            Document doc = Jsoup.connect("http://www.gsmchoice.com" + baseUrl).get();
            Element first = doc.body().select("#main-con").select("#NextPrevSelector").first().select("ul.pagination").first();
            if (first == null) {
                break;
            }
            Elements elements = first.select("a.1NPSa");
            Set<String> sets = elements.stream().map(element -> element.attr("href")).collect(Collectors.toSet());


        }


        System.out.println();
    }

}
