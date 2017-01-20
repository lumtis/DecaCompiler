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

    private Label pile_pleine = new Label("pile_pleine");
    private Label tas_plein = new Label("tas_plein");
    private Label no_return = new Label("no_return");
    private Label dereferencement_nul  = new Label("dereferencement_nul");
    private Label divided_zero = new Label("divided_zero");
    private Label objectEquals = new Label("Object.equals");


    // Fonction utilitaires pour obtenir le nom d'un registre en fonction de
    // son numero
    private String getRegName(int n) {
        return "R" + String.valueOf(n);
    }
    //private GPRegister getReg(int n) {
    //    return new GPRegister(getRegName(n), n);
    //}


    public GPRegister getR0() {
        return r0;
    }

    public GPRegister getR1() {
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

    public Label getInitLabel(Identifier c) {
        String className = c.getName().getName();

        return new Label(className + ".init.init");
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

                    // Si c'est une méthode, on tente de l'inserer dans la table des méthodes
                    if(entry.getValue().isMethod()) {
                        String name = entry.getKey().getName();
                        MethodDefinition method = (MethodDefinition)entry.getValue();
                        Label lab = new Label(cDef.getType().getName().getName() + "." + name); // nomClass.nomMethod

                        // On vérifie que la méthode n'a pas déjà été redéfinie
                        if(redef[method.getIndex()] == false) {
                            comp.addInstruction(new LOAD(new LabelOperand(lab), r0));
                            comp.addInstruction(new STORE(r0, new RegisterOffset(indexGB + method.getIndex(), Register.GB)));
                            redef[method.getIndex()] = true;
                        }
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

        // Pour la methode object il n'y a rien a initialiser
        addSeparatorComment();
        comp.addComment(((Identifier)comp.getObject()).getName().getName() + ".init");
        comp.addLabel(getInitLabel((Identifier)comp.getObject()));
        comp.addInstruction(new RTS());

        // On initialise la methode Object.equals
        generateObjectEquals();

        for (AbstractDeclClass ac:lc){
            DeclClass c = (DeclClass)ac;

            addSeparatorComment();
            comp.addComment(c.getClassName().getName().getName());
            addSeparatorComment();

            // On fixe l'operande des attributs et leur initialisation
            addSeparatorComment();
            comp.addComment(c.getClassName().getName().getName() + ".init");
            comp.addLabel(getInitLabel(c.getClassName()));

            for(AbstractDeclField af : c.getFields().getList()) {
                DeclField f = (DeclField)af;
                DAddr addr = new RegisterOffset(f.getFieldName().getFieldDefinition().getIndex(), getRetReg());

                // Si l'attribut a une initalisation, on la realise
                if(f.getInitialization().hasInitialization()) {
                    comp.addInstruction(new PUSH(getRetReg()));
                    f.getInitialization().codeGenInit(comp, this);
                    comp.addInstruction(new LOAD(getRetReg(), getR0()));
                    comp.addInstruction(new POP(getRetReg()));
                    comp.addInstruction(new STORE(getR0(), addr));
                }
                else {
                    // Sinon on met une valeur par defaut dans l'attributs
                    if(f.getFieldName().getFieldDefinition().getType().isInt()) {
                        comp.addInstruction(new LOAD(new ImmediateInteger(0), getR0()));
                        comp.addInstruction(new STORE(getR0(), addr));
                    }
                    else if(f.getFieldName().getFieldDefinition().getType().isFloat()) {
                        comp.addInstruction(new LOAD(new ImmediateFloat(0), getR0()));
                        comp.addInstruction(new STORE(getR0(), addr));
                    }
                    else if(f.getFieldName().getFieldDefinition().getType().isBoolean()) {
                        comp.addInstruction(new LOAD(new ImmediateInteger(0), getR0()));
                        comp.addInstruction(new STORE(getR0(), addr));
                    }
                    else if(f.getFieldName().getFieldDefinition().getType().isClass()) {
                        comp.addInstruction(new LOAD(new NullOperand(), getR0()));
                        comp.addInstruction(new STORE(getR0(), addr));
                    }
                }
            }

            // On apelle l'initialisation parente pour initialiser les attributs hérités
            comp.addInstruction(new BSR(getInitLabel(c.getParent())));
            comp.addInstruction(new RTS());

            // Creation des methodes de la class
            for (AbstractDeclMethod am:c.getMethods().getList()){
                DeclMethod m = (DeclMethod)am;
                generateMethod(c, m);
            }
        }
    }

    public void generateObjectEquals() {
        Label equal = newLabel("objEquals");
        Label end = newLabel("objEnd");

        comp.addLabel(objectEquals);

        // On compare l'objet et le premier argument
        comp.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), getR0()));
        comp.addInstruction(new LOAD(new RegisterOffset(-3, Register.LB), getR1()));
        comp.addInstruction(new CMP(getR0(), getR1()));
        comp.addInstruction(new BEQ(equal));

        // Pas egal
        comp.addInstruction(new LOAD(0, getRetReg()));
        comp.addInstruction(new BRA(end));

        // egal
        comp.addLabel(equal);
        comp.addInstruction(new LOAD(1, getRetReg()));

        comp.addLabel(end);
        comp.addInstruction(new RTS());
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
        m.getBody().generateMethod(comp, this, m.getMethodName().getMethodDefinition().getType().isVoid());

        comp.addInstruction(new RTS());
    }


    // Sauvegarde les registres déjà utilisés et remet à 0 le gestionnaire
    // de registre
    public void saveRegister() {
        int i = 3;
        while(i < maxReg) {
            comp.addInstruction(new PUSH(Register.getR(i)));
            i++;
        }
    }

    // Restore les registres utilisés precedement
    public void restoreRegister() {
        int i = maxReg - 1;
        while(i >= 3) {
            comp.addInstruction(new POP(Register.getR(i)));
            i--;
        }
    }


    // Création d'un objet
    public void newObject(Identifier c) {
        ClassDefinition cd = c.getClassDefinition();
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

        comp.addInstruction(new BSR(getInitLabel(c)));
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

        addSeparatorComment();
        comp.addLabel(pile_pleine);
        comp.addInstruction(new WSTR("Error: full stack."));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());

        addSeparatorComment();
        comp.addLabel(tas_plein);
        comp.addInstruction(new WSTR("Error : impossible allocation, full heap."));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());

        addSeparatorComment();
        comp.addLabel(no_return);
        comp.addInstruction(new WSTR("Error : function without return."));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());

        addSeparatorComment();
        comp.addLabel(dereferencement_nul);
        comp.addInstruction(new WSTR("Error : null dereferencing."));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());

        addSeparatorComment();
        comp.addLabel(divided_zero);
        comp.addInstruction(new WSTR("Error : divided by zero."));
        comp.addInstruction(new WNL());
        comp.addInstruction(new ERROR());
    }

    public Label getLabelNoReturn() {
        return no_return;
    }
    public Label getLabelDereferencementNul() {
        return dereferencement_nul;
    }
    public Label getDividedZero() {
        return divided_zero;
    }

    public void addSeparatorComment() {
        comp.addComment("--------------------------------------------------");
    }

    // Fonctions generales
    ////////////////////////////////////////////////////////////////////


}
