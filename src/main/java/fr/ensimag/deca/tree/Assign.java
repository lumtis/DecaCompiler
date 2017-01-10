package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl35
 * @date 01/01/2017
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t = compiler.getType("void");
        this.setType(t);
        AbstractExpr leftOp = this.getLeftOperand();
        Type typeL = leftOp.verifyExpr(compiler,localEnv,currentClass);
        setRightOperand(getRightOperand().verifyRValue(compiler, localEnv, currentClass, typeL));
        return t;
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        DAddr addrVar = gc.getVariablesG().getVariable((Identifier)getLeftOperand());

        // On réalise l'expression
        getRightOperand().codeGenInst(compiler, gc);

        // Si l'instruction est fausse on saute directement au else
        compiler.addInstruction(new STORE(gc.getRetReg(), addrVar));
    }

}
