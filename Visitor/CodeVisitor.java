package Visitor;

import Lexer.TokenTypes;
import Parser.*;
import Parser.Statements.*;
import Parser.Values.*;

import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.CancellationException;

public class CodeVisitor implements Visitor {

    private HashMap<String, FunctionStatement> functions;
    private HashMap<String, ClassStatement> classes;
    Stack<Scope> vsstack;
    Scope curr;

    public CodeVisitor(){
        this.vsstack = new Stack<>();
        this.curr = new Scope();
        this.functions = new HashMap<>();
        this.classes = new HashMap<>();
    }

    public CodeVisitor(HashMap<String, FunctionStatement> functions, HashMap<String, ClassStatement> classes) {
        this.functions = functions;
        this.classes = classes;
        this.vsstack = new Stack<>();
        this.curr = new Scope();
    }

    public void setFunctions(HashMap<String, FunctionStatement> functions) {
        this.functions = functions;
    }

    public void setClasses(HashMap<String, ClassStatement> classes) {
        this.classes = classes;
    }


    public Scope getCurr() {
        return curr;
    }

    public void setCurr(Scope curr) {
        this.curr = curr;
    }

    @Override
    public Object visit(BoolValue boolValue) {
        return boolValue.getValue();
    }

    @Override
    public Object visit(IntValue intValue) {
        return intValue.getValue();
    }

    @Override
    public Object visit(MessValue messValue) {
        return messValue.getValue();
    }

    @Override
    public Object visit(Value value) throws Exception {
        return curr.getValue(value.getName());
    }

    @Override
    public Object visit(MinusOperator minusOperator) throws Exception {
        Object o = minusOperator.getEx().accept(this);

        if(!(o instanceof Number))
            throw new CancellationException("Cannot evaluate '-' operation with instances: " + o.getClass());
        return (-1)*(Integer)o;
    }

    @Override
    public Object visit(Sum sum) throws Exception {

        Object o1 = sum.getLex().accept(this);
        Object o2 = sum.getRex().accept(this);

        if( (o1 instanceof Number) && (o2 instanceof Number))
            return (Integer)o1 + (Integer)o2;
        else if ( o1 instanceof String)
            return o1 + o2.toString();
        else
            throw new CancellationException("Cannot evaluate '+' operation with instances: " + o1.getClass() +
                    " and " + o2.getClass());
    }

    @Override
    public Object visit(Subtraction sub) throws Exception {

        Object o1 = sub.getLex().accept(this);
        Object o2 = sub.getRex().accept(this);

        if( (o1 instanceof Number) && (o2 instanceof Number))
            return (Integer)o1 - (Integer)o2;
        else
            throw new CancellationException("Cannot evaluate '+' operation with instances: " + o1.getClass() +
                    " and " + o2.getClass());
    }

    @Override
    public Object visit(Multiplication mul) throws Exception {
        Object o1 = mul.getLex().accept(this);
        Object o2 = mul.getRex().accept(this);

        if( (o1 instanceof Number) && (o2 instanceof Number))
            return (Integer)o1 * (Integer)o2;
        else
            throw new CancellationException("Cannot evaluate '*' operation with instances: " + o1.getClass() +
                    " and " + o2.getClass());
    }

    @Override
    public Object visit(Division div) throws Exception {

        Object o1 = div.getLex().accept(this);
        Object o2 = div.getRex().accept(this);

        if( (o1 instanceof Number) && (o2 instanceof Number))
            return (Integer)o1 / (Integer)o2;
        else
            throw new CancellationException("Cannot evaluate '*' operation with instances: " + o1.getClass() +
                    " and " + o2.getClass());
    }

    @Override
    public Object visit(Modulo modulo) throws Exception {
        Object o1 = modulo.getLex().accept(this);
        Object o2 = modulo.getRex().accept(this);

        if( (o1 instanceof Number) && (o2 instanceof Number))
            return (Integer)o1 % (Integer)o2;
        else
            throw new CancellationException("Cannot evaluate '%' operation with instances: " + o1.getClass() +
                    " and " + o2.getClass());
    }

