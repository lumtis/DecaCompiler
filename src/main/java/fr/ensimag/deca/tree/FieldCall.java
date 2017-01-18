package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;

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
        if (!fieldName.getExpDefinition().isField()) {
            throw new ContextualError("Un attribut est attendu.", fieldName.getLocation());
        }
        //Il faut vérifier si on peut accéder à l'attribut (dans le cas protected).
        FieldDefinition fieldDef = fieldName.getFieldDefinition();
        System.out.println((fieldDef.getVisibility() == Visibility.PROTECTED) + "/" + (!typeObject.isSubClassOf(currentClass.getType())));
        if (fieldDef.getVisibility() == Visibility.PROTECTED &&
                !typeObject.isSubClassOf(currentClass.getType())) {
            throw new ContextualError("L'attribut est de type protected.",fieldName.getLocation());
        }
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
        getObjectName().codeGenInst(compiler, gc);

        // On récupere la variable en fonction de son index
        compiler.addInstruction(new LOAD(new RegisterOffset(index, gc.getRetReg()), gc.getRetReg()));
    }
}
