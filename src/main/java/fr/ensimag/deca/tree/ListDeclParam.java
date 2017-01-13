package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.log4j.Logger;

/**
 * Created by buthodgt on 1/13/17.
 */
public class ListDeclParam extends TreeList<AbstractDeclParam>{
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclParam p : getList()) {
            p.decompile(s);
            s.println();
        }
    }

    void verifyListParam(DecacCompiler compiler, ClassDefinition classDef) throws ContextualError {
        for (AbstractDeclParam p : getList()) {
            p.verifyDeclParam(compiler, classDef);
            this.add(p);
        }
    }
}
