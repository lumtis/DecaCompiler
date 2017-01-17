package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.io.PrintStream;

/**
 * Created by buthodgt on 1/17/17.
 */
public abstract class AbstractReturn extends AbstractInst {
    private AbstractExpr returnExpr;

    public AbstractReturn(AbstractExpr returnExpr) {
        this.returnExpr = returnExpr;
    }

    public AbstractExpr getReturnExpr() {
        return this.returnExpr;
    }

    public abstract void verifyReturn(DecacCompiler compiler, EnvironmentExp localEnv,
                                      ClassDefinition currentClass, Type returnType)
            throws ContextualError;

    @Override
    protected boolean verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
                              ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.verifyReturn(compiler,localEnv,currentClass, returnType);
        return true;
    }

    protected void codeGenPrint(DecacCompiler compiler, GenCode gc) {
        throw new UnsupportedOperationException("Lucas le hmar se traine dessus, il n'a pas encore fait cette partie.");
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        throw new UnsupportedOperationException("Lucas le hmar se traine dessus, il n'a pas encore fait cette partie.");
    }

    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = returnExpr.getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        returnExpr.iter(f);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("return ");
        returnExpr.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        returnExpr.prettyPrint(s, prefix, true);
    }
}
