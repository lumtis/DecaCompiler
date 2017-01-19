package fr.ensimag.deca.context;

import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;

/**
 * Deca Type (internal representation of the compiler)
 *
 * @author gl35
 * @date 01/01/2017
 */

public abstract class Type {

    /**True if this and otherType are the same type or if this is
     * a subclass of otherType.
     * @param otherType
     * @return
     */
    public boolean compatibleTo(Type otherType) throws ContextualError {
        if (this.sameType(otherType)) {
            return true;
        }
        else if (this.isClass() && otherType.isClass()) {
            String errorMessage = "Erreur interne de conversion de type.";
            ClassType classType = this.asClassType(errorMessage, new Location(0,0,""));
            ClassType classOther = otherType.asClassType(errorMessage, new Location(0,0,""));
            return classType.isSubClassOf(classOther);
        }
        return false;
    }
    /**
     * True if this and otherType represent the same type (in the case of
     * classes, this means they represent the same class).
     */
    public abstract boolean sameType(Type otherType);

    private final Symbol name;

    public Type(Symbol name) {
        this.name = name;
    }

    public Symbol getName() {
        return name;
    }

    @Override
    public String toString() {
        return getName().toString();
    }

    public boolean isClass() {
        return false;
    }

    public boolean isInt() {
        return false;
    }

    public boolean isFloat() {
        return false;
    }

    public boolean isBoolean() {
        return false;
    }

    public boolean isVoid() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

    public boolean isClassOrNull() {
        return false;
    }

    /**
     * Returns the same object, as type ClassType, if possible. Throws
     * ContextualError(errorMessage, l) otherwise.
     *
     * Can be seen as a cast, but throws an explicit contextual error when the
     * cast fails.
     */
    public ClassType asClassType(String errorMessage, Location l)
            throws ContextualError {
        try {
            return (ClassType) this;
        }
        catch (ClassCastException e) {
            throw new ContextualError(errorMessage, l);
        }
    }

}
