package fr.ensimag.ima.pseudocode;
import java.io.PrintStream;


public class CustomLine extends AbstractLine {
    public CustomLine(String line) {
        super();
        this.line = line;
    }

    private String line;

    @Override
    void display(PrintStream s) {
        s.print("\t");
        s.print(line.replace("\\", ""));
        s.println();
    }
}
