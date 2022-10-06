package uni.projects.parser;

import uni.projects.lexer.*;
import uni.projects.parser.statements.*;
import uni.projects.parser.values.*;

import java.util.HashMap;
import java.util.Vector;

public class Parser {

    Lexer lexer;

    private HashMap<String, FunctionStatement> functions;
    private HashMap<String, ClassStatement> classes;

    public Parser(Lexer l) {
        this.lexer = l;
        this.classes = new HashMap<>();
        this.functions = new HashMap<>();
    }

    public HashMap<String, FunctionStatement> getFunctions() {
        return this.functions;
    }

    public HashMap<String, ClassStatement> getClasses() {
        return this.classes;
    }

    public void parse() throws Exception {

        FunctionStatement fs;
        ClassStatement cs = null;
        while (((fs = parseFunction()) != null) || ((cs = parseClass()) != null)) {
            if (fs != null) {
                if (functions.containsKey(fs.getName()))
                    throwRedefinitionException("function", fs.getName());
                functions.put(fs.getName(), fs);
            } else {
                if (classes.containsKey(cs.getName()))
                    throwRedefinitionException("class", cs.getName());
                classes.put(cs.getName(), cs);
            }
        }
        if (!isToken(TokenTypes.EOF))
            throwTokenException("EOF");
    }

    private FunctionStatement parseFunction() throws Exception {
        if (!lexer.getCurrentToken().getName().equals(TokenTypes.IDENTIFIER))
            return null;

        String name = ((IdentifierToken) lexer.getCurrentAndAdvance()).getId();
        mustBe(TokenTypes.ROUND_OPEN);
        var header = parseFunctionHeader();
        mustBe(TokenTypes.ROUND_CLOSE);
        var instructions = parseInstructions();

        return new FunctionStatement(name, header, instructions);
    }

    private Vector<VariableDeclaration> parseFunctionHeader() throws Exception {
        Vector<VariableDeclaration> header = new Vector<>();

        VariableDeclaration vd;

        vd = parseVarDeclaration();
        if (vd != null) {
            header.add(vd);
            while (isTokenThenConsume(TokenTypes.COMMA)) {
                header.add(parseVarDeclaration());
            }
        }

        return header;
    }

    private VariableDeclaration parseVarDeclaration() throws Exception {
        if (!checkType(lexer.getCurrentToken().getName()))
            return null;

        VariableDeclaration vd;

        vd = parseClassVariableDeclaration();

        if (vd == null) {
            vd = NormalVariableDeclaration();
        }

        return vd;
    }

    private VariableDeclaration parseClassVariableDeclaration() throws Exception {
        if (!isToken(TokenTypes.IDENTIFIER))
            return null;

        Token classNameT = lexer.getCurrentAndAdvance();

        String className = ((IdentifierToken) classNameT).getId();

        if (!isToken(TokenTypes.IDENTIFIER))
            throwTokenException("IDENTIFIER");

        Token nameT = lexer.getCurrentAndAdvance();

        String name = ((IdentifierToken) nameT).getId();

        return new VariableClassDeclaration(TokenTypes.REF, className, name);
    }

    private VariableDeclaration NormalVariableDeclaration() throws Exception {
        Token type = lexer.getCurrentAndAdvance();

        if (!isToken(TokenTypes.IDENTIFIER))
            throwTokenException("IDENTIFIER");

        Token name = lexer.getCurrentAndAdvance();

        return new VariableDeclaration(type.getName(), ((IdentifierToken) name).getId());
    }

