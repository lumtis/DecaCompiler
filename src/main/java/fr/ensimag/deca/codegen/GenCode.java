package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.*;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.ima.pseudocode.*;


public class GenCode {
    private DecacCompiler comp;
    private int labelIndex;

    private GPRegister tmpReg;
    private GPRegister returnReg;

    public GenCode(DecacCompiler comp) {
        labelIndex = 0;
        this.comp = comp;

        // Le registre qui contient le retour d'une expression est le 2
        returnReg = new GPRegister("R2", 2);

        // Le registre qui contient le une valeur temporaire pour une expression
        // binaire est le 3
        tmpReg = new GPRegister("R3", 3);
    }


    /* Obtient un nom qui n'a jamais été pris pour un nouveau label */
    public Label newLabel() {
        String name = "label" + labelIndex;
        labelIndex++;

        Label l = new Label(name);
        return l;
    }

    public Label newLabel(String prefix) {
        String name = prefix + labelIndex;
        labelIndex++;

        Label l = new Label(name);
        return l;
    }

    /* Ajoute les instructions au debut du programme */
    public void initProgram()
    {
        // TODO: ...
    }

    /* Ajoute les declarations de variable au programme */
    public void initDecla()
    {
        // TODO: ...
    }

    /* Ajoute les instructions au debut d'une expression binaire */
    public void initBinaryExpr(AbstractExpr e1, AbstractExpr e2)
    {
        // On ajoute le code permettant de réaliser l'expression 1
        e1.codeGenInst(comp, this);

        // On met le resultat de la premiere expression dans un registre temporaire
        comp.addInstruction(new LOAD(returnReg, tmpReg));

        // On ajoute le code permettant de réaliser l'expression 2
        e2.codeGenInst(comp, this);
    }


    /* Ajoute les instructions au debut d'une expression unaire */
    public void initUnaryExpr(AbstractExpr e)
    {
        // On ajoute le code permettant de réaliser l'expression
        e.codeGenInst(comp, this);
    }


    /************************************************************/
    /*              OPERATIONS                                  */

    /* permet de stocker la valeur de R dans la pile PC*/
    public void push (Register R){
        //comp.addInstruction(new PUSH(R));
    }

    /* permet de dépiler PC et mettre la valeur dépilée dans R */
    public void pop (GPRegister R){
        //comp.addInstruction(new POP(R));
    }

    /* stocke la valeur v dans R*/
    public void load (DVal v, GPRegister R){
        //comp.addInstruction(new LOAD (v,R));
    }

    /* stocke la valeur de R dans une adresse de GB */
    public void store (Register R, DAddr d){
        //comp.addInstruction(new STORE (R, d));
    }

    public void add(AbstractExpr e1, AbstractExpr e2){
        initBinaryExpr(e1, e2);
        comp.addInstruction(new ADD(tmpReg, returnReg));
    }

    public void sub (AbstractExpr e1, AbstractExpr e2){
        initBinaryExpr(e1, e2);
        comp.addInstruction(new SUB(tmpReg, returnReg));
    }

    public void mul (AbstractExpr e1, AbstractExpr e2){
        initBinaryExpr(e1, e2);
        comp.addInstruction(new MUL(tmpReg, returnReg));
    }

    public void quo (AbstractExpr e1, AbstractExpr e2){
        initBinaryExpr(e1, e2);
        comp.addInstruction(new QUO(tmpReg, returnReg));
    }


    public void opp (AbstractExpr e){
        initUnaryExpr(e);
        comp.addInstruction(new OPP(returnReg, returnReg));
    }


    public void new1 (DVal v, GPRegister R){

        //comp.addInstruction(new NEW(v,R));
    }

    public void del (GPRegister R){

        //comp.addInstruction(new DEL(R));
    }

    // TODO : Ajouter les instructions restantes
    //...

}
