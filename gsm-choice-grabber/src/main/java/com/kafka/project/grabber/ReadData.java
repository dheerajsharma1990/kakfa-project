package com.kafka.project.grabber;

import com.kafka.project.domain.AllMobiles;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ReadData {

    public static void main(String[] args) throws Exception {
        Schema<AllMobiles> schema = RuntimeSchema.getSchema(AllMobiles.class);
        AllMobiles allMobiles = schema.newMessage();
        System.out.println(System.currentTimeMillis());
        byte[] bytes = Files.readAllBytes(Paths.get("mobileProto.data"));
        System.out.println(System.currentTimeMillis());
        ProtostuffIOUtil.mergeFrom(bytes, allMobiles, schema);
        System.out.println(System.currentTimeMillis());
        System.out.println();
    }
}
