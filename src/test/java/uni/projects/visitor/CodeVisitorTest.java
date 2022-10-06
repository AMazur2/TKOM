package uni.projects.visitor;

import org.junit.Assert;
import org.junit.Test;
import uni.projects.lexer.Lexer;
import uni.projects.lexer.TokenTypes;
import uni.projects.parser.*;
import uni.projects.parser.statements.ClassStatement;
import uni.projects.parser.statements.FunctionCallStatement;
import uni.projects.parser.statements.FunctionStatement;
import uni.projects.parser.statements.VariableDeclaration;
import uni.projects.parser.values.*;
import uni.projects.reader.Reader;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Vector;

public class CodeVisitorTest {

    @Test
    public void visitBoolTest() {

        Visitor v = new CodeVisitor();

        BoolValue bv = new BoolValue(true);

        Assert.assertTrue(bv.getValue());

        Assert.assertTrue((Boolean) bv.accept(v));

        bv = new BoolValue(false);

        Assert.assertFalse(bv.getValue());

        Assert.assertFalse((Boolean) bv.accept(v));
    }

    @Test
    public void visitIntTest() {

        Visitor v = new CodeVisitor();

        IntValue iv = new IntValue(5);

        Assert.assertEquals(iv.getValue(), 5);

        Assert.assertEquals(iv.accept(v), 5);

        iv = new IntValue(-2);

        Assert.assertEquals(iv.getValue(), -2);

        Assert.assertEquals(iv.accept(v), -2);
    }

    @Test
    public void visitMessTest() {

        Visitor v = new CodeVisitor();

        MessValue mv = new MessValue("abc");

        Assert.assertEquals(mv.getValue(), "abc");

        Assert.assertEquals(mv.accept(v), "abc");

        mv = new MessValue("a1b2c3d4e5");

        Assert.assertEquals(mv.getValue(), "a1b2c3d4e5");

        Assert.assertEquals(mv.accept(v), "a1b2c3d4e5");
    }

    @Test
    public void visitValueTest() throws Exception {
        CodeVisitor cv = new CodeVisitor();

        Scope s = new Scope();

        Variable v1 = new Variable(VariableType.INT, 125);
        s.putVariable("num", v1);

        Variable v2 = new Variable(VariableType.BOOL, false);
        s.putVariable("b", v2);

        Variable v3 = new Variable(VariableType.STRING, "abc");
        s.putVariable("string", v3);

        Value val1 = new Value("num");
        Value val2 = new Value("b");
        Value val3 = new Value("string");

        cv.setCurr(s);

        Assert.assertEquals(val1.accept(cv), 125);
        Assert.assertFalse((Boolean) val2.accept(cv));
        Assert.assertEquals(val3.accept(cv), "abc");
    }

    @Test
    public void visitMinusOperatorTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv = new IntValue(2);

        MinusOperator mo = new MinusOperator(iv);

        Assert.assertEquals(mo.accept(v), -2);

        iv = new IntValue(-7);

        mo = new MinusOperator(iv);

