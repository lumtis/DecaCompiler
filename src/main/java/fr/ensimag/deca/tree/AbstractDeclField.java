package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Created by buthodgt on 1/12/17.
 */
public abstract class AbstractDeclField extends Tree {

    protected abstract void verifyDeclFieldHeader(DecacCompiler compiler, ClassDefinition currentClass)
            throws ContextualError;

    protected abstract void verifyDeclFieldInit(DecacCompiler compiler, ClassDefinition currentClass)
            throws ContextualError;
}
