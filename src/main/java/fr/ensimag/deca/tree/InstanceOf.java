package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Created by clastret on 1/17/17.
 */
public class InstanceOf extends AbstractExpr {

    AbstractIdentifier ident_type;
    AbstractExpr expr;
    public InstanceOf(AbstractIdentifier type, AbstractExpr expr) {
        Validate.notNull(type);
        Validate.notNull(expr);
        this.ident_type= type;
        this.expr=expr;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        expr.decompile(s);
        s.print(" instanceof ");
        ident_type.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        ident_type.prettyPrint(s,prefix, false);
        expr.prettyPrint(s,prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr.iter(f);
        ident_type.iter(f);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type_ref = ident_type.verifyType(compiler);
        Type type = expr.verifyExpr(compiler,localEnv,currentClass);

        if ((!type.isNull() && !type.isClass()) || !type_ref.isClass()) {
            throw new ContextualError("InstanceOf impossible, classes non reconnues", this.getLocation());
        }
        Type t = compiler.getType("boolean");
        this.setType(t);
        return t;
    }


}
