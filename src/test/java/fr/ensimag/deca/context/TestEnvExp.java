package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tree.Location;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by buthodgt on 1/12/17.
 */
public class TestEnvExp {
    final Type INT = new IntType(null);
    final Type FLOAT = new FloatType(null);

    SymbolTable variables;

    SymbolTable.Symbol sym1;
    SymbolTable.Symbol sym2;
    SymbolTable.Symbol sym3;
    SymbolTable.Symbol sym4;

    @Before
    public void setup() throws ContextualError {
        variables = new SymbolTable();
        sym1 = variables.create("sym1");
        sym2 = variables.create("sym2");
        sym3 = variables.create("sym3");
        sym4 = variables.create("sym4");
    }

    @Test
    public void testVariableDef() {
        EnvironmentExp env1 = new EnvironmentExp(null);
        EnvironmentExp env2 = new EnvironmentExp(env1);
        EnvironmentExp env3 = new EnvironmentExp(env2);
        try {
            env1.declare(sym1, new VariableDefinition(INT,new Location(0,0,"")));
            env1.declare(sym2, new VariableDefinition(FLOAT,new Location(0,0,"")));
            env3.declare(sym3, new VariableDefinition(INT,new Location(0,0,"")));
        }
        catch (EnvironmentExp.DoubleDefException e) {
            throw new InternalError("EnvironmentExp ne fonctionne pas.");
        }
        ExpDefinition def;
        def = env3.get(sym4);
        assert(def ==null);
        def = env2.get(sym3);
        assert(def == null);
        def = env3.get(sym1);
        assert(def != null);
        boolean error = false;
        try {
            env2.declare(sym2,new VariableDefinition(FLOAT,new Location(0,0,"")));
        }
        catch (EnvironmentExp.DoubleDefException e) {
            error = true;
        }
        assert(!error);
        assert(def != env3.get(sym2));
    }
}
