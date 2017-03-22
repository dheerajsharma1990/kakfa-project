package com.kafka.project.grabber;

import com.kafka.project.domain.AllMobiles;
import com.kafka.project.domain.MobileRawData;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StringReaderWriter {

    public void writeInString(AllMobiles allMobiles) throws Exception {
        StringBuilder builder = new StringBuilder();
        for (MobileRawData mobileRawData : allMobiles.getAllMobiles()) {
            builder.append(mobileRawData.getName());
            builder.append("\n");
            for (Map.Entry<String, String> entry : mobileRawData.getAttributes().entrySet()) {
                builder.append(entry.getKey()).append("\n").append(entry.getValue()).append("\n");
            }
            builder.append("\n");
        }
        Files.write(Paths.get("stringData.txt"), builder.toString().getBytes());

    }

    public AllMobiles getAllMobiles() throws Exception {
        List<String> list = Files.readAllLines(Paths.get("stringData.txt"));
        List<MobileRawData> mobileRawDatas = new ArrayList<>();
        int i = 0;
        boolean start = true;
        String name = null;
        Map<String, String> attributes = new HashMap<>();
        while (i < list.size()) {
            if (list.get(i).equals("")) {
                MobileRawData mobileRawData = new MobileRawData(name, attributes);
                mobileRawDatas.add(mobileRawData);
                attributes = new HashMap<>();
                start = true;
            } else if (start) {
                name = list.get(i);
                start = false;
            } else {
                attributes.put(list.get(i), list.get(++i));
            }
            i++;
        }
        return new AllMobiles(mobileRawDatas);
    }

    public static void main(String[] args) throws Exception {
        StringReaderWriter stringWriter = new StringReaderWriter();
        ReadData readData = new ReadData();
        AllMobiles data = readData.getData();
        long startTime = System.currentTimeMillis();
        stringWriter.writeInString(data);
        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken to write file " + (endTime - startTime) + " millis.");
        AllMobiles allMobiles = stringWriter.getAllMobiles();
        startTime = System.currentTimeMillis();
        System.out.println("Time taken to read from file " + (startTime - endTime ) + " millis.");
    }

}
