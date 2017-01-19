package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.commons.lang.Validate;

import java.io.PrintStream;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 *
 * @author gl35
 * @date 01/01/2017
 */
public class DeclClass extends AbstractDeclClass {

    final private AbstractIdentifier className;
    final private AbstractIdentifier superName;
    private ListDeclField fields;
    private ListDeclMethod methods;

    public DeclClass(AbstractIdentifier className, AbstractIdentifier superName, ListDeclMethod methods, ListDeclField fields) {
        Validate.notNull(className);
        Validate.notNull(superName);
        Validate.notNull(methods);
        Validate.notNull(fields);
        this.className = className;
        this.superName = superName;
        this.methods = methods;
        this.fields =  fields;

    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        className.decompile(s);
        s.print(" extends ");
        superName.decompile(s);
        s.println(" {");
        s.indent();
        fields.decompile(s);
        methods.decompile(s);
        s.unindent();
        s.println("}");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        if (compiler.getType(this.className.getName()) != null) {
            throw new ContextualError("Classe déjà définie.", this.getLocation());
        }
        Type superType = compiler.getType(superName.getName());
        if (superType == null) {
            throw new ContextualError("Super classe non définie.", this.getLocation());
        }
        String errorMessage = superName.getName() + " n'est pas un type de classe.";
        ClassType superTypeClass = superType.asClassType(errorMessage, this.getLocation());
        Validate.notNull(superType, "Erreur : Le type de la super classe est nulle.");
        superName.setDefinition(superTypeClass.getDefinition());
        ClassType t = new ClassType(className.getName(), this.getLocation(), superTypeClass.getDefinition());
        compiler.addType(className.getName(), t);
        className.setDefinition(t.getDefinition());
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        ClassDefinition defClass = className.getClassDefinition();
        ClassDefinition defSuper = superName.getClassDefinition();
        defClass.setNumberOfFields(defSuper.getNumberOfFields());
        defClass.setNumberOfMethods(defSuper.getNumberOfMethods());
        this.fields.verifyListFieldHeader(compiler, className.getClassDefinition());
        this.methods.verifyListMethodHeader(compiler, className.getClassDefinition());
    }

    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
        this.fields.verifyListFieldInit(compiler, className.getClassDefinition());
        this.methods.verifyListMethodBody(compiler, className.getClassDefinition());
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        className.prettyPrint(s, prefix, false);
        superName.prettyPrint(s, prefix, false);
        fields.prettyPrint(s, prefix, false);
        methods.prettyPrint(s, prefix, true);

    }

    @Override
    protected void iterChildren(TreeFunction f) {
            className.iter(f);
            superName.iter(f);
            fields.iter(f);
            methods.iter(f);
     }

    // Getter
    public ListDeclMethod getMethods() {
        return methods;
    }
    public ListDeclField getFields() {
        return fields;
    }
    public Identifier getClassName() {
        return (Identifier)className;
    }
    public Identifier getParent() {
        return (Identifier)superName;
    }


    public int numberMethods() {
        return className.getClassDefinition().getNumberOfMethods();
    }
}
