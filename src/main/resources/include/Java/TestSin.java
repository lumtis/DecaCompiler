/**
 * Created by buthodgt on 1/21/17.
 */
import java.lang.Math;


public class TestSin {
    public static void main(String[] args) {
        Mathe m = new Mathe();
        float v1 = m.pi();
        float v2 = (float) 0.45485;
        float v3 = (float) 0.00006748;
        System.out.println("------------Début du test de sinus---------");
        System.out.println("Valeur testée :" + v1);
        System.out.println("Notre résultat :" + m.sin(v1));
        System.out.println("Résultat Java :" + Math.sin(v1));
        System.out.println("Résultat attendu : 6.53589793e-7");
        System.out.println("Valeur testée :" + v2);
        System.out.println("Notre résultat :" + m.sin(v2));
        System.out.println("Résultat Java :" + Math.sin(v2));
        System.out.println("Résultat attendu : 0.4393275697");
        System.out.println("Valeur testée :" + v3);
        System.out.println("Notre résultat :" + m.sin(v3));
        System.out.println("Résultat Java :" + Math.sin(v3));
        System.out.println("Résultat attendu : 6.747999995e-5");
    }
}
