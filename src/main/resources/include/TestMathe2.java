
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
        
        //float  py=Mathe.pi();
        
        System.out.println("Debut");
        //System.out.println(py);
        //System.out.println("3.1415926535897932");
        //float py = (float)3.141592653589793238462643383279;
        float py = Mathe.pi();
        //float py2=Mathe.adapt(11*py);
        //ystem.out.println(py);
        //System.out.println(py2);
        
        
        
        float testlimite=(float)0.000001;
        FonctionsTest fon= new FonctionsTest(testlimite,true);
        
       
        
        
        //float a=Mathe.cos2((float)23*py/48);
        //float b=Mathe.cordic_cos((float)23*py/48);
        //float c=(float) java.lang.Math.cos(23*py/48);
        
        //System.out.println(a);
        //System.out.println(b);
        //System.out.println(c);
        
        
        
        System.out.println("TEST SIN");
        int c11=0;
        int c22=0;
        for (float i=-40*py/48;i<=40*py/48;i=i+py/48){    
            System.out.print(i);
            System.out.print(" ");
            c11=c11+fon.testSinus(i);
        
            System.out.print(i);
            System.out.print("  ");
            c22=c22+fon.testSinus2(i);
            System.out.println("");
        }
        System.out.print("Nombre total de SIN PASS : ");
        System.out.println(c11);
        
        System.out.print("Nombre total de SINSER PASS : ");
        System.out.println(c22);
        
        /*
        System.out.println("TEST COS");
        int c1=0;
        int c2=0;
        for (float i=-py;i<=py;i=i+py/24){    
            System.out.print(i);
            System.out.print("  ");
            
            c1=c1+fon.testCosinus(i);
            
            System.out.print(i);
            System.out.print("  ");
            c2=c2+fon.testCosinus2(i);
            System.out.println("");
        }
        System.out.print("Nombre total de COS PASS : ");
        System.out.println(c1);
        
        System.out.print("Nombre total de COSSER PASS : ");
        System.out.println(c2);*/
       
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
