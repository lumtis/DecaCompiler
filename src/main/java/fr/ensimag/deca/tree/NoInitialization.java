package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.ima.pseudocode.*;
import fr.ensimag.ima.pseudocode.instructions.*;
import java.io.PrintStream;

/**
 * Absence of initialization (e.g. "int x;" as opposed to "int x =
 * 42;").
 *
 * @author gl35
 * @date 01/01/2017
 */
public class NoInitialization extends AbstractInitialization {

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        //On ne fait probablement rien.
    }


    /**
     * Node contains no real information, nothing to check.
     */
    @Override
    protected void checkLocation() {
        // nothing
    }

    @Override
    public void decompile(IndentPrintStream s) {
        // nothing
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }


    @Override
    public void codeGenInit(DecacCompiler comp, GenCode gc, Type t){
        // Valeur par défaut
        if(t.isInt()) {
            comp.addInstruction(new LOAD(new ImmediateInteger(0), gc.getRetReg()));
        }
        else if(t.isFloat()) {
            comp.addInstruction(new LOAD(new ImmediateFloat(0), gc.getRetReg()));
        }
        else if(t.isBoolean()) {
            comp.addInstruction(new LOAD(new ImmediateInteger(0), gc.getRetReg()));
        }
        else if(t.isClass()) {
            comp.addInstruction(new LOAD(new NullOperand(), gc.getRetReg()));
        }
    }

    @Override
    public void codeGenInit(DAddr addr, DecacCompiler comp, GenCode gc, Type t){
        codeGenInit(comp, gc, t);
        comp.addInstruction(new STORE(gc.getRetReg() ,addr));
    }
}
