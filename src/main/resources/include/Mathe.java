


// Grenoble INP - Ensimag projet GL -*- mode: java -*-
// Library for class Math of Deca, coded in Deca

public class Mathe {
    
    public static float adapt2(float f){
        //calcule f-pi/2
        float c1=pow(2,-1);
        float c2=pow(2,-4);
        float c3=pow(2,-7);
        float c4=pow(2,-12);
        float c5=pow(2,-13);
        float c6=pow(2,-14);
        float c7=pow(2,-15);
        float c8=pow(2,-16);
        float c9=pow(2,-17);
        float c10=pow(2,-15);
        float c11=pow(2,-19);
        float c12=pow(2,-20);
        float c13=pow(2,-22);
        float c14=pow(2,-23);
        f=f-c1;
        f=f-c2;
        f=f-c3;
        f=f-c4;
        f=f-c5;
        f=f-c6;
        f=f-c7;
        f=f-c8;
        f=f-c9;
        f=f-c10;
        f=f-c11;
        f=f-c12;
        f=f-c13;
        f=f-c14;
        f=f-1;
        return f;
        
        
    }

    public static float adapt(float f){
        float c1=pow(2,2);
        float c2=pow(2,1);
        float c3=pow(2,-2);
        float c4=pow(2,-5);
        float c5=pow(2,-10);
        float c6=pow(2,-11);
        float c7=pow(2,-12);
        float c8=pow(2,-13);
        float c9=pow(2,-14);
        float c10=pow(2,-15);
        float c11=pow(2,-17);
        float c12=pow(2,-18);
        float c13=pow(2,-20);
        float c14=pow(2,-21);
       
        int k=0;
        float f1=f;
        while (f1<=-pi()){
            f1=f1+2*pi();
            k=k+1;
            //f=f+2*c1+2*c2+2*c3+2*c4+2*c5+2*c6+2*c7+2*c8+2*c9+2*c10+2*c11+2*c12+2*c13+2*c14;
            
        }
 
        while (f1>pi()){
            f1=f1-2*pi();
            k=k+1;
            //f=f-2*c1-2*c2-2*c3-2*c4-2*c5-2*c6-2*c7-2*c8-2*c9-2*c10-2*c11-2*c12-2*c13-2*c14;
        }
        
        if (f>pi()){
            f=f-k*c1;
            f=f-k*c2;
            f=f-k*c3;
            f=f-k*c4;
            f=f-k*c5;
            f=f-k*c6;
            f=f-k*c7;
            f=f-k*c8;
            f=f-k*c9;
            f=f-k*c10;
            f=f-k*c11;
            f=f-k*c12;
            f=f-k*c13;
            f=f-k*c14;
           
        }
        
        if (f<=-pi()){
            f=f+k*c1;
            f=f+k*c2;
            f=f+k*c3;
            f=f+k*c4;
            f=f+k*c5;
            f=f+k*c6;
            f=f+k*c7;
            f=f+k*c8;
            f=f+k*c9;
            f=f+k*c10;
            f=f+k*c11;
            f=f+k*c12;
            f=f+k*c13;
            f=f+k*c14;
           
        }
        
        return f;
    }

    // fonction sinus première solution série entière
    public static float sin_ser(float f) {
        int k=0;
        float solution=0;
        while (k < 6) {
            solution+=Mathe.pow((float)-1,k)* Mathe.pow(f,2*k+1)/Mathe.factoriel(2*k+1);
            k+=1;
        }
        return solution;
    }
   
    // fonction cosinus première solution
    public static float cos_ser(float f) {
        int k=0;
        float solution=0;
        while (k < 6) {
            solution+= Mathe.pow((float)-1,k)* (Mathe.pow(f,2*k))/(Mathe.factoriel(2*k));
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
            return b * cordic_sin(f);
        }
        // si non on ramène à cet interval
        if (f>pi()/2 && f<=pi()){
            return cos(adapt2(f));
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
            return cos2(adapt2(f));
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
        return (float)3.1415927;
        /*float k=-4;
        float i=0;
        float s=0;
        while (i<999999999){
            k=-k;
            s=s+k/(2*i+1); 
            i=i+1;
        }
        return s;*/
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
            return b*cordic_cos(f);
        }
        if (f>pi()/2 && f<=pi()){
            return -sin(adapt2(f));
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
            
            return sin2(-adapt2(f));
        }
        
        if (f>pi()/2 && f<=pi()){
            return -sin2(adapt2(f));
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
