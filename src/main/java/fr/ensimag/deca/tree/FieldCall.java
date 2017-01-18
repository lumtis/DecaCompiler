package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;

import java.io.PrintStream;

/**
 * Created by buthodgt on 1/17/17.
 */
public class FieldCall extends AbstractMemberCall {

    private AbstractIdentifier fieldName;

    public FieldCall(AbstractExpr objectName, AbstractIdentifier fieldName) {
        super(objectName);
        this.fieldName = fieldName;
    }

    @Override
    public Type verifyMember(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass,
                             ClassType typeObject) throws ContextualError {
        Type fieldType = fieldName.verifyExpr(compiler, typeObject.getDefinition().getMembers(),
                typeObject.getDefinition());
        return fieldType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("Pas encore implémenté.");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        getObjectName().prettyPrint(s, prefix, false);
        fieldName.prettyPrint(s,prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        fieldName.iter(f);
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        int index = fieldName.getFieldDefinition().getIndex();

        // On réalise l'expression derrière le champ
        objectName.codeGenInst(compiler, gc);

        // On récupere la variable en fonction de son index
        compiler.addInstruction(new LOAD(new RegisterOffset(index, gc.getRetReg()), getRetReg()));
    }
}
