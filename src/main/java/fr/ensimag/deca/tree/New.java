package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Created by clastret on 1/17/17.
 */
public class New extends AbstractExpr {

    private AbstractIdentifier class_ident;
    public New(AbstractIdentifier ident){
        Validate.notNull(ident);
        this.class_ident = ident;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("new ");
        class_ident.decompile(s);
        s.print("()");
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
        Type type_cour = class_ident.verifyType(compiler);
        if (type_cour== null || !type_cour.isClass()) {
            throw new ContextualError("Class not found", this.getLocation());
        }
        this.setType(type_cour);
        return type_cour;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
          Identifier c = (Identifier)class_ident;
          gc.newObject(c);
    }
}
