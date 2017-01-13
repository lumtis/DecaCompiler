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
        GPRegister tmp;   // registre temporaire pour les expressions binaires
        super.codeGenInst(compiler, gc);

        tmp = gc.popTmpReg();

        if(gc.isExprFloat()) {
            compiler.addInstruction(new DIV(gc.getRetReg(), tmp));
            compiler.addInstruction(new LOAD(tmp, gc.getRetReg()));
        }
        else {
            compiler.addInstruction(new QUO(gc.getRetReg(), tmp));
            compiler.addInstruction(new LOAD(tmp, gc.getRetReg()));
        }
    }

}