    private ClassStatement parseClass() throws Exception {
        if (!isToken(TokenTypes.CLASS))
            return null;

        lexer.advance();

        String name = ((IdentifierToken) lexer.getCurrentAndAdvance()).getId();

        if (classes.containsKey(name))
            throwRedefinitionException("class", name);

        mustBe(TokenTypes.CURL_OPEN);

        Vector<VariableDeclaration> args = new Vector<>();

        boolean variable = true;

        Token temporary;

        while (variable) {
            if (!checkType(lexer.getCurrentToken().getName()))
                variable = false;
            else {
                if (isToken(TokenTypes.IDENTIFIER)) {
                    temporary = lexer.getCurrentAndAdvance();

                    if (!isToken(TokenTypes.IDENTIFIER)) {
                        lexer.regress();
                        variable = false;
                    } else {
                        String className = ((IdentifierToken) temporary).getId();

                        Token nameT = lexer.getCurrentAndAdvance();

                        String varName = ((IdentifierToken) nameT).getId();

                        args.add(new VariableClassDeclaration(TokenTypes.REF, className, varName));

                        mustBe(TokenTypes.SEMICOLON);
                    }
                } else {
                    args.add(NormalVariableDeclaration());
                    mustBe(TokenTypes.SEMICOLON);
                }
            }
        }

        HashMap<String, FunctionStatement> functions = new HashMap<>();

        boolean function = true;

        FunctionStatement fs;

        while (function) {
            fs = parseFunction();
            if (fs == null)
                function = false;
            else {
                if (functions.containsKey(fs.getName()))
                    throwRedefinitionException("function in class " + name, fs.getName());
                else
                    functions.put(fs.getName(), fs);
            }
        }

        mustBe(TokenTypes.CURL_CLOSE);

        return new ClassStatement(args, functions, name);

    }

    private Expression parseAddExpression() throws Exception {
        Expression lex = parseMultExpression();
        if (lex == null)
            return null;
        while (isToken(TokenTypes.PLUS) || isToken(TokenTypes.MINUS)) {
            TokenTypes operator = lexer.getCurrentToken().getName();
            lexer.advance();

            Expression rex = parseMultExpression();
            if (rex == null)
                throw new Exception("Missing expression!");
            if (operator == TokenTypes.PLUS) {
                lex = new Sum(lex, rex);
            } else
                lex = new Subtraction(lex, rex);
        }

        return lex;
    }

    private Expression parseMultExpression() throws Exception {
        Expression lex = parseModExpression();
        if (lex == null)
            return null;
        while (isToken(TokenTypes.MULT) || isToken(TokenTypes.DIVIDE)) {
            TokenTypes operator = lexer.getCurrentToken().getName();
            lexer.advance();

            Expression rex = parseModExpression();
            if (rex == null)
                throw new Exception();
            if (operator == TokenTypes.MULT) {
                lex = new Multiplication(lex, rex);
            } else
                lex = new Division(lex, rex);
        }

        return lex;
    }

    private Expression parseModExpression() throws Exception {
        Expression lex = parsePowerExpression();
        if (lex == null)
            return null;

        while (isToken(TokenTypes.MOD_OP)) {
            lexer.advance();

            Expression rex = parsePowerExpression();
            if (rex == null)
                throw new Exception();
            lex = new Modulo(lex, rex);
        }

        return lex;
    }

    private Expression parsePowerExpression() throws Exception {
        Expression lex = parseHighOperatorOpExpression();
        if (lex == null)
            return null;

        if (!isTokenThenConsume(TokenTypes.POW_OP))
            return lex;

        Expression rex = parsePowerExpression();

        return new Power(lex, rex);
    }

    private Expression parseHighOperatorOpExpression() throws Exception {
        Token t = lexer.getCurrentAndAdvance();
        if (isToken(TokenTypes.ROUND_OPEN, t)) {
            Expression e = parseAddExpression();
            if (e == null)
                throw new Exception("Sematic error!");

            mustBe(TokenTypes.ROUND_CLOSE);

            return e;
        } else if (isToken(TokenTypes.MINUS, t)) {
            Expression rex = parseHighOperatorOpExpression();
            if (rex == null)
                throw new Exception("Missing expression!");

            return new MinusOperator(rex);
        } else if (t instanceof IntToken) {
            int value = ((IntToken) t).getValue();
            return new IntValue(value);
        } else if (t instanceof MessToken) {
            String value = ((MessToken) t).getMess();
            return new MessValue(value);
        } else if (t instanceof BoolToken) {
            boolean value = ((BoolToken) t).getValue();
            return new BoolValue(value);
        } else {
            lexer.regress();
            return parseFloorOperator();
        }
    }

