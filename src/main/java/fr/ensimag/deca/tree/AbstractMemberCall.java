package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.*;
import org.apache.commons.lang.Validate;

/**
 * Created by buthodgt on 1/17/17.
 */
public abstract class AbstractMemberCall extends AbstractLValue {

    private AbstractExpr objectName;

    public AbstractMemberCall(AbstractExpr objectName) {
        if(objectName == null){
            this.objectName = new This();
        }
        else {
            this.objectName = objectName;
        }
    }


    public AbstractExpr getObjectName() {
        return objectName;
    }

    public abstract Type verifyMember(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass,
                                 ClassType typeObject) throws ContextualError ;

    @Override
    public Type verifyExpr(DecacCompiler compiler,
                           EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        Type t = objectName.verifyExpr(compiler, localEnv, currentClass);
        ClassType typeClass = t.asClassType("Cet objet n'est pas un type.", this.getLocation());
        Type memberType = verifyMember(compiler, localEnv, currentClass, typeClass);
        this.setType(memberType);
        return memberType;
    }
}
