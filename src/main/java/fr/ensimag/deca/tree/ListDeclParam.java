package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
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

    Signature verifyListParam(DecacCompiler compiler, EnvironmentExp env_exp, ClassDefinition classDef) throws ContextualError {
        Signature sign = new Signature();
        for (AbstractDeclParam p : getList()) {
            Type t = p.verifyDeclParam(compiler, env_exp, classDef);
            sign.add(t);
        }
        return sign;
    }
}
