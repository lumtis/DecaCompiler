package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by buthodgt on 1/17/17.
 */
public class MethodCall extends AbstractMemberCall {

    private AbstractIdentifier methodName;
    private ListExpr arguments;

    public MethodCall(AbstractExpr objectName, AbstractIdentifier methodName,
                      ListExpr arguments) {
        super(objectName);
        this.methodName = methodName;
        this.arguments = arguments;
    }


    @Override
    public Type verifyMember(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass,
                             ClassType typeObject) throws ContextualError {
        Type methType = methodName.verifyExpr(compiler, typeObject.getDefinition().getMembers(), typeObject.getDefinition());
        if (!methodName.getExpDefinition().isMethod()) {
            throw new ContextualError("Une méthode est attendue.", methodName.getLocation());
        }
        this.arguments = arguments.verifyArgs(compiler, localEnv, currentClass, methodName.getMethodDefinition());
        return methType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getObjectName().decompile(s);
        s.print(".");
        methodName.decompile(s);
        s.print("(");
        arguments.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        getObjectName().prettyPrint(s, prefix, false);
        methodName.prettyPrint(s,prefix,false);
        arguments.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        methodName.iter(f);
        arguments.iter(f);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        super.codeGenInst(compiler, gc);

        List<AbstractExpr> argList = arguments.getList();
        int index = methodName.getMethodDefinition().getIndex();

        // On reserve la memoire requise sur la pile
        compiler.addInstruction(new ADDSP(1 + argList.size()));

        // On place l'objet de la methode sur la pile
        compiler.addInstruction(new STORE(gc.getRetReg(), new RegisterOffset(0, Register.SP)));

        // On ajoute les arguments sur la pile
        int i = -1;
        for(AbstractExpr e:argList) {
            e.codeGenInst(compiler, gc);
            compiler.addInstruction(new STORE(gc.getRetReg(), new RegisterOffset(i, Register.SP)));
            i--;
        }

        // On verifie que le déférencement n'est pas nul
        compiler.addInstruction(new LOAD(new RegisterOffset(0, Register.SP), gc.getR0()));
        compiler.addInstruction(new CMP(new NullOperand(), gc.getR0()));
        compiler.addInstruction(new BEQ(gc.getLabelDereferencementNul()));

        // On appelle la methode
        compiler.addInstruction(new LOAD(new RegisterOffset(0, gc.getR0()), gc.getR0()));
        compiler.addInstruction(new BSR(new RegisterOffset(index, gc.getR0())));

        compiler.addInstruction(new SUBSP(1 + argList.size()));
    }
}
