package serialize;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package serialize
 * @Description:
 * @date 2018/10/14 上午10:54
 */
public class ArrayListSerializableDemo {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        List<String> stringList = new ArrayList<>();
        stringList.add("hello");
        stringList.add("world");
        stringList.add("ran");
        stringList.add("bo");
        System.out.println("init stringList: " + stringList);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("/Users/ranbo/IdeaProjects/JVM/tempDir/arrayListString"));
        objectOutputStream.writeObject(stringList);
        objectOutputStream.close();
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("/Users/ranbo/IdeaProjects/JVM/tempDir/arrayListString"));
        List<String> stringListRead = (List<String>) objectInputStream.readObject();
        objectInputStream.close();
        System.out.println("stringListRead: " + stringListRead);
    }

}
