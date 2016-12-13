package fr.ensimag.deca.tree;


/**
 *
 * @author gl35
 * @date 01/01/2017
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<";
    }

}
