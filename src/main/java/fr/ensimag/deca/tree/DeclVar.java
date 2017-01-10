package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import java.io.PrintStream;

import fr.ensimag.deca.tools.SymbolTable;
import org.apache.commons.lang.Validate;

/**
 * @author gl35
 * @date 01/01/2017
 */
public class DeclVar extends AbstractDeclVar {

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }
    
    public AbstractIdentifier getName(){
        return this.varName;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type t = this.type.verifyType(compiler);
        varName.setDefinition(new VariableDefinition(t,this.getLocation()));
        try {
            EnvironmentExp cour = localEnv;
            while (cour != null) {
                if (cour.get(varName.getName()) != null) {
                    throw new ContextualError("Variable définie 2 fois.", this.getLocation());
                }
                cour = cour.getParent();
            }
            localEnv.declare(varName.getName(), varName.getExpDefinition());
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("Variable définie 2 fois.", this.getLocation());
        }
        initialization.verifyInitialization(compiler, t, localEnv, currentClass);
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }
}
