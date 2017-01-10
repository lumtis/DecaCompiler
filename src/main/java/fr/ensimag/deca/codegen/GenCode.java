package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.DAddr;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.deca.*;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.Identifier;
import fr.ensimag.ima.pseudocode.*;
import static fr.ensimag.ima.pseudocode.Register.*;
import java.util.Set;
import java.util.Stack;


public class GenCode {
    private DecacCompiler comp;
    private GenCodeVar gcv;

    private Set<Identifier> listeVar; //liste des variables globales/locales
    private Stack<GenCodeVar> pileListeVar; //pile qui contient les listes de var
   
    private int labelIndex = 0;
    private final int  taillePile=20;
    private Label pile_pleine= newLabel();

    private GPRegister tmpReg;
    private GPRegister retReg;

    public GenCode(DecacCompiler comp , GenCodeVar gcv) {
        this.comp = comp;
        this.gcv=gcv;
        this.listeVar=gcv.getListeVar();

        this.pileListeVar= new Stack<GenCodeVar>();

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
        //à compléter les autres erreurs possibles à la fin


    }

    /* Ajoute les declarations de variable au programme */

    /*Permet de remplir la liste de correspondance et stocker les 
          valeurs des variables dans les registres GB en meme temps*/
    public void initDecla(){
        comp.addComment("variables globales");
        for (Identifier v: listeVar){
            gcv.ajoutElement(v); //ajout de la variable v à la table de correspondance
            comp.addInstruction(new LOAD(v.getVariableDefinition().getOperand(),retReg)); //on load sa valeur
            comp.addInstruction(new STORE(retReg,new RegisterOffset(gcv.obtenirIndice(v),GB))); 
            //on store sa valeur dans x(GB) avec x l'indice de v dans la table
        }
    }    
}
