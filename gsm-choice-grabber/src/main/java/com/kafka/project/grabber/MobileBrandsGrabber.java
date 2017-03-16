package com.kafka.project.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Map;
import java.util.stream.Collectors;

public class MobileBrandsGrabber {

    public Map<String, String> getBrandUrls() throws Exception {
        Document doc = Jsoup.connect("http://www.gsmchoice.com/en/catalogue/").get();
        return doc.body().select("#CatalogueBrands").select("a").stream()
                .collect(Collectors.toMap(element -> element.select("span.BrandName").text(), element -> element.attr("href")));

    }

}
