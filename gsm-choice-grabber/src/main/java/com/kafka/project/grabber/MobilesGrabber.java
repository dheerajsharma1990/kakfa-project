package com.kafka.project.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MobilesGrabber {

    private static final String baseUrl = "http://www.gsmchoice.com";

    public void something(String url, List<String> phoneUrls) throws Exception {
        Document document = Jsoup.connect(baseUrl + url).get();
        Elements elements = document.body().select("#main-con").select("li.phone-small-box-list").select("a");
        phoneUrls.addAll(elements.stream().map(element -> element.attr("href")).collect(Collectors.toList()));
        Element nextPreviousSelector = document.body().select("#main-con").select("#NextPrevSelector").first();
        Element pagination = nextPreviousSelector.select("ul.pagination").first();
        if (pagination != null) {
            Element lastLi = pagination.select("li").last();
            if (lastLi.className().equals("arrow")) {
                something(lastLi.select("a").first().attr("href"), phoneUrls);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        MobileBrandsGrabber mobileBrandsGrabber = new MobileBrandsGrabber();
        MobilesGrabber mobilesGrabber = new MobilesGrabber();
        Map<String, String> brandUrls = mobileBrandsGrabber.getBrandUrls();
        Map<String, List<String>> allPhonesMap = new HashMap<>();
        for (Map.Entry<String, String> brandUrl : brandUrls.entrySet()) {
            List<String> allPhones = new ArrayList<>();
            mobilesGrabber.something(brandUrl.getValue(), allPhones);
            allPhonesMap.put(brandUrl.getKey(), allPhones);
        }
        System.out.println();
    }


}
