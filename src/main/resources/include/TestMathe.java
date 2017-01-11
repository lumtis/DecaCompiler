public class TestMathe {

    public static void main(String[] args) {
        // premier test, vérification de la bonne compréhenstion de la représentation binaire
        float f= (float) 2.25;
        float g= (float) 5.25;
        float h;
        h = f + g;
        float erreur = (float) 7.5;
        // on vérifie que 5,25 ( 101,01 en binaire) + 2.25 ( 10,01 en binaire ) ne génère pas d'erreur
        erreur = (h- erreur)/h;
        if (erreur==0) {
            System.out.println( " représentation binaire : pass ");
        }
        else {
            System.out.println( " représentation binaire : not pass");
        }





        // test de la puissance Dans le cas de nombre ne générant pas d'erreur en binaire
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
        if (puissance  == (float) 610.35156 ) {
            System.out.println(" puissance3 : pass ");
        }
        else {
            System.out.println("puissance3 : not pass");
        }
        // test d'écart de puissance ( on vérifie que l'erreur est inférieur à ulp(x)
        puissance = Mathe.pow((float)2.1,2);
        if ( ( (float) 4.41 - puissance) <= (float) 0.000000476837158 ) {
            System.out.println(" écart puissance : pass ");
        }
        else {
            System.out.println( " écart puissance : not pass");
            float erreurlucas= - puissance + (float) 4.41;
            System.out.println("en effet"+ erreurlucas +" est supérieur à " + (float)0.000000476837158);
        }
        // test puissance négative
        puissance = Mathe.pow((float)2.0, -2);
        if ( puissance == (float)0.25) {
            System.out.println(" puissance négative 1 : Pass ");
        }
        else {
            System.out.println(" puissance négative 1 : not pass");
        }
        // test écart puissance négatif
        puissance = Mathe.pow((float)5,-2);
        if ((float)0.04- puissance ==(float) -0.0000000037252903){
             System.out.println(" puissance négative 2 : Pass ");
        }
        else{
            System.out.println(" puissance négative 2 : not Pass ");
            float erreurlucas= - puissance + (float) 0.04;
            System.out.println("en effet"+ erreurlucas +" est supérieur à " + (float)0.0000000037252903 );
        }




        // test exposant
        int exposant = Mathe.exposant((float) 3.8) ;
        if ( exposant == 1) {
            System.out.println("exposant 1: pass");
        }
        else {
            System.out.println("exposant 1: pass");
        }
        exposant = Mathe.exposant((float) 0.000052) ;
        if ( exposant == -15) {
            System.out.println("exposant 2: pass");
        }
        else {
            System.out.println("exposant 2: not pass");
        }
        exposant = Mathe.exposant((float) -1000) ;
        if ( exposant == 9) {
            System.out.println("exposant 3: pass");
        }
        else {
            System.out.println("exposant 3: not pass");
        }











        //test ulp
        float ulp =Mathe.ulp((float)1);
        if ( ulp == Mathe.pow( (float)2, -23) ){
            System.out.println("ulp 1: pass");
        }
        else{
            System.out.println("ulp 1: not pass");

        }

        ulp = Mathe.ulp( Mathe.pow(( float)2,-23));
        if ( ulp == Mathe.pow( (float)2, -46) ){
            System.out.println("ulp 2: pass");
        }
        else {
            System.out.println("ulp 2: not pass");
        }

        ulp = Mathe.ulp((float)10000000);
        if ( ulp == 1 ){
            System.out.println("ulp 3: pass");
        }
        else {
            System.out.println("ulp 3: not pass");
        }
        ulp = Mathe.ulp( Mathe.pow((float)2,30));
        if ( ulp == 128 ){
            System.out.println("ulp 4: pass");
        }
        else {
            System.out.println("ulp 4: not pass");
        }











        // test factoriel
        int fact =Mathe.factoriel(5);
        System.out.println("factoriel "+ fact );
        if (fact==120){
            System.out.println("factoriel : Pass");
        }
        else {
            System.out.println("factoriel : not pass");
        }



        //float sin1 =Mathe.sin((float)0);
        //System.out.println(" sin 0 =" + sin1);
        float sin2= Mathe.sin_ser((float)0.52360);
        System.out.println( "Sin pi/6= " + sin2);
        //float erreursin = (float)0.5 - sin2;
        //System.out.println( " lucas gros hmar " + erreursin);

        float tan= Mathe.atan((float)0.5);
        System.out.println("tan 0.5=" + tan);
        float erreurtan= tan  - (float)0.46364761;
        ulp =Mathe.ulp((float)0.46364761);
        //System.out.println("ulp"+ ulp);
        //System.out.println(" erreur"+ erreurtan);
        if ( erreurtan <= Mathe.ulp((float)0.46364761)){
            System.out.println("bsa7tek artan pass");
        }
        else {
            System.out.println("artan not pass");
        }



    }
}