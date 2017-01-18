package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.io.PrintStream;

/**
 * Created by clastret on 1/17/17.
 */
public class This extends AbstractExpr {

    ClassDefinition current_class;

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("this");
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
        this.current_class = currentClass;
        Type type = currentClass.getType();
        this.setType(type);
        return type;
    }
    @Override
    String prettyPrintNode() {
        return "This";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        // L'adresse de l'objet est toujours dans -2(LB)
        compiler.addInstruction(new LOAD(new RegisterOffset(-2, Register.LB), gc.getRetReg()));
    }
}