    @Override
    public Object visit(Power power) throws Exception {
        Object o1 = power.getLex().accept(this);
        Object o2 = power.getRex().accept(this);

        if( (o1 instanceof Number) && (o2 instanceof Number))
            return (int) Math.pow((Integer)o1, (Integer)o2);
        else
            throw new CancellationException("Cannot evaluate '^' operation with instances: " + o1.getClass() +
                    " and " + o2.getClass());
    }

    @Override
    public Object visit(NotOperator notOperator) throws Exception {
        Object o = notOperator.getEx().accept(this);
        if(!(o instanceof Boolean))
            throw new CancellationException("Cannot evaluate 'not' operation with instances: " + o.getClass());
        return !((Boolean) o);
    }

    @Override
    public Object visit(AndOperator andOperator) throws Exception {
        Object o1 = andOperator.getLex().accept(this);
        Object o2 = andOperator.getRex().accept(this);

        if(!(o1 instanceof Boolean && o2 instanceof Boolean))
            throw new CancellationException("Cannot evaluate 'and' operation with instances: " + o1.getClass() +
                    " and " + o2.getClass());
        return (Boolean) o1 && (Boolean) o2;
    }

    @Override
    public Object visit(OrOperator orOperator) throws Exception {
        Object o1 = orOperator.getLex().accept(this);
        Object o2 = orOperator.getRex().accept(this);

        if(!(o1 instanceof Boolean && o2 instanceof Boolean))
            throw new CancellationException("Cannot evaluate 'or' operation with instances: " + o1.getClass() +
                    " and " + o2.getClass());
        return (Boolean) o1 || (Boolean) o2;
    }

    @Override
    public Object visit(Equals equals) throws Exception {

        Object o1 = equals.getLex().accept(this);
        Object o2 = equals.getRex().accept(this);

        return o1.equals(o2);
    }

    @Override
    public Object visit(GreaterEqual greaterEqual) throws Exception {

        Object o1 = greaterEqual.getLex().accept(this);
        Object o2 = greaterEqual.getRex().accept(this);

        if((o1 instanceof Number) && (o2 instanceof Number))
            return ((Integer) o1 >= (Integer) o2);
        else if((o1 instanceof Boolean) && (o2 instanceof Boolean))
            return (Boolean.compare((Boolean)o1, (Boolean)o2) >= 0);
        else if((o1 instanceof String) && (o2 instanceof String))
            return (((String) o1).compareTo((String)o2) >= 0);
        else
            throw new CancellationException("Cannot compare using '>=' elements of instances: " + o1.getClass() + " and " +
                    o2.getClass());
    }

    @Override
    public Object visit(GreaterThan greaterThan) throws Exception {

        Object o1 = greaterThan.getLex().accept(this);
        Object o2 = greaterThan.getRex().accept(this);

        if((o1 instanceof Number) && (o2 instanceof Number))
            return ((Integer) o1 > (Integer) o2);
        else if((o1 instanceof Boolean) && (o2 instanceof Boolean))
            return (Boolean.compare((Boolean)o1, (Boolean)o2) > 0);
        else if((o1 instanceof String) && (o2 instanceof String))
            return (((String) o1).compareTo((String)o2) > 0);
        else
            throw new CancellationException("Cannot compare using '>' elements of instances: " + o1.getClass() + " and " +
                    o2.getClass());
    }

    @Override
    public Object visit(LowerEqual lowerEqual) throws Exception {

        Object o1 = lowerEqual.getLex().accept(this);
        Object o2 = lowerEqual.getRex().accept(this);

        if((o1 instanceof Number) && (o2 instanceof Number))
            return ((Integer) o1 <= (Integer) o2);
        else if((o1 instanceof Boolean) && (o2 instanceof Boolean))
            return (Boolean.compare((Boolean)o1, (Boolean)o2) <= 0);
        else if((o1 instanceof String) && (o2 instanceof String))
            return (((String) o1).compareTo((String)o2) <= 0);
        else
            throw new CancellationException("Cannot compare using '<=' elements of instances: " + o1.getClass() + " and " +
                    o2.getClass());
    }

