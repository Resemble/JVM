package Thread;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author ranbo
 * @version V1.0
 * @Title:
 * @Package Thread
 * @Description:
 * @date 2018/8/15 下午9:38
 */
public class TimerTest {

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        Timer timer = new Timer();
//        timer.scheduleAtFixedRate(new MyTask(), calendar.getTime(), 1000);
        timer.schedule(new MyTask(), calendar.getTime(), 1000);
    }
}

class MyTask extends TimerTask {

    @Override public void run() {
        System.out.println("execute time start is : " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(this.scheduledExecutionTime()));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
