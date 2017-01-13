


// Grenoble INP - Ensimag projet GL -*- mode: java -*-
// Library for class Math of Deca, coded in Deca

public class Mathe {

    public static float adapt(float f){
        while (f<=-pi()){
            f=f+2*pi();
            
        }
        while (f>pi()){
            f=f-2*pi();
        }
        return f;
    }

    // fonction sinus première solution série entière
    public static float sin_ser(float f) {
        int k=0;
        float solution=0;
        while (k < 6) {
            solution+= Mathe.pow((float)-1,k)* Mathe.pow(f,2*k+1)/Mathe.factoriel(2*k+1);
            k+=1;
        }
        return solution;
    }
    
    // fonction cosinus première solution
    public static float cos_ser(float f) {
        int k=0;
        float solution=0;
        while (k < 6) {
            solution+= Mathe.pow((float)-1,k)* Mathe.pow(f,2*k)/Mathe.factoriel(2*k);
            k+=1;
        }
        return solution;
    }

    // fonction sinus cornic
    public static float sin(float f) {
        
        // Si l'angle est entre 0 et pi/2 on applique l'algorithme
        f=adapt(f);
        if (0<=f && f<=pi()/2) {
            float b;
            b = (float) 0.60725294;
            return b * aux_sin(f, 23);
        }
        // si non on ramène à cet interval
        if (f>pi()/2 && f<=pi()){
            return cos(f-pi()/2);
        }
        if (-pi()<=f && f<0){
            return -sin(-f);
            
        }
        // on ne devrait jamais en arriver là
        throw new IllegalArgumentException(" ça n'arrivera jamais");
    }
    
    public static float sin2(float f) {
        if (f==pi()){
            return 1;
        }
             
        f=adapt(f);
        if (0<=f && f<=pi()/2) {
            
            return sin_ser(f);
        }

        if (f>pi()/2 && f<=pi()){
            return cos2(f-pi()/2);
        }
        if (-pi()<=f && f<0){
            return -sin2(-f);
            
        }
        return 0;
    }

    //fonction aux sin

    public static float aux_sin(float f, int n ){
        if (n == 0) {
            return 0;
            }
        float prec = aux_trigo(f, n - 1);
        if (prec < 0) {
            return aux_sin(f, n - 1) - aux_cos(f, n - 1) * Mathe.pow(2, -(n - 1));
        }
        if (prec == 0) {
            return aux_sin(f, n - 1);
        }
        return aux_sin(f, n - 1) + aux_cos(f, n - 1) * Mathe.pow(2, -(n - 1));

    }


    public static float pi (){
        return (float)3.141592653589793238462643383279;
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
        f=adapt(f);
        if (f==0){
            return 1;
        }
        if (0<=f && f<=pi()/2) {
            float b;
            b = (float) 0.60725294;
            return b*aux_cos(f,23);
        }
        if (f>pi()/2 && f<=pi()){
            return -sin(f-pi()/2);
        }
        if (-pi()<=f && f<0){
            return cos(-f);
            
        }
        return 0;
        
        

    }
    
    public static float cos2(float f) {
        f=adapt(f);
        if (f==0){
            return 1;
        }
        if (0<=f && f<=pi()/4) {
            
            return cos_ser(f);
        }
        if (0<pi()/4 && f<=pi()/2) {
            
            return sin2(pi()/2-f);
        }
        
        if (f>pi()/2 && f<=pi()){
            return -sin2(f-pi()/2);
        }
        if (-pi()<=f && f<0){
            return cos2(-f);
            
        }
        return 0;
        
        

    }
    
    public static float tan (float f){
        if (f==pi()/2){
            throw new IllegalArgumentException();
        }
        return sin2(f)/cos2(f);
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

    public static float  cordic_cos(float f){
        int k =0;
        float cos =1;
        float sin =0;
        float z=f;
        float tmp1;
        float tmp2;

        while( k <100){
            tmp1=z;
            z=tmp1-signe(tmp1)*atan(pow(2,-k));
            tmp2=cos;
            cos=cos -signe(tmp1)*sin*pow(2,-k);
            sin= sin +signe(tmp1)*tmp2*pow(2,-k);
            k+=1;

        }
        return (float) 0.60725294*cos;
    }

    public static float  cordic_sin(float f) {
        int k = 0;
        float cos = 1;
        float sin = 0;
        float z = f;
        float tmp1;
        float tmp2;

        while (k < 100) {
            tmp1 = z;
            z = tmp1 - signe(tmp1) * atan(pow(2, -k));
            tmp2 = cos;
            cos = cos - signe(tmp1) * sin * pow(2, -k);
            sin = sin + signe(tmp1) * tmp2 * pow(2, -k);
            k += 1;

        }
        return (float) 0.60725294*sin;
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
    public static int signe( float f){
        if (f<0)
            return -1;
        else
            return 1;
    }

}



// End of Deca Math library
