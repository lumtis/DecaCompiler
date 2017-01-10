package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.*;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.ima.pseudocode.*;
import static fr.ensimag.ima.pseudocode.Register.*;
import java.util.LinkedList;
import java.util.Set;


public class GenCode {
    private DecacCompiler comp;
    private GenCodeVar gcv;
    private Set<DVal> listeVar; //liste des variables globales/locales
    private LinkedList<GenCodeVar> pile; //pile qui contient les listes de var

    private int labelIndex = 0;
    private final int  taillePile=20;
    private Label pile_pleine= newLabel();


    private Set <DVal> listeVar; //liste des variables globales


    private GPRegister tmpReg;
    private GPRegister retReg;

    public GenCode(DecacCompiler comp , GenCodeVar gcv) {
        this.comp = comp;
        this.gcv=gcv;
        this.listeVar=gcv.getListeVar();
        this.pile= new LinkedList<GenCodeVar>();

        // Le registre qui contient le retour d'une expression est le 2
        retReg = new GPRegister("R2", 2);

        // Le registre qui contient le une valeur temporaire pour une expression
        // binaire est le 3
        tmpReg = new GPRegister("R3", 3);
    }


    public GPRegister getTmpReg()
    {
        return tmpReg;
    }

    public GPRegister getRetReg()
    {
        return retReg;
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
        initDecla(); //initialisation des variables globales
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

        //ajout des variables dans listeVar qui manque
        GenCodeVar gcv=new GenCodeVar(listeVar);


        /*  valeurs des variables dans les registres GB en meme temps */
        for (DVal s: listeVar){
            gcv.ajoutElement(s);

            comp.addInstruction(new LOAD(s,retReg));
            comp.addInstruction(new STORE(retReg,new RegisterOffset(gcv.obtenirIndice(s),GB)));
            // stockage de la valeur de la variable s dans GB
        }
    }

}
