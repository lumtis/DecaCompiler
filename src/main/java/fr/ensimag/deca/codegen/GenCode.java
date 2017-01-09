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

    private int labelIndex = 0;
    private final int  taillePile=20;
    private Label pile_pleine= newLabel();
    private Set <String> listeVar; //liste des variables globales

    private GPRegister tmpReg;
    private GPRegister retReg;

    public GenCode(DecacCompiler comp) {
        this.comp = comp;

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

    // TODO : Ajouter les instructions restantes
    //...

}
