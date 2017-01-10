public class TestMathe {

    public static void main(String[] args) {
        // premier test, vérification de la bonne compréhenstion de la représentation binaire
        float f= (float) 2.25;
        float g= (float) 5.25;
        float h;
        h = f + g;
        float erreur = (float) 7.5;
        // on vérifie que 5,25 ( 101,01 en binaire) + 2.25 ( 10,01 en binaire ) ne génère pas d'erreur
        System.out.println( "h = " + erreur );
        erreur = (h- erreur)/h;
        if (erreur==0) {
            System.out.println( " représentation binaire : pass ");
        }
        else {
            System.out.println( " représentation binaire : not pass");
        }


        // test de la puissance
        float puissance = Mathe.pow((float) 2.5,2);
        if (puissance == 6.25) {
            System.out.println(" puissance1 : pass ");
        }
        else {
            System.out.println( "puissance1 : not pass");
        }
        puissance = Mathe.pow((float)2.5, 5);
        if (puissance == 97.65625) {
            System.out.println(" puissance2 : pass ");
        }
        else {
            System.out.println( "puissance2 : not pass");
        }

        puissance = Mathe.pow((float)2.5, 7);
        System.out.println( " puissance3 = " + puissance );
        float erreur2 = puissance - (float) 610.35156;
        System.out.println( " erreur = " + erreur2);
        if (puissance  == (float) 610.35156 ) {
            System.out.println(" puissance3 : pass ");
        }
        else {
            System.out.println("puissance3 : not pass");
        }
    }
}