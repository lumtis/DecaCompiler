/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tree.AbstractDeclVar;
import fr.ensimag.deca.tree.DeclVar;
import fr.ensimag.deca.tree.Identifier;
import java.util.AbstractList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author belhadjn
 */
public class GenCodeVar {
    private HashMap <Identifier,Integer> listeCorr;
    private Set<Identifier> listeVar;
    private Integer i=1;

    public GenCodeVar(List<AbstractDeclVar> a) {
        this.listeCorr = new HashMap <Identifier,Integer>();
        this.listeVar= new HashSet<Identifier>() ;
        for (AbstractDeclVar d:a){
            DeclVar e=(DeclVar)d;
            this.listeVar.add((Identifier) e.getName());
            
        }
    }

    public HashMap <Identifier,Integer> getListeCorr(){
       return this.listeCorr;

    }
    
    public Set<Identifier> getListeVar(){
        return this.listeVar;
    }
    
    

    public void ajoutElement(Identifier s){
        this.i++;
        this.listeCorr.put(s,i);
    }

    public void supprimeElement(Identifier s){
        this.listeCorr.remove(s);

    }


    public Integer obtenirIndice(VariableDefinition s){
        return this.listeCorr.get(s);
    }

}
