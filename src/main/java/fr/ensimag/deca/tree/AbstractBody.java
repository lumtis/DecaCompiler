package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

/**
 * Created by buthodgt on 1/16/17.
 */
public abstract class AbstractBody extends Tree {

    protected abstract void codeGenMain(DecacCompiler compiler);


    /**
     * Implements non-terminal "body" of [SyntaxeContextuelle] in pass 3
     */
    protected abstract void verifyBody(DecacCompiler compiler, EnvironmentExp env_exp, ClassDefinition currentClass,
                                       Type returnType) throws ContextualError;
}
