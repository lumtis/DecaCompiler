package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import fr.ensimag.deca.codegen.GenCode;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.*;
import fr.ensimag.ima.pseudocode.*;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Created by clastret on 1/17/17.
 */
public class Cast extends AbstractExpr{

    AbstractIdentifier ident_type;
    AbstractExpr expr_cast;
    public Cast(AbstractIdentifier type, AbstractExpr expr_cast) {
        Validate.notNull(type);
        Validate.notNull(expr_cast);
        this.ident_type= type;
        this.expr_cast=expr_cast;
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("(");
        ident_type.decompile(s);
        s.print(") ");
        s.print("(");
        expr_cast.decompile(s);
        s.print(")");
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        ident_type.prettyPrint(s,prefix, false);
        expr_cast.prettyPrint(s,prefix,true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        expr_cast.iter(f);
        ident_type.iter(f);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type type_ref = ident_type.verifyType(compiler);
        Type type_casted = expr_cast.verifyExpr(compiler,localEnv,currentClass);

        if (type_casted.isVoid() || !type_ref.castCompatibleTo(type_casted)) {
            throw new ContextualError("Cast impossible.", this.getLocation());
        }
        this.setType(type_ref);
        return type_ref;
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {

        // On réalise l'expression
        expr_cast.codeGenInst(compiler, gc);

        if(ident_type.getDefinition().getType().isInt()) {
            compiler.addInstruction(new INT(gc.getRetReg(), gc.getRetReg()));
        }
        else if(ident_type.getDefinition().getType().isFloat()) {
            compiler.addInstruction(new FLOAT(gc.getRetReg(), gc.getRetReg()));
        }
        else if(ident_type.getDefinition().getType().isClass()) {
            Label debut = gc.newLabel("CastDebut");
            Label bloc = gc.newLabel("CastBloc");
            Label find = gc.newLabel("CastFind");

            // On obtient l'adresse de la classe à retrouver
            int offset = ident_type.getClassDefinition().getOffset();
            DAddr addrClass = new RegisterOffset(offset, Register.GB);

            compiler.addInstruction(new PUSH(gc.getRetReg()));

            compiler.addLabel(debut);
            compiler.addInstruction(new LOAD(new RegisterOffset(0, gc.getRetReg()), gc.getRetReg()));
            compiler.addInstruction(new CMP(new NullOperand(), gc.getRetReg()));
            compiler.addInstruction(new BNE(bloc));
            compiler.addInstruction(new POP(gc.getRetReg()));
            compiler.addInstruction(new BRA(gc.getLabelConversion_mauvaise()));

            compiler.addLabel(bloc);
            compiler.addInstruction(new LEA(addrClass, gc.getR0()));
            compiler.addInstruction(new CMP(gc.getR0(), gc.getRetReg()));
            compiler.addInstruction(new BEQ(find));
            compiler.addInstruction(new BRA(debut));

            compiler.addLabel(find);
            compiler.addInstruction(new POP(gc.getRetReg()));
        }
    }
}
