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
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        Label faux = gc.newLabel();  // Label lorsque l'expression est fausse
        Label fin = gc.newLabel();   // Label de la fin de l'expression

        // On ajoute le code permettant de réaliser l'expression 1
        getLeftOperand().codeGenInst(compiler, gc);

        // Si l'évaluation est fausse on va dans le code correspondant
        compiler.addInstruction(new CMP(0, gc.getRetReg()));
        compiler.addInstruction(new BEQ(fin));

        // On ajoute le code permettant de réaliser l'expression 2
        getRightOperand().codeGenInst(compiler, gc);

        // Si l'évaluation est fausse on va dans le code correspondant
        compiler.addInstruction(new CMP(0, gc.getRetReg()));
        compiler.addInstruction(new BEQ(fin));

        // Comparaison bonne
        compiler.addInstruction(new LOAD(1, gc.getRetReg()));
        compiler.addInstruction(new BRA(fin));

        // Zone faux
        compiler.addLabel(faux);
        compiler.addInstruction(new LOAD(0, gc.getRetReg()));

        // fin
        compiler.addLabel(fin);
    }
}
