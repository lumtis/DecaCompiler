package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import jdk.internal.dynalink.linker.ConversionComparator;
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
            ClassType typeClass = t.asClassType("Type is not a class",this.getLocation());
            ClassType typeClassReturn = returnType.asClassType("Type is not a class",this.getLocation());
            if (!typeClass.isSubClassOf(typeClassReturn)) {
                throw new ContextualError("Return type not compatible", this.getLocation());
            }
        }
        else if (t.isInt() && returnType.isFloat()) {
            ConvFloat conv = new ConvFloat(getReturnExpr());
            conv.verifyExpr(compiler, localEnv, currentClass);
            getReturnExpr().setLocation(getReturnExpr().getLocation());
            setReturnExpr(conv);
        }
        else if (!t.sameType(returnType)) {
            throw new ContextualError("Return type not compatible", this.getLocation());
        }
    }
}
