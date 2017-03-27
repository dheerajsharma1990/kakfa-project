package com.kafka.project.grabber;

import com.kafka.project.gsm.domain.RawMobileData;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ReadData {

    class AllMobiles implements Serializable {

        private List<RawMobileData> rawMobileData = new ArrayList<>();

        List<RawMobileData> getRawMobileData() {
            return rawMobileData;
        }
    }

    public List<RawMobileData> getData() throws Exception {
        Schema<AllMobiles> schema = RuntimeSchema.getSchema(AllMobiles.class);

        AllMobiles allMobiles = schema.newMessage();
        long startTime = System.currentTimeMillis();
        byte[] bytes = Files.readAllBytes(Paths.get(ReadData.class.getClassLoader().getResource("mobileProto.data").toURI()));
        long endTime = System.currentTimeMillis();
        long diff = endTime - startTime;
        ProtostuffIOUtil.mergeFrom(bytes, allMobiles, schema);
        return allMobiles.getRawMobileData();
    }


    public static void main(String[] args) throws Exception {
        ReadData readData = new ReadData();
        List<RawMobileData> data = readData.getData();
        RawMobileData rawMobileData = data.get(0);

        System.out.println();
    }

    private static byte[] decompressGzip(byte[] data) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();
            //close resources
            byteArrayOutputStream.close();
            gzipInputStream.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] compressGzip(byte[] data) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(data);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            //close resources
            gzipOutputStream.close();
            byteArrayOutputStream.close();
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

}
