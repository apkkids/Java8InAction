package lambdasinaction.chap6;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description TODO
 * @Author alex
 * @Date 2019/7/14 20:23
 */
public class MyCollect {
    public static void main(String[] args) {
        Random r = new Random(System.nanoTime());
        //产生100个随机数，将其中大于50的收集到list中
        List<Integer> list = IntStream.iterate(0, (i) -> r.nextInt(100))
                .limit(100)
                .boxed()
                .filter(i -> i > 50)
                .collect(Collectors.toList());
        list.stream().forEach(System.out::println);
    }
}
