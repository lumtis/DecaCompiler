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
        expr_cast.decompile(s);
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
        ident_type.verifyType(compiler);
        Type type_ref = compiler.getType(this.ident_type.getName());
        Type type_casted = expr_cast.verifyExpr(compiler,localEnv,currentClass);

        if (type_casted.isVoid() || !cast_compatible(type_ref, type_casted)) {
            throw new ContextualError("Cast impossible", this.getLocation());
        }
        this.setType(type_ref);
        return type_ref;

    }

    private boolean cast_compatible(Type typeA, Type typeB) throws ContextualError {
        return (assign_compatible(typeA, typeB) || assign_compatible(typeB, typeA));
    }

    private boolean assign_compatible(Type typeA, Type typeB) throws ContextualError {

        if(typeB.isFloat() && typeA.isInt() ) {
            return true;
        }
        if (!typeA.isClass() || !typeB.isClass()) {
            return false;
        }
        ClassType classTypeA= typeA.asClassType("Cast impossible.",this.getLocation());
        ClassType classTypeB= typeB.asClassType("Cast impossible.",this.getLocation());
        if(!classTypeB.isSubClassOf(classTypeA)) {
            return false;
        }
        return true;

    }
}
