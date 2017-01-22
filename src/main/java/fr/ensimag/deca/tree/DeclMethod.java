package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import javax.naming.Context;
import java.io.PrintStream;
import java.lang.reflect.Method;

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

    public boolean getIsAsm(){ return this.isAsm;}

    public AbstractIdentifier getType(){
        return this.type;
    }

    public AbstractBody getBody() {
        return this.body;
    }

    public ListDeclParam getParams() {
        return this.params;
    }

    public StringLiteral getPortion() {
        return portionAssembleur;
    }

    @Override
    protected void verifyDeclMethodHeader(DecacCompiler compiler, ClassDefinition classDef)
            throws ContextualError {
        env_exp_body = new EnvironmentExp(classDef.getMembers());
        Signature sign = params.verifyListParam(compiler, env_exp_body, classDef);
        Type returnType = type.verifyType(compiler);
        //On regarde si le nom de la méthode est un type.
        if (compiler.getType(methodName.getName()) != null) {
            throw new ContextualError("Name of the method used is a type", this.getLocation());
        }
        //On vérifie si la méthode Override une autre.
        ExpDefinition existingDef = classDef.getSuperClass().getMembers().get(methodName.getName());
        MethodDefinition def;
        if (existingDef != null) {
            //Il faut tester si c'est bien un Override, sinon on renvoie une erreur.
            String errorMessage = "Name of the method already used for an other thing";
            MethodDefinition existingMethod = existingDef.asMethodDefinition(errorMessage, this.getLocation());
            if (!existingMethod.getSignature().sameSignature(sign)) {
                throw new ContextualError("Method with different signature already declared",
                                            this.getLocation());
            }
            else if (!returnType.compatibleTo(existingMethod.getType())) {
                throw new ContextualError("Return type is not compatible for override",type.getLocation());
            }
            //L'override est possible, il faut lui donner l'index de la méthode override.
            def = new MethodDefinition(returnType,this.getLocation(),sign,existingMethod.getIndex());
        }
        else {
            def = new MethodDefinition(returnType, this.getLocation(), sign, classDef.incNumberOfMethods());
        }
        //On déclare la méthode dans l'environnement local.
        try {
            classDef.getMembers().declare(methodName.getName(), def);
        }
        catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("A method with the same name has already been declared in the class", this.getLocation());
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
        s.print(" ");
        methodName.decompile(s);
        params.decompile(s);
        if(isAsm){
            s.println(" asm (");
            portionAssembleur.decompile(s);
            s.println(" );");
        }
        else {
            body.decompile(s);
        }
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        type.iter(f);
        methodName.iter(f);
        params.iter(f);
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
        params.prettyPrint(s, prefix, false);
        if(isAsm){
            portionAssembleur.prettyPrint(s,prefix,true);
        }
        else {
            body.prettyPrint(s, prefix, true);
        }
    }
}
