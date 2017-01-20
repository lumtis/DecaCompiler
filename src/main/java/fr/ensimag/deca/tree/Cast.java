package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Created by clastret on 1/17/17.
 */
public class Cast extends AbstractExpr{

    AbstractIdentifier ident_type;
    AbstractExpr expr_cast;
    public Cast(AbstractIdentifier type, AbstractExpr expr_cast) {
        Validate.notNull(type);
        Validate.notNull(expr_cast);
        this.ident_type= type;
        this.expr_cast=expr_cast;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        ident_type.decompile(s);
        s.print(") ");
        s.print("(");
        expr_cast.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        ident_type.prettyPrint(s,prefix, false);
        expr_cast.prettyPrint(s,prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr_cast.iter(f);
        ident_type.iter(f);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type_ref = ident_type.verifyType(compiler);
        Type type_casted = expr_cast.verifyExpr(compiler,localEnv,currentClass);

        if (type_casted.isVoid() || !type_ref.castCompatibleTo(type_casted)) {
            throw new ContextualError("Impossible cast", this.getLocation());
        }
        this.setType(type_ref);
        return type_ref;

    }
}
