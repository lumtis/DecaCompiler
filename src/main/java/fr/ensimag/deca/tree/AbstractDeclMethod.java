package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Created by buthodgt on 1/13/17.
 */
public abstract class AbstractDeclMethod extends Tree {

    protected abstract void verifyDeclMethodHeader(DecacCompiler compiler, ClassDefinition classDef)
            throws ContextualError;
}
