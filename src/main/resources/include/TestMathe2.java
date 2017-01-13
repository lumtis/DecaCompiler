
import static java.lang.Math.sin;
import static java.time.Clock.system;
import java.util.LinkedList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author belhadjn
 */
public class TestMathe2 {
    public static void main(String[] args) {
        float  py;
        py = (float)3.141592653589793238462643383279;
        float u1 = java.lang.Math.ulp((float) java.lang.Math.sin(0.00000000001));
        float u2 = Mathe.ulp((float) java.lang.Math.sin(0.00000000001));
        float testlimite=(float)0.000001;
        FonctionsTest fon= new FonctionsTest(testlimite,true);
        //FonctionsTest fon= new FonctionsTest(testlimite,true);
        //true pour utiliser la vraie limite
        
        //false pour definir une valeur comme limite (testlimite)
        //float a=Mathe.cos2((float)1);
        //float b=Mathe.cos((float)1);
        //float c=(float) java.lang.Math.cos(1);
        //System.out.println(a);
        //System.out.println(b);
        //System.out.println(c);
        
        
        /*
        System.out.println("TEST SIN");
        for (float i=-py;i<=py;i=i+py/48){    
            System.out.print(i);
            System.out.print(" ");
            //System.out.print("  LIM=");
            //float lim=java.lang.Math.ulp((float) java.lang.Math.sin(i));
            //System.out.print(lim);
            
            fon.testSinus2(i);
        }*/
       
        /*
        System.out.println("TEST COS");
        for (float i=-py;i<=py;i=i+py/24){    
            System.out.print(i);
            System.out.print("  ");
            //System.out.print("  LIM=");
            //float lim=java.lang.Math.ulp((float) java.lang.Math.cos(i));
            //System.out.print(lim);
            //System.out.print(" ");
            fon.testCosinus2(i);
        }*/
       
        /*
        System.out.println("TEST TAN");
        for (float i=0;i<py/2;i=i+py/24){    
            System.out.print(i);
            System.out.print("  ");
            fon.testTan(i);
        }*/
        /*
        System.out.println("TEST ASIN");
        for (float i=-py;i<=py;i=i+py/12){    
            System.out.print(i);
            System.out.print("  ");
            fon.testAsinus(i);
        }*/
        /*
        System.out.println("TEST ACOS");
        for (float i=-py;i<=py;i=i+py/12){    
            System.out.print(i);
            System.out.print("  ");
            fon.testAcosinus(i);
        }*/
        
        /*
        System.out.println("TEST ATAN");
        for (float i=-py/4;i<=py/4;i=i+py/48){    
            System.out.print(i);
            System.out.print("  ");
            fon.testAtan(i);
        }*/
        
        
        
    }    
      
    
    
}