    @Override
    public Object visit(LowerThan lowerThan) throws Exception {

        Object o1 = lowerThan.getLex().accept(this);
        Object o2 = lowerThan.getRex().accept(this);

        if((o1 instanceof Number) && (o2 instanceof Number))
            return ((Integer) o1 < (Integer) o2);
        else if((o1 instanceof Boolean) && (o2 instanceof Boolean))
            return (Boolean.compare((Boolean)o1, (Boolean)o2) < 0);
        else if((o1 instanceof String) && (o2 instanceof String))
            return (((String) o1).compareTo((String)o2) < 0);
        else
            throw new CancellationException("Cannot compare using '<' elements of instances: " + o1.getClass() + " and " +
                    o2.getClass());
    }

    @Override
    public Object visit(NotEquals notEquals) throws Exception {
        Object o1 = notEquals.getLex().accept(this);
        Object o2 = notEquals.getRex().accept(this);

        return !(o1.equals(o2));
    }

    @Override
    public Object visit(Equal equal) throws Exception {

        Expression lex = equal.getLex();
        Expression rex = equal.getRex();

        if(lex instanceof Value) {
            Variable v = curr.getVariable(((Value) lex).getName());
            VariableType vt = v.getVt();
            var o = rex.accept(this);
            if (vt == VariableType.INT && o instanceof Integer)
                curr.setValue(((Value) lex).getName(), o);
            else if (vt == VariableType.BOOL && o instanceof Boolean)
                curr.setValue(((Value) lex).getName(), o);
            else if (vt == VariableType.STRING && o instanceof String)
                curr.setValue(((Value) lex).getName(), o);
            else if (vt == VariableType.CLASS && o instanceof HashMap) {
                curr.setValue(((Value) lex).getName(), o);
            } else
                throw new Exception("Wrong variable types! Trying to assign value of type: " + o.getClass() +
                        " to variable of type: " + vt);
        }

        return null;
    }

    @Override
    public Object visit(VariableDeclaration vd) throws Exception {
        TokenTypes type = vd.getType();
        VariableType vt;

        switch(type)
        {
            case NUM:
                vt = VariableType.INT;
                break;
            case BOOL:
                vt = VariableType.BOOL;
                break;
            case MESS:
                vt = VariableType.STRING;
                break;
            default:
                vt = null;
        }

        curr.putVariable(vd.getName(), new Variable(vt, null));
        return null;
    }

    @Override
    public Object visit(WhileStatement ws) throws Exception {
        Scope s = new Scope(curr);
        vsstack.push(curr);
        curr = s;
        while((Boolean)ws.getCondition().accept(this))
        {
            Object ret = runInstructions(ws.getInstructions());
            if(ret != null) {
                curr = vsstack.pop();
                return ret;
            }
        }
        curr = vsstack.pop();
        return null;
    }

    @Override
    public Object visit(ForStatement fs) throws Exception {
        Scope newS = new Scope(curr);
        vsstack.push(curr);
        curr = newS;

        runInstructions(fs.getInitialize());
        while((Boolean) fs.getCondition().accept(this))
        {
            Object ret = runInstructions(fs.getInstructions());
            if(ret!=null) {
                curr = vsstack.pop();
                return ret;
            }

            Expression e = ((OperatorExpression)fs.getPostInstr()).getLex();
            if(e instanceof Value)
            {
                String name = ((Value)e).getName();
                curr.setValue(name, fs.getPostInstr().accept(this));
            }
        }

        curr = vsstack.pop();
        return null;
    }

    @Override
    public Object visit(IfStatement is) throws Exception {
        Scope newS = new Scope(curr);
        vsstack.push(curr);
        curr = newS;

        int i = 0;

        while(i < is.getConditions().size())
        {
            if((Boolean) is.getConditions().elementAt(i).accept(this)) {
                Object result = runInstructions(is.getIfInstructions().elementAt(i));
                curr = vsstack.pop();
                return result;
            }
            i++;
        }
        if(is.getElseInstructions() != null) {
            Object result = runInstructions(is.getElseInstructions());
            curr = vsstack.pop();
            return result;
        }

        curr = vsstack.pop();
        return null;
    }

