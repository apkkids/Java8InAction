package lambdasinaction.chap5;

import java.io.File;
import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class BuildingStreams {

    public static void main(String...args) throws Exception{
        
        // Stream.of
        Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        // Stream.empty
        Stream<String> emptyStream = Stream.empty();

        // Arrays.stream
        int[] numbers = {2, 3, 5, 7, 11, 13};
        System.out.println(Arrays.stream(numbers).sum());

        System.out.println("=============iterate================");
        int result = Stream.iterate(0,n->n+3).limit(20).reduce(0,(x,y)->x+y);
        System.out.println(result);
        // Stream.iterate
        Stream.iterate(0, n -> n + 2)
              .limit(10)
              .forEach(System.out::println);

        // fibonnaci with iterate
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1],t[0] + t[1]})
              .limit(10)
              .forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));
        
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1],t[0] + t[1]})
              .limit(10)
              . map(t -> t[0])  
              .forEach(System.out::println);

        // random stream of doubles with Stream.generate
        Stream.generate(Math::random)
              .limit(10)
              .forEach(System.out::println);
 
        // stream of 1s with Stream.generate
        IntStream.generate(() -> 1)
                 .limit(5)
                 .forEach(System.out::println);

        IntStream.generate(new IntSupplier(){
            public int getAsInt(){
                return 2;
            }
        }).limit(5)
          .forEach(System.out::println);
   

        IntSupplier fib = new IntSupplier(){
                  private int previous = 0;
                  private int current = 1;
                  public int getAsInt(){
                      int nextValue = this.previous + this.current;
                      this.previous = this.current;
                      this.current = nextValue;
                      return this.previous;
                  }
              };
         IntStream.generate(fib).limit(10).forEach(System.out::println);

        File directory = new File("");//设定为当前文件夹
        String path1=directory.getAbsolutePath();//获取绝对路径

        Path path = Paths.get(path1+"/src/main/java/lambdasinaction/chap5/data.txt");
        System.out.println(path);
         long uniqueWords = Files.lines(path, Charset.defaultCharset())
                                 .flatMap(line -> Arrays.stream(line.split(" ")))
                                 .distinct()
                                 .count();

         System.out.println("There are " + uniqueWords + " unique words in data.txt");
    }
}
