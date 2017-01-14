package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.log4j.Logger;

import javax.naming.Context;

/**
 * Created by buthodgt on 1/12/17.
 */
public class ListDeclField extends TreeList<AbstractDeclField>{
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclField f : getList()) {
            f.decompile(s);
            s.println();
        }
    }

    void verifyListFieldHeader(DecacCompiler compiler, ClassDefinition classDef) throws ContextualError {
        for (AbstractDeclField f : getList()) {
            f.verifyDeclFieldHeader(compiler, classDef);
        }
    }
}
