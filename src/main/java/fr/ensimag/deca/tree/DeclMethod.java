package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
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
    private EnvironmentExp env_exp_body;

    public DeclMethod(AbstractIdentifier type, AbstractIdentifier fieldName, ListDeclParam param, AbstractMain body) {
        Validate.notNull(type);
        Validate.notNull(fieldName);
        Validate.notNull(body);
        Validate.notNull(param);
        this.type = type;
        this.fieldName = fieldName;
        this.params = param;
        this.body = body;
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
    protected void verifyDeclMethodHeader(DecacCompiler compiler, ClassDefinition classDef)
            throws ContextualError {
        env_exp_body = new EnvironmentExp(classDef.getMembers());
        Signature sign = params.verifyListParam(compiler, env_exp_body, classDef);
        Type t = type.verifyType(compiler);
        //TODO : Il faut tester si la méthode a déjà été définie (même nom, même type et même signature).
        definition = new MethodDefinition(t, this.getLocation(), sign, classDef.incNumberOfMethods());
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
