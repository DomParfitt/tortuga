import lexer.Lexer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TortugaLexer {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please provide a file name");
            return;
        } else if (args[0].endsWith(".tortuga")) {
            System.err.println("File provided is not a valid tortuga source file");
            return;
        }

        String filePath = args[0];
        File file = new File(filePath);
        List<String> strings = new ArrayList<>();
        try {
            Scanner in = new Scanner(file);
            while (in.hasNext()) {
                strings.add(in.next());
            }
        } catch (FileNotFoundException e) {
            System.err.println("Could not find the provided file");
        }

        Lexer lexer = new Lexer();
        for(String string : strings) {
            lexer.tokenize(string);

        }
    }
}
