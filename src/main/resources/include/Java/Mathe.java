





// Grenoble INP - Ensimag projet GL -*- mode: java -*-
// Library for class Math of Deca, coded in Deca

public class Mathe {
    
    
    public static float adapt3(float f){
        //calcule f-pi
        float c1=pow(2,0);
        float c2=pow(2,1);
        float c3=pow(2,-3);
        float c4=pow(2,-6);
        float c5=pow(2,-11);
        float c6=pow(2,-12);
        float c7=pow(2,-13);
        float c8=pow(2,-14);
        float c9=pow(2,-15);
        float c10=pow(2,-16);
        float c11=pow(2,-18);
        float c12=pow(2,-19);
        float c13=pow(2,-21);
        float c14=pow(2,-22);
        
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
        //return GrandFloat.additionGrandFloat(GrandPi(), new GrandFloat(-f,0)).getFLoatt();
        return -f;
        
        
    }
    
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
        
        return f;
        
        
    }

    public static float fma( float a, float b, float c){
        // ASM("  ");
        return a+b*c;
    }

    public static float adapt(float f){
        float c14=pow(2,-21);
        float c13=2*c14;
        float c12=4*c13;
        float c11=2*c12;
        float c10=4*c11;
        float c9=2*c10;
        float c8=2*c9;
        float c7=2*c8;
        float c6=2*c7;
        float c5=2*c6;
        float c4=32*c5;
        float c3=8*c4;
        float c2=8*c3;
        float c1=2*c2;
        
        
       
        int k=0;
        float f1=f;
        while (f1<=-pi()){
            f1=f1+2*pi();
            k=k+1;  
        }
 
        while (f1>pi()){
            f1=f1-2*pi();
            k=k+1; 
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
            System.out.println(solution);
        }
        return solution;
    }



    public static float sin_grandfloat(float f){
        int k=0;
        GrandFloat solution= new GrandFloat(0,0);
        while (k<6){
            float tmp = (float) 1.0/factoriel(2*k+1);
            float tmp1= pow(f,2*k+1) * pow((float)-1,k);
            GrandFloat r =GrandFloat.multiplicationSimple(tmp,tmp1);
            solution= GrandFloat.additionGrandFloat(solution,r);
            //System.out.println(tmp);
            //System.out.println(tmp1);
            //System.out.println(r);
            //System.out.println(solution);
            k+=1;
        }
        return solution.f + solution.erreur;
    }






   
    // fonction cosinus première solution série entière
    public static float cos_ser(float f) {
        int k=0;
        float solution=0;
        while (k < 6) {
            solution+= Mathe.pow((float)-1,k)* (Mathe.pow(f,2*k))/(Mathe.factoriel(2*k));
            k+=1;
        }
        return solution;
    }
    /*
    // fonction sinus cordic
    public static float sin2(float f) {
        
        // Si l'angle est entre 0 et pi/2 on applique l'algorithme
        f=adapt(f);
        if (0<=f && f<=pi()/2) {
            
            return cordic_sin(f);
        }
        // si non on ramène à cet interval
        if (f>pi()/2 && f<=pi()){
            return cos2(adapt2(f));
        }
        if (-pi()<=f && f<0){
            return -sin2(-f);
            
        }
        // on ne devrait jamais en arriver là
        throw new IllegalArgumentException(" ça n'arrivera jamais");
    }*/
    
    public static float sin(float f) {
        
             
        f=adapt(f);
        
        if (0<=f && f<=pisur2()) {
            
            return sin_grandfloat(f);
        }

        if (f>pisur2() && f<=pi()){
             
            //sin(pi-f)
            //return sin(GrandFloat.additionGrandFloat(GrandPi(), new GrandFloat(-f,0)).getFLoatt());
            f=GrandFloat.pimoinsf(f);
            
            return sin_grandfloat(f);
        }
        if (-pi()<=f && f<0){
            return -sin(-f);
            
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


    public static GrandFloat GrandPi(){
        
        return GrandFloat.GrandPi();
    }
    
    public static GrandFloat GrandPiSur2(){
        
        return GrandFloat.multiplicationGrandFloat(GrandPi(),new GrandFloat((float)0.5,0));
    }
    
    public static float pi(){
        //return (float)3.141592653589793238462643383279;
        return GrandPi().getFLoatt();
    }
    
    public static float pisur2(){
        
        return GrandPiSur2().getFLoatt();
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





    // fonction cosinus cordic
    public static float cos(float f) {
        f=adapt(f);
        if (f==0){
            return 1;
        }
        if (0<=f && f<=pi()/2) {
            
            return cordic_cos(f);
        }
        if (f>pi()/2 && f<=pi()){
            return -sin(pi()/2-f);
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
            
            return sin(-adapt2(f));
        }
        
        if (f>pi()/2 && f<=pi()){
            return -sin(adapt2(f));
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
        return sin(f)/cos(f);
    }


    // fonction arcsin première version série entière
    public static float asin2(float f) {
        if (f<=1 && f>=-1 ) { //definie seulement sur -1..1
        int k=0;
        float solution=0;
        while (k < 7) {
            
            solution+=(pow(f,2*k+1)*factoriel(2*k))/((2*k+1)*pow(factoriel(k)*pow(2,k),2));
            k+=1;
        }
        return solution;
        }
        return f;
    }
    //arcsin(x)=arctan(x/sqrt(1-x²))) 
    public static float asin(float f) {
        if (f<1 && f>-1 ) {
            return atan(f/sqrt(1-pow(f,2)));
        }
        if (f==1){
            return pi()/2;
        }
        if (f==-1){
            return -pi()/2;
        }
        
        return f;
    }
    
    public static float acos(float f) {
        return (float)1.570796327-asin(f);
    }
        

    // fonction arctan première méthode série entière
    public static float atan1(float f) {
        
        
        if (f == 1) {
            return (float)0.78539816339;
        }
        
        if (f<1 && f>=0 ) {
        int k=0;
        float solution=0;
        while (k < 1000) {
            solution+= Mathe.pow((float)-1,k)* Mathe.pow(f,2*k+1)/(2*k+1);
            k+=1;
        }
        return solution;
        }
        if (f>1) {
            return pi()/2-atan(1/f);//pi/2-atan(1/f)
        }
        if (f<0) {
            return -atan(-f);
        }
       return 0; 
    }
    public static float atan(float f) {
        if (abs(f)>0.9 && abs(f)<1.1){
            return atan2(f);
        }
        return atan1(f);    
        }
    
    public static float atan2(float f) {
        float a=1/sqrt(1+f*f);
        float b=1;
        int i=1;
        while (i<12){
            a=(a+b)/2;
            b=sqrt(a*b);
            i++;
        }
        return f/(sqrt(1+f*f)*a);
    }


    // calcul de l'ulp
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
        if (n==0){
            return 1;
        }
        int a = 1;
        while (n>1){
            a*=n;
            n-=1;
        }
        return a;
    }
    
    //fonction signe
    public static int signe( float f){
        if (f<0)
            return -1;
        else
            return 1;
    }
    
    //fonction max
    public static float max(float a,float b){
        if (a>=b){
            return a;
        }
        else {
            return b;
        }
        
    }
    //fonction min
    public static float min(float a,float b){
        if (a>=b){
            return b;
        }
        else {
            return a;
        }
        
    }
    
    //fonction racine carrée
    public static float sqrt(float f){
        float pred=f/2;
        int k=0;
        float succ=0;
        while (k<100){
            succ=(float)0.5*(pred+f/pred);
            pred=succ;
            k=k+1;
        }
        return succ;
    }
    
   //fonction valeur absolue
    public static float abs(float f){
        if (f>=0){
            return f;
        }
        else {
            return -f;
        }
    }



}








// End of Deca Math library