        Assert.assertEquals(mo.accept(v), 7);
    }

    @Test
    public void visitSumTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(1);
        IntValue iv2 = new IntValue(2);
        IntValue iv3 = new IntValue(3);

        Sum sum = new Sum(iv1, iv2);

        Assert.assertEquals(sum.accept(v), 3);

        Sum sum1 = new Sum(sum, iv3);

        Assert.assertEquals(sum1.accept(v), 6);

        MessValue mv = new MessValue("abc");
        IntValue iv = new IntValue(1);

        sum = new Sum(mv, iv);

        Assert.assertEquals(sum.accept(v), "abc1");

        MessValue mv1 = new MessValue("abc");
        MessValue mv2 = new MessValue("def");

        sum = new Sum(mv1, mv2);

        Assert.assertEquals(sum.accept(v), "abcdef");
    }

    @Test
    public void visitSubtractionTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(2);
        IntValue iv2 = new IntValue(1);
        IntValue iv3 = new IntValue(3);

        Subtraction sub = new Subtraction(iv1, iv2);

        Assert.assertEquals(sub.accept(v), 1);

        Subtraction sub1 = new Subtraction(sub, iv3);

        Assert.assertEquals(sub1.accept(v), -2);

        sub1 = new Subtraction(iv3, sub);

        Assert.assertEquals(sub1.accept(v), 2);

        iv1 = new IntValue(1);
        iv2 = new IntValue(2);

        sub = new Subtraction(iv1, iv2);

        Assert.assertEquals(sub.accept(v), -1);
    }

    @Test
    public void visitMultiplicationTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(2);
        IntValue iv2 = new IntValue(1);
        IntValue iv3 = new IntValue(3);

        Multiplication mult = new Multiplication(iv1, iv2);

        Assert.assertEquals(mult.accept(v), 2);

        Multiplication mult1 = new Multiplication(mult, iv3);

        Assert.assertEquals(mult1.accept(v), 6);

        iv1 = new IntValue(-2);
        iv2 = new IntValue(1);

        mult = new Multiplication(iv1, iv2);

        Assert.assertEquals(mult.accept(v), -2);

        iv1 = new IntValue(-2);
        iv2 = new IntValue(-1);

        mult = new Multiplication(iv1, iv2);

        Assert.assertEquals(mult.accept(v), 2);
    }

    @Test
    public void visitDivisionTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(2);
        IntValue iv2 = new IntValue(1);

        Division div = new Division(iv1, iv2);

        Assert.assertEquals(div.accept(v), 2);

        iv1 = new IntValue(1);
        iv2 = new IntValue(2);

        div = new Division(iv1, iv2);

        Assert.assertEquals(div.accept(v), 0);

        iv1 = new IntValue(-2);
        iv2 = new IntValue(1);

        div = new Division(iv1, iv2);

        Assert.assertEquals(div.accept(v), -2);
    }

    @Test
    public void visitModuloTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(4);
        IntValue iv2 = new IntValue(2);

        Modulo m = new Modulo(iv1, iv2);

        Assert.assertEquals(m.accept(v), 0);

        iv1 = new IntValue(62);
        iv2 = new IntValue(13);

        m = new Modulo(iv1, iv2);

        Assert.assertEquals(m.accept(v), 10);
    }

    @Test
    public void visitPowerTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(2);
        IntValue iv2 = new IntValue(1);

        Power p = new Power(iv1, iv2);

        Assert.assertEquals(p.accept(v), 2);

        iv1 = new IntValue(7);
        iv2 = new IntValue(6);

        p = new Power(iv1, iv2);

        Assert.assertEquals(p.accept(v), 117649);
    }

    @Test
    public void visitNotOperatorTest() throws Exception {
        Visitor v = new CodeVisitor();

        BoolValue bv1 = new BoolValue(true);

        NotOperator no = new NotOperator(bv1);

        Assert.assertFalse((Boolean) no.accept(v));

        bv1 = new BoolValue(false);

        no = new NotOperator(bv1);

        Assert.assertTrue((Boolean) no.accept(v));
    }

    @Test
    public void visitAndOperatorTest() throws Exception {
        Visitor v = new CodeVisitor();

        BoolValue bv1 = new BoolValue(true);
        BoolValue bv2 = new BoolValue(true);

        AndOperator ao = new AndOperator(bv1, bv2);

        Assert.assertTrue((Boolean) ao.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(true);

        ao = new AndOperator(bv1, bv2);

        Assert.assertFalse((Boolean) ao.accept(v));

        bv1 = new BoolValue(true);
        bv2 = new BoolValue(false);

        ao = new AndOperator(bv1, bv2);

        Assert.assertFalse((Boolean) ao.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(false);

        ao = new AndOperator(bv1, bv2);

        Assert.assertFalse((Boolean) ao.accept(v));
    }

    @Test
    public void visitOrOperatorTest() throws Exception {
        Visitor v = new CodeVisitor();

        BoolValue bv1 = new BoolValue(true);
        BoolValue bv2 = new BoolValue(true);

        OrOperator oo = new OrOperator(bv1, bv2);

        Assert.assertTrue((Boolean) oo.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(true);

        oo = new OrOperator(bv1, bv2);

        Assert.assertTrue((Boolean) oo.accept(v));

        bv1 = new BoolValue(true);
        bv2 = new BoolValue(false);

        oo = new OrOperator(bv1, bv2);

        Assert.assertTrue((Boolean) oo.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(false);

        oo = new OrOperator(bv1, bv2);

        Assert.assertFalse((Boolean) oo.accept(v));
    }

    @Test
    public void visitEqualsTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(2);
        IntValue iv2 = new IntValue(3);

        Equals eo = new Equals(iv1, iv2);

        Assert.assertFalse((Boolean) eo.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(3);

        eo = new Equals(iv1, iv2);

        Assert.assertTrue((Boolean) eo.accept(v));

        MessValue mv1 = new MessValue("abc");
        MessValue mv2 = new MessValue("abd");

        eo = new Equals(mv1, mv2);

        Assert.assertFalse((Boolean) eo.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abc");

        eo = new Equals(mv1, mv2);

        Assert.assertTrue((Boolean) eo.accept(v));

        BoolValue bv1 = new BoolValue(true);
        BoolValue bv2 = new BoolValue(true);

        eo = new Equals(bv1, bv2);

        Assert.assertTrue((Boolean) eo.accept(v));

        bv1 = new BoolValue(true);
        bv2 = new BoolValue(false);

        eo = new Equals(bv1, bv2);

        Assert.assertFalse((Boolean) eo.accept(v));

        eo = new Equals(bv1, iv1);

        Assert.assertFalse((Boolean) eo.accept(v));

        eo = new Equals(bv1, mv1);

        Assert.assertFalse((Boolean) eo.accept(v));

        eo = new Equals(iv1, mv1);

        Assert.assertFalse((Boolean) eo.accept(v));

        iv1 = new IntValue(1);
        mv1 = new MessValue("1");

        eo = new Equals(bv1, iv1);

        Assert.assertFalse((Boolean) eo.accept(v));

        eo = new Equals(bv1, mv1);

        Assert.assertFalse((Boolean) eo.accept(v));

        eo = new Equals(iv1, mv1);

        Assert.assertFalse((Boolean) eo.accept(v));
    }

    @Test
    public void visitGreaterEqualTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(3);
        IntValue iv2 = new IntValue(5);

        GreaterEqual ge = new GreaterEqual(iv1, iv2);

        Assert.assertFalse((Boolean) ge.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(3);

        ge = new GreaterEqual(iv1, iv2);

        Assert.assertTrue((Boolean) ge.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(1);

        ge = new GreaterEqual(iv1, iv2);

        Assert.assertTrue((Boolean) ge.accept(v));

        MessValue mv1 = new MessValue("abc");
        MessValue mv2 = new MessValue("abd");

        ge = new GreaterEqual(mv1, mv2);

        Assert.assertFalse((Boolean) ge.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abc");

        ge = new GreaterEqual(mv1, mv2);

        Assert.assertTrue((Boolean) ge.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abb");

        ge = new GreaterEqual(mv1, mv2);

        Assert.assertTrue((Boolean) ge.accept(v));

        BoolValue bv1 = new BoolValue(true);
        BoolValue bv2 = new BoolValue(false);

        ge = new GreaterEqual(bv1, bv2);

        Assert.assertTrue((Boolean) ge.accept(v));

        bv1 = new BoolValue(true);
        bv2 = new BoolValue(true);

        ge = new GreaterEqual(bv1, bv2);

        Assert.assertTrue((Boolean) ge.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(true);

        ge = new GreaterEqual(bv1, bv2);

        Assert.assertFalse((Boolean) ge.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(false);

        ge = new GreaterEqual(bv1, bv2);

        Assert.assertTrue((Boolean) ge.accept(v));
    }

    @Test
    public void visitGreaterThanTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(3);
        IntValue iv2 = new IntValue(5);

        GreaterThan gt = new GreaterThan(iv1, iv2);

        Assert.assertFalse((Boolean) gt.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(3);

        gt = new GreaterThan(iv1, iv2);

        Assert.assertFalse((Boolean) gt.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(1);

        gt = new GreaterThan(iv1, iv2);

        Assert.assertTrue((Boolean) gt.accept(v));

        MessValue mv1 = new MessValue("abc");
        MessValue mv2 = new MessValue("abd");

        gt = new GreaterThan(mv1, mv2);

        Assert.assertFalse((Boolean) gt.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abc");

        gt = new GreaterThan(mv1, mv2);

        Assert.assertFalse((Boolean) gt.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abb");

        gt = new GreaterThan(mv1, mv2);

        Assert.assertTrue((Boolean) gt.accept(v));

        BoolValue bv1 = new BoolValue(true);
        BoolValue bv2 = new BoolValue(false);

        gt = new GreaterThan(bv1, bv2);

        Assert.assertTrue((Boolean) gt.accept(v));

        bv1 = new BoolValue(true);
        bv2 = new BoolValue(true);

        gt = new GreaterThan(bv1, bv2);

        Assert.assertFalse((Boolean) gt.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(true);

        gt = new GreaterThan(bv1, bv2);

        Assert.assertFalse((Boolean) gt.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(false);

        gt = new GreaterThan(bv1, bv2);

        Assert.assertFalse((Boolean) gt.accept(v));
    }

    @Test
    public void visitLowerEqualTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(3);
        IntValue iv2 = new IntValue(5);

        LowerEqual le = new LowerEqual(iv1, iv2);

        Assert.assertTrue((Boolean) le.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(3);

        le = new LowerEqual(iv1, iv2);

        Assert.assertTrue((Boolean) le.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(1);

        le = new LowerEqual(iv1, iv2);

        Assert.assertFalse((Boolean) le.accept(v));

        MessValue mv1 = new MessValue("abc");
        MessValue mv2 = new MessValue("abd");

        le = new LowerEqual(mv1, mv2);

        Assert.assertTrue((Boolean) le.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abc");

        le = new LowerEqual(mv1, mv2);

        Assert.assertTrue((Boolean) le.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abb");

        le = new LowerEqual(mv1, mv2);

        Assert.assertFalse((Boolean) le.accept(v));

        BoolValue bv1 = new BoolValue(true);
        BoolValue bv2 = new BoolValue(false);

        le = new LowerEqual(bv1, bv2);

        Assert.assertFalse((Boolean) le.accept(v));

        bv1 = new BoolValue(true);
        bv2 = new BoolValue(true);

        le = new LowerEqual(bv1, bv2);

        Assert.assertTrue((Boolean) le.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(true);

        le = new LowerEqual(bv1, bv2);

        Assert.assertTrue((Boolean) le.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(false);

        le = new LowerEqual(bv1, bv2);

        Assert.assertTrue((Boolean) le.accept(v));
    }

    @Test
    public void visitLowerThanTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(3);
        IntValue iv2 = new IntValue(5);

        LowerThan lt = new LowerThan(iv1, iv2);

        Assert.assertTrue((Boolean) lt.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(3);

        lt = new LowerThan(iv1, iv2);

        Assert.assertFalse((Boolean) lt.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(1);

        lt = new LowerThan(iv1, iv2);

        Assert.assertFalse((Boolean) lt.accept(v));

        MessValue mv1 = new MessValue("abc");
        MessValue mv2 = new MessValue("abd");

        lt = new LowerThan(mv1, mv2);

        Assert.assertTrue((Boolean) lt.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abc");

        lt = new LowerThan(mv1, mv2);

        Assert.assertFalse((Boolean) lt.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abb");

        lt = new LowerThan(mv1, mv2);

        Assert.assertFalse((Boolean) lt.accept(v));

        BoolValue bv1 = new BoolValue(true);
        BoolValue bv2 = new BoolValue(false);

        lt = new LowerThan(bv1, bv2);

        Assert.assertFalse((Boolean) lt.accept(v));

        bv1 = new BoolValue(true);
        bv2 = new BoolValue(true);

        lt = new LowerThan(bv1, bv2);

        Assert.assertFalse((Boolean) lt.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(true);

        lt = new LowerThan(bv1, bv2);

        Assert.assertTrue((Boolean) lt.accept(v));

        bv1 = new BoolValue(false);
        bv2 = new BoolValue(false);

        lt = new LowerThan(bv1, bv2);

        Assert.assertFalse((Boolean) lt.accept(v));
    }

    @Test
    public void visitNotEqualsTest() throws Exception {
        Visitor v = new CodeVisitor();

        IntValue iv1 = new IntValue(2);
        IntValue iv2 = new IntValue(3);

        NotEquals no = new NotEquals(iv1, iv2);

        Assert.assertTrue((Boolean) no.accept(v));

        iv1 = new IntValue(3);
        iv2 = new IntValue(3);

        no = new NotEquals(iv1, iv2);

        Assert.assertFalse((Boolean) no.accept(v));

        MessValue mv1 = new MessValue("abc");
        MessValue mv2 = new MessValue("abd");

        no = new NotEquals(mv1, mv2);

        Assert.assertTrue((Boolean) no.accept(v));

        mv1 = new MessValue("abc");
        mv2 = new MessValue("abc");

        no = new NotEquals(mv1, mv2);

        Assert.assertFalse((Boolean) no.accept(v));

        BoolValue bv1 = new BoolValue(true);
        BoolValue bv2 = new BoolValue(true);

        no = new NotEquals(bv1, bv2);

        Assert.assertFalse((Boolean) no.accept(v));

        bv1 = new BoolValue(true);
        bv2 = new BoolValue(false);

        no = new NotEquals(bv1, bv2);

        Assert.assertTrue((Boolean) no.accept(v));

        no = new NotEquals(bv1, iv1);

        Assert.assertTrue((Boolean) no.accept(v));

        no = new NotEquals(bv1, mv1);

        Assert.assertTrue((Boolean) no.accept(v));

        no = new NotEquals(iv1, mv1);

        Assert.assertTrue((Boolean) no.accept(v));

        iv1 = new IntValue(1);
        mv1 = new MessValue("1");

        no = new NotEquals(bv1, iv1);

        Assert.assertTrue((Boolean) no.accept(v));

        no = new NotEquals(bv1, mv1);

        Assert.assertTrue((Boolean) no.accept(v));

        no = new NotEquals(iv1, mv1);

        Assert.assertTrue((Boolean) no.accept(v));
    }

    @Test
    public void visitVariableDeclarationTest() throws Exception {
        CodeVisitor cv = new CodeVisitor();

        VariableDeclaration vd1 = new VariableDeclaration(TokenTypes.BOOL, "a");
        VariableDeclaration vd2 = new VariableDeclaration(TokenTypes.NUM, "b");
        VariableDeclaration vd3 = new VariableDeclaration(TokenTypes.MESS, "c");

        vd1.accept(cv);
        vd2.accept(cv);
        vd3.accept(cv);

        Assert.assertTrue(cv.getCurr().inScope("a"));
        Assert.assertTrue(cv.getCurr().inScope("b"));
        Assert.assertTrue(cv.getCurr().inScope("c"));
    }

    @Test
    public void visitWhileStatementTest() throws Exception {

        CodeVisitor cv = new CodeVisitor();

        String s1 = "num iter = 0;";
        String s2 = "num a = 4;";
        String s3 = "while( a > 0 ) { iter = iter + 1; a = a - 1; } ";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        Vector<Statement> st = p.parseAssignmentOrVarDeclarationT();
        st.elementAt(0).accept(cv);
        st.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        st = p.parseAssignmentOrVarDeclarationT();
        st.elementAt(0).accept(cv);
        st.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Statement stt = p.parseWhileT();
        stt.accept(cv);

        Assert.assertEquals(cv.getCurr().getValue("iter"), 4);
        Assert.assertEquals(cv.getCurr().getValue("a"), 0);
    }

    @Test
    public void visitForStatementTest() throws Exception {
        CodeVisitor cv = new CodeVisitor();

        String s1 = "num a = 0;";
        String s2 = "for(num i = 5 | i < 15 | i*2){ a = a + 1;}";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        Vector<Statement> st = p.parseAssignmentOrVarDeclarationT();
        st.elementAt(0).accept(cv);
        st.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Statement stt = p.parseForT();
        stt.accept(cv);

        Assert.assertEquals(cv.getCurr().getValue("a"), 2);

        s1 = "num a = 0;";
        s2 = "num i = 0;";
        String s3 = "for(i = 5 | i < 15 | i*2){ a = a + 1;}";

        cv = new CodeVisitor();

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        st = p.parseAssignmentOrVarDeclarationT();
        st.elementAt(0).accept(cv);
        st.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        st = p.parseAssignmentOrVarDeclarationT();
        st.elementAt(0).accept(cv);
        st.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        stt = p.parseForT();
        stt.accept(cv);

        Assert.assertEquals(cv.getCurr().getValue("a"), 2);
        Assert.assertEquals(cv.getCurr().getValue("i"), 20);
    }

    @Test
    public void visitIfStatementTest() throws Exception {
        CodeVisitor cv = new CodeVisitor();

        String s1 = "num a = 1;";
        String s2 = "if(a == 1) { a = a*2; }";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        Vector<Statement> st = p.parseAssignmentOrVarDeclarationT();
        st.elementAt(0).accept(cv);
        st.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Statement stt = p.parseIfT();
        stt.accept(cv);

        Assert.assertEquals(cv.getCurr().getValue("a"), 2);

        s1 = "num a = 1;";
        s2 = "if(a != 1) { a = a*2; } else { a = 10; }";                                // wejdzie w else

        cv = new CodeVisitor();

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        st = p.parseAssignmentOrVarDeclarationT();
        st.elementAt(0).accept(cv);
        st.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        stt = p.parseIfT();
        stt.accept(cv);

        Assert.assertEquals(cv.getCurr().getValue("a"), 10);

        s1 = "num a = 8;";
        s2 = "if(a < 1){ a = a*2; } elseif( a > 7 ){ a = a % 3; } else{ a = 10; }";     // wejdzie w elseif

        cv = new CodeVisitor();

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        st = p.parseAssignmentOrVarDeclarationT();
        st.elementAt(0).accept(cv);
        st.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        stt = p.parseIfT();
        stt.accept(cv);

        Assert.assertEquals(cv.getCurr().getValue("a"), 2);
    }

    @Test
    public void visitReturnStatementTest() throws Exception {

        CodeVisitor cv = new CodeVisitor();

        String s1 = " return 2;";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        Statement st = p.parseReturnT();

        Assert.assertEquals(st.accept(cv), 2);

        s1 = " num a = 4;";
        String s2 = "return a;";

        cv = new CodeVisitor();

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Vector<Statement> vst = p.parseAssignmentOrVarDeclarationT();
        vst.elementAt(0).accept(cv);
        vst.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        st = p.parseReturnT();

        Assert.assertEquals(st.accept(cv), 4);

        s1 = " num a = 4;";
        s2 = "return a*3;";

        cv = new CodeVisitor();

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        vst = p.parseAssignmentOrVarDeclarationT();
        vst.elementAt(0).accept(cv);
        vst.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        st = p.parseReturnT();

        Assert.assertEquals(st.accept(cv), 12);
    }

    @Test
    public void visitFunctionCallStatementTest() throws Exception {

        CodeVisitor cv = new CodeVisitor();

        String s1 = "fun(num a, num b) { return a + b; }";
        String s2 = "fun(1, 2)";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        p.parse();

        cv.setFunctions(p.getFunctions());

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Expression e = p.parseFunctionCallT();

        FunctionCallStatement fcs = (FunctionCallStatement) e;

        Assert.assertEquals(fcs.accept(cv), 3);

        s1 = "fun(num a) { if(a>1){ return fun(a-2) + fun(a-1); }elseif(a < 0){ return 0; } return 1; }";
        s2 = "fun(4)";

        cv = new CodeVisitor();

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();

        cv.setFunctions(p.getFunctions());

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFunctionCallT();

        fcs = (FunctionCallStatement) e;

        Assert.assertEquals(fcs.accept(cv), 5);
    }

    @Test
    public void visitVariableClassDeclarationTest() throws Exception {

        CodeVisitor cv = new CodeVisitor();

        String s1 = "class MyClass{ num a; bool b; mess c; }";
        String s2 = "MyClass myObj;";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        p.parse();

        cv.setClasses(p.getClasses());

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Vector<Statement> v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);

        Assert.assertTrue(cv.getCurr().inScope("myObj"));

        Object hm = cv.getCurr().getValue("myObj");

        Assert.assertTrue(hm instanceof HashMap);
        Assert.assertTrue(((HashMap<?, ?>) hm).containsKey("a"));
        Assert.assertTrue(((HashMap<?, ?>) hm).containsKey("b"));
        Assert.assertTrue(((HashMap<?, ?>) hm).containsKey("c"));

        Object o = ((HashMap<?, ?>) hm).get("a");

        Assert.assertTrue(o instanceof Variable);
        Assert.assertEquals(((Variable) o).getVt(), VariableType.INT);

        o = ((HashMap<?, ?>) hm).get("b");

        Assert.assertTrue(o instanceof Variable);
        Assert.assertEquals(((Variable) o).getVt(), VariableType.BOOL);

        o = ((HashMap<?, ?>) hm).get("c");

        Assert.assertTrue(o instanceof Variable);
        Assert.assertEquals(((Variable) o).getVt(), VariableType.STRING);
    }

    @Test
    public void constructorTest() throws Exception {

        CodeVisitor cv = new CodeVisitor();

        String s1 = "class MyClass{ num a; MyClass( num b ){ a = b } }";
        String s2 = "MyClass myObj = MyClass(2);";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        p.parse();

        Assert.assertTrue(p.getClasses().containsKey("MyClass"));

        cv.setClasses(p.getClasses());

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Vector<Statement> v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        Assert.assertTrue(cv.getCurr().inScope("myObj"));

        Variable var = cv.getCurr().getVariable("myObj");

        Assert.assertNotNull(var);
        Assert.assertTrue(var instanceof ObjectVariable);

        ObjectVariable ovar = (ObjectVariable) var;

        Assert.assertEquals(ovar.getClassName(), "MyClass");
        Assert.assertNotNull(ovar.getValue());
        Assert.assertTrue(ovar.getValue() instanceof HashMap);
        Assert.assertTrue(((HashMap<?, ?>) ovar.getValue()).containsKey("a"));
        Assert.assertTrue(((HashMap<?, ?>) ovar.getValue()).get("a") instanceof Variable);

        Object o = ((HashMap<?, ?>) ovar.getValue()).get("a");
        var = (Variable) o;

        Assert.assertNotNull(var);
        Assert.assertEquals(var.getValue(), 2);

        cv = new CodeVisitor();

        s1 = "class MyClass{ num a; MyClass( num b ){ a = b } }";
        s2 = "MyClass myObj = MyClass(2+3);";

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();

        cv.setClasses(p.getClasses());

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        var = cv.getCurr().getVariable("myObj");
        ovar = (ObjectVariable) var;
        o = ((HashMap<?, ?>) ovar.getValue()).get("a");
        var = (Variable) o;

        Assert.assertEquals(var.getValue(), 5);
    }

    @Test
    public void visitFloorOperatorTest() throws Exception {
        CodeVisitor cv = new CodeVisitor();

        String s1 = "class MyClass{ num a; bool b; mess c; MyClass(num ap, bool bp, mess cp){ a = ap; b = bp; c = cp}}";
        String s2 = "MyClass a = MyClass(1, true, \"abc\");";
        String s3 = "a_b;";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        p.parse();

        HashMap<String, ClassStatement> classes = p.getClasses();

        cv.setClasses(classes);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Vector<Statement> v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Expression e = p.parseFloorOperatorT();

        Assert.assertTrue((Boolean) e.accept(cv));

        cv = new CodeVisitor();

        s2 = "MyClass a = MyClass(1, true, \"abc\");";
        s3 = "a_a;";

        cv.setClasses(classes);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Object o = e.accept(cv);

        Assert.assertNotNull(o);
        Assert.assertTrue(o instanceof Integer);
        Assert.assertEquals(o, 1);

        cv = new CodeVisitor();

        s2 = "MyClass a = MyClass(1, true, \"abc\");";
        s3 = "a_c;";

        cv.setClasses(classes);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        o = e.accept(cv);

        Assert.assertNotNull(o);
        Assert.assertTrue(o instanceof String);
        Assert.assertEquals(o, "abc");

        cv = new CodeVisitor();

        s1 = "class MyClass{ num a; MyClass(num ap){ a = ap; } fun(){ a = a+1; return a*2; } }";
        s2 = "MyClass a = MyClass(1);";
        s3 = "a_fun();";
        String s4 = "a_a;";

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();

        classes = p.getClasses();

        cv.setClasses(classes);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertEquals(e.accept(cv), 4);

        br = new BufferedReader(new StringReader(s4));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertEquals(e.accept(cv), 2);

        cv = new CodeVisitor();

        s1 = "class MyClass{ num a; MyClass(num ap){ a = ap; } fun(){ a = a+1; return a*2; } }";
        s2 = "objFun(MyClass obj){ return obj;}";
        s3 = "MyClass a = MyClass(1);";
        s4 = "objFun(a);";
        String s5 = "objFun(a)_fun();";
        String s6 = "objFun(a)_a;";

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();

        classes = p.getClasses();

        cv.setClasses(classes);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();

        HashMap<String, FunctionStatement> functions = p.getFunctions();

        cv.setFunctions(functions);

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s4));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFunctionCallT();

        FunctionCallStatement fsc = (FunctionCallStatement) e;

        Assert.assertTrue(fsc.accept(cv) instanceof ObjectVariable);

        br = new BufferedReader(new StringReader(s5));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertEquals(e.accept(cv), 4);

        br = new BufferedReader(new StringReader(s6));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        Assert.assertEquals(e.accept(cv), 2);

        cv = new CodeVisitor();
        classes = new HashMap<>();

        s1 = "class MyClass{ num a; MyClass(num ap){ a = ap; } fun(){ a = a+1; return a*2; } }";
        s2 = "class OtherClass{ MyClass obj; OtherClass(MyClass myobj){ obj = myobj; } getObj(){ return obj; }}";
        s3 = "MyClass a = MyClass(1);";
        s4 = "OtherClass b = OtherClass(a);";
        s5 = "b_getObj();";
        s6 = "b_getObj()_a;";

        br = new BufferedReader(new StringReader(s1));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();

        classes.put("MyClass", p.getClasses().get("MyClass"));

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();
        classes.put("OtherClass", p.getClasses().get("OtherClass"));

        cv.setClasses(classes);

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s4));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s5));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        var result = e.accept(cv);

        Assert.assertTrue(result instanceof ObjectVariable);

        ObjectVariable ov = (ObjectVariable) result;

        Assert.assertEquals(ov.getClassName(), "MyClass");

        br = new BufferedReader(new StringReader(s6));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();

        result = e.accept(cv);

        Assert.assertEquals(result, 1);

    }

    @Test
    public void myTest() throws Exception {
        CodeVisitor cv = new CodeVisitor();

        String s1 = "class MyClass{ num a; bool b; mess c; MyClass(num ap, bool bp, mess cp){ a = ap; b = bp; c = cp}}";
        String s2 = "fun(){ MyClass obj = MyClass(1, true, \"abc\"); return obj;}";
        String s3 = "fun();";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        p.parse();

        HashMap<String, ClassStatement> classes = p.getClasses();

        cv.setClasses(classes);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();

        cv.setFunctions(p.getFunctions());

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Expression e = p.parseFunctionCallT();

        Object o = e.accept(cv);

        Assert.assertTrue(o instanceof ObjectVariable);
    }

    @Test
    public void myTest2() throws Exception {

        CodeVisitor cv = new CodeVisitor();
        HashMap<String, FunctionStatement> functions = new HashMap<>();
        HashMap<String, ClassStatement> classes = new HashMap<>();


        String s1 = "start(){ return calc(8,5,2,1); }";
        String s2 = "class NumberCalc{ num a; num b; NumberCalc(num x, num y){ a = x; b = y; } add(num e){ return a +b+e;}}";
        String s3 = "calc(num w, num q, num r, num t){ NumberCalc numb = NumberCalc(w, q); return numb_add(r); }";
        String s4 = "start();";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        p.parse();
        functions.put("start", p.getFunctions().get("start"));

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();

        classes.put("NumberCalc", p.getClasses().get("NumberCalc"));

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        p.parse();
        functions.put("calc", p.getFunctions().get("calc"));

        cv.setClasses(classes);
        cv.setFunctions(functions);

        br = new BufferedReader(new StringReader(s4));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Expression e = p.parseFunctionCallT();

        Assert.assertEquals(e.accept(cv), 15);
    }

    @Test
    public void myTest3() throws Exception {

        CodeVisitor cv = new CodeVisitor();

        String s1 = "class MyClass{ num a; MyClass(num b){ a = b; } addOne(){ a = a + 1; }}";
        String s2 = "MyClass obj = MyClass(5);";
        String s3 = "MyClass copy;";
        String s4 = "copy = obj";
        String s5 = "obj_addOne();";
        String s6 = "obj_a;";
        String s7 = "copy_a;";

        BufferedReader br = new BufferedReader(new StringReader(s1));
        Reader r = new Reader(br, 10);
        Lexer l = new Lexer(r, 14, 3);
        Parser p = new Parser(l);

        p.parse();

        HashMap<String, ClassStatement> classes = p.getClasses();

        cv.setClasses(classes);

        br = new BufferedReader(new StringReader(s2));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Vector<Statement> v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);
        v.elementAt(1).accept(cv);

        br = new BufferedReader(new StringReader(s3));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);

        br = new BufferedReader(new StringReader(s4));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        v = p.parseAssignmentOrVarDeclarationT();

        v.elementAt(0).accept(cv);

        br = new BufferedReader(new StringReader(s5));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        Expression e = p.parseFloorOperatorT();

        e.accept(cv);

        br = new BufferedReader(new StringReader(s6));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();
        Assert.assertEquals(e.accept(cv), 6);

        br = new BufferedReader(new StringReader(s7));
        r = new Reader(br, 10);
        l = new Lexer(r, 14, 3);
        p = new Parser(l);

        e = p.parseFloorOperatorT();
        Assert.assertEquals(e.accept(cv), 6);
    }
}