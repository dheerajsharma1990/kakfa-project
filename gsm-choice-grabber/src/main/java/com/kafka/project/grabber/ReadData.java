package com.kafka.project.grabber;

import com.kafka.project.domain.AllMobiles;
import com.kafka.project.domain.MobileRawData;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReadData {

    private static final int bufferSize = 2048;

    public AllMobiles getData() throws Exception {
        Schema<AllMobiles> schema = RuntimeSchema.getSchema(AllMobiles.class);
        Schema<MobileRawData> mobileRawDataSchema = RuntimeSchema.getSchema(MobileRawData.class);
        AllMobiles allMobiles = schema.newMessage();
        byte[] bytes = Files.readAllBytes(Paths.get("mobileProto.data"));
        ProtostuffIOUtil.mergeFrom(bytes, allMobiles, schema);

        List<byte[]> byteObjects = getByteObjects(allMobiles);

        long startTime = System.currentTimeMillis();
        List<MobileRawData> mobileRawDatas = new ArrayList<>();
        for (byte[] b : byteObjects) {
            MobileRawData mobileRawData = mobileRawDataSchema.newMessage();
            ProtostuffIOUtil.mergeFrom(b, mobileRawData, mobileRawDataSchema);
            mobileRawDatas.add(mobileRawData);
        }
        long endTime = System.currentTimeMillis();
        long diff = endTime - startTime;
        System.out.println("Time taken to individually deserialize objects " + diff);
        return allMobiles;
    }


    private List<byte[]> getByteObjects(AllMobiles allMobiles) {
        List<MobileRawData> mobileRawDatas = allMobiles.getAllMobiles();
        Schema schema = RuntimeSchema.getSchema(MobileRawData.class);

        List<byte[]> bytes = new ArrayList<>();
        for (MobileRawData mobileRawData : mobileRawDatas) {
            try {
                bytes.add(ProtostuffIOUtil.toByteArray(mobileRawData, schema,
                        getApplicationBuffer()));
            } finally {
                getApplicationBuffer().clear();
            }
        }
        return bytes;
    }

    private static LinkedBuffer getApplicationBuffer() {
        return localBuffer.get();
    }

    private static final ThreadLocal<LinkedBuffer> localBuffer = new ThreadLocal<LinkedBuffer>() {
        public LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(bufferSize);
        }
    };


    public static void main(String[] args) throws Exception {
        ReadData readData = new ReadData();
        AllMobiles data = readData.getData();
        System.out.println();
    }

}
