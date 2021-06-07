package Tests;

import Lexer.*;
import Parser.*;
import Parser.Statements.*;
import Parser.Values.*;
import Reader.Reader;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;

public class ParserTest {

    @Test
    public void floorOpTest() throws Exception
    {
        String s = "a";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseFloorOperatorT();

        Assert.assertNotNull(e);
        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Value");

        String name = ((Value) e).getName();

        Assert.assertEquals("a", name);

        s = "a_b";
        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertNotNull(e);
        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.FloorOperator");

        Expression lex, rex;
        lex = ((OperatorExpression) e).getLex();
        rex = ((OperatorExpression) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.Value");

        name = ((Value) lex).getName();
        String name2 = ((Value) rex).getName();

        Assert.assertEquals("a", name);
        Assert.assertEquals("b", name2);

        s = "a()";
        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Statements.FunctionCallStatement");

        name = ((FunctionCallStatement) e).getName();
        Vector<Expression> v = ((FunctionCallStatement) e).getArguments();

        Assert.assertEquals(name, "a");
        Assert.assertTrue(v.isEmpty());

        s ="a_b()";
        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.FloorOperator");

        lex = ((OperatorExpression) e).getLex();
        rex = ((OperatorExpression) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Statements.MethodCallStatement");

        name = ((MethodCallStatement) rex).getName();
        v = ((MethodCallStatement) rex).getArguments();

        Assert.assertEquals(name, "b");
        Assert.assertTrue(v.isEmpty());

        s ="a()_b()";
        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.FloorOperator");

        lex = ((OperatorExpression) e).getLex();
        rex = ((OperatorExpression) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Statements.FunctionCallStatement");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Statements.MethodCallStatement");

        name = ((FunctionCallStatement) lex).getName();
        name2 = ((MethodCallStatement) rex).getName();

        Assert.assertEquals(name, "a");
        Assert.assertEquals(name2, "b");

        s ="a()_b";
        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.FloorOperator");

        lex = ((OperatorExpression) e).getLex();
        rex = ((OperatorExpression) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Statements.FunctionCallStatement");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.Value");

        s ="a()_b_c()_d";
        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.FloorOperator");

        lex = ((OperatorExpression) e).getLex();
        rex = ((OperatorExpression) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.FloorOperator");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.Value");

        name2 = ((Value) rex).getName();

        Assert.assertEquals(name2, "d");

        e = lex;
        lex = ((FloorOperator) e).getLex();
        rex = ((FloorOperator) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.FloorOperator");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Statements.MethodCallStatement");

        name2 = ((MethodCallStatement) rex).getName();

        Assert.assertEquals(name2, "c");

        e = lex;
        lex = ((FloorOperator) e).getLex();
        rex = ((FloorOperator) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Statements.FunctionCallStatement");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.Value");

        name = ((FunctionCallStatement) lex).getName();
        name2 = ((Value) rex).getName();

        Assert.assertEquals(name, "a");
        Assert.assertEquals(name2, "b");
    }

    @Test
    public void functionAndMethodCallTest() throws Exception
    {
        String s = "fun(a,b,c)";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseFunctionCallT();

        Assert.assertNotNull(e);
        Assert.assertEquals(e.getClass().toString(), "class Parser.Statements.FunctionCallStatement");

        FunctionCallStatement fcs = (FunctionCallStatement) e;

        Assert.assertEquals(fcs.getName(), "fun");
        Assert.assertNotNull(fcs.getArguments());
        Assert.assertEquals(fcs.getArguments().size(), 3);

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseMethodCallT();

        Assert.assertNotNull(e);
        Assert.assertEquals(e.getClass().toString(), "class Parser.Statements.MethodCallStatement");

        MethodCallStatement mcs = (MethodCallStatement) e;

        Assert.assertEquals(mcs.getName(), "fun");
        Assert.assertNotNull(mcs.getArguments());
        Assert.assertEquals(mcs.getArguments().size(), 3);
    }

    @Test
    public void returnStatementTest() throws Exception
    {
        String s = "return a";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Statement st = p.parseReturnT();

        Assert.assertEquals(st.getClass().toString(), "class Parser.Statements.ReturnStatement");

        Expression e = ((ReturnStatement) st).getExpression();

        Assert.assertNotNull(e);
        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Value");
        Assert.assertEquals(((Value) e).getName(), "a");

    }

    @Test
    public void highOperatorStatementTest() throws Exception
    {
        String s = "-b";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseHighOperatorOpExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.MinusOperator");

        Expression e1 = ((MinusOperator) e).getEx();

        Assert.assertNotNull(e1);
        Assert.assertEquals(e1.getClass().toString(), "class Parser.Values.Value");
        Assert.assertEquals(((Value) e1).getName(), "b");

        s = "-3";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseHighOperatorOpExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.MinusOperator");

        e1 = ((MinusOperator) e).getEx();

        Assert.assertNotNull(e1);
        Assert.assertEquals(e1.getClass().toString(), "class Parser.Values.IntValue");
        Assert.assertEquals(((IntValue) e1).getValue(), 3);

        s = "-(\"abc\")";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseHighOperatorOpExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.MinusOperator");

        e1 = ((MinusOperator) e).getEx();

        Assert.assertNotNull(e1);
        Assert.assertEquals(e1.getClass().toString(), "class Parser.Values.MessValue");
        Assert.assertEquals(((MessValue) e1).getValue(), "abc");

        s = "-(-true)";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseHighOperatorOpExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.MinusOperator");

        e1 = ((MinusOperator) e).getEx();

        Assert.assertNotNull(e1);
        Assert.assertEquals(e1.getClass().toString(), "class Parser.Values.MinusOperator");

        e = ((MinusOperator) e1).getEx();
        Assert.assertNotNull(e);
        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.BoolValue");
        Assert.assertTrue(((BoolValue) e).getValue());
    }

    @Test
    public void powerOperatorTest() throws Exception
    {
        String s = "a^2";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parsePowerExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Power");

        Expression lex, rex;

        lex = ((Power) e).getLex();
        rex = ((Power) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "a");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);

        s = "a^2^b^3";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parsePowerExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Power");

        lex = ((Power) e).getLex();
        rex = ((Power) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.Power");
        Assert.assertEquals(((Value) lex).getName(), "a");

        e = rex;

        lex = ((Power) e).getLex();
        rex = ((Power) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.Power");
        Assert.assertEquals(((IntValue) lex).getValue(), 2);

        e = rex;

        lex = ((Power) e).getLex();
        rex = ((Power) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "b");
        Assert.assertEquals(((IntValue) rex).getValue(), 3);
    }

    @Test
    public void moduloOperatorTest() throws Exception
    {
        String s = "a%2";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseModExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Modulo");

        Expression lex, rex;

        lex = ((Modulo) e).getLex();
        rex = ((Modulo) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "a");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);

        s = "a%2%b%3";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseModExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Modulo");

        lex = ((Modulo) e).getLex();
        rex = ((Modulo) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Modulo");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((IntValue) rex).getValue(), 3);

        e = lex;

        lex = ((Modulo) e).getLex();
        rex = ((Modulo) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Modulo");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(((Value) rex).getName(), "b");

        e = lex;

        lex = ((Modulo) e).getLex();
        rex = ((Modulo) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "a");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);
    }

    @Test
    public void multOperatorTest() throws Exception
    {
        String s = "a*2";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseMultExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Multiplication");

        Expression lex, rex;

        lex = ((Multiplication) e).getLex();
        rex = ((Multiplication) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "a");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);

        s = "b/2";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseMultExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Division");

        lex = ((Division) e).getLex();
        rex = ((Division) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "b");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);

        s = "a*2/b*3";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseMultExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Multiplication");

        lex = ((Multiplication) e).getLex();
        rex = ((Multiplication) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Division");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((IntValue) rex).getValue(), 3);

        e = lex;

        lex = ((Division) e).getLex();
        rex = ((Division) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Multiplication");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(((Value) rex).getName(), "b");

        e = lex;

        lex = ((Multiplication) e).getLex();
        rex = ((Multiplication) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "a");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);
    }

    @Test
    public void addOperatorTest() throws Exception
    {
        String s = "a+2";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseAddExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Sum");

        Expression lex, rex;

        lex = ((Sum) e).getLex();
        rex = ((Sum) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "a");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);

        s = "b-2";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseAddExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Subtraction");

        lex = ((Subtraction) e).getLex();
        rex = ((Subtraction) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "b");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);

        s = "a+3-b+2";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseAddExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Sum");

        lex = ((Sum) e).getLex();
        rex = ((Sum) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Subtraction");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);

        e = lex;

        lex = ((Subtraction) e).getLex();
        rex = ((Subtraction) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Sum");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(((Value) rex).getName(), "b");

        e = lex;

        lex = ((Sum) e).getLex();
        rex = ((Sum) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "a");
        Assert.assertEquals(((IntValue) rex).getValue(), 3);
    }

    @Test
    public void mathOperationTest() throws Exception
    {
        String s = "a*b+2-(3%4*5)+4^7";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br,10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseAddExpressionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Sum");

        Expression lex, rex, lex1, rex1;

        lex = ((Sum) e).getLex();
        rex = ((Sum) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.Subtraction");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.Power");

        lex1 = ((Power) rex).getLex();
        rex1 = ((Power) rex).getRex();

        Assert.assertNotNull(lex1);
        Assert.assertNotNull(rex1);
        Assert.assertEquals(lex1.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(rex1.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((IntValue) lex1).getValue(), 4);
        Assert.assertEquals(((IntValue) rex1).getValue(), 7);

        e = lex;
        lex = ((Subtraction) e).getLex();
        rex = ((Subtraction) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.Sum");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.Multiplication");

        lex1 = ((Multiplication) rex).getLex();
        rex1 = ((Multiplication) rex).getRex();

        Assert.assertNotNull(lex1);
        Assert.assertNotNull(rex1);
        Assert.assertEquals(lex1.getClass().toString(),"class Parser.Values.Modulo");
        Assert.assertEquals(rex1.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((IntValue) rex1).getValue(), 5);

        rex = lex1;
        lex1 = ((Modulo) rex).getLex();
        rex1 = ((Modulo) rex).getRex();

        Assert.assertNotNull(lex1);
        Assert.assertNotNull(rex1);
        Assert.assertEquals(lex1.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(rex1.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((IntValue) lex1).getValue(), 3);
        Assert.assertEquals(((IntValue) rex1).getValue(), 4);

        e = lex;
        lex = ((Sum) e).getLex();
        rex = ((Sum) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Multiplication");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);

        lex1 = ((Multiplication) lex).getLex();
        rex1 = ((Multiplication) lex).getRex();

        Assert.assertNotNull(lex1);
        Assert.assertNotNull(rex1);
        Assert.assertEquals(lex1.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex1.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(((Value) lex1).getName(), "a");
        Assert.assertEquals(((Value) rex1).getName(), "b");
    }

    @Test
    public void conditionTest() throws Exception
    {
        String s = "a > 2";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.GreaterThan");

        Expression lex, rex;

        lex = ((GreaterThan) e).getLex();
        rex = ((GreaterThan) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "a");
        Assert.assertEquals(((IntValue) rex).getValue(), 2);

        s = "b >= 3";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.GreaterEqual");

        lex = ((GreaterEqual) e).getLex();
        rex = ((GreaterEqual) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(((Value) lex).getName(), "b");
        Assert.assertEquals(((IntValue) rex).getValue(), 3);

        s = "b*2 < 3+c";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.LowerThan");

        lex = ((LowerThan) e).getLex();
        rex = ((LowerThan) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.Multiplication");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.Sum");

        s = "a_b <= c()";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.LowerEqual");

        lex = ((LowerEqual) e).getLex();
        rex = ((LowerEqual) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.FloorOperator");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Statements.FunctionCallStatement");

        s = "a_b() == -c";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Equals");

        lex = ((Equals) e).getLex();
        rex = ((Equals) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.FloorOperator");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.MinusOperator");

        s = "3 != a^3";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br,10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.NotEquals");

        lex = ((NotEquals) e).getLex();
        rex = ((NotEquals) e).getRex();

        Assert.assertNotNull(lex);
        Assert.assertNotNull(rex);
        Assert.assertEquals(lex.getClass().toString(),"class Parser.Values.IntValue");
        Assert.assertEquals(rex.getClass().toString(),"class Parser.Values.Power");
    }

    @Test
    public void notConditionTest() throws Exception
    {
        String s = "!a";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseNotConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.NotOperator");

        Expression a = ((NotOperator) e).getEx();

        Assert.assertEquals(a.getClass().toString(), "class Parser.Values.Value");

        s = "!(a<b)";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseNotConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.NotOperator");

        a = ((NotOperator) e).getEx();

        Assert.assertEquals(a.getClass().toString(), "class Parser.Values.LowerThan");
    }

    @Test
    public  void andConditionTest() throws Exception
    {
        String s = "a & b";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseAndConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.AndOperator");

        Expression lex, rex;

        lex = ((AndOperator) e).getLex();
        rex = ((AndOperator) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.Value");

        s = "a!=b & c>3";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseAndConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.AndOperator");

        lex = ((AndOperator) e).getLex();
        rex = ((AndOperator) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.NotEquals");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.GreaterThan");
    }

    @Test
    public void orConditionTest() throws Exception
    {
        String s = "a v b";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseOrConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.OrOperator");

        Expression lex, rex;

        lex = ((OrOperator) e).getLex();
        rex = ((OrOperator) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.Value");

        s = "a^b v c<=3";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        e = p.parseOrConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.OrOperator");

        lex = ((OrOperator) e).getLex();
        rex = ((OrOperator) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.Power");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.LowerEqual");
    }

    @Test
    public void compoundCondition() throws Exception
    {
        String s = "a() > b_x & c < d v 2^5 != f & !c";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Expression e = p.parseOrConditionT();

        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.OrOperator");

        Expression lex, rex, lex1, rex1, lex2, rex2;

        lex = ((OrOperator) e).getLex();
        rex = ((OrOperator) e).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.AndOperator");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.AndOperator");

        lex1 = ((AndOperator) lex).getLex();
        rex1 = ((AndOperator) lex).getRex();

        Assert.assertEquals(lex1.getClass().toString(), "class Parser.Values.GreaterThan");
        Assert.assertEquals(rex1.getClass().toString(), "class Parser.Values.LowerThan");

        lex2 = ((GreaterThan) lex1).getLex();
        rex2 = ((GreaterThan) lex1).getRex();

        Assert.assertEquals(lex2.getClass().toString(), "class Parser.Statements.FunctionCallStatement");
        Assert.assertEquals(rex2.getClass().toString(), "class Parser.Values.FloorOperator");

        lex2 = ((LowerThan) rex1).getLex();
        rex2 = ((LowerThan) rex1).getRex();

        Assert.assertEquals(lex2.getClass().toString(), "class Parser.Values.Value");
        Assert.assertEquals(rex2.getClass().toString(), "class Parser.Values.Value");

        lex1 = ((AndOperator) rex).getLex();
        rex1 = ((AndOperator) rex).getRex();

        Assert.assertEquals(lex1.getClass().toString(), "class Parser.Values.NotEquals");
        Assert.assertEquals(rex1.getClass().toString(), "class Parser.Values.NotOperator");

        lex2 = ((NotEquals) lex1).getLex();
        rex2 = ((NotEquals) lex1).getRex();

        Assert.assertEquals(lex2.getClass().toString(), "class Parser.Values.Power");
        Assert.assertEquals(rex2.getClass().toString(), "class Parser.Values.Value");

        e = ((NotOperator) rex1).getEx();
        Assert.assertEquals(e.getClass().toString(), "class Parser.Values.Value");
    }

    @Test
    public void declarationTest() throws Exception
    {
        String s = "num a;";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 2);
        Parser p = new Parser(l);

        Vector<Statement> v = p.parseAssignmentOrVarDeclarationT();

        Assert.assertFalse(v.isEmpty());

        Statement st = v.elementAt(0);

        Assert.assertEquals(st.getClass().toString(), "class Parser.Statements.VariableDeclaration");

        TokenTypes type = ((VariableDeclaration) st).getType();
        String name = ((VariableDeclaration) st).getName();

        Assert.assertEquals(name, "a");
        Assert.assertEquals(type, TokenTypes.NUM);

        s = "mess b;";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        Assert.assertFalse(v.isEmpty());

        st = v.elementAt(0);

        Assert.assertEquals(st.getClass().toString(), "class Parser.Statements.VariableDeclaration");

        type = ((VariableDeclaration) st).getType();
        name = ((VariableDeclaration) st).getName();

        Assert.assertEquals(name, "b");
        Assert.assertEquals(type, TokenTypes.MESS);

        s = "bool c;";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        Assert.assertFalse(v.isEmpty());

        st = v.elementAt(0);

        Assert.assertEquals(st.getClass().toString(), "class Parser.Statements.VariableDeclaration");

        type = ((VariableDeclaration) st).getType();
        name = ((VariableDeclaration) st).getName();

        Assert.assertEquals(name, "c");
        Assert.assertEquals(type, TokenTypes.BOOL);

        s = "MyClass d;";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        Assert.assertFalse(v.isEmpty());

        st = v.elementAt(0);

        Assert.assertEquals(st.getClass().toString(), "class Parser.Statements.VariableClassDeclaration");

        String className = ((VariableClassDeclaration) st).getClassName();
        name = ((VariableClassDeclaration) st).getName();

        Assert.assertEquals(name, "d");
        Assert.assertEquals(className, "MyClass");

        s = "num a = 1;";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 2);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        Assert.assertFalse(v.isEmpty());
    }

    @Test
    public void assignmentTest() throws Exception
    {
        String s = "a=2;";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        Vector<Statement> v = p.parseAssignmentOrVarDeclarationT();
        Assert.assertFalse(v.isEmpty());

        Statement st = v.elementAt(0);

        Assert.assertEquals(st.getClass().toString(), "class Parser.Values.Equal");

        Expression lex, rex;

        lex = ((Equal) st).getLex();
        rex = ((Equal) st).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.Value");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.IntValue");

        s = "a_b = 3;";

        br = new BufferedReader(new StringReader(s));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        Assert.assertFalse(v.isEmpty());

        st = v.elementAt(0);

        Assert.assertEquals(st.getClass().toString(), "class Parser.Values.Equal");

        lex = ((Equal) st).getLex();
        rex = ((Equal) st).getRex();

        Assert.assertEquals(lex.getClass().toString(), "class Parser.Values.FloorOperator");
        Assert.assertEquals(rex.getClass().toString(), "class Parser.Values.IntValue");
    }

    @Test
    public void forTest() throws Exception
    {
        String s = "for(num a = 3 | a < 6 | a+1) { num c = a; a = 2*3; } ";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        Statement st = p.parseForT();

        Assert.assertNotNull(st);
        Assert.assertEquals(st.getClass().toString(), "class Parser.Statements.ForStatement");

        Vector<Statement> v;
        Expression cond, after;

        cond = ((ForStatement) st).getCondition();
        after = ((ForStatement) st).getPostInstr();
        v = ((ForStatement) st).getInitialize();

        Assert.assertNotNull(cond);
        Assert.assertNotNull(v);
        Assert.assertNotNull(after);
        Assert.assertEquals(v.size(), 2);
        Assert.assertEquals(cond.getClass().toString(), "class Parser.Values.LowerThan");
        Assert.assertEquals(after.getClass().toString(), "class Parser.Values.Sum");
    }

    @Test
    public void ifTest() throws Exception
    {
        String s = "if(a<3){a=a+1;}elseif(a<=5){a = a*2;}elseif(a>b){b=b-3;}else{a=fun();}";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        Statement st = p.parseIfT();

        Assert.assertNotNull(st);
        Assert.assertEquals(st.getClass().toString(), "class Parser.Statements.IfStatement");

        Vector<Expression> conditions = ((IfStatement) st).getConditions();
        Vector<Vector<Statement>> ifInstructions = ((IfStatement) st).getIfInstructions();
        Vector<Statement> elseInstructions = ((IfStatement) st).getElseInstructions();

        Assert.assertNotNull(conditions);
        Assert.assertNotNull(ifInstructions);
        Assert.assertNotNull(elseInstructions);

        Assert.assertEquals(conditions.size(), ifInstructions.size());
        Assert.assertEquals(conditions.elementAt(0).getClass().toString(), "class Parser.Values.LowerThan");
        Assert.assertEquals(conditions.elementAt(1).getClass().toString(), "class Parser.Values.LowerEqual");
        Assert.assertEquals(conditions.elementAt(2).getClass().toString(), "class Parser.Values.GreaterThan");

        Vector<Statement> instructions = ifInstructions.elementAt(0);

        Assert.assertEquals(instructions.elementAt(0).getClass().toString(), "class Parser.Values.Equal");

        instructions = ifInstructions.elementAt(1);

        Assert.assertEquals(instructions.elementAt(0).getClass().toString(), "class Parser.Values.Equal");

        instructions = ifInstructions.elementAt(2);

        Assert.assertEquals(instructions.elementAt(0).getClass().toString(), "class Parser.Values.Equal");

        Assert.assertEquals(elseInstructions.elementAt(0).getClass().toString(), "class Parser.Values.Equal");

    }

    @Test
    public void whileTest() throws Exception
    {
        String s = "while(a < 5){ a = a - 1; num b = fun(); }";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        Statement st = p.parseWhileT();

        Assert.assertNotNull(st);
        Assert.assertEquals(st.getClass().toString(), "class Parser.Statements.WhileStatement");

        Expression con = ((WhileStatement) st).getCondition();

        Assert.assertNotNull(con);
        Assert.assertEquals(con.getClass().toString(), "class Parser.Values.LowerThan");

        Vector<Statement> inst = ((WhileStatement) st).getInstructions();

        Assert.assertNotNull(inst);
        Assert.assertEquals(inst.size(), 3);
    }

    @Test
    public void functionTest() throws Exception
    {
        String s = "fun(num a, mess b, bool c){ a = a*2; b = \"sth\"; c = true; return c v !c; }";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        p.parse();

        HashMap<String, FunctionStatement> function = p.getFunctions();

        Assert.assertNotNull(function);
        Assert.assertEquals(function.size(), 1);
        Assert.assertTrue(function.containsKey("fun"));

        FunctionStatement fs = function.get("fun");

        Assert.assertNotNull(fs.getArguments());
        Assert.assertNotNull(fs.getInstructions());
        Assert.assertEquals(fs.getArguments().size(), 3);
        Assert.assertEquals(fs.getInstructions().size(), 4);
    }

    @Test
    public void classTest() throws Exception
    {
        String s = "class MyClass{ num a; mess b; bool c; ClassB cb; funA(){ return a; } funB(){} }";

        BufferedReader br = new BufferedReader(new StringReader(s));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        p.parse();

        HashMap<String, ClassStatement> classes = p.getClasses();

        Assert.assertNotNull(classes);
        Assert.assertTrue(classes.containsKey("MyClass"));

        ClassStatement cs = classes.get("MyClass");
        Vector<VariableDeclaration> arguments = cs.getArguments();

        Assert.assertNotNull(arguments);
        Assert.assertEquals(arguments.size(), 4);

        HashMap<String, FunctionStatement> functions = cs.getFunctions();

        Assert.assertNotNull(functions);
        Assert.assertEquals(functions.size(), 2);
    }
}