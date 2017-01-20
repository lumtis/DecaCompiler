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
            throw new ContextualError("Type not compatible with arithmetic operator",
                        leftOp.getLocation());
        }
        if (!(typeR.isInt() || typeR.isFloat())) {
            throw new ContextualError("Type not compatible with arithmetic operator",
                    rightOp.getLocation());
        }
        //A partir d'ici, les deux types sont soit des int, soit des float
        //Il faut faire des ConvFloat si nécessaire
        if (typeL.isFloat() && typeR.isInt()) {
            ConvFloat conv = new ConvFloat(this.getRightOperand());
            conv.verifyExpr(compiler, localEnv, currentClass);
            conv.setLocation(this.getRightOperand().getLocation());
            this.setRightOperand(conv);
        }
        else if (typeR.isFloat() && typeL.isInt()) {
            ConvFloat conv = new ConvFloat(this.getLeftOperand());
            conv.verifyExpr(compiler, localEnv, currentClass);
            conv.setLocation(this.getLeftOperand().getLocation());
            this.setLeftOperand(conv);
        }
        Type t;
        if (typeR.isFloat() || typeL.isFloat()) {
            t = compiler.getType("float");
        } else {
            t = compiler.getType("int");
        }
        this.setType(t);
        return t;
    }
}
