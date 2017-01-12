package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.*;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tree.*;
import fr.ensimag.ima.pseudocode.*;
import static fr.ensimag.ima.pseudocode.Register.*;
import java.util.Set;
import java.util.Stack;
import java.util.List;


public class GenCode {
    private DecacCompiler comp;

    private int labelIndex = 0;
    private final int  taillePile=20;
    private Label pile_pleine= newLabel();

    private GPRegister tmpReg;
    private GPRegister retReg;

    // Definit si l'expression en cours renvoie un float
    private boolean exprFloat;

    private int indexMem;



    public GenCode(DecacCompiler comp) {
        this.comp = comp;

        // Le registre qui contient le retour d'une expression est le 2
        retReg = new GPRegister("R2", 2);

        // Le registre qui contient le une valeur temporaire pour une expression
        // binaire est le 3
        tmpReg = new GPRegister("R3", 3);

        exprFloat = false;
        indexMem = 1;
    }


    public GPRegister getTmpReg()
    {
        return tmpReg;
    }

    public GPRegister getRetReg()
    {
        return retReg;
    }

    public void setExprFloat(boolean b) {
        exprFloat = b;
    }

    public boolean isExprFloat() {
        return exprFloat;
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
        //à compléter les autres erreurs possibles à la fin
    }

    public void initMem() {
        indexMem = 1;
    }

    public void setMem(Register r, Identifier i) {
        DAddr addr = new RegisterOffset(indexMem, r);

        // On fixe l'adresse de la variable
        i.getExpDefinition().setOperand(addr);
        indexMem++;
    }

    public DAddr getAddrVar(Identifier i) {
          return i.getExpDefinition().getOperand();
    }

    // Initialise les variables globlales
    public void initGlobalVar(List<AbstractDeclVar> a) {
        initMem();

        for (AbstractDeclVar d:a){
            DeclVar declVar=(DeclVar)d;
            Identifier var = (Identifier)declVar.getVarName();

            setMem(Register.GB, var);

            // On initialise la variable
            declVar.getInitialization().codeGenInit(getAddrVar(var), comp, this);
        }
    }
}
