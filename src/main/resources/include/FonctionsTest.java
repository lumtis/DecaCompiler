
import static java.lang.Math.abs;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author belhadjn
 */
public class FonctionsTest {
    public static float test;
    public static Boolean b; //true pour utiliser la vraie limite
    
    public FonctionsTest(float i, Boolean b){
        this.test=i;
        this.b=b;
    }
    
    public int Erreur(float a, float b,float c){
        float d=abs(a-b);
        int nb=0;
        while(d>0){
            d=d-c;
            nb++;
        }
        return nb;
    }
    
    public int testSinus(float f){
        int compt=0;
        float s=Mathe.cordic_sin(f);
        float lim=Mathe.ulp((float) java.lang.Math.sin(f));
        float testsin;
        if (b==true){
            testsin=lim;
        }
        else {
            testsin=this.test;
        }
        System.out.print("Erreur ");
        System.out.print(Erreur((float)java.lang.Math.sin(f),s,lim));
        if (abs((float)java.lang.Math.sin(f)-s)<=testsin){
            System.out.println(" SIN PASS");
            compt++;
        }
        else {
            System.out.println(" SIN NO");
            
        }
        return compt;
        
    }
    
    public int testSinus2(float f){
        int compt=0;
        float s=Mathe.sin2(f);
        float lim=(java.lang.Math.ulp((float) java.lang.Math.sin(f)));
        float testsin;
        if (b==true){
            testsin=lim;
        }
        else {
            testsin=test;
        }
        System.out.print("Erreur ");
        System.out.print(Erreur((float)java.lang.Math.sin(f),s,lim));
        if (abs(java.lang.Math.sin(f)-s)<=testsin){
            System.out.println(" SINSER PASS");
            compt++;
        }
        else {
            System.out.println(" SINSER NO");
            
        }
        return compt;
    }
    
    public int testCosinus(float f){
        float s=Mathe.cordic_cos(f);
        int compt=0;
        float lim=java.lang.Math.ulp((float) java.lang.Math.cos(f));
        float testcos;
        if (b==true){
            testcos=lim;
        }
        else {
            testcos=test;
        }
        System.out.print("Erreur ");
        System.out.print(Erreur((float)java.lang.Math.cos(f),s,lim));
        if (abs((float)java.lang.Math.cos(f)-s)<=testcos){
            System.out.println(" COS PASS");
            compt++;
        }
        else {
            System.out.println(" COS NO");
            
        }
        return compt;
    }
    
    
    public int testCosinus2(float f){
        float s=Mathe.cos2(f);
        int compt=0;
        float lim=10*(java.lang.Math.ulp((float) java.lang.Math.cos(f)));
        float testcos;
        if (b==true){
            testcos=lim;
        }
        else {
            testcos=test;
        }
        System.out.print("Erreur ");
        System.out.print(Erreur((float)java.lang.Math.cos(f),s,lim));
        if (abs((float)java.lang.Math.cos(f)-s)<=testcos){
            System.out.println(" COSSER PASS");
            compt++;
        }
        else {
            System.out.println(" COSSER NO");
            
        }
        return compt;
    }
    
    
    public void testTan(float f){
        float s=Mathe.tan(f);
        float lim=10*(java.lang.Math.ulp((float) java.lang.Math.tan(f)));
        float testtan;
        if (b==true){
            testtan=lim;
        }
        else {
            testtan=test;
        }
        //System.out.print("Erreur ");
        //System.out.print((abs((float)java.lang.Math.tan(f)-s)));
        if ((float)abs((float)java.lang.Math.tan(f)-s)<=testtan){
            System.out.println("TAN PASS");
        }
        else {
            System.out.println("TAN NO");
            
        }
    }
    
    /*
    public void testAsinus(float f){
        float lim=Mathe.ulp((float) java.lang.Math.asin(f));
        float testcasin;
        if (b==true){
            testasin=lim;
        }
        else {
            testasin=test;
        }
        if ((float)abs(Mathe.asin(f)-Mathe.asin(f))<=2*testasin){
            System.out.println("ASIN PASS");
        }
        else {
            System.out.println("ASIN NO");
            
        }
    }*/
    
    /*
    public void testAcosinus(float f){
        float lim=Mathe.ulp((float) java.lang.Math.acos(f));
        float testacos;
        if (b==true){
            testcos=lim;
        }
        else {
            testacos=test;
        }
        if ((float)abs(Mathe.acos(f)-Mathe.acos(f))<=2*testacos){
            System.out.println("ACOS PASS");
        }
        else {
            System.out.println("ACOS NO");
            
        }
    }*/
    
    public void testAtan(float f){
        float s=Mathe.atan(f);
        float lim=10*(java.lang.Math.ulp((float) java.lang.Math.atan(f)));
        float testatan;
        if (b==true){
            testatan=lim;
        }
        else {
            testatan=test;
        }
        if ((float)abs(java.lang.Math.atan(f)-s)<=testatan){
            System.out.println("ATAN PASS");
        }
        else {
            System.out.println("ATAN NO");
            
        }
    }
    
    
    
    
}