    private Expression parseFloorOperator() throws Exception {
        Expression rex, lex;
        lex = parseFunctionCall();
        if (lex == null && !isToken(TokenTypes.IDENTIFIER))
            return null;
        else if (lex == null)
            lex = new Value(((IdentifierToken) lexer.getCurrentAndAdvance()).getId());

        while (isTokenThenConsume(TokenTypes.FLOOR)) {
            if (!isToken(TokenTypes.IDENTIFIER))
                throw new Exception("Missing expresion!");

            Token name = lexer.getCurrentAndAdvance();
            if (!isToken(TokenTypes.ROUND_OPEN))
                rex = new Value(((IdentifierToken) name).getId());
            else {
                lexer.regress();
                rex = parseMethodCall();
            }
            if (rex == null)
                throw new Exception("Missing expression!");

            lex = new FloorOperator(lex, rex);
        }
        return lex;
    }

    private Expression parseFunctionCall() throws Exception {
        if (!isToken(TokenTypes.IDENTIFIER))
            return null;

        String name = ((IdentifierToken) lexer.getCurrentToken()).getId();

        lexer.advance();

        if (!isTokenThenConsume(TokenTypes.ROUND_OPEN)) {
            lexer.regress();
            return null;
        }

        FunctionCallStatement fcs = new FunctionCallStatement(name);

        Expression ex;

        if (!isToken(TokenTypes.ROUND_CLOSE)) {
            ex = parseOrCondition();
            if (ex == null)
                throw new Exception("Missing Expression");

            fcs.addArgument(ex);
        }
        while (!isTokenThenConsume(TokenTypes.ROUND_CLOSE)) {
            mustBe(TokenTypes.COMMA);

            ex = parseAndCondition();
            if (ex == null)
                throw new Exception("Missing Expression");
            fcs.addArgument(ex);
        }

        return fcs;
    }

    private Expression parseMethodCall() throws Exception {
        if (!isToken(TokenTypes.IDENTIFIER))
            return null;

        String name = ((IdentifierToken) lexer.getCurrentToken()).getId();

        lexer.advance();

        if (!isTokenThenConsume(TokenTypes.ROUND_OPEN))
            return null;

        MethodCallStatement mcs = new MethodCallStatement(name);

        Expression ex;

        if (!isToken(TokenTypes.ROUND_CLOSE)) {
            ex = parseAndCondition();
            if (ex == null)
                throw new Exception("Missing Expression");
            mcs.addArgument(ex);
        }
        while (!isToken(TokenTypes.ROUND_CLOSE)) {
            mustBe(TokenTypes.COMMA);

            ex = parseAndCondition();
            if (ex == null)
                throw new Exception("Missing Expression");
            mcs.addArgument(ex);
        }

        lexer.advance();

        return mcs;
    }

    private Vector<Statement> parseInstructions() throws Exception {
        Vector<Statement> instructions = new Vector<>();

        if (!isTokenThenConsume(TokenTypes.CURL_OPEN))
            return null;

        Vector<Statement> instr;
        while ((instr = parseInstruction()) != null)
            instructions.addAll(instr);

        mustBe(TokenTypes.CURL_CLOSE);

        return instructions;
    }

