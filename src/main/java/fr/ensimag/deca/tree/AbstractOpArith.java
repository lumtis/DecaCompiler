package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl35
 * @date 01/01/2017
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        AbstractExpr leftOp = this.getLeftOperand();
        AbstractExpr rightOp = this.getRightOperand();
        Type typeL = leftOp.verifyExpr(compiler,localEnv,currentClass);
        Type typeR = rightOp.verifyExpr(compiler, localEnv, currentClass);
        if (!(typeL.isInt() || typeL.isFloat())) {
            throw new ContextualError("Type non compatible avec opérateur arithmétique.",
                        leftOp.getLocation());
        }
        if (!(typeR.isInt() || typeR.isFloat())) {
            throw new ContextualError("Type non compatible avec opérateur arithmétique.",
                    rightOp.getLocation());
        }
        //A partir d'ici, les deux types sont soit des int, soit des float
        //Si typeL et typeR sont des int, opType est un int, sinon opType est un float.
        Type opType = typeL;
        if (typeR.isFloat()) {
            opType = typeR;
        }
        this.setType(opType);
        return opType;
    }
}
