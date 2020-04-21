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
            System.out.println("子程序运行中");
            System.out.println();
            System.out.println("测试");
            System.out.println("测试");
        });
        t.start();
        t.join();
        System.out.println(Thread.currentThread());

        System.out.println("主程序结束");
    }
}
