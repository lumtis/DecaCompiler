/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author belhadjn
 */
public class GenCodeVar {
    private HashMap <String,Integer> listeCorr;
    private Set <String> listeVar;
    private Integer i=1;
    
    public GenCodeVar(Set listeVar) {
        this.listeCorr = new HashMap <String,Integer>();
        this.listeVar=listeVar;
    }

    public HashMap <String,Integer> getListeCorr(){
       return this.listeCorr;
   }
    
    
    public void ajoutElement(String s){
        this.i++;
        this.listeCorr.put(s,i);
    }
    
    public void supprimeElement(String s){
        this.listeCorr.remove(s);
        
    }
            
    
    public Integer obtenirIndice(String s){
        return this.listeCorr.get(s);
    }
      
}
            
    