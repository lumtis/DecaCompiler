package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl35
 * @date 01/01/2017
 */
public class ListExpr extends TreeList<AbstractExpr> {

    public void verifyArgs(DecacCompiler compiler, EnvironmentExp localenv, ClassDefinition currentClass,
                           MethodDefinition methDef) throws ContextualError{
        Signature sign = methDef.getSignature();
        int i = 0;
        for (AbstractExpr expr : getList()) {
            expr = expr.verifyRValue(compiler, localenv, currentClass, sign.paramNumber(i));
            i++;
        }
    }

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractExpr expr : getList()) {
            expr.decompile(s);
        }
    }
}
