package OOM;

/**
 * @author ranran
 * @version V1.0
 * @Title:
 * @Package OOM
 * @Description: windows上不要执行一旦执行系统假死
 * VM Args: -Xss2M
 * @date 2017/7/31 10:37
 */
public class JavaVMStackOOM {

    private void dontStop() {
        while(true) {

        }
    }

    public void stackLeakByThread() {
        while(true) {
            new Thread(() -> {
                dontStop();
            }).start();
        }
    }

//    public static void main(String[] args) {
//        JavaVMStackOOM oom = new JavaVMStackOOM();
//        oom.stackLeakByThread();
//    }

}
