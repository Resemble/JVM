package Java2;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Java2
 * @Description:
 * @date 2018/10/31 4:02 PM
 */
public class TestHelloService {

    private String username;

    public TestHelloService(String username) {
        this.username = username;
    }

    void say(HelloService helloService) {
        helloService.sayHello(this.username);
    }

    static void print(String username) {
        System.out.println("Hello, " + username + "!");
    }

    void println(String username) {
        this.username = "ranbo";
        System.out.println("Hello, " + username + "!");
    }

    public static void main(String[] args) {
        TestHelloService testHelloService = new TestHelloService("ranbo02");
        testHelloService.say(username1 -> System.out.println(username1 + " 哈哈"));
        testHelloService.say(TestHelloService::print);
        testHelloService.say(testHelloService::println);
    }
}
