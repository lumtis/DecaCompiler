package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl35
 * @date 01/01/2017
 */
public class ConvFloat extends AbstractUnaryExpr {
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
        Type t = compiler.getType("float");
        this.setType(t);
        return t;
    }


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
