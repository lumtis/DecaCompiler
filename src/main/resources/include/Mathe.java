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
    public float ulp(float f) {
        System.out.println("ulp(f) not yet implemented");
        return f;
    }
    public static float pow(float f, int n) {
        if (n==1) return f;
        float tmp = pow(f,n/2);
        if ( n%2 == 0 ) {
            return tmp*tmp;
            }
        return f*tmp*tmp;
        }
}



// End of Deca Math library
