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

import java.io.PrintStream;

/**
 * Created by buthodgt on 1/16/17.
 */
public class Body extends AbstractBody{
    private static final Logger LOG = Logger.getLogger(Main.class);

    private ListDeclVar declVariables;
    private ListInst insts;

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
        LOG.debug("verify Body: start");
        LOG.debug("verify ListDeclVar: start");
        this.declVariables.verifyListDeclVariable(compiler,env_exp,currentClass);
        LOG.debug("verify ListDeclVar: end");
        LOG.debug("verify ListInst: start");
        boolean returnOk = this.insts.verifyListInst(compiler,env_exp,currentClass, returnType);
        if (!returnOk && !returnType.isVoid()) {
            throw new ContextualError("Aucun type de retour, "+returnType.getName()+" attendu.", this.getLocation());
        }
        LOG.debug("verify ListInst: end");
        LOG.debug("verify Body: end");
    }
    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        throw new UnsupportedOperationException("Pas encore fait");
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
}
