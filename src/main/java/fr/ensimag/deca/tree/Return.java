package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import jdk.nashorn.internal.runtime.Context;

import java.io.PrintStream;

/**
 * Created by buthodgt on 1/17/17.
 */
public class Return extends AbstractReturn {

    public Return(AbstractExpr returnExpr) {
        super(returnExpr);
    }

    @Override
    public void verifyReturn(DecacCompiler compiler, EnvironmentExp localEnv,
                        ClassDefinition currentClass, Type returnType)
            throws ContextualError{
        Type t = this.getReturnExpr().verifyExpr(compiler, localEnv, currentClass);
        if (t.isClass() && returnType.isClass()) {
            ClassType typeClass = t.asClassType("Type n'est pas une classe.",this.getLocation());
            ClassType typeClassReturn = returnType.asClassType("Type n'est pas une classe.",this.getLocation());
            if (!typeClass.isSubClassOf(typeClassReturn)) {
                throw new ContextualError("Type de retour non compatible.", this.getLocation());
            }
        }
        else if (!t.sameType(returnType)) {
            throw new ContextualError("Le type de retour n'est pas bon.", this.getLocation());
        }
    }
}