    @Override
    public Object visit(ReturnStatement rs) throws Exception {
        Expression e = rs.getExpression();
        if(e instanceof Value)
        {
            Value v = (Value) e;
            String name = v.getName();
            Object o = curr.getVariable(name);

            if(o instanceof ObjectVariable)
                return o;

        }
        return e.accept(this);
    }

    @Override
    public Object visit(FunctionCallStatement fcs) throws Exception {

        FunctionStatement fs = functions.get(fcs.getName());
        if(fs == null)
        {
            ClassStatement cs = classes.get(fcs.getName());
            if(cs == null)
                throw new Exception("Function named: " + fcs.getName() + " was not declared");

            return constructor(fcs, cs);
        }

        Scope newS = new Scope();

        if(fs.getArguments().size() != fcs.getArguments().size())
            throw new Exception("Invalid number of parameters! Given: " + fcs.getArguments().size() +
                                " Expected: " + fs.getArguments().size());
        int i = 0;
        Vector<Object> values = new Vector<>();

        for(int j = 0; j < fcs.getArguments().size(); ++j)
            values.add((fcs.getArguments().get(j)).accept(this));

        vsstack.push(curr);
        curr = newS;

        for(VariableDeclaration var : fs.getArguments())
        {
            var.accept(this);
            curr.setValue(var.getName(), values.elementAt(i));
            ++i;
        }

        Object result = runInstructions(fs.getInstructions());
        curr = vsstack.pop();
        return result;

    }

    @Override
    public Object visit(FunctionStatement fs) throws Exception {
        return null;
    }

    @Override
    public Object visit(VariableClassDeclaration vcd) throws Exception {
        String name = vcd.getName();
        String className = vcd.getClassName();

        ClassStatement cs = classes.get(className);

        if(cs == null)
            throw new Exception("Class: " + className + " was not defined!");

        Vector<VariableDeclaration> args = cs.getArguments();
        HashMap<String, Variable> classArguments = new HashMap<>();

        String vn;
        VariableType vt;
        TokenTypes type;

        for( VariableDeclaration vd : args)
        {
            vn = vd.getName();
            type = vd.getType();

            switch(type)
            {
                case NUM:
                    vt = VariableType.INT;
                    break;
                case BOOL:
                    vt = VariableType.BOOL;
                    break;
                case MESS:
                    vt = VariableType.STRING;
                    break;
                case REF:
                    vt = VariableType.CLASS;
                    break;
                default:
                    vt = null;
            }

            classArguments.put(vn, new Variable(vt, null));
        }

        curr.putVariable(name, new ObjectVariable(VariableType.CLASS, className, classArguments));
        return null;
    }

