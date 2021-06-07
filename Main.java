import Lexer.Lexer;
import Lexer.TokenTypes;
import Parser.Parser;
import Parser.Statements.FunctionCallStatement;
import Parser.Statements.VariableDeclaration;
import Parser.Values.BoolValue;
import Parser.Values.IntValue;
import Parser.Values.MessValue;
import Reader.Reader;
import Visitor.CodeVisitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args ) throws Exception {

        int MaxContextLength = 10;
        int MaxLength = 14;
        int MaxTokens = 2;

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("------Welcome in MA++ console------");
        System.out.println("Please give a name of file wit code:");

        String name = reader.readLine();
        File tempFile = new File("src/" + name);

        while (!tempFile.exists()) {
            System.out.println("File with name: " + name + " do not exist in this directory!");
            System.out.println("Pleas give a correct name or type 'end' to exit");
            name = reader.readLine();
            tempFile = new File(name);
            if (name.equals("end")) {
                System.out.println("------Goodbye------");
                return;
            }
        }

        System.out.println("------Opening file: " + name + "------");

        System.out.println("------Starting lexer------");
        BufferedReader br = new BufferedReader(new FileReader(tempFile));
        Reader r = new Reader(br, MaxContextLength);
        Lexer l = new Lexer(r, MaxLength, MaxTokens);

        System.out.println("------Starting parser------");
        Parser p = new Parser(l);
        p.parse();
        System.out.println("------Parsing completed------");

        System.out.println("------Initializig CodeViewer------");
        CodeVisitor cv = new CodeVisitor(p.getFunctions(), p.getClasses());

        System.out.println("------Choose function to start------");
        name = reader.readLine();
        if(!p.getFunctions().containsKey(name))
        {
            System.out.println("------Function '" + name + "' do not exist!------");
            System.out.println("------Abort------");
            System.out.println("------Goodbye------");
            return;
        }

        FunctionCallStatement fcs = new FunctionCallStatement(name);

        int parametersNumber =  p.getFunctions().get(name).getArguments().size();
        System.out.println("Number of required parameters: " + parametersNumber);
        if(parametersNumber > 0)
        {
            Vector<VariableDeclaration> arguments = p.getFunctions().get(name).getArguments();
            Pattern pa = Pattern.compile("^(-?[1-9][0-9]*|0)");
            System.out.println("------Give required parameters------");
            for(int i = 0; i < parametersNumber; i++)
            {
                Matcher m;
                boolean b;

                TokenTypes type = arguments.elementAt(i).getType();
                boolean correct = false;
                while(!correct)
                {
                    System.out.println("Give parameter of type: " + type);
                    String input = reader.readLine();
                    switch(type)
                    {
                        case NUM:
                            m = pa.matcher(input);
                            b = m.matches();
                            if(b)
                            {
                                correct = true;
                                int value = Integer.parseInt(input);
                                fcs.addArgument(new IntValue(value));
                            }
                            else
                                System.out.println("Incorrect value!");
                            break;
                        case MESS:
                            if(input.length() > MaxLength)
                                System.out.println("Too big string, max length: " + MaxLength);
                            else
                            {
                                correct = true;
                                fcs.addArgument(new MessValue(input));
                            }
                            break;
                        case BOOL:
                            if(input.equals("true")) {
                                correct = true;
                                fcs.addArgument(new BoolValue(true));
                            }
                            else if(input.equals("false")) {
                                correct = true;
                                fcs.addArgument(new BoolValue(false));
                            }
                            else
                                System.out.println("Boolean value must be either true or false!");
                            break;
                        default:
                            System.out.println("Cannot create Class variable in console!");
                            System.out.println("------Abort------");
                            System.out.println("------Goodbye------");
                            return;
                    }
                }
            }
            System.out.println("------All parameters given correctly!------");
        }

        System.out.println("------Starting function: " + name + "------");
        var result = fcs.accept(cv);
        System.out.println("------Execution completed------");
        System.out.println("Result: " + result);
        System.out.println("------Goodbye------");

    }
}
