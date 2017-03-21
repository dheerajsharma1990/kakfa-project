package com.kafka.project.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class MobileDetailGrabber {


    private Map<String, String> grab(String phoneDetailsUrl) throws Exception {
        Map<String, String> map = new HashMap<>();
        Document doc = Jsoup.connect(phoneDetailsUrl).get();
        addMobileName(map, doc);
        addMobileFeatures(map, doc);
        return map;
    }

    private void addMobileFeatures(Map<String, String> map, Document doc) {
        List<Element> categoryNames = doc.body().select("#main-con").select("div.phoneCategoryName").stream().collect(Collectors.toList());
        List<Element> values = doc.body().select("#main-con").select("div.phoneCategoryValue").stream().collect(Collectors.toList());
        for (int i = 0; i < categoryNames.size(); i++) {
            Element value = values.get(i);
            String categoryName = categoryNames.get(i).text();
            Elements children = value.children();
            if (!value.ownText().equals("")) {
                map.put(categoryName, value.ownText());
            } else if (!value.select("span.tick").isEmpty()) {
                map.put(categoryName, "TICK");
            } else if (!value.select("span.cross").isEmpty()) {
                map.put(categoryName, "CROSS");
            } else if (!value.select("span.question").isEmpty() || !value.select("div.question").isEmpty()) {
                map.put(categoryName, "QUESTION");
            } else if (children.stream().anyMatch(e -> !e.ownText().equals(""))) {
                map.put(categoryName, children.stream().filter(e -> !e.ownText().equals("")).findFirst().get().ownText());
            } else {
                map.put(categoryName, "UNKNOWN");
            }
        }
    }

    private void addMobileName(Map<String, String> map, Document doc) {
        String name = doc
                .select("body")
                .select("#main-con")
                .select("div.row")
                .select("div.large-8.medium-8.small-12.columns")
                .select("div.row")
                .select("div#PhoneModelName")
                .select("h1").first().text();
        map.put("name", name);
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        MobileBrandsGrabber mobileBrandsGrabber = new MobileBrandsGrabber();
        MobilesGrabber mobilesGrabber = new MobilesGrabber();
        Map<String, String> brandUrls = mobileBrandsGrabber.getBrandUrls();
        Map<String, List<String>> allPhonesMap = new HashMap<>();
        for (Map.Entry<String, String> brandUrl : brandUrls.entrySet()) {
            List<String> allPhones = new ArrayList<>();
            mobilesGrabber.something(brandUrl.getValue(), allPhones);
            allPhonesMap.put(brandUrl.getKey(), allPhones);
        }
        List<Map<String, String>> allDetails = new ArrayList<>();
        List<Future<Map<String, String>>> futures = new ArrayList<>();
        for (List<String> allPhones : allPhonesMap.values()) {
            for (String phoneDetailUrl : allPhones) {
                String baseUrl = "http://www.gsmchoice.com";
                MobileDetailGrabber mobileDetailGrabber = new MobileDetailGrabber();
                futures.add(executorService.submit(() -> mobileDetailGrabber.grab(baseUrl + phoneDetailUrl)));
            }
        }
        for (Future<Map<String, String>> future : futures) {
            allDetails.add(future.get());
        }
        System.out.println(allDetails);
    }

}
