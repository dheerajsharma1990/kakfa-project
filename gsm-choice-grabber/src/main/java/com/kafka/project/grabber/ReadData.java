package com.kafka.project.grabber;

import com.kafka.project.gsm.domain.RawMobileData;
import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class ReadData {

    private static final Schema<RawMobileData> rawMobileDataSchema = RuntimeSchema.getSchema(RawMobileData.class);
    private static final int bufferSize = 2048;

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
        List<byte[]> protoBytes = new ArrayList<>();
        for (RawMobileData rawMobileData : data) {
            protoBytes.add(ProtostuffIOUtil.toByteArray(rawMobileData, rawMobileDataSchema, getApplicationBuffer()));
            getApplicationBuffer().clear();
        }

        long startTime = System.currentTimeMillis();
        List<byte[]> bytes = protoBytes.stream().map(b -> compress(b)).collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        System.out.println("Time to compress " + (endTime - startTime) + " millis.");

        startTime = System.currentTimeMillis();
        List<byte[]> uncompressed = bytes.stream().map(b -> decompress(b)).collect(Collectors.toList());
        endTime = System.currentTimeMillis();
        System.out.println("Time to decompress " + (endTime - startTime) + " millis.");

    }

    private static byte[] decompress(byte[] data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            IOUtils.copy(new GZIPInputStream(new ByteArrayInputStream(data)), out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return out.toByteArray();
    }

    private static byte[] compress(byte[] content) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gzipOutputStream.write(content);
            gzipOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return byteArrayOutputStream.toByteArray();
    }

    private static LinkedBuffer getApplicationBuffer() {
        return localBuffer.get();
    }

    private static final ThreadLocal<LinkedBuffer> localBuffer = new ThreadLocal<LinkedBuffer>() {
        public LinkedBuffer initialValue() {
            return LinkedBuffer.allocate(bufferSize);
        }
    };

}
