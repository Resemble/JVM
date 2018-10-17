package serialize;

import java.io.*;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package serialize
 * @Description:
 * @date 2018/10/16 下午10:31
 */
public class OperatePerson {

    public void serializable(Person person) {
        try {
            File file = new File("/Users/ranbo/IdeaProjects/JVM/tempDir/personFile.txt");
            if(file.exists()) {
                file.delete();
            }
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("/Users/ranbo/IdeaProjects/JVM/tempDir/personFile.txt"));
            objectOutputStream.writeObject(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Person deSerializable() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(
            new FileInputStream("/Users/ranbo/IdeaProjects/JVM/tempDir/personFile.txt"));
        return (Person) objectInputStream.readObject();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        OperatePerson operatePerson = new OperatePerson();
        Person person = new Person("ranran", "123", 23);
        System.out.println("序列化之前的相关数据如下：\n" + person.toString());
        operatePerson.serializable(person);
        Person newPerson = operatePerson.deSerializable();
        System.out.println("-------------------------");
        System.out.println("序列化之后的相关数据如下：\n" + newPerson);
    }

}
