
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
        //System.out.println(Mathe.adapt((float)-3.1415927));
        //float  py=Mathe.pi();
        //System.out.print(FonctionsTest.Erreur((float)java.lang.Math.atan(0.99999),Mathe.atan((float)0.99999),java.lang.Math.ulp((float) java.lang.Math.atan((float)0.99999))));
        
        //System.out.println("3.1415926535897932");
        float py = (float)3.141592653589793238462643383279;
        
        
        float testlimite=(float)0.000001;
        FonctionsTest fon= new FonctionsTest(testlimite,true);
        //fon.testSinus(py);
        //System.out.println("sin(pi)=");
        System.out.println(Mathe.sin2((float)3.141592653589793238462643383279));
        System.out.println(java.lang.Math.sin((float)3.141592653589793238462643383279));
        
        
        
            
        //System.out.println(Mathe.asin((float)0.5));
        
        //System.out.println((float)java.lang.Math.asin(0.52));
        //System.out.println(Mathe.atan2((float)0.01));
        //System.out.println(Mathe.atan((float)0.01));
        //System.out.println(java.lang.Math.atan((float)0.01));
        
        //float a=Mathe.cos2((float)2.5);
        //float b=Mathe.cordic_cos((float)2.5);
        //float c=(float) java.lang.Math.cos(2.5);
        
        //System.out.println(a);
        //System.out.println(b);
        //System.out.println(c);
        
        
        
        System.out.println("TEST SIN");
        int c11=0;
        int c22=0;
        for (float i=-py+(float)0.00001;i<=py;i=i+py/48){    
            System.out.print(i);
            System.out.print("  ");
            //System.out.println(Mathe.sin(i));
            c11=c11+fon.testSinus(i);
        
            //System.out.print(i);
            //System.out.print("  ");
            //c22=c22+fon.testSinus2(i);
            //System.out.println("");
        }
        //System.out.print("Nombre total de SIN PASS : ");
        //System.out.println(c11);
        
        //System.out.print("Nombre total de SINSER PASS : ");
        //System.out.println(c22);
        
        /*
        System.out.println("TEST COS");
        int c1=0;
        int c2=0;
        for (float i=0;i<=py/2-py/24;i=i+py/24){    
            //System.out.println(i);
            //System.out.print("  ");
            
            c1=c1+fon.testCosinus(i);
            
            //System.out.print(i);
            //System.out.print("  ");
            c2=c2+fon.testCosinus2(i);
            //System.out.println("");
        }
        for (float i=py/2+py/24;i<=py;i=i+py/24){    
            //System.out.println(i);
            //System.out.print("  ");
            
            c1=c1+fon.testCosinus(i);
            
            //System.out.print(i);
            //System.out.print("  ");
            //c2=c2+fon.testCosinus2(i);
            //System.out.println("");
        }*/
        //System.out.print("Nombre total de COS PASS : ");
        //System.out.println(c1);
        
        //System.out.print("Nombre total de COSSER PASS : ");
        //System.out.println(c2);*/
       
        /*
        System.out.println("TEST TAN");
        for (float i=-py/2+py/48;i<py/2+py/48;i=i+py/24){    
            System.out.print(i);
            System.out.println("  ");
            fon.testTan(i);
        }*/
        /*
        System.out.println("TEST ASIN");
        for (float i=-1;i<=1;i=i+(float)0.01){    
            //System.out.print(i);
            //System.out.println(" ");
            
            
            
            //fon.testAsin(i);
            fon.testAsin2(i);
        }*/
        /*
        System.out.println("TEST ACOS");
        for (float i=-1;i<=1;i=i+(float)0.01){    
            //System.out.print(i);
            //System.out.print("  ");
            fon.testAcos(i);
        }*/
        
        /*
        System.out.println("TEST ATAN");
        for (float i=-10;i<=10;i=i+(float)0.1){    
            //System.out.print(i);
            //System.out.print(" ");
            
            //System.out.println(Mathe.atan(i));
            
            //fon.testAtan2(i);
            
            
            //System.out.println(i);
            //System.out.print(" ");
            fon.testAtan(i);
        }*/
        
        
        
    }    
      
    
    
}
