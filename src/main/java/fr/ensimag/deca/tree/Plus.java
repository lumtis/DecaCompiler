package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;



/**
 * @author gl35
 * @date 01/01/2017
 */
public class Plus extends AbstractOpArith {


    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        //gc.add(this.getLeftOperand(),R);
    }
}
