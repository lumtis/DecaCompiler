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
        AbstractExpr leftOp = this.getLeftOperand();
        Type typeL = leftOp.verifyExpr(compiler,localEnv,currentClass);
        setRightOperand(getRightOperand().verifyRValue(compiler, localEnv, currentClass, typeL));
        this.setType(typeL);
        return typeL;
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        DAddr addrVar;

        // On réalise l'expression
        getRightOperand().codeGenInst(compiler, gc);

        // On verifie s'il s'agit du champ d'une classe (adresse dynamique)
        if(getLeftOperand().isFieldCall()) {
            // On sauvegarde l'expression precedante
            compiler.addInstruction(new PUSH(gc.getRetReg()));
            ((FieldCall)getLeftOperand()).codeGenAddr(compiler, gc);
            compiler.addInstruction(new POP(gc.getR0()));

            compiler.addInstruction(new STORE(gc.getR0(), new RegisterOffset(0, gc.getRetReg())));
        }
        else {
            Identifier res = (Identifier)getLeftOperand();

            if(res.getDefinition().isParam()) {
                addrVar = new RegisterOffset(res.getParamDefinition().getOffset(), Register.LB);
                compiler.addInstruction(new STORE(gc.getRetReg(), addrVar));
            }
            else if(res.getDefinition().isField()) {
                // L'objet est toujours dans -2(LB)
                compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), gc.getR0()));
                compiler.addInstruction(new STORE(gc.getRetReg(), new RegisterOffset(res.getFieldDefinition().getIndex(), gc.getR0())));
            }
            else {
                // S'il s'agit d'une simple variable on recupere directement son adresse
                addrVar = gc.getAddrVar(res);
                compiler.addInstruction(new STORE(gc.getRetReg(), addrVar));
            }
        }
    }
}
