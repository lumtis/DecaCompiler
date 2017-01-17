package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Created by clastret on 1/17/17.
 */
public class New extends AbstractExpr {

    AbstractIdentifier class_ident;
    public New(AbstractIdentifier ident){
        Validate.notNull(ident);
        this.class_ident = ident;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Pas encore implémenté");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        class_ident.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        class_ident.prettyPrint(s,prefix, true);
    }


    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
        class_ident.verifyType(compiler);
        Type type_cour = compiler.getType(this.class_ident.getName());
        if (type_cour== null || !type_cour.isClass()) {
            throw new ContextualError("Classe non reconnue", this.getLocation());
        }
        this.setType(type_cour);
        return type_cour;

    }
}
