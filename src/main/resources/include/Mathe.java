// Grenoble INP - Ensimag projet GL -*- mode: java -*-
// Library for class Math of Deca, coded in Deca

public class Mathe {

    
    


    // fonction sinus première solution
    public static float sin_ser(float f) {
        int k=0;
        float solution=0;
        while (k < 6) {
            solution+= Mathe.pow((float)-1,k)* Mathe.pow(f,2*k+1)/Mathe.factoriel(2*k+1);
            k+=1;
        }
        return solution;
    }

    // fonction sinus cornic
    public static float sin(float f) {
        float b;
        b=(float)0.60725294;
        return b*aux_sin(f,10);

    }

    //fonction aux sin

    public static float aux_sin(float f, int n ){

        if (n ==0) {
            return 0;
        }
        float prec=aux_trigo(f,n-1);
        if ( prec < 0){
            return aux_sin(f,n-1) - aux_cos(f,n-1)*Mathe.pow(2,-(n-1));
        }
        if (prec ==0){
            return aux_sin(f,n-1);
        }
        return aux_sin(f,n-1) + aux_cos(f,n-1)*Mathe.pow(2,-(n-1));
    }

    public static float aux_cos(float f, int n) {
        
        if (n ==0) {
            return 1;
        }
        float prec = aux_trigo(f,n-1);
        if ( prec < 0){
            return aux_cos(f,n-1) + aux_sin(f,n-1)*Mathe.pow(2,-(n-1));
        }
        if ( prec ==0){
            return aux_cos(f,n-1);
        }
        return aux_cos(f,n-1) - aux_sin(f,n-1)*Mathe.pow(2,-(n-1));
    }



    public static float aux_trigo(float f, int n) {
        if (n ==0){
            return f;
        }
        float prec = Mathe.aux_trigo(f,n-1);
        if (prec < 0){
            return prec + Mathe.atan(Mathe.pow(2,-(n-1)));
        }
        if (prec==0) {
            return prec;
        }
        return prec - Mathe.atan(Mathe.pow(2,-(n-1)));
    }





    // fonction cosinus
    public static float cos(float f) {
        float b;
        b=(float)0.60725294;
        return b*aux_cos(f,10);

    }


    // fonction arcsin
    public float asin(float f) {
        System.out.println("asin(f) not yet implemented");
        return f;
    }


    // fonction arctan
    public static float atan(float f) {
        if (f>1 || f<-1 ) {
            throw new IllegalArgumentException("hmar c'est arctan là quesque tu me fais même lucas est meilleur que toi");
        }
        if (f == 1) {
            return (float)0.78539816339;
        }
        if(f==-1){
            return (float)-0.78539816339;
        }
        int k=0;
        float solution=0;
        while (k < 1000) {
            solution+= Mathe.pow((float)-1,k)* Mathe.pow(f,2*k+1)/(2*k+1);
            k+=1;
        }
        return solution;
    }



    // calcul d
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




    // fonction factoriel
    public static int factoriel(int n){
        int a = 1;
        while (n>1){
            a*=n;
            n-=1;
        }
        return a;
    }
}



// End of Deca Math library
