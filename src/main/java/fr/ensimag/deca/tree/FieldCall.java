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
public class FieldCall extends AbstractFieldCall {

    private AbstractExpr objectName;
    private AbstractIdentifier fieldName;

    public FieldCall(AbstractExpr objectName, AbstractIdentifier fieldName) {
        this.objectName = objectName;
        this.fieldName = fieldName;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type t = objectName.verifyExpr(compiler, localEnv, currentClass);
        ClassType typeObject = t.asClassType("This object is not a type", this.getLocation());
        Type fieldType = fieldName.verifyExpr(compiler, typeObject.getDefinition().getMembers(),
                typeObject.getDefinition());
        if (!fieldName.getExpDefinition().isField()) {
            throw new ContextualError("Field expected", fieldName.getLocation());
        }
        //Il faut vérifier si on peut accéder à l'attribut (dans le cas protected).
        FieldDefinition fieldDef = fieldName.getFieldDefinition();
        if (fieldDef.getVisibility() == Visibility.PROTECTED && (currentClass == null ||
                !typeObject.isSubClassOf(currentClass.getType()))) {
            throw new ContextualError("The field is protected",fieldName.getLocation());
        }
        this.setType(fieldType);
        return fieldType;
    }

    @Override
    public void decompile(IndentPrintStream s) {
        objectName.decompile(s);
        s.print(".");
        fieldName.decompile(s);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        objectName.prettyPrint(s, prefix, false);
        fieldName.prettyPrint(s,prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        fieldName.iter(f);
    }

    @Override
    public boolean isFieldCall() {
        return true;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        objectName.codeGenInst(compiler, gc);

        // On verifie que le déréférencement n'est pas nul
        compiler.addInstruction(new CMP(new NullOperand(), gc.getRetReg()));
        compiler.addInstruction(new BEQ(gc.getLabelDereferencementNul()));

        // On récupere la variable en fonction de son index
        int index = fieldName.getFieldDefinition().getIndex();
        compiler.addInstruction(new LOAD(new RegisterOffset(index, gc.getRetReg()), gc.getRetReg()));
    }


    // Permet d'obtenir dans le registre de retour, l'adresse du champ
    public void codeGenAddr(DecacCompiler compiler, GenCode gc) {
        objectName.codeGenInst(compiler, gc);

        int index = fieldName.getFieldDefinition().getIndex();

        // On récupere la variable en fonction de son index
        compiler.addInstruction(new LEA(new RegisterOffset(index, gc.getRetReg()), gc.getRetReg()));
    }
}
