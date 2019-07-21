package lambdasinaction.chap8;

/**
 * @Description TODO
 * @Author alex
 * @Date 2019/7/21 20:45
 */
public class SomeExampleChapter8 {
    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello in r1");
            }
        };
        Runnable r2 = () -> System.out.println("Hello in r2");
        new Thread(r1).start();
        new Thread(r2).start();

        //编译错误
        int a = 0;
        Runnable r3 = ()->{
//            int a = 6;
            System.out.println("Hello in r3, a="+a);
        };

    }
}
