// Grenoble INP - Ensimag projet GL -*- mode: java -*-
// Library for class Math of Deca, coded in Deca

public class Mathe {
    public float sin(float f) {
        System.out.println("sin(f) not yet implemented");
        return f;
    }
    public float cos(float f) {
        System.out.println("cos(f) not yet implemented");
        return f;
    }
    public float asin(float f) {
        System.out.println("asin(f) not yet implemented");
        return f;
    }
    public float atan(float f) {
        System.out.println("atan(f) not yet implemented");
        return f;
    }
    public static float ulp(float f) {

        return Mathe.pow((float)2,Mathe.exposant(f)) * Mathe.pow((float)2,-23);
    }


    // calcul l'exposant de la représentaion en binaire 32
    public static int exposant(float f){
        if (f < 0) {
            return exposant(-f);
        }
        if (f==0) {
            return 0;
        }
        int exp = 0;
        if (f > 1) {
            int f1= (int) f;
            while ( f1> 1 ){
                exp+=1;
                f1/=2;
            }
            return exp;
        }
        if (f <1 ){
            while( f< 1){
                exp-=1;
                f*=2;
            }
            return exp;
        }
        return exp;
    }

    // calcul des puissance entière de floatants
    public static float pow(float f, int n) {
        if (n >= 0) {
            if (n==0) return (float)1;
            float tmp = pow(f,n/2);
            if ( n%2 == 0 ) {
                return tmp*tmp;
            }
            return f*tmp*tmp;
        }
        else {
            if (n == -1){
                return (float)1/f;
            }
            float tmp = pow(f,n/2);
            if ( n%2 == 0 ) {
                return tmp*tmp;
            }
            return tmp*tmp/f;
        }

        }
}



// End of Deca Math library
