package MscInvisibleDemo;

/**
 * @ClassName mtest
 * @Author 吴俊淇
 * @Date 2020/4/20 12:52
 * @Version 1.0
 **/
public class mtest {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(() -> {
            MscTest.main(new String[0]);
            System.out.println("1子程序运行");

        });
        t.start();
        t.join();
        System.out.println(Thread.currentThread());

        System.out.println("主程序结束");
    }
}
