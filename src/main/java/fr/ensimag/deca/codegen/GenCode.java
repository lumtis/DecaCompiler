package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.*;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.ima.pseudocode.*;
import java.util.Set;


public class GenCode {
    private IMAProgram ima;
    private DecacCompiler comp;
    private int labelIndex=0;
    private GPRegister R2;
    private final int  taillePile=20;
    private Label pile_pleine= newLabel();
    private Set <String> listeVar; //liste des variables globales
    

    private GPRegister op1;
    private GPRegister op2;

    public GenCode(DecacCompiler comp) {
        this.comp=comp;
        R2 = new GPRegister("R2", 2);
        //this.listeVar= new Set <String>();
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
        comp.addComment("Début du programme principal");
        comp.addComment("Taille maximale de la pile");
        comp.addInstruction(new TSTO(taillePile));
        comp.addInstruction(new BOV(pile_pleine));
        
        initDecla(); //cette fonction va faire l'initialisation des variables globales ADDSP #d2
    }
    
    public void terminateProgram(){
        comp.addComment("Code du programme principal");
        comp.addInstruction(new HALT());
        comp.addComment("Messages d’erreurs");
        comp.addLabel(pile_pleine);
        comp.addInstruction(new WSTR("Erreur: Pile pleine"));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());
        comp.addComment("Autres erreurs");
        //à compléter les autres erreurs possibles
        
        
    }

    /* Ajoute les declarations de variable au programme */
    public void initDecla()
    {
        comp.addComment("variables globales");
        //ajout des variables dans listeVar
        GenCodeVar gcv=new GenCodeVar(listeVar);
        /*Permet de remplir la liste de correspondance et stocker les valeurs des variables
          dans les registres Gb en meme temps*/
        for (String s: listeVar){
            gcv.ajoutElement(s);
            
            //il manque le stockage de la variable s dans GB   
        }
    }

    /* Ajoute les instructions au debut d'une expression binaire */
    public void initBinaryExpr()
    {
        // TODO: ...
    }

    /* Ajoute les instructions de fin d'une expression binaire */
    public void terminateBinaryExpr()
    {
        // TODO: ...
    }

    /* Ajoute les instructions au debut d'une expression unaire */
    public void initUnaryExpr()
    {
        // TODO: ...
    }

    /* Ajoute les instructions de fin d'une expression unaire */
    public void terminateUnaryExpr()
    {
        // TODO: ...
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

    public void del (GPRegister R){

        //comp.addInstruction(new DEL(R));
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
        initBinaryExpr();
        //comp.addInstruction(new ADD (v, R));
        terminateBinaryExpr();
    }

    public void sub (AbstractExpr e1, AbstractExpr e2){
        initBinaryExpr();
        //comp.addInstruction(new SUB(v,R));
        terminateBinaryExpr();
    }

    public void mul (AbstractExpr e1, AbstractExpr e2){
        initBinaryExpr();
        //comp.addInstruction(new MUL(v,R));
        terminateBinaryExpr();
    }


    public void new1 (DVal v, GPRegister R){

        //comp.addInstruction(new NEW(v,R));
    }

    public void opp (DVal v, GPRegister R){

        //comp.addInstruction(new OPP(v,R));
    }

    public void quo (DVal v, GPRegister R){

        //comp.addInstruction(new QUO(v,R));
    }


    // TODO : Ajouter les instructions restantes
    //...

}
