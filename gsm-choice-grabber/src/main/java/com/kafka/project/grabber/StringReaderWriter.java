package com.kafka.project.grabber;

import com.kafka.project.domain.AllMobiles;
import com.kafka.project.domain.MobileRawData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

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
        byte[] bytes = builder.toString().getBytes();
        long startTime = System.currentTimeMillis();
        byte[] compress = compress(bytes);
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to compress bytes " + (endTime - startTime) + " millis.");
        Files.write(Paths.get("stringData.txt"), compress);
        startTime = System.currentTimeMillis();
        System.out.println("Time taken to write to file " + (startTime - endTime) + " millis.");
    }

    private byte[] compress(byte[] data) throws IOException {
        Deflater deflater = new Deflater();
        deflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        deflater.finish();
        byte[] buffer = new byte[1024];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer); // returns the generated code... index
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        System.out.println("Original: " + data.length);
        System.out.println("Compressed: " + output.length);
        return output;
    }

    public AllMobiles getAllMobiles() throws Exception {
        long startTime = System.currentTimeMillis();
        byte[] bytes = Files.readAllBytes(Paths.get("stringData.txt"));
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken to read file while deserialization " + (endTime - startTime) + " millis.");
        byte[] decompress = decompress(bytes);
        startTime = System.currentTimeMillis();
        System.out.println("Time taken to decompress bytes " + (startTime - endTime) + " millis.");
        String string = new String(decompress);
        List<String> list = Arrays.stream(string.split("\n")).collect(Collectors.toList());
        endTime = System.currentTimeMillis();
        System.out.println("Time taken to deserialize " + (endTime - startTime) + " millis. ");
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

    private static byte[] decompress(byte[] data) throws IOException, DataFormatException {
        Inflater inflater = new Inflater();
        inflater.setInput(data);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[1024];
        while (!inflater.finished()) {
            int count = inflater.inflate(buffer);
            outputStream.write(buffer, 0, count);
        }
        outputStream.close();
        byte[] output = outputStream.toByteArray();
        System.out.println("Original: " + data.length);
        System.out.println("De Compressed: " + output.length);
        return output;
    }

    public static void main(String[] args) throws Exception {
        StringReaderWriter stringWriter = new StringReaderWriter();
        ReadData readData = new ReadData();
        AllMobiles data = readData.getData();
        stringWriter.writeInString(data);
        AllMobiles allMobiles = stringWriter.getAllMobiles();
        System.out.println();
    }

}
