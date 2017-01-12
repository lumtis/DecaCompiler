package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl35
 * @date 01/01/2017
 */
public class Main extends AbstractMain {
    private static final Logger LOG = Logger.getLogger(Main.class);

    private ListDeclVar declVariables;
    private ListInst insts;
    public Main(ListDeclVar declVariables,
            ListInst insts) {
        Validate.notNull(declVariables);
        Validate.notNull(insts);
        this.declVariables = declVariables;
        this.insts = insts;
    }

    @Override
    protected void verifyMain(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify Main: start");
        //A modifier plus tard
        EnvironmentExp env_exp = compiler.getEnvExpPredef();
        LOG.debug("verify ListDeclVar: start");
        this.declVariables.verifyListDeclVariable(compiler,env_exp,null);
        LOG.debug("verify ListDeclVar: end");
        LOG.debug("verify ListInst: start");
        this.insts.verifyListInst(compiler,env_exp,null,compiler.getType("void"));
        LOG.debug("verify ListInst: end");
        LOG.debug("verify Main: end");
    }
    @Override
    protected void codeGenMain(DecacCompiler compiler) {
        GenCode gc = new GenCode(compiler);

        // On initialise le début du code
        gc.initProgram();
        gc.initGlobalVar(this.declVariables.getList());

        compiler.addComment("Beginning of main instructions:");
        insts.codeGenListInst(compiler, gc);
        gc.terminateProgram();
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
