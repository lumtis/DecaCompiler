/**
 * Created by benjellt on 1/18/17.
 */
public class TestGrandFloat {
    public static void main(String[] args) {
        GrandFloat test = GrandFloat.additionSimple((float)3.123456723,(float)0.00234578123);
        System.out.println(test);
        test = GrandFloat.additionPrecise((float)3.123456723,(float)0.00234578123);
        System.out.println(test);
        GrandFloat pi = new GrandFloat((float)3.1415927,(float)-0.0000000464102068);
        System.out.println("pi " +pi);
        GrandFloat deuxpi= GrandFloat.additionGrandFloat(pi,pi);
        System.out.println( " 2*pi " + deuxpi);
    }
}
