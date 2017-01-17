package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.*;


/**
 * Full if/else if/else statement.
 *
 * @author gl35
 * @date 01/01/2017
 */
public class IfThenElse extends AbstractInst {

    private final AbstractExpr condition;
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    protected boolean verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.condition.verifyCondition(compiler,localEnv,currentClass);
        EnvironmentExp env_exp_then = new EnvironmentExp(localEnv);
        EnvironmentExp env_exp_else = new EnvironmentExp(localEnv);
        //returnType surement à modifier.
        boolean returnOk = true;
        returnOk &= this.thenBranch.verifyListInst(compiler,env_exp_then,currentClass,returnType);
        returnOk &= this.elseBranch.verifyListInst(compiler,env_exp_else, currentClass,returnType);
        return returnOk;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        Label sinon = gc.newLabel("IfSinon");
        Label fin = gc.newLabel("IfFin");

        // On réalise l'expression
        condition.codeGenInst(compiler, gc);

        // Si l'instruction est fausse on saute directement au else
        compiler.addInstruction(new CMP(0, gc.getRetReg()));
        compiler.addInstruction(new BEQ(sinon));

        // then
        thenBranch.codeGenListInst(compiler, gc);
        compiler.addInstruction(new BRA(fin));

        // else
        compiler.addLabel(sinon);
        elseBranch.codeGenListInst(compiler, gc);

        compiler.addLabel(fin);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("if (");
        condition.decompile(s);
        s.println(") {");
        s.indent();
        this.thenBranch.decompile(s);
        s.unindent();
        s.println("else {");
        s.indent();
        this.elseBranch.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
}
