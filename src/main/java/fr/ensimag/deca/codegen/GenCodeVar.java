/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.ima.pseudocode.DVal;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author belhadjn
 */
public class GenCodeVar {
    private HashMap <VariableDefinition,Integer> listeCorr;
    private Set<VariableDefinition> listeVar;
    private Integer i=1;

    public GenCodeVar(Set<VariableDefinition> listeVar) {
        this.listeCorr = new HashMap <VariableDefinition,Integer>();
        this.listeVar=listeVar;
    }

    public HashMap <VariableDefinition,Integer> getListeCorr(){
       return this.listeCorr;

    }
    
    public Set<VariableDefinition> getListeVar(){
        return this.listeVar;
    }
    
    

    public void ajoutElement(VariableDefinition s){
        this.i++;
        this.listeCorr.put(s,i);
    }

    public void supprimeElement(VariableDefinition s){
        this.listeCorr.remove(s);

    }


    public Integer obtenirIndice(VariableDefinition s){
        return this.listeCorr.get(s);
    }

}
