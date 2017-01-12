package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.*;

/**
 *
 * @author gl35
 * @date 01/01/2017
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        AbstractExpr expr = this.getOperand();
        expr.verifyCondition(compiler, localEnv, currentClass);
        this.setType(expr.getType());
        return expr.getType();
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        Label vrai = gc.newLabel();  // Label lorsque l'expression est vrai
        Label fin = gc.newLabel();   // Label de la fin de l'expression

        super.codeGenInst(compiler, gc);

        compiler.addInstruction(new CMP(0, gc.getRetReg()));
        compiler.addInstruction(new BNE(vrai));

        // L'expression est fausse, on renvoie donc 1
        compiler.addInstruction(new LOAD(1, gc.getRetReg()));
        compiler.addInstruction(new BRA(fin));

        // L'expression est vrai, on renvoie donc 0
        compiler.addLabel(vrai);
        compiler.addInstruction(new LOAD(0, gc.getRetReg()));

        compiler.addLabel(fin);
    }
}
