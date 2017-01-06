package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.*;


public class GenCode {

    /* permet de stocker la valeur de R dans la pile PC*/
    static void push (Register R , DecacCompiler comp){
        comp.addInstruction(new PUSH(R));
    }
    /* permet de dépiler PC et mettre la valeur dépilée dans R */
    static void pop (GPRegister R , DecacCompiler comp){
        comp.addInstruction(new POP(R));
    }
    /* stocke la valeur v dans R*/
    static void load (DVal v, GPRegister R , DecacCompiler comp){
        comp.addInstruction(new LOAD (v, R));
    }
    /* stocke la valeur de R dans une adresse de GB */
    static void store (Register R, DAddr d , DecacCompiler comp){
        comp.addInstruction(new STORE (R, d));
    }

    static void add (DVal v, GPRegister R , DecacCompiler comp){
        comp.addInstruction(new ADD (v, R));
    }

    /*faire la somme de deux valeurs v1 et v2 et la mettre dans R*/

    static void plus (DVal v1 , DVal v2, GPRegister R){
//        load(v1,R2); //petit prob avec R2 qui est non def pour le moment
//        store(R2,R);
//        load(v2,R2);
//        add(R2,R);
    }

}





