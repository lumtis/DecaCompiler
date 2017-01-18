package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

/**
 * Created by buthodgt on 1/17/17.
 */
public class MethodCall extends AbstractMemberCall {

    private AbstractIdentifier methodName;
    private ListExpr arguments;

    public MethodCall(AbstractExpr objectName, AbstractIdentifier methodName,
                      ListExpr arguments) {
        super(objectName);
        this.methodName = methodName;
        this.arguments = arguments;
    }


    @Override
    public Type verifyMember(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass,
                             ClassType typeObject) throws ContextualError {
        Type methType = methodName.verifyExpr(compiler, typeObject.getDefinition().getMembers(), typeObject.getDefinition());
        if (!methodName.getExpDefinition().isMethod()) {
            throw new ContextualError("Une méthode est attendue.", methodName.getLocation());
        }
        this.arguments = arguments.verifyArgs(compiler, localEnv, currentClass, methodName.getMethodDefinition());
        return methType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Pas encore implémenté.");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        getObjectName().prettyPrint(s, prefix, false);
        methodName.prettyPrint(s,prefix,false);
        arguments.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        methodName.iter(f);
        arguments.iter(f);
    }
}
