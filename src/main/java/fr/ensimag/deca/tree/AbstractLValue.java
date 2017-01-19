package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Left-hand side value of an assignment.
 *
 * @author gl35
 * @date 01/01/2017
 */
public abstract class AbstractLValue extends AbstractExpr {

    // Permet de distinguer les fiels call des identifiers
    public boolean isFieldCall() {
        return false;
    }

}
