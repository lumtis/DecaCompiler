package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;

/**
 * Created by buthodgt on 1/13/17.
 */
public abstract class AbstractDeclParam extends Tree {
    protected abstract Type verifyDeclParam(DecacCompiler compiler, EnvironmentExp env_exp, ClassDefinition currentClass)
            throws ContextualError;
}
