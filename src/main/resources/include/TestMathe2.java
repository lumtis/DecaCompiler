
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
        float py;
        py = (float)3.141592653589793238462643383279;
        float u1 = java.lang.Math.ulp((float) java.lang.Math.sin(0.00000000001));
        float u2 = Mathe.ulp((float) java.lang.Math.sin(0.00000000001));
        //System.out.println("Debut prog");
        //System.out.println(u1);
        //System.out.println(u2);

        //float sin=Mathe.sin(py/2);
        //System.out.println(sin);
        
        
        System.out.println("TEST SIN");
        for (float i=-py;i<=py;i=i+py/12){    
            System.out.print(i);
            System.out.print("  ");
            FonctionsTest.testSinus(i);
        }
        
        System.out.println("TEST SINSER");
        for (float i=-py;i<=py;i=i+py/12){    
            System.out.print(i);
            System.out.print("  ");
            FonctionsTest.testSinus2(i);
           
        }
        
        System.out.println("TEST ATAN");
        for (float i=-py/4;i<=py/4;i=i+py/48){    
            System.out.print(i);
            System.out.print("  ");
            FonctionsTest.testAtan(i);
        }
        
        
        
    }    
      
    
    
}
