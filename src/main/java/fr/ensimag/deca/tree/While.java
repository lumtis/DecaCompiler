package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.*;

/**
 *
 * @author gl35
 * @date 01/01/2017
 */
public class While extends AbstractInst {
    private AbstractExpr condition;
    private ListInst body;

    public AbstractExpr getCondition() {
        return condition;
    }

    public ListInst getBody() {
        return body;
    }

    public While(AbstractExpr condition, ListInst body) {
        Validate.notNull(condition);
        Validate.notNull(body);
        this.condition = condition;
        this.body = body;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        Label debutWhile = gc.newLabel();
        Label bloc = gc.newLabel();
        Label finWhile = gc.newLabel();

        compiler.addLabel(debutWhile);

        // On réalise l'expression et on la test
        condition.codeGenInst(compiler, gc);
        compiler.addInstruction(new CMP(1, gc.getRetReg()));
        compiler.addInstruction(new BEQ(bloc));
        compiler.addInstruction(new BRA(finWhile));

        // Bloc du while
        compiler.addLabel(bloc);
        body.codeGenListInst(compiler, gc);
        compiler.addInstruction(new BRA(debutWhile));

        compiler.addLabel(finWhile);
    }


    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.condition.verifyCondition(compiler,localEnv, currentClass);
        EnvironmentExp env_exp = new EnvironmentExp(localEnv);
        this.body.verifyListInst(compiler,env_exp,currentClass,returnType);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("while (");
        getCondition().decompile(s);
        s.println(") {");
        s.indent();
        getBody().decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        condition.iter(f);
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        body.prettyPrint(s, prefix, true);
    }

}
