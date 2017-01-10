/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DVal;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author belhadjn
 */
public class GenCodeVar {
    private HashMap <DVal,Integer> listeCorr;
    private Set <DVal> listeVar;
    private Integer i=1;
    
    public GenCodeVar(Set listeVar) {
        this.listeCorr = new HashMap <DVal,Integer>();
        this.listeVar=listeVar;
    }

    public HashMap <DVal,Integer> getListeCorr(){
       return this.listeCorr;
    }
    
    public Set<DVal> getListeVar(){
        return this.listeVar;
    }
    
    
    public void ajoutElement(DVal s){
        this.i++;
        this.listeCorr.put(s,i);
    }
    
    public void supprimeElement(DVal s){
        this.listeCorr.remove(s);
        
    }
            
    
    public Integer obtenirIndice(DVal s){
        return this.listeCorr.get(s);
    }
      
}
            
    