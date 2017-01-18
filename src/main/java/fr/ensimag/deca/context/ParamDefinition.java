package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;

/**
 * Definition of a method parameter.
 *
 * @author gl35
 * @date 01/01/2017
 */
public class ParamDefinition extends ExpDefinition {

    public ParamDefinition(Type type, Location location) {
        super(type, location);
    }

    // Definie l'index par rapport au registre LB
    private int offset = -3;

    public void setOffset(int o) {
        offset = o;
    }

    public int getOffset() {
        return offset;
    }

    @Override
    public String getNature() {
        return "parameter";
    }

    @Override
    public boolean isExpression() {
        return true;
    }

    @Override
    public boolean isParam() {
        return true;
    }

}
