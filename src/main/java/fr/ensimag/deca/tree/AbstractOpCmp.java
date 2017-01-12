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
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        //A modifier pour les classes
        AbstractExpr leftOp = this.getLeftOperand();
        AbstractExpr rightOp = this.getRightOperand();
        Type typeL = leftOp.verifyExpr(compiler,localEnv,currentClass);
        Type typeR = rightOp.verifyExpr(compiler, localEnv, currentClass);
        if (!(typeL.isInt() || typeL.isFloat()) || !(typeR.isInt() || typeR.isFloat())) {
            throw new ContextualError("Types non compatibles pour comparaison", this.getLocation());
        }
        Type t = compiler.getType("boolean");
        this.setType(t);
        return t;
    }


}
