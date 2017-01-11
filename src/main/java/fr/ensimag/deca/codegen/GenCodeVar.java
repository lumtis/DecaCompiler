/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.ensimag.deca.codegen;

import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tree.*;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
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
    private DecacCompiler comp;
    private GenCode gc;
    private HashMap<String, Integer> listeCorr;
    private Integer i=1;

    public GenCodeVar(DecacCompiler comp) {

        this.comp = comp;
        this.listeCorr = new HashMap<String,Integer>();
    }

    public void setGenCode(GenCode gc) {
        this.gc = gc;
    }

    public void initVar(List<AbstractDeclVar> a) {

        comp.addComment("variables globales");
        for (AbstractDeclVar d:a){
            DeclVar declVar=(DeclVar)d;
            Identifier var = (Identifier)declVar.getVarName();

            //ajout de la variable v à la table de correspondance
            ajoutVariable(var);

            // On initialise la variable
            declVar.getInitialization().codeGenInit(new RegisterOffset(i, Register.GB), comp, gc);
            this.i++;
        }
    }

    public HashMap <String,Integer> getListeCorr(){
       return this.listeCorr;

    }

    public void ajoutVariable(Identifier v){
        String nameVar = v.getName().getName();
        this.listeCorr.put(nameVar,i);
    }

    public Integer obtenirIndice(Identifier v){
        String nameVar = v.getName().getName();
        return this.listeCorr.get(nameVar);
    }

    public DAddr getVariable(Identifier v){
        return new RegisterOffset(obtenirIndice(v), Register.GB);
    }

}
