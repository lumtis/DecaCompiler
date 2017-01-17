package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.WSTR;
import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import fr.ensimag.ima.pseudocode.*;
import javax.naming.Context;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.*;

/**
 * Expression, i.e. anything that has a value.
 *
 * @author gl35
 * @date 01/01/2017
 */
public abstract class AbstractExpr extends AbstractInst {
    /**
     * @return true if the expression does not correspond to any concrete token
     * in the source code (and should be decompiled to the empty string).
     */
    boolean isImplicit() {
        return false;
    }

    /**
     * Get the type decoration associated to this expression (i.e. the type computed by contextual verification).
     */
    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        Validate.notNull(type);
        this.type = type;
    }
    private Type type;

    @Override
    protected void checkDecoration() {
        if (getType() == null) {
            throw new DecacInternalError("Expression " + /*decompile() +*/ " has no Type decoration");
        }
    }

    /**
     * Verify the expression for contextual error.
     *
     * implements non-terminals "expr" and "lvalue"
     *    of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return the Type of the expression
     *            (corresponds to the "type" attribute)
     */
    public abstract Type verifyExpr(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Verify the expression in right hand-side of (implicit) assignments
     *
     * implements non-terminal "rvalue" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass corresponds to the "class" attribute
     * @param expectedType corresponds to the "type1" attribute
     * @return this with an additional ConvFloat if needed...
     */
    public AbstractExpr verifyRValue(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass,
            Type expectedType)
            throws ContextualError {
        Type t = this.verifyExpr(compiler, localEnv, currentClass);
        if (expectedType.sameType(t)) {
            return this;
        }
        else if (expectedType.isFloat() && t.isInt()) {
            AbstractExpr expr = new ConvFloat(this);
            expr.verifyExpr(compiler, localEnv, currentClass);
            expr.setLocation(this.getLocation());
            return expr;
        }
        else if (expectedType.isClass() && t.isClass()) {
            ClassType typeClass = t.asClassType("Type n'est pas une classe.", this.getLocation());
            ClassType expectedTypeClass = expectedType.asClassType("Type n'est pas une classe.", this.getLocation());
            if (typeClass.isSubClassOf(expectedTypeClass)) {
                return this;
            }
        }
        throw new ContextualError("Affectation non valide.", this.getLocation());
    }


    @Override
    protected boolean verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        this.verifyExpr(compiler,localEnv,currentClass);
        return false;
    }

    /**
     * Verify the expression as a condition, i.e. check that the type is
     * boolean.
     *
     * @param localEnv
     *            Environment in which the condition should be checked.
     * @param currentClass
     *            Definition of the class containing the expression, or null in
     *            the main program.
     */
    void verifyCondition(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t = this.verifyExpr(compiler,localEnv, currentClass);
        if (!t.isBoolean()) {
            throw new ContextualError("L'expression n'est pas une condition.", this.getLocation());
        }
    }

    protected void codeGenPrint(DecacCompiler compiler, GenCode gc) {
        // On réalise l'expression
        this.codeGenInst(compiler, gc);

        // On met le retour de l'expression dans le registre r1
        compiler.addInstruction(new LOAD(gc.getRetReg(), GPRegister.getR(1)));

        // On affiche un float si l'expression evalue un float
        if(gc.isExprFloat())
            compiler.addInstruction(new WFLOAT());
        else
            compiler.addInstruction(new WINT());
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        throw new UnsupportedOperationException("not yet implemented");
    }

    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }

    // Genere le code de l'expression
    public void codeGenExpr(DecacCompiler compiler, GenCode gc) {
        codeGenInst(compiler, gc);
    }
}
