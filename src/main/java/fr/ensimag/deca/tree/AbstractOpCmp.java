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
        AbstractExpr leftOp = this.getLeftOperand();
        AbstractExpr rightOp = this.getRightOperand();
        Type typeL = leftOp.verifyExpr(compiler,localEnv,currentClass);
        Type typeR = rightOp.verifyExpr(compiler, localEnv, currentClass);
        if (!(typeL.isInt() || typeL.isFloat()) || !(typeR.isInt() || typeR.isFloat())) {
            throw new ContextualError("Types non compatibles pour comparaison", this.getLocation());
        }
        if (typeL.isFloat() && typeR.isInt()) {
            ConvFloat conv = new ConvFloat(rightOp);
            conv.verifyExpr(compiler, localEnv, currentClass);
            conv.setLocation(rightOp.getLocation());
            this.setRightOperand(conv);
        }
        else if (typeL.isInt() && typeR.isFloat()) {
            ConvFloat conv = new ConvFloat(leftOp);
            conv.verifyExpr(compiler, localEnv, currentClass);
            conv.setLocation(leftOp.getLocation());
            this.setLeftOperand(conv);
        }
        Type t = compiler.getType("boolean");
        this.setType(t);
        return t;
    }


}
