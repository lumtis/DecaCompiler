/**
 * Created by benjellt on 1/19/17.
 */
public class TestMultiplicationGF {
    public static void main(String[] args) {
        GrandFloat r = GrandFloat.multiplicationSimple((float)0.12345,(float)0.000001414);
        float resulatnormal = (float)0.12345 * (float)0.000001414;
        float resulatamélioré = r.f + r.erreur;
        System.out.println(resulatnormal);
        System.out.println(resulatamélioré);

    }
}
