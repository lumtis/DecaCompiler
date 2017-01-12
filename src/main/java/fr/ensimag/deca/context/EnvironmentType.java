package fr.ensimag.deca.context;

import fr.ensimag.deca.context.*;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.*;

import java.util.HashMap;

/**
 * Created by buthodgt on 1/9/17.
 */
public class EnvironmentType {

    private HashMap<Symbol, Type> env_type;

    private HashMap<Type, Definition> def_type;

    public EnvironmentType(SymbolTable symbols) {
        env_type = new HashMap<>();
        def_type = new HashMap<>();
        //Création des types de base.
        Type t[] = new Type[5];
        Symbol intSym = symbols.create("int");
        t[0] = new IntType(intSym);
        env_type.put(intSym, t[0]);
        Symbol strSym = symbols.create("String");
        t[1] = new StringType(strSym);
        env_type.put(strSym, t[1]);
        Symbol floatSym = symbols.create("float");
        t[2] = new FloatType(floatSym);
        env_type.put(floatSym, t[2]);
        Symbol boolSym = symbols.create("boolean");
        t[3] = new BooleanType(boolSym);
        env_type.put(boolSym, t[3]);
        Symbol voidSym = symbols.create("void");
        t[4] = new VoidType(voidSym);
        env_type.put(voidSym, t[4]);
        for (int i=0; i<t.length; i++) {
            Location loc = new Location(0,0,"Default");
            def_type.put(t[i], new TypeDefinition(t[i],loc));
        }
        //Création du type Object.
        Symbol objectSym = symbols.create("Object");
        ClassType objectType = new ClassType(objectSym,new Location(0,0,"Default"),null);
        env_type.put(objectSym, objectType);
        //Création de la méthode equals
        EnvironmentExp env_exp = objectType.getDefinition().getMembers();
        Symbol equalsSym = symbols.create("equals");
        Signature sign = new Signature();
        sign.add(objectType);
        sign.add(objectType);
        MethodDefinition eqDef = new MethodDefinition(t[3],new Location(0,0,"Default"),sign,0);
        try {
            env_exp.declare(equalsSym, eqDef);
        }
        catch (EnvironmentExp.DoubleDefException e) {
            throw new DecacInternalError("equals définit 2 fois.");
        }
    }

    public Type getType(SymbolTable.Symbol sym) {
        if (env_type.containsKey(sym)) {
            return env_type.get(sym);
        }
        else {
            return null;
        }
    }

    public Definition getDefinition(Type t) {
        if (def_type.containsKey(t)) {
            return def_type.get(t);
        }
        else {
            return null;
        }
    }
}
