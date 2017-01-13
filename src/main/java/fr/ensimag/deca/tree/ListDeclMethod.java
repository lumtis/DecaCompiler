package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import org.apache.log4j.Logger;

/**
 * Created by buthodgt on 1/13/17.
 */
public class ListDeclMethod extends TreeList<AbstractDeclMethod> {
        private static final Logger LOG = Logger.getLogger(ListDeclClass.class);

        @Override
        public void decompile(IndentPrintStream s) {
            for (AbstractDeclMethod m : getList()) {
                m.decompile(s);
                s.println();
            }
        }

        void verifyListField(DecacCompiler compiler) throws ContextualError {
            //TODO : Surement à modifier pour prendre en compte la classe
            for (AbstractDeclMethod f : getList()) {
                //A modifier pour environnement
                f.verifyDeclMethod(compiler, null, null);
            }
        }
}
