package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.*;
import fr.ensimag.ima.pseudocode.*;


public class GenCode {
    static DecacCompiler comp;
    private int labelIndex;

    
    public GenCode(DecacCompiler comp) {
        labelIndex = 0;
        this.comp = comp;
    }
    
    
    /* Obtient un nom qui n'a jamais été pris pour un nouveau label */
    public Label newLabel() {
        String name = "l" + labelIndex;
        labelIndex++;
        
        Label l = new Label(name);
        return l;
    }
    
    
    /* permet de stocker la valeur de R dans la pile PC*/
    public void push (Register R){
        comp.addInstruction(new PUSH(R));
    }

    /* permet de dépiler PC et mettre la valeur dépilée dans R */
    public void pop (GPRegister R){
        comp.addInstruction(new POP(R));
    }

    public static void del (GPRegister R){

        comp.addInstruction(new DEL(R));
    }

    /* stocke la valeur v dans R*/
    public void load (DVal v, GPRegister R){
        comp.addInstruction(new LOAD (v,R));
    }

    /* stocke la valeur de R dans une adresse de GB */
    void store (Register R, DAddr d){
        comp.addInstruction(new STORE (R, d));
    }

    void add (DVal v, GPRegister R){
        comp.addInstruction(new ADD (v, R));
    }

    public void sub (DVal v, GPRegister R){

        comp.addInstruction(new SUB(v,R));
    }

    public void mul (DVal v, GPRegister R){

        comp.addInstruction(new MUL(v,R));
    }


    public void new1 (DVal v, GPRegister R){

        comp.addInstruction(new NEW(v,R));
    }

    public void opp (DVal v, GPRegister R){

        comp.addInstruction(new OPP(v,R));
    }

    public void quo (DVal v, GPRegister R){

        comp.addInstruction(new QUO(v,R));
    }

    /*faire une operation binaire entre deux valeurs v1 et v2 et la mettre dans R*/
    /*public static void instructionBinaireDValToReg (DVal v1 , DVal v2, GPRegister R , BinaryInstructionDValToReg A){
        Register R2= new Register("R2"); //pas sur que c'est le R2 de l'assembleur
        load(v2,R2);
        store(R2,R);

        switch (A) {
            case ADD:
                add(v1,R);
            case SUB:
                sub(v1,R);
            case MUL:
                mul(v1,R);
            case NEW:
                new1(v1,R);
            case OPP:
                opp(v1,R);
            case QUO:
                quo(v1,R);
        }
    //a priori le resultat est dans le registre R mais peut etre il faut sortir avec la valeur et pas le registre
    }*/

}

