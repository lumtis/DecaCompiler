package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.*;

/**
 *
 * @author gl35
 * @date 01/01/2017
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "||";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        Label vrai = gc.newLabel();  // Label lorsque l'expression est fausse
        Label fin = gc.newLabel();   // Label de la fin de l'expression

        // On ajoute le code permettant de réaliser l'expression 1
        getLeftOperand().codeGenInst(compiler, gc);

        // Si l'évaluation est fausse on va dans le code correspondant
        compiler.addInstruction(new CMP(0, gc.getRetReg()));
        compiler.addInstruction(new BNE(vrai));

        // On ajoute le code permettant de réaliser l'expression 2
        getRightOperand().codeGenInst(compiler, gc);

        // Si l'évaluation est fausse on va dans le code correspondant
        compiler.addInstruction(new CMP(0, gc.getRetReg()));
        compiler.addInstruction(new BNE(vrai));

        // Comparaison fausse
        compiler.addInstruction(new LOAD(0, gc.getRetReg()));
        compiler.addInstruction(new BRA(fin));

        // Zone vrai
        compiler.addLabel(vrai);
        compiler.addInstruction(new LOAD(1, gc.getRetReg()));

        // fin
        compiler.addLabel(fin);
    }


}
