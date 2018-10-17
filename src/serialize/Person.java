package serialize;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package serialize
 * @Description:
 * @date 2018/10/16 下午10:17
 */
public class Person implements Externalizable {

    private static final long serialVersionUID = 1L;

    String userName;
    String password;
    int age;

    public Person(String userName, String password, int age) {
        super();
        this.userName = userName;
        this.password = password;
        this.age = age;
    }

    public Person() {
        super();
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }




    @Override public void writeExternal(ObjectOutput out) throws IOException {
        // 增加一个新的对象
        Date date = new Date();
        out.writeObject(userName);
        out.writeObject(age);
        out.writeObject(date);
    }

    @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        userName = (String) in.readObject();
        age = (int) in.readObject();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = (Date) in.readObject();
        System.out.println("反序列化的日期为：" + simpleDateFormat.format(date));
    }

    @Override public String toString() {
        return "Person{" + "userName='" + userName + '\'' + ", password='" + password + '\''
            + ", age=" + age + '}';
    }
}
