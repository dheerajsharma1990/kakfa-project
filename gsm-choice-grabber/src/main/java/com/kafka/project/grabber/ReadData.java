package com.kafka.project.grabber;

import com.kafka.project.gsm.domain.RawMobileData;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

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
        System.out.println();
    }

}
