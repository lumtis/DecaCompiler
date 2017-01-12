package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl35
 * @date 01/01/2017
 */
public abstract class AbstractOpBool extends AbstractBinaryExpr {

    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        AbstractExpr leftOp = this.getLeftOperand();
        AbstractExpr rightOp = this.getRightOperand();
        leftOp.verifyCondition(compiler, localEnv, currentClass);
        rightOp.verifyCondition(compiler, localEnv, currentClass);
        Type bool = compiler.getType("boolean");
        this.setType(bool);
        return bool;
    }

}