    @Override
    public Object visit(FloorOperator fo) throws Exception {

        Expression lex = fo.getLex();
        Expression rex = fo.getRex();
        Object o;
        ObjectVariable ov;

        if(lex instanceof FloorOperator) {
            o = lex.accept(this);

            if(o == null)
                throw new Exception("Problem in FloorOperator, expression is null!");

            if(!(o instanceof ObjectVariable))
                throw new Exception("Problem in FloorOperator, expected ObjectVariable, get: " + o.getClass());

            ov = (ObjectVariable) o;

        } else if(lex instanceof Value) {
            Value lv = (Value) lex;

            if (!(curr.getVariable(lv.getName()) instanceof ObjectVariable))
                throw new Exception("Expression should be an instance of some class");

            ov = (ObjectVariable) curr.getVariable(lv.getName());
        } else if(lex instanceof ObjectVariable)
            ov = (ObjectVariable) lex;
        else if(lex instanceof FunctionCallStatement)
        {
            Object result = lex.accept(this);

            if(result == null)
                throw new Exception("Missing return value or value uninitialized - result is null!");

            if(!(result instanceof ObjectVariable))
                throw new Exception("Result value needs to be an ObjectVariable for FloorOperator to work properly!");

            ov = (ObjectVariable) result;
        }
        else
            throw new Exception("Expression should be either Value or ObjectVariable or FunctionCall!");

        if( rex instanceof Value)
        {
            String name = ((Value) rex).getName();
            var result = ((HashMap<?, ?>) ov.getValue()).get(name);

            if(result == null)
                throw new Exception("Unknown variable - probably not defined in class!");

            Object value = ((Variable) result).getValue();

            if(value == null)
                throw new Exception("Uninitialized variable!");

            return value;
        }
        else if( rex instanceof MethodCallStatement )
        {

            MethodCallStatement mcs = (MethodCallStatement) rex;

            String className = ov.getClassName();

            ClassStatement cs = classes.get(className);

            if(cs == null)
                throw new Exception("Unknown class: " + className);

            String funName = mcs.getName();

            FunctionStatement fs = cs.getFunctions().get(funName);

            if(fs == null)
                throw new Exception("Undefined method: " + funName);

            Scope scope = new Scope();

            Vector<VariableDeclaration> classArgs = cs.getArguments();
            String name;
            Object var;

            for(VariableDeclaration vd : classArgs)
            {
                name = vd.getName();
                var = ((HashMap<?,?>) ov.getValue()).get(name);

                if(!(var instanceof Variable))
                    throw new Exception("Unknown problem in FloorOperator!");

                scope.putVariable(name, (Variable) var);
            }

            if(fs.getArguments().size() != mcs.getArguments().size())
                throw new Exception("Invalid number of parameters! Given: " + mcs.getArguments().size() +
                        " Expected: " + fs.getArguments().size());

            int i = 0;
            Vector<Object> values = new Vector<>();

            for(int j = 0; j < mcs.getArguments().size(); ++j)
                values.add((mcs.getArguments().get(j)).accept(this));

            vsstack.push(curr);
            curr = scope;

            Scope methodScope = new Scope(curr);
            vsstack.push(curr);
            curr = methodScope;

            for(VariableDeclaration varD : fs.getArguments())
            {
                varD.accept(this);
                curr.setValue(varD.getName(), values.elementAt(i));
                ++i;
            }

            var result = runInstructions(fs.getInstructions());

            Scope temp = vsstack.pop();
            curr = vsstack.pop();

            for(VariableDeclaration vd : classArgs)
            {
                name = vd.getName();

                ((HashMap<String,Variable>) ov.getValue()).put(name, temp.getVariable(name));
            }

            return result;
        }
        else
            throw new Exception("Expression should be either Value or MethodCall, is: " + rex.getClass());

    }

    @Override
    public Object visit(MethodCallStatement mcs) throws Exception {
        return null;
    }

    public Object runInstructions(Vector<Statement> inst) throws Exception {
        for(Statement in : inst)
        {
            if(in instanceof ReturnStatement)
                return  in.accept(this);
            Object result = in.accept(this);
            if(result != null)
                return result;
        }
        return null;
    }

    public HashMap<String, Variable> constructor(FunctionCallStatement fcs, ClassStatement cs) throws Exception {

        FunctionStatement fs = cs.getFunctions().get(fcs.getName());
        if(fs == null)
            throw new Exception("Undefined constructor!");

        Scope newS = new Scope();

        if(fs.getArguments().size() != fcs.getArguments().size())
            throw new Exception("Invalid number of parameters! Given: " + fcs.getArguments().size() +
                    " Expected: " + fs.getArguments().size());

        int i = 0;
        Vector<Object> values = new Vector<>();

        for(int j = 0; j < fcs.getArguments().size(); ++j)
            values.add((fcs.getArguments().get(j)).accept(this));

        vsstack.push(curr);
        curr = newS;

        for(VariableDeclaration var: cs.getArguments())
            var.accept(this);

        Scope s = new Scope(curr);

        vsstack.push(curr);
        curr = s;

        for(VariableDeclaration var : fs.getArguments())
        {
            var.accept(this);
            curr.setValue(var.getName(), values.elementAt(i));
            ++i;
        }

        runInstructions(fs.getInstructions());

        curr = vsstack.pop();

        HashMap<String, Variable> hm = curr.getVariables();

        curr = vsstack.pop();

        return hm;
    }
}