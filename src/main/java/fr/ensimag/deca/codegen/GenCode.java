package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.*;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tree.*;
import fr.ensimag.ima.pseudocode.*;
import static fr.ensimag.ima.pseudocode.Register.*;
import java.util.Set;
import java.util.Stack;
import java.util.List;


public class GenCode {
    private DecacCompiler comp;

    private int labelIndex = 0;
    private final int  taillePile=1024;
    private Label pile_pleine= newLabel("pile_pleine");
    private Label tas_plein= newLabel("tas_plein");

    private Stack<GPRegister> tmpReg;
    private int indexTmp;
    private int maxReg;
    private GPRegister retReg;
    private GPRegister r0;
    private GPRegister r1;

    // Definit si l'expression en cours renvoie un float
    private boolean exprFloat;

    private int indexGB;
    private int indexLB;



    // Fonction utilitaires pour obtenir le nom d'un registre en fonction de
    // son numero
    private String getRegName(int n) {
        return "R" + String.valueOf(n);
    }


    private GPRegister getR0() {
        return r0;
    }

    private GPRegister getR1() {
        return r1;
    }


    public GenCode(DecacCompiler comp) {
        this.comp = comp;

        // Le registre qui contient le retour d'une expression est le 2
        retReg = new GPRegister("R2", 2);
        r0 = new GPRegister("R0", 0);
        r1 = new GPRegister("R1", 1);

        // On commence l'enregistrement des registres à 3
        indexTmp = 3;
        tmpReg = new Stack<GPRegister>();
        maxReg = comp.getCompilerOptions().getRegisters();//comp.getCompilerOptions().getRegisters();

        exprFloat = false;
        indexGB = 1;
    }


    ////////////////////////////////////////////////////////////////////
    // Gestion des variables

    // Initialise les variables globlales
    public void initGlobalVar(List<AbstractDeclVar> a) {

        addSeparatorComment();
        comp.addComment("Initialisation des variables globales");
        addSeparatorComment();

        for (AbstractDeclVar d:a){
            DeclVar declVar=(DeclVar)d;
            Identifier var = (Identifier)declVar.getVarName();
            DAddr addr = new RegisterOffset(indexGB, Register.GB);

            // On fixe l'adresse de la variable
            var.getExpDefinition().setOperand(addr);
            indexGB++;

            // On initialise la variable
            declVar.getInitialization().codeGenInit(getAddrVar(var), comp, this);
        }
    }


    public DAddr getAddrVar(Identifier i) {
          return i.getExpDefinition().getOperand();
    }

    public void pushTmpReg(DVal v) {
        GPRegister r;

        // S'il n'y a plus de registre disponible, on push l'ancienne valeur
        if(indexTmp >= maxReg) {
            // On met dans la pile (du programme la valeur du dernier registre)
            comp.addInstruction(new PUSH(tmpReg.peek()));

            r = new GPRegister(getRegName(maxReg-1), maxReg-1);
            comp.addInstruction(new LOAD(v, r));
            tmpReg.push(r);
        }
        else {
            r = new GPRegister(getRegName(indexTmp), indexTmp);
            comp.addInstruction(new LOAD(v, r));
            tmpReg.push(r);
        }

        indexTmp++;
    }

    public GPRegister popTmpReg() {
        GPRegister r;
        indexTmp--;

        // S'il n'y a plus de registre disponible, on pop la nouvelle valeur
        if(indexTmp >= maxReg) {
            r = tmpReg.pop();
            comp.addInstruction(new LOAD(r, r1));
            comp.addInstruction(new POP(r));
        }
        else {
            return tmpReg.pop();
        }

        return r1;
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

    // Gestion des variables
    ////////////////////////////////////////////////////////////////////

    // Initialise les variables locales
    public void initLocalVar(List<AbstractDeclVar> a) {

        for (AbstractDeclVar d:a){
            DeclVar declVar=(DeclVar)d;
            Identifier var = (Identifier)declVar.getVarName();

            //setMem(Register.GB, var);

            // On initialise la variable
            declVar.getInitialization().codeGenInit(getAddrVar(var), comp, this);
        }
    }


    ////////////////////////////////////////////////////////////////////
    // Gestion des labels

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

    // Gestion des labels
    ////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////
    // Gestion des classes


    // Utilitaires
    public DAddr getParentAddr(DeclClass c) {
        Identifier parent = c.getParent();

        return new RegisterOffset(1, Register.GB);
    }

    public Label getMethodLabel(DeclClass c, DeclMethod m) {
        String className = c.getClassName().getName().getName();
        String methodName = m.getFieldName().getName().getName();

        return new Label(className + "." + methodName);
    }


    public void initMethodTable(List<AbstractDeclClass> lc) {
        int indexObject;

        addSeparatorComment();
        comp.addComment("Code de la table des methodes");
        addSeparatorComment();

        // TODO: Definir ObjectEquals
        Label objectEquals = new Label("Object.equals");

        // Table des methodes de object
        indexObject = indexGB;
        comp.addInstruction(new LOAD(new NullOperand(), r0));
        comp.addInstruction(new STORE(r0, new RegisterOffset(indexGB, Register.GB)));
        indexGB++;
        comp.addInstruction(new LOAD(new LabelOperand(objectEquals), r0));
        comp.addInstruction(new STORE(r0, new RegisterOffset(indexGB, Register.GB)));
        indexGB++;

        // On parcourt les classes
        for (AbstractDeclClass ac:lc) {
            DeclClass c = (DeclClass)ac;

            // On place l'addresse de la table des methodes parentes
            comp.addInstruction(new LOAD(getParentAddr(c), r0));
            comp.addInstruction(new STORE(r0, new RegisterOffset(indexGB, Register.GB)));
            indexGB++;

            // Pour chacune des classes on parcourt toute les methodes
            List<AbstractDeclMethod> lm = c.getMethods().getList();
            for (AbstractDeclMethod am:lm) {
                DeclMethod m = (DeclMethod)am;

                Label lab = getMethodLabel(c, m);
                MethodDefinition def = m.getDefinition();

                def.setLabel(lab);
                comp.addInstruction(new LOAD(new LabelOperand(lab), r0));
                comp.addInstruction(new STORE(r0, new RegisterOffset(indexGB, Register.GB)));
                indexGB++;
            }
        }
    }

    public void initClass(List<DeclClass> l) {

        for (DeclClass c:l){

        }
    }


    // Gestion des classes
    ////////////////////////////////////////////////////////////////////





    ////////////////////////////////////////////////////////////////////
    // Fonctions generales

    /* Ajoute les instructions au debut du programme */
    public void initProgram()
    {
        comp.addComment("Début du programme principal");
        comp.addComment("Taille maximale de la pile");
        comp.addInstruction(new TSTO(taillePile));
        comp.addInstruction(new BOV(pile_pleine));
        comp.addInstruction(new ADDSP(taillePile));
    }

    public void terminateProgram(){
        comp.addComment("Code du programme principal");
        comp.addInstruction(new HALT());
        comp.addComment("Messages d’erreurs");
        comp.addLabel(pile_pleine);
        comp.addInstruction(new WSTR("Erreur: Pile pleine"));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());
        comp.addLabel(tas_plein);
        comp.addInstruction(new WSTR("Erreur : allocation impossible, tas plein"));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());
        comp.addComment("Autres erreurs");
        //à compléter les autres erreurs possibles à la fin
    }

    public void addSeparatorComment() {
        comp.addComment("--------------------------------------------------");
    }

    // Fonctions generales
    ////////////////////////////////////////////////////////////////////


}
