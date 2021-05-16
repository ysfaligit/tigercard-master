import java.sql.Time;
import java.util.Date;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        long l = new Date().getTime();
        Time newTrip = new Time(l);
        Time t = new Time(l);
        Thread.sleep(4000);
        Time t1 = new Time(new Date().getTime());

        System.out.println(newTrip.equals(t));
        System.out.println(newTrip.after(t));

//        System.out.println(t.after(t1));
//        System.out.println(t.before(t1));
    }
}
