package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Created by buthodgt on 1/13/17.
 */
public class DeclMethod extends AbstractDeclMethod {
    final private AbstractIdentifier type;
    final private AbstractIdentifier fieldName;
    private MethodDefinition definition;
    private ListDeclParam params;
    private AbstractMain body;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier fieldName) {
        Validate.notNull(type);
        Validate.notNull(fieldName);
        this.type = type;
        this.fieldName = fieldName;
    }

    public AbstractIdentifier getFieldName(){
        return this.fieldName;
    }

    public AbstractIdentifier getType(){
        return this.type;
    }

    public MethodDefinition getDefinition(){
        return this.definition;
    }

    public AbstractMain getBody() {
        return this.body;
    }

    @Override
    protected void verifyDeclMethod(DecacCompiler compiler, ClassDefinition classDef)
            throws ContextualError {
        params.verifyListParam(compiler, classDef);
        throw new ContextualError("Pas encore fait", this.getLocation());
    }


    @Override
    public void decompile(IndentPrintStream s) {
        type.decompile(s);
        fieldName.decompile(s);
        body.decompile(s);
        s.println("");
        throw new UnsupportedOperationException("Pas encore implémenté");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        fieldName.iter(f);
        //Afficher la signature
        body.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        fieldName.prettyPrint(s, prefix, false);
        //Afficher la signature de la méthode
        body.prettyPrint(s, prefix, false);
    }
}
