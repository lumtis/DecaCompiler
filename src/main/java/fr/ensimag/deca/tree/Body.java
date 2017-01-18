package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.io.PrintStream;

/**
 * Created by buthodgt on 1/16/17.
 */
public class Body extends AbstractBody{

    private ListDeclVar declVariables;
    private ListInst insts;
    private boolean isVoid = true;

    public Body(ListDeclVar declVariables,
                ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyBody(DecacCompiler compiler, EnvironmentExp env_exp, ClassDefinition currentClass,
                              Type returnType)
            throws ContextualError {
        this.declVariables.verifyListDeclVariable(compiler,env_exp,currentClass);
        boolean returnOk = this.insts.verifyListInst(compiler,env_exp,currentClass, returnType);
        if (!returnOk && !returnType.isVoid()) {
            throw new ContextualError("Aucun type de retour, "+returnType.getName()+" attendu.", this.getLocation());
        }
    }
    @Override
    protected void codeGenBody(DecacCompiler compiler, GenCode gc) {

        gc.initLocalVar(this.declVariables.getList());

        // On sauvegarde les registres actuellement utilisés
        compiler.addComment("Save used register:");
        gc.saveRegister();

        compiler.addComment("Beginning of method:");
        insts.codeGenListInst(compiler, gc);

        if(!isVoid) {
            // Si l'instruction doit retourner une valeur alors il y aura un soucis
            compiler.addInstruction(new BRA(gc.getLabelNoReturn()));
        }

        compiler.addLabel(gc.getRetLabel());
        compiler.addComment("Restore register:");
        gc.restoreRegister();
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.println("{");
        s.indent();
        declVariables.decompile(s);
        insts.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        declVariables.iter(f);
        insts.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        declVariables.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
    }

    @Override
    public void generateMethod(DecacCompiler compiler, GenCode gc, boolean isVoid) {
        this.isVoid = isVoid;
        codeGenBody(compiler, gc);
    }
}
