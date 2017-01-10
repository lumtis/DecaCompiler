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

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type t = this.type.verifyType(compiler);
        //On vérifie si le nom de variable utilisé est le même que celui d'un type
        if (compiler.getType(varName.getName()) != null) {
            throw new ContextualError("Nom de variable utilisé est un type.", this.getLocation());
        }
        varName.setDefinition(new VariableDefinition(t,this.getLocation()));
        //On doit parcourir tous les env_exp parents pour vérifier si la variable existe déjà.
        try {
            EnvironmentExp cour = localEnv;
            while (cour != null) {
                if (cour.get(varName.getName()) != null) {
                    throw new ContextualError("Variable déjà définie.", this.getLocation());
                }
                cour = cour.getParent();
            }
            localEnv.declare(varName.getName(), varName.getExpDefinition());
        } catch (EnvironmentExp.DoubleDefException e) {
            throw new ContextualError("Variable déjà définie.", this.getLocation());
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
