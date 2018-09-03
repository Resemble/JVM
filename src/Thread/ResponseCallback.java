package Thread;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2018/7/30 下午11:24
 */
public class ResponseCallback {
    public String call(String o) {
        System.out.println("ResponseCallback：处理完成，返回：" + o);
        return o;
    }
}
