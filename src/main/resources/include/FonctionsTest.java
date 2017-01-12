
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
    
    public static void testSinus(float f){
        float lim=Mathe.ulp((float) java.lang.Math.sin(f));
        if (abs(Mathe.sin(f)-Mathe.sin(f))<lim){
            System.out.println("SIN PASS");
        }
        else {
            System.out.println("SIN NO");
            
        }
    }
    public static void testSinus2(float f){
        float lim=java.lang.Math.ulp((float) java.lang.Math.sin(f));
        if (abs(java.lang.Math.sin(f)-Mathe.sin_ser(f))<lim){
            System.out.println("SINSER PASS");
        }
        else {
            System.out.println("SINSER NO");
            
        }
    }
    
    public static void testCosinus(float f){
        float lim=java.lang.Math.ulp((float) java.lang.Math.cos(f));
        if (abs(java.lang.Math.cos(f)-Mathe.cos(f))<lim){
            System.out.println("COS PASS");
        }
        else {
            System.out.println("COS NO");
            
        }
    }
    
    /*
    public static void testTan(float f){
        float lim=Mathe.ulp((float) java.lang.Math.tan(f));
        if (abs(java.lang.Math.tan(f)-Mathe.tan(f))<lim){
            System.out.println("TAN PASS");
        }
        else {
            System.out.println("TAN NO");
            
        }
    }*/
    
    /*
    public static void testAsinus(float f){
        float lim=Mathe.ulp((float) java.lang.Math.asin(f));
        if (abs(Mathe.asin(f)-Mathe.asin(f))<lim){
            System.out.println("ASIN PASS");
        }
        else {
            System.out.println("ASIN NO");
            
        }
    }*/
    
    /*
    public static void testAcosinus(float f){
        float lim=Mathe.ulp((float) java.lang.Math.acos(f));
        if (abs(Mathe.acos(f)-Mathe.acos(f))<lim){
            System.out.println("ACOS PASS");
        }
        else {
            System.out.println("ACOS NO");
            
        }
    }*/
    
    public static void testAtan(float f){
        float lim=Mathe.ulp((float) java.lang.Math.atan(f));
        if (abs(java.lang.Math.atan(f)-Mathe.atan(f))<lim){
            System.out.println("ATAN PASS");
        }
        else {
            System.out.println("ATAN NO");
            
        }
    }
    
    
    
    
}