    private Vector<Statement> parseInstruction() throws Exception {
        Statement st;

        if (checkType(lexer.getCurrentToken().getName()))
            return parseAssignmentOrVarDeclarationT();

        Vector<Statement> v = new Vector<>();

        st = parseIf();
        if (st != null) {
            v.add(st);
            return v;
        }

        st = parseWhile();
        if (st != null) {
            v.add(st);
            return v;
        }

        st = parseFor();
        if (st != null) {
            v.add(st);
            return v;
        }

        st = parseReturn();
        if (st != null) {
            v.add(st);
            return v;
        }
        return null;
    }

    private Statement parseIf() throws Exception {
        if (!isTokenThenConsume(TokenTypes.IF))
            return null;

        mustBe(TokenTypes.ROUND_OPEN);

        Vector<Expression> conditions = new Vector<>();

        Expression ex = parseOrCondition();

        if (ex == null)
            throw new Exception("Missing condition in line: " + lexer.getCurrentToken().getPos().getLine() +
                    " column: " + lexer.getCurrentToken().getPos().getColumn() + "\ncontext: " +
                    lexer.getCurrentToken().getContext());
        conditions.add(ex);

        mustBe(TokenTypes.ROUND_CLOSE);

        Vector<Vector<Statement>> ifInstructions = new Vector<>();

        Vector<Statement> trueInstructions = parseInstructions();

        if (trueInstructions == null)
            throw new Exception();

        ifInstructions.add(trueInstructions);

        while (isTokenThenConsume(TokenTypes.ELSEIF)) {
            mustBe(TokenTypes.ROUND_OPEN);

            ex = parseOrCondition();
            if (ex == null)
                throw new Exception("Missing condition in line: " + lexer.getCurrentToken().getPos().getLine() +
                        " column: " + lexer.getCurrentToken().getPos().getColumn() + "\ncontext: " +
                        lexer.getCurrentToken().getContext());

            conditions.add(ex);

            mustBe(TokenTypes.ROUND_CLOSE);

            trueInstructions = parseInstructions();
            if (trueInstructions == null)
                throw new Exception();
            ifInstructions.add(trueInstructions);
        }

        Vector<Statement> elseInstructions = null;

        if (lexer.getCurrentToken().getName().equals(TokenTypes.ELSE)) {
            lexer.advance();

            elseInstructions = parseInstructions();
            if (elseInstructions == null)
                throw new Exception();
        }

        return new IfStatement(conditions, ifInstructions, elseInstructions);
    }

    private Statement parseWhile() throws Exception {
        if (!isTokenThenConsume(TokenTypes.WHILE))
            return null;

        mustBe(TokenTypes.ROUND_OPEN);

        Expression ex = parseOrCondition();
        if (ex == null)
            throw new Exception("Missing condition in line: " + lexer.getCurrentToken().getPos().getLine() +
                    " column: " + lexer.getCurrentToken().getPos().getColumn() + "\ncontext: " +
                    lexer.getCurrentToken().getContext());

        mustBe(TokenTypes.ROUND_CLOSE);

        Vector<Statement> instructions = parseInstructions();

        return new WhileStatement(ex, instructions);

    }

    private Statement parseFor() throws Exception {
        if (!isTokenThenConsume(TokenTypes.FOR))
            return null;

        mustBe(TokenTypes.ROUND_OPEN);

        Vector<Statement> initialize = parseAssignmentOrVarDeclaration();

        mustBe(TokenTypes.BAR);

        Expression condition = parseCondition();
        if (condition == null)
            throw new Exception("Missing condition in line: " + lexer.getCurrentToken().getPos().getLine() +
                    " column: " + lexer.getCurrentToken().getPos().getColumn() + "\ncontext: " +
                    lexer.getCurrentToken().getContext());

        mustBe(TokenTypes.BAR);

        Expression postInstr = parseAddExpression();
        if (postInstr == null)
            throw new Exception("Missing condition in line: " + lexer.getCurrentToken().getPos().getLine() +
                    " column: " + lexer.getCurrentToken().getPos().getColumn() + "\ncontext: " +
                    lexer.getCurrentToken().getContext());

        mustBe(TokenTypes.ROUND_CLOSE);

        Vector<Statement> instructions = parseInstructions();

        return new ForStatement(initialize, condition, postInstr, instructions);
    }

    private Expression parseCondition() throws Exception {
        Expression lex = parseAddExpression();

        if (lex == null)
            return null;

        ComparisonOperator operator;

        if (isTokenThenConsume(TokenTypes.EQUAL))
            operator = new Equals();
        else if (isTokenThenConsume(TokenTypes.NOT_EQUAL))
            operator = new NotEquals();
        else if (isTokenThenConsume(TokenTypes.LESS))
            operator = new LowerThan();
        else if (isTokenThenConsume(TokenTypes.LESS_EQUAL))
            operator = new LowerEqual();
        else if (isTokenThenConsume(TokenTypes.GREATER))
            operator = new GreaterThan();
        else if (isTokenThenConsume(TokenTypes.GREATER_EQUAL))
            operator = new GreaterEqual();
        else
            return lex;

        Expression rex = parseAddExpression();

        if (rex == null)
            throw new Exception("Missing expression!");

        operator.setLex(lex);
        operator.setRex(rex);

        return operator;
    }

    private Expression parseOrCondition() throws Exception {
        Expression lex = parseAndCondition();
        if (lex == null)
            return null;

        while (isTokenThenConsume(TokenTypes.OR_OP)) {
            Expression rex = parseAndCondition();
            if (rex == null)
                throw new Exception("Missing expression!");

            lex = new OrOperator(lex, rex);
        }

        return lex;
    }

    private Expression parseAndCondition() throws Exception {
        Expression lex = parseNotCondition();
        if (lex == null)
            return null;

        while (isTokenThenConsume(TokenTypes.AND_OP)) {
            Expression rex = parseNotCondition();
            if (rex == null)
                throw new Exception("Missing expression!");

            lex = new AndOperator(lex, rex);
        }

        return lex;
    }

    private Expression parseNotCondition() throws Exception {
        if (isTokenThenConsume(TokenTypes.NOT_OP)) {
            return new NotOperator(parseNotCondition());
        } else if (isTokenThenConsume(TokenTypes.ROUND_OPEN)) {
            Expression ex = parseOrCondition();

            if (ex == null)
                throw new Exception("Missing expression!");

            mustBe(TokenTypes.ROUND_CLOSE);

            return ex;
        } else
            return parseCondition();
    }

    private Statement parseReturn() throws Exception {
        if (!isTokenThenConsume(TokenTypes.RETURN))
            return null;

        ReturnStatement rs = new ReturnStatement();

        rs.setExpression(parseOrCondition());

        isTokenThenConsume(TokenTypes.SEMICOLON);

        return rs;
    }

    private Vector<Statement> parseAssignmentOrVarDeclaration() throws Exception {
        Vector<Statement> st = new Vector<>();

        if (!checkType(lexer.getCurrentToken().getName()))
            return null;

        Token type = lexer.getCurrentAndAdvance();
        Token second = lexer.getCurrentToken();

        if (isToken(TokenTypes.IDENTIFIER)) // deklaracja
        {

            String name = ((IdentifierToken) second).getId();
            if (isToken(TokenTypes.IDENTIFIER, type)) {
                String className = ((IdentifierToken) type).getId();
                st.add(new VariableClassDeclaration(TokenTypes.REF, className, name));
            } else {
                TokenTypes varType = type.getName();
                st.add(new VariableDeclaration(varType, name));
            }

            lexer.advance();

            if (isTokenThenConsume(TokenTypes.ASSIGNMENT_OP)) {
                Expression ex = parseAndCondition();

                if (ex == null)
                    throw new Exception("Missing expression!");

                st.add(new Equal(new Value(name), ex));

            }

            if (isTokenThenConsume(TokenTypes.SEMICOLON))
                return st;
            else if (isToken(TokenTypes.BAR))
                return st;
            else
                throw new Exception("Sematnic error!");
        }

        Expression lex; // samo przypisanie

        if (isToken(TokenTypes.FLOOR)) {
            lexer.regress();
            lex = parseFloorOperator();
        } else if (isToken(TokenTypes.IDENTIFIER, type))
            lex = new Value(((IdentifierToken) type).getId());
        else
            throw new Exception("Semantic error!");

        if (isTokenThenConsume(TokenTypes.ASSIGNMENT_OP)) {
            Expression rex = parseAndCondition();
            if (rex == null)
                throw new Exception("Missing expression!");

            st.add(new Equal(lex, rex));
        } else
            throw new Exception("Semantic error!");

        if (isToken(TokenTypes.SEMICOLON))
            lexer.advance();

        return st;
    }

    private boolean isToken(TokenTypes expected, Token t) {
        return expected.equals(t.getName());
    }

    private boolean isToken(TokenTypes expected) throws Exception {
        return expected.equals(this.lexer.getCurrentToken().getName());
    }

    private boolean isTokenThenConsume(TokenTypes expected) throws Exception {
        if (!isToken(expected))
            return false;
        lexer.advance();
        return true;
    }

    private void mustBe(TokenTypes expected) throws Exception {
        if (!isTokenThenConsume(expected))
            throwTokenException(expected.toString());
    }

    private boolean checkType(TokenTypes type) {
        return type.equals(TokenTypes.NUM) || type.equals(TokenTypes.BOOL) || type.equals(TokenTypes.MESS)
                || type.equals(TokenTypes.IDENTIFIER);
    }

    private void throwTokenException(String expected) throws Exception {
        throw new Exception("Error in line: " + lexer.getCurrentToken().getPos().getLine() +
                " in column: " + lexer.getCurrentToken().getPos().getColumn() +
                " context: " + lexer.getCurrentToken().getContext() + "\n" +
                "Expected token " + expected + " ; actual: " + lexer.getCurrentToken().getName());
    }

    private void throwRedefinitionException(String what, String name) throws Exception {
        throw new Exception("Error in line: " + lexer.getCurrentToken().getPos().getLine() +
                " in column: " + lexer.getCurrentToken().getPos().getColumn() +
                " context: " + lexer.getCurrentToken().getContext() + "\n" +
                "redefinition of " + what + ": " + name);
    }

    // Cheater functions for tests
    public Expression parseFloorOperatorT() throws Exception {
        return parseFloorOperator();
    }

    public Expression parseFunctionCallT() throws Exception {
        return parseFunctionCall();
    }

    public Expression parseMethodCallT() throws Exception {
        return parseMethodCall();
    }

    public Statement parseReturnT() throws Exception {
        return parseReturn();
    }

    public Expression parseHighOperatorOpExpressionT() throws Exception {
        return parseHighOperatorOpExpression();
    }

    public Expression parsePowerExpressionT() throws Exception {
        return parsePowerExpression();
    }

    public Expression parseModExpressionT() throws Exception {
        return parseModExpression();
    }

    public Expression parseMultExpressionT() throws Exception {
        return parseMultExpression();
    }

    public Expression parseAddExpressionT() throws Exception {
        return parseAddExpression();
    }

    public Expression parseConditionT() throws Exception {
        return parseCondition();
    }

    public Expression parseNotConditionT() throws Exception {
        return parseNotCondition();
    }

    public Expression parseAndConditionT() throws Exception {
        return parseAndCondition();
    }

    public Expression parseOrConditionT() throws Exception {
        return parseOrCondition();
    }

    public Vector<Statement> parseAssignmentOrVarDeclarationT() throws Exception {
        return parseAssignmentOrVarDeclaration();
    }

    public Statement parseForT() throws Exception {
        return parseFor();
    }

    public Statement parseIfT() throws Exception {
        return parseIf();
    }

    public Statement parseWhileT() throws Exception {
        return parseWhile();
    }
}