package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Created by buthodgt on 1/12/17.
 * Voir si on peut fusionner cette classe avec DeclVar
 */
public class DeclField extends AbstractDeclField {
    final private Visibility privacy;
    final private AbstractIdentifier type;
    final private AbstractIdentifier fieldName;
    private AbstractInitialization initialization;

    public DeclField(Visibility privacy, AbstractIdentifier type, AbstractIdentifier fieldName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(fieldName);
        Validate.notNull(initialization);
        this.privacy = privacy;
        this.type = type;
        this.fieldName = fieldName;
        this.initialization = initialization;
    }

    public Visibility isPrivate() {
        return this.privacy;
    }

    public AbstractIdentifier getFieldName(){
        return this.fieldName;
    }

    public AbstractIdentifier getType(){
        return this.type;
    }

    public AbstractInitialization getInitialization(){
        return this.initialization;
    }

    @Override
    protected void verifyDeclFieldHeader(DecacCompiler compiler, ClassDefinition currentClass)            throws ContextualError {
        Type t = type.verifyType(compiler);
        if (compiler.getType(fieldName.getName()) != null) {
            throw new ContextualError("Nom d'attribut utilisé est un type.", this.getLocation());
        }
        int index = currentClass.getNumberOfFields()+1;
        fieldName.setDefinition(new FieldDefinition(t, this.getLocation(), privacy, currentClass, index));
        try {
            currentClass.getMembers().declare(fieldName.getName(), fieldName.getFieldDefinition());
        }
        catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("Attribut définit 2 fois.", this.getLocation());
        }
        currentClass.incNumberOfFields();
    }


    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        s.print(" ");
        fieldName.decompile(s);
        initialization.decompile(s);
        s.println(";");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        fieldName.iter(f);
        initialization.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        fieldName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
