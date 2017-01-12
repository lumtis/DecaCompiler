package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 *
 * @author gl35
 * @date 01/01/2017
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        super.codeGenInst(compiler, gc);

        if(gc.isExprFloat()) {
            compiler.addInstruction(new DIV(gc.getRetReg(), gc.getTmpReg()));
            compiler.addInstruction(new LOAD(gc.getTmpReg(), gc.getRetReg()));
        }
        else {
            compiler.addInstruction(new QUO(gc.getRetReg(), gc.getTmpReg()));
            compiler.addInstruction(new LOAD(gc.getTmpReg(), gc.getRetReg()));
        }
    }

}
