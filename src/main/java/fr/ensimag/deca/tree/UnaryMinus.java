package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * @author gl35
 * @date 01/01/2017
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        AbstractExpr expr = this.getOperand();
        Type t = expr.verifyExpr(compiler, localEnv, currentClass);
        if (!t.isFloat() && !t.isInt()) {
            throw new ContextualError("Type non valide pour MinusUnaire", expr.getLocation());
        }
        this.setType(t);
        return t;
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        super.codeGenInst(compiler, gc);

        compiler.addInstruction(new OPP(gc.getRetReg(), gc.getRetReg()));
    }

}
