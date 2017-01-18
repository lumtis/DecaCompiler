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
    final private AbstractIdentifier methodName;
    private ListDeclParam params;
    private AbstractBody body;
    private EnvironmentExp env_exp_body;
    private boolean isAsm;
    private StringLiteral portionAssembleur;


    public DeclMethod(AbstractIdentifier type, AbstractIdentifier methodName, ListDeclParam params, AbstractBody body) {
        Validate.notNull(type);
        Validate.notNull(methodName);
        Validate.notNull(body);
        Validate.notNull(params);
        this.isAsm = false;
        this.type = type;
        this.methodName = methodName;
        this.params = params;
        this.body = body;
    }
    public DeclMethod(AbstractIdentifier type, AbstractIdentifier methodName, ListDeclParam params, StringLiteral portionAssembleur) {
        Validate.notNull(type);
        Validate.notNull(methodName);
        Validate.notNull(portionAssembleur);
        Validate.notNull(params);
        this.isAsm = true;
        this.type = type;
        this.methodName = methodName;
        this.params = params;
        this.portionAssembleur = portionAssembleur;
    }

    public AbstractIdentifier getMethodName(){
        return this.methodName;
    }

    public AbstractIdentifier getType(){
        return this.type;
    }

    public AbstractBody getBody() {
        return this.body;
    }

    public ListDeclParam getParams() {
        return this.params;
    }


    /*
    protected void verifyDeclMethod(DecacCompiler compiler, ClassDefinition classDef)
            throws ContextualError {
        params.verifyListParam(compiler, classDef);
        throw new ContextualError("Pas encore fait", this.getLocation());
    */

    @Override
    protected void verifyDeclMethodHeader(DecacCompiler compiler, ClassDefinition classDef)
            throws ContextualError {
        env_exp_body = new EnvironmentExp(classDef.getMembers());
        Signature sign = params.verifyListParam(compiler, env_exp_body, classDef);
        Type returnType = type.verifyType(compiler);
        //On déclare la méthode dans env_exp
        //A modifier s'il faut prendre en compte les fonctions de même nom mais signature différente.
        MethodDefinition def = new MethodDefinition(returnType, this.getLocation(), sign, classDef.incNumberOfMethods());
        try {
            classDef.getMembers().declare(methodName.getName(), def);
        }
        catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("Une méthode de même nom a été déclarée 2 fois dans la classe.", this.getLocation());
        }
        methodName.setDefinition(def);
        methodName.setType(returnType);
    }

    @Override
    protected void verifyDeclMethodBody(DecacCompiler compiler, ClassDefinition classDef)
            throws ContextualError {
        if(!isAsm) {
            body.verifyBody(compiler, env_exp_body, classDef, type.getType());
        }else{
            portionAssembleur.verifyExpr(compiler,env_exp_body,classDef);
        }
    }


    @Override
    public void decompile(IndentPrintStream s) {

        type.decompile(s);
        methodName.decompile(s);
        if(isAsm){
            portionAssembleur.decompile(s);
        }
        else {
            body.decompile(s);
        }
        s.println("");

    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        methodName.iter(f);
        if(isAsm) {
            portionAssembleur.iter(f);
        }
        else {
            body.iter(f);
        }
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        methodName.prettyPrint(s, prefix, false);
        if(isAsm){
            portionAssembleur.prettyPrint(s,prefix,true);
        }
        else {
            body.prettyPrint(s, prefix, true);
        }
    }
}
