package serialize;



import java.io.*;
import java.util.Date;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package serialize
 * @Description:
 * @date 2018/10/13 下午9:40
 */
public class SerializableDemo {

    public static void main(String[] args) {
        User user = new User();
        user.setName("Resemble");
        user.setGender("male");
        user.setAge(23);
        user.setBirthday(new Date());
        System.out.println(user);

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("/Users/ranbo/IdeaProjects/JVM/tempDir/tempFile.txt"));
            objectOutputStream.writeObject(user);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File file = new File("/Users/ranbo/IdeaProjects/JVM/tempDir/tempFile.txt");
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));
            User userRead = (User) objectInputStream.readObject();
            System.out.println(userRead);
            objectInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
