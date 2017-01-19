package fr.ensimag.deca.tree;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.GenCode;


/**
 * @author gl35
 * @date 01/01/2017
 */
public class Print extends AbstractPrint {
    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printx)
     */
    public Print(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    String getSuffix() {
        return "";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler, GenCode gc) {
        super.codeGenInst(compiler, gc);
    }
}
