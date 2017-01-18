package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.*;
import fr.ensimag.deca.tools.SymbolTable.Symbol;;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tree.*;
import fr.ensimag.ima.pseudocode.*;
import static fr.ensimag.ima.pseudocode.Register.*;
import java.util.Set;
import java.util.Stack;
import java.util.List;
import java.util.Map;


public class GenCode {
    private DecacCompiler comp;

    private int labelIndex = 0;
    private final int  taillePile=1024;

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

    private Label pile_pleine = newLabel("pile_pleine");
    private Label tas_plein = newLabel("tas_plein");
    private Label no_return = newLabel("no_return");
    private Label dereferencement_nul  = newLabel("dereferencement_nul");

    // Fonction utilitaires pour obtenir le nom d'un registre en fonction de
    // son numero
    private String getRegName(int n) {
        return "R" + String.valueOf(n);
    }
    //private GPRegister getReg(int n) {
    //    return new GPRegister(getRegName(n), n);
    //}


    private GPRegister getR0() {
        return r0;
    }

    private GPRegister getR1() {
        return r1;
    }


    public GenCode(DecacCompiler comp) {
        this.comp = comp;

        // Le registre qui contient le retour d'une expression est le 2
        retReg = Register.getR(2);
        r0 = Register.getR(0);
        r1 = Register.getR(1);

        // On commence l'enregistrement des registres à 3
        indexTmp = 3;
        tmpReg = new Stack<GPRegister>();
        maxReg = comp.getCompilerOptions().getRegisters();//comp.getCompilerOptions().getRegisters();

        exprFloat = false;
        indexGB = 1;

        pileRegSave = new Stack<Integer>();
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

    // Initialise les variables locales par rapport au registre LB
    public void initLocalVar(List<AbstractDeclVar> a) {

        comp.addComment("Initialisation des variables Locales");

        indexLB = 1;

        for (AbstractDeclVar d:a){
            DeclVar declVar=(DeclVar)d;
            Identifier var = (Identifier)declVar.getVarName();
            DAddr addr = new RegisterOffset(indexLB, Register.LB);

            // On fixe l'adresse de la variable
            var.getExpDefinition().setOperand(addr);
            indexLB++;

            // On initialise la variable
            declVar.getInitialization().codeGenInit(getAddrVar(var), comp, this);
        }

        // On incremente la pile pour sauvegarder les registres plus tard
        comp.addInstruction(new ADDSP(a.size()));
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

            r = GPRegister.getR(maxReg-1);
            comp.addInstruction(new LOAD(v, r));
            tmpReg.push(r);
        }
        else {
            r = GPRegister.getR(indexTmp);
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

    public Label getMethodLabel(DeclClass c, DeclMethod m) {
        String className = c.getClassName().getName().getName();
        String methodName = m.getMethodName().getName().getName();

        return new Label(className + "." + methodName);
    }

    public Label getMethodLabel(DeclClass c, String mName) {
        String className = c.getClassName().getName().getName();

        return new Label(className + "." + mName);
    }


    private DeclClass currClass = null;
    private DeclMethod currMethod = null;

    public void setCurrContext(DeclClass c, DeclMethod m) {
        currClass = c;
        currMethod = m;
    }

    public Label getRetLabel() {
        String className = currClass.getClassName().getName().getName();
        String methodName = currMethod.getMethodName().getName().getName();

        return new Label(className + "." + methodName + "." + "end");
    }

    // Gestion des labels
    ////////////////////////////////////////////////////////////////////



    ////////////////////////////////////////////////////////////////////
    // Gestion des classes


    // Utilitaires
    public DAddr getParentAddr(DeclClass c) {
        int parentOffset = c.getParent().getClassDefinition().getOffset();

        return new RegisterOffset(parentOffset, Register.GB);
    }

    public void initMethodTable(List<AbstractDeclClass> lc) {
        int indexObject;

        addSeparatorComment();
        comp.addComment("Code de la table des methodes");
        addSeparatorComment();

        // TODO: Definir ObjectEquals
        Label objectEquals = new Label("Object.equals");

        comp.addComment("Table de Object");

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
            int pas = 1 + c.numberMethods();

            // Tableau de booleens pour savoir si une méthode a été redéfinie
            boolean[] redef = new boolean[pas + 1];
            for(int i = 0; i<=pas; i++) {
                redef[i] = false;
            }

            // On initialise l'offset de la classe dans la table
            c.getClassName().getClassDefinition().setOffset(indexGB);

            comp.addComment("Table de " + c.getClassName().getName().getName());

            // On place l'addresse de la table des methodes parentes
            comp.addInstruction(new LEA(getParentAddr(c), r0));
            comp.addInstruction(new STORE(r0, new RegisterOffset(indexGB, Register.GB)));

            // On obtient l'environnement ExpDefinition
            ClassDefinition cDef = c.getClassName().getClassDefinition();
            EnvironmentExp env = cDef.getMembers();

            // On parcourt les envirronements de l'arborescence des classes
            while(env != null) {
                for (Map.Entry<Symbol, ExpDefinition> entry : env.getHashMap().entrySet()) {
                    String name = entry.getKey().getName();
                    MethodDefinition method = (MethodDefinition)entry.getValue();
                    Label lab = new Label(cDef.getType().getName().getName() + "." + name); // nomClass.nomMethod

                    if(redef[method.getIndex()] == false) {
                        comp.addInstruction(new LOAD(new LabelOperand(lab), r0));
                        comp.addInstruction(new STORE(r0, new RegisterOffset(indexGB + method.getIndex(), Register.GB)));
                        redef[method.getIndex()] = true;
                    }
                }
                env = env.getParent();
                cDef = cDef.getSuperClass();
            }
            indexGB += pas;
        }
    }


    // Initialisation des classes à la fin du programme
    public void initClass(List<AbstractDeclClass> lc) {

        // TODO: Méthode Object
        //comp.getObject().getType()

        for (AbstractDeclClass ac:lc){
            DeclClass c = (DeclClass)ac;

            addSeparatorComment();
            comp.addComment(c.getClassName().getName().getName());
            addSeparatorComment();

            // On fixe l'operande des attributs et leur initialisation
            addSeparatorComment();
            comp.addComment(c.getClassName().getName().getName() + ".init");

            //for (AbstractDeclField af:c.getFields().getList()){
            //    DeclField f = (DeclField)af;
                //generateMethod(c, m);
                // TODO: init
            //}

            // Creation des methodes de la class
            for (AbstractDeclMethod am:c.getMethods().getList()){
                DeclMethod m = (DeclMethod)am;
                generateMethod(c, m);
            }
        }
    }

    public void generateMethod(DeclClass c, DeclMethod m) {
        String className = c.getClassName().getName().getName();
        String methodName = m.getMethodName().getName().getName();
        List<AbstractDeclParam> lp = m.getParams().getList();
        Label lab = getMethodLabel(c, m);

        // On remplie d'abord la définition des param
        int offset = -3;
        for (AbstractDeclParam ap:m.getParams().getList()){
            DeclParam p = (DeclParam)ap;
            p.getName().getParamDefinition().setOffset(offset);
            offset--;
        }

        // On enregistre la classe et la methode courante pour le label
        // de retour
        setCurrContext(c, m);

        addSeparatorComment();
        comp.addComment(className + "." + methodName);
        comp.addLabel(getMethodLabel(c, m));

        // Verification pile
        comp.addInstruction(new TSTO(lp.size() + 1));
        comp.addInstruction(new BOV(pile_pleine));

        // On genere le code de la methode
        m.getBody().generateMethod(comp, this, m.getType().getMethodDefinition().getType().isVoid());

        comp.addInstruction(new RTS());
    }



    // Pile permettant d'enregistrer les nombres de registre utilisé
    Stack<Integer> pileRegSave;

    // Sauvegarde les registres déjà utilisés et remet à 0 le gestionnaire
    // de registre
    public void saveRegister() {
        int regToSave = indexTmp < maxReg ? indexTmp : maxReg;

        int i = regToSave;
        while(i > 3) {
            i--;
            //comp.addInstruction(new PUSH(getReg(i)));
            comp.addInstruction(new PUSH(Register.getR(i)));
        }

        pileRegSave.push(regToSave);
    }

    // Restore les registres utilisés precedement
    public void restoreRegister() {
        int regToRestore = pileRegSave.pop();

        int i = 3;
        while(i < regToRestore) {
            //comp.addInstruction(new POP(getReg(i)));
            comp.addInstruction(new POP(Register.getR(i)));
            i++;
        }
    }

    // Création d'un objet
    public void newObject(ClassDefinition cd) {
        DAddr classAddr = new RegisterOffset(cd.getOffset(), Register.GB);
        int totalNumberField;
        int offsetAttr = 1;

        // On obtient le nombre total d'attribut avec les classes parentes
        totalNumberField = cd.getNumberOfFields();

        // On reserve l'espace memoire pour l'objet
        comp.addInstruction(new NEW(1 + totalNumberField, getRetReg()));

        // La première valeur contient le debut de la table des methodes
        // de l'objet
        comp.addInstruction(new LEA(classAddr, r0));
        comp.addInstruction(new STORE(r0, new RegisterOffset(0, getRetReg())));

        /*
        // On fixe l'oprérande des attributs de la methode
        for (ExpDefinition attr : cd.getMembers().getHashMap().values()) {
          // Les attributs sont placés par rapport au registre
            if(attr.isField()) {
                attr.setOperand(new RegisterOffset(offsetAttr, getRetReg()));
                offsetAttr++;
            }
        }
        */

        // TODO
        // Initialisation de l'objet
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

        comp.addLabel(no_return);
        comp.addInstruction(new WSTR("Erreur : fonction sans retour"));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());

        comp.addLabel(dereferencement_nul);
        comp.addInstruction(new WSTR("Erreur : deferencement nul"));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());
    }

    public Label getLabelNoReturn() {
        return no_return;
    }
    public Label getLabelDereferencementNul() {
        return dereferencement_nul;
    }

    public void addSeparatorComment() {
        comp.addComment("--------------------------------------------------");
    }

    // Fonctions generales
    ////////////////////////////////////////////////////////////////////


}
