package OOM;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package OOM
 * VM Args: -Xss128k
 * @Description:
 * @date 2017/7/31 9:57
 */
public class JavaVMStackSOF {

    private int stackLength = 1;

    public void stackLeak() {
        stackLength++;
        stackLeak();
    }

    public static void main(String[] args) throws Throwable{

        JavaVMStackSOF oom = new JavaVMStackSOF();
        try {
            oom.stackLeak();
        } catch (Throwable e) {
            System.out.println("stack length:" + oom.stackLength);
            throw e;
        }

    }

}
