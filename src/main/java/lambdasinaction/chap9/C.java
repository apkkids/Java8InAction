package lambdasinaction.chap9;

interface A {
    default void hello(){
        System.out.println("Hello from A");
    }
}
interface B extends A{
    default void hello(){
        System.out.println("Hello from B");
    }
}

public class C implements A, B {
    public static void main(String[] args) {
        new C().hello();
    }
}
