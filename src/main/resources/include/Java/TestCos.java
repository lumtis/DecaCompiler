/**
 * Created by buthodgt on 1/21/17.
 */
import java.lang.Math;


public class TestCos {
    public static void main(String[] args) {
        Mathe m = new Mathe();
        float v1 = (float) 3.1415;
        float v2 = (float) 0.45485;
        float v3 = (float) 0.00006748;
        float v4 = (float) 1.5708;
        System.out.println("------------Début du test de cosinus---------");
        System.out.println("Valeur testée :" + v1);
        System.out.println("Notre résultat :" + m.cos(v1));
        System.out.println("Résultat Java :" + Math.cos(v1));
        System.out.println("Résultat attendu : -0.9999999957");
        System.out.println("Valeur testée :" + v2);
        System.out.println("Notre résultat :" + m.cos(v2));
        System.out.println("Résultat Java :" + Math.cos(v2));
        System.out.println("Résultat attendu : 0.8983269374");
        System.out.println("Valeur testée :" + v3);
        System.out.println("Notre résultat :" + m.cos(v3));
        System.out.println("Résultat Java :" + Math.cos(v3));
        System.out.println("Résultat attendu : 0.9999999977");
        System.out.println("Valeur testée :" + v4);
        System.out.println("Notre résultat :" + m.cos(v4));
        System.out.println("Résultat Java :" + Math.cos(v4));
        System.out.println("Résultat attendu : -3.6732051e-6");
    }
}
