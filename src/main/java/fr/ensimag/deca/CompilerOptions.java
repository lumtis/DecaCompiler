package fr.ensimag.deca;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * User-specified options influencing the compilation.
 *
 * @author gl35
 * @date 01/01/2017
 */
public class CompilerOptions {
    public static final int QUIET = 0;
    public static final int INFO  = 1;
    public static final int DEBUG = 2;
    public static final int TRACE = 3;
    public int getDebug() {
        return debug;
    }

    public boolean getParallel() {
        return parallel;
    }

    public boolean getPrintBanner() {
        return printBanner;
    }

    public boolean getVerifOnly() { return verifOnly;}

    public boolean getParse() { return parse;}

    public boolean getNoCheck() {return noCheck;}

    public int getRegisters() {return registers;}
    
    public List<File> getSourceFiles() {
        return Collections.unmodifiableList(sourceFiles);
    }

    private int debug = 0;
    private int registers=16;
    private boolean parallel = false;
    private boolean printBanner = false;
    private boolean verifOnly = false;
    private boolean parse = false;
    private boolean needFiles = false;
    private boolean noCheck = false;
    private boolean sameFiles = false;
    private List<File> sourceFiles = new ArrayList<File>();

    
    public void parseArgs(String[] args) throws CLIException {

        int i=0;
        while (i<args.length) {
            switch (args[i]) {
                case "-r":
                    needFiles = true;
                    i++;
                    try{
                        int nbrRegisters=Integer.parseInt(args[i]);
                        if(nbrRegisters<4 || nbrRegisters >16){
                            throw new IllegalArgumentException("L'option -r doit être suivi d'un entier entre 4 et 16.");
                        }
                        registers = nbrRegisters;
                    }
                    catch (Exception e){
                        throw new IllegalArgumentException("L'option -r doit être suivi d'un entier entre 4 et 16.");
                    }
                    break;
                case "-d":
                    needFiles = true;
                    debug++;
                    break;
                case "-p":
                    needFiles = true;
                    parse = true;
                    if(verifOnly){
                        erreurIncompatible_pv();
                    }
                    break;
                case "-b":
                    if(args.length>1){
                        erreurIncompatible_banner();
                    }
                    printBanner = true;
                    break;
                case "-v":
                    needFiles = true;
                    verifOnly = true;
                    if(parse){
                        erreurIncompatible_pv();
                    }
                    break;
                case "-P":
                    needFiles = true;
                    parallel = true;
                    break;

                case "-n":
                    needFiles = true;
                    noCheck = true;
                    break;

                default:
                    sameFiles=false;


                    File f = new File(args[i]);
                    for(File file: sourceFiles) {
                        if (file.equals(f)) {
                            sameFiles = true;
                            break;
                        }
                    }
                    if(!sameFiles) {
                        if (f == null || !args[i].endsWith(".deca")) {
                            throw new IllegalArgumentException("Fichier non valide.");
                        }
                        sourceFiles.add(f);
                    }
            }
            i++;
        }
        if (needFiles && sourceFiles.size()==0){
            erreurNeedFiles();
        }
        Logger logger = Logger.getRootLogger();
        // map command-line debug option to log4j's level.
        switch (getDebug()) {
        case QUIET: break; // keep default
        case INFO:
            logger.setLevel(Level.INFO); break;
        case DEBUG:
            logger.setLevel(Level.DEBUG); break;
        case TRACE:
            logger.setLevel(Level.TRACE); break;
        default:
            logger.setLevel(Level.ALL); break;
        }
        logger.info("Application-wide trace level set to " + logger.getLevel());

        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (assertsEnabled) {
            logger.info("Java assertions enabled");
        } else {
            logger.info("Java assertions disabled");
        }

        //throw new UnsupportedOperationException("not yet implemented");
    }
    public void erreurIncompatible_pv(){
        throw new IllegalArgumentException("Les options -v et -p sont incompatibles");
    }
    public void erreurIncompatible_banner(){
        throw new IllegalArgumentException("La commande -b est incompatible avec toutes autres options ou fichiers");
    }
    public void erreurNeedFiles(){
        throw new IllegalArgumentException("Vous devez inclure au moins un fichier en argument");
    }

    public void afficheBanner(){

        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println("\n                               Equipe n° : 35");
        System.out.println("\n Membres de l'équipe: Thami Benjelloun, Nizar Bel Hadj Salah, Lucas Bertrand, Tony Buthod-Garçon, Thomas Clastres");
        System.out.println("\n Projet GL 2017");
        System.out.println("-----------------------------------------------------------------------------------------");


    }
    public void affiche_commande(){
        System.out.println("usage : decac [[-p | -v] [-n] [-r X] <fichier deca>...] | [-b]");
        System.out.println("\n -b  (banner) : affiche une bannière indiquant le nom de l’équipe");
        System.out.println("\n -p  (parse) : arrête decac après l’étape de construction de\n" +
                "l’arbre, et affiche la décompilation de ce dernier\n" +
                "(i.e. s’il n’y a qu’un fichier source à\n" +
                "compiler, la sortie doit être un programme\n" +
                "deca syntaxiquement correct)");
        System.out.println("\n -v  (verification) arrête decac après l’étape de vérifications\n" +
                "(ne produit aucune sortie en l’absence d’erreur)");
        System.out.println("\n -n  (no check) supprime les tests de débordement à l’exécution\n" +
                "- débordement arithmétique\n" +
                "- débordement mémoire\n" +
                "- déréférencement de null");
        System.out.println("\n -r X (registers) limite les registres banalisés disponibles à\n" +
                "R0 ... R{X-1}, avec 4 <= X <= 16");
        System.out.println("\n -d  (debug) active les traces de debug. Répéter\n" +
                "l’option plusieurs fois pour avoir plus de\n" +
                "traces.");
        System.out.println("\n -P  (parallel) s’il y a plusieurs fichiers sources,\n" +
                "lance la compilation des fichiers en\n" +
                "parallèle (pour accélérer la compilation)");
        System.out.println("\n -w (warning) autorise l’affichage de messages d’avertissement (« warnings »)\n" +
                "en cours de compilation.");

    }

    protected void displayUsage() {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
