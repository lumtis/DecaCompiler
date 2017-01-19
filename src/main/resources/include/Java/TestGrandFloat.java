/**
 * Created by benjellt on 1/18/17.
 */
public class TestGrandFloat {
    public static void main(String[] args) {
        GrandFloat test = GrandFloat.additionSimple((float)3.123456723,(float)0.00234578123);
        System.out.println(test);
        test = GrandFloat.additionSimple((float)3.123456723,(float)0.00234578123);
        System.out.println(test);
        GrandFloat pi = new GrandFloat((float)3.141592,(float)0.000000653589793);
        GrandFloat pisur4 = new GrandFloat((float)0.7853981,(float)0.00000006339);
        System.out.println("pi " +pi);
        GrandFloat zebi= GrandFloat.additionGrandFloat(pi,pisur4);
        System.out.println( " 5 pi / 4  " + zebi);
       // GrandFloat troispi= GrandFloat.additionGrandFloat(zebi,pi);
        //System.out.println( " 3*pi " + troispi);
        //float troispis = 3*Mathe.pi();
        //System.out.println("mauvais 3*pi = " + troispis);
    }
}
