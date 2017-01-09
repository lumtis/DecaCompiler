public class TestMathe {

    public static void main(String[] args) {
        // double d = 2,44444444;
        float f= (float) 2.25;
        // d= 5.444444444;
        float g= (float) 5.25;
        float h;
        h = f + g;
        float erreur = (float) 7.5;
        System.out.println( "h = " + erreur );
        erreur = (h- erreur)/h;
        System.out.println( " h calculé= " + h);
        System.out.println(" erreur = " + erreur);
    }
}