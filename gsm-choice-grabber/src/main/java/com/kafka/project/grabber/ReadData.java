package com.kafka.project.grabber;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ReadData {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("mobile.data");
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        Object object = objectInputStream.readObject();
        objectInputStream.close();
        fileInputStream.close();
    }
}
