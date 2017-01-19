package fr.ensimag.deca.tree;

import com.sun.tools.javac.resources.compiler;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
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

        void verifyListMethodHeader(DecacCompiler compiler, ClassDefinition classDef) throws ContextualError {
            for (AbstractDeclMethod m : getList()) {
                m.verifyDeclMethodHeader(compiler, classDef);
            }
        }

        void verifyListMethodBody(DecacCompiler compiler, ClassDefinition classDef) throws ContextualError {
            for (AbstractDeclMethod m : getList()) {
                m.verifyDeclMethodBody(compiler, classDef);
            }
        }
}
