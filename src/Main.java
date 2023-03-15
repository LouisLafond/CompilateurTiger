import ast.*;
import graphViz.*;
import tds.*;
import codegen.*;

import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.io.IOException;

import org.antlr.v4.gui.TreeViewer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;

import parser.*;
import parser.tigerParser.ProgramContext;

public class Main {

    public static void main(String[] args){

        if (args.length < 1){
            System.out.println("Error : Expected 1 argument filepath, found 0");
            return;
        }

        String testFile = args[0];

        try {

            //chargement du fichier et construction du parser
            CharStream input = CharStreams.fromFileName(testFile);
            tigerLexer lexer = new tigerLexer(input); 
            CommonTokenStream stream = new CommonTokenStream(lexer);
            tigerParser parser = new tigerParser(stream);

            /*ProgramContext program = parser.program();

            // code d'affichage de l'arbre syntaxique
            JFrame frame = new JFrame("Antlr AST");
            JPanel panel = new JPanel();
            TreeViewer viewer = new TreeViewer(Arrays.asList(
                    parser.getRuleNames()),program);
            viewer.setScale(0.9); // Scale a little
            panel.add(viewer);
            frame.add(panel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);*/

            // Récupération du noeud program (le noeud à la racine)
            ProgramContext program = parser.program();

            // Visiteur de création de l'AST + création de l'AST
            AstCreator creator = new AstCreator();
            Ast ast = program.accept(creator);

            // Visiteur de représentation graphique + appel
            GraphVizVisitor graphViz = new GraphVizVisitor();
            ast.accept(graphViz);
        
            graphViz.dumpGraph("./out/tree.dot");

            // Visiteur de création de la TDS + création de la TDS
            TdsCreator tdsCreator = new TdsCreator();
            ast.accept(tdsCreator);

            // Affichage de la TDS
            tdsCreator.printAll();

            CodeGenerator codeGenerator = new CodeGenerator(tdsCreator);
            ast.accept(codeGenerator);
            codeGenerator.saveCode();



        } 
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (RecognitionException e) {
            e.printStackTrace();
        }
        

    }
    
}