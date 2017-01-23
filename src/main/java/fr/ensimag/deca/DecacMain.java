package fr.ensimag.deca;

import java.io.File;
import org.apache.log4j.Logger;

/**
 * Main class for the command-line Deca compiler.
 *
 * @author gl35
 * @date 01/01/2017
 */
public class DecacMain {
    private static Logger LOG = Logger.getLogger(DecacMain.class);

    static boolean error = false;
    public static void main(String[] args) {
        // example log4j message.
        LOG.info("Decac compiler started");

        final CompilerOptions options = new CompilerOptions();
        try {
            options.parseArgs(args);
        } catch (CLIException e) {
            System.err.println("Error during option parsing:\n"
                    + e.getMessage());
            options.displayUsage();
            System.exit(1);
        }
        if (options.getPrintBanner()) {
            options.afficheBanner();
            System.exit(0);
        }
        if (options.getSourceFiles().isEmpty()) {
            options.displayUsage();
        }
        if (options.getParallel()) {
            //Fait planter cobertura. Commenter cette ligne avant lancer cobertura.
            options.getSourceFiles().parallelStream().forEach(e -> compilation(e, options));

        } else {
            for (File source : options.getSourceFiles()) {
                compilation(source,options);
            }
        }
        System.exit(error ? 1 : 0);
    }

    public static void compilation(File source, CompilerOptions options){
        DecacCompiler compiler = new DecacCompiler(options, source);
        if (compiler.compile()) {
            error = true;
        }

    }

}
