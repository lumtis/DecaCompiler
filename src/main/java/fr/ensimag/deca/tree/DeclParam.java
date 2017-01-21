package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;
import javax.naming.Context;
import java.io.PrintStream;

/**
 * Created by buthodgt on 1/13/17.
 */
public class DeclParam extends AbstractDeclParam {
    final private AbstractIdentifier type;
    final private AbstractIdentifier name;

    public DeclParam(AbstractIdentifier type, AbstractIdentifier name) {
        Validate.notNull(type);
        Validate.notNull(name);
        this.type = type;
        this.name = name;
    }

    public AbstractIdentifier getName(){
        return this.name;
    }

    public AbstractIdentifier getType(){
        return this.type;
    }

    @Override
    protected Type verifyDeclParam(DecacCompiler compiler, EnvironmentExp env_exp, ClassDefinition currentClass)
            throws ContextualError {
        Type t = type.verifyType(compiler);
        if (compiler.getType(name.getName()) != null) {
                throw new ContextualError("Parameter name used is a type", this.getLocation());
        }
        name.setDefinition(new ParamDefinition(t, this.getLocation()));
        try {
            env_exp.declare(name.getName(), name.getExpDefinition());
        }
        catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("Parameter with same name already defined", this.getLocation());
        }
        return t;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        name.decompile(s);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        name.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        name.prettyPrint(s, prefix, true);
    }
}
