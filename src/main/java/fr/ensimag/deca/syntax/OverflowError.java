package fr.ensimag.deca.syntax;


import org.antlr.v4.runtime.ParserRuleContext;

/**
 * Created by clastret on 1/19/17.
 */
public class OverflowError extends DecaRecognitionException {


    public OverflowError(DecaParser recognizer, ParserRuleContext ctx) {
        super(recognizer, ctx);
    }

    @Override
    public String getMessage() {
        return "Erreur de débordement";
    }
}
