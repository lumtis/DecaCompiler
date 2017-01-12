
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
    
    public void testSinus(float f){
        float s=Mathe.sin(f);
        float lim=Mathe.ulp((float) java.lang.Math.sin(f));
        float testsin;
        if (b==true){
            testsin=lim;
        }
        else {
            testsin=this.test;
        }
        System.out.print("Erreur ");
        System.out.println((abs((float)java.lang.Math.sin(f)-s)/lim));
        if (abs((float)java.lang.Math.sin(f)-s)<=testsin){
            System.out.println("SIN PASS");
        }
        else {
            System.out.println("SIN NO");
            
        }
    }
    
    public void testSinus2(float f){
        float s=Mathe.sin_ser(f);
        float lim=java.lang.Math.ulp((float) java.lang.Math.sin(f));
        float testsin;
        if (b==true){
            testsin=lim;
        }
        else {
            testsin=test;
        }
        System.out.print("Erreur ");
        System.out.print((abs((float)java.lang.Math.sin(f)-s)/lim));
        if (abs(java.lang.Math.sin(f)-s)<=testsin){
            System.out.println(" SINSER PASS");
        }
        else {
            System.out.println(" SINSER NO");
            
        }
    }
    
    public void testCosinus(float f){
        float s=Mathe.sin(f);
        float lim=java.lang.Math.ulp((float) java.lang.Math.cos(f));
        float testcos;
        if (b==true){
            testcos=lim;
        }
        else {
            testcos=test;
        }
        if (abs((float)java.lang.Math.cos(f)-s)<=testcos){
            System.out.println("COS PASS");
        }
        else {
            System.out.println("COS NO");
            
        }
    }
    
    /*
    public void testTan(float f){
        float lim=Mathe.ulp((float) java.lang.Math.tan(f));
        float testtan;
        if (b==true){
            testtan=lim;
        }
        else {
            testtan=test;
        }
        if ((float)abs((float)java.lang.Math.tan(f)-Mathe.tan(f))<=2*testtan){
            System.out.println("TAN PASS");
        }
        else {
            System.out.println("TAN NO");
            
        }
    }*/
    
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
        float lim=Mathe.ulp((float) java.lang.Math.atan(f));
        float testatan;
        if (b==true){
            testatan=lim;
        }
        else {
            testatan=test;
        }
        if ((float)abs(java.lang.Math.atan(f)-Mathe.atan(f))<=testatan){
            System.out.println("ATAN PASS");
        }
        else {
            System.out.println("ATAN NO");
            
        }
    }
    
    
    
    
}
