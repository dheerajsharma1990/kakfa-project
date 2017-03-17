package com.kafka.project.grabber;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class MobileDetailGrabber {


    private Map<String, String> grab() throws Exception {
        Map<String, String> map = new HashMap<>();
        Document doc = Jsoup.connect("http://www.gsmchoice.com/en/catalogue/gionee/a1plus/Gionee-A1-Plus.html").get();
        addMobileName(map, doc);
        addMobileFeatures(map, doc);
        return map;
    }

    private void addMobileFeatures(Map<String, String> map, Document doc) {
        Map<String, String> subCategories = new HashMap<>();
        Elements children = doc.body().getElementById("main-con").select("div.row").first().select("div.large-8.medium-8.small-12.columns").last().children();

        Elements select = doc
                .select("body")
                .select("#main-con")
                .select("div")
                .select("div")
                .select("div.row")
                .select("div.large-8.medium-8.small-12.columns");
        System.out.println();
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
        MobileDetailGrabber mobileDetailGrabber = new MobileDetailGrabber();
        Map<String, String> map = mobileDetailGrabber.grab();
        System.out.println(map);
    }

}
