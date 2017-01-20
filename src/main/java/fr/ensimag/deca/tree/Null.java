package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.io.PrintStream;

/**
 * Created by clastret on 1/17/17.
 */
public class Null extends AbstractExpr {


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("null");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        Type t = new NullType(compiler.getSymbols().create("null"));
        this.setType(t);
        return t;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        compiler.addInstruction(new LOAD(new NullOperand(), gc.getRetReg()));
    }
}
