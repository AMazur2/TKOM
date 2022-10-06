package uni.projects.visitor;

import uni.projects.parser.statements.*;
import uni.projects.parser.values.*;

public interface Visitor {

    Object visit(BoolValue boolValue);

    Object visit(IntValue intValue);

    Object visit(MessValue messValue);

    Object visit(Value value) throws Exception;

    Object visit(MinusOperator minusOperator) throws Exception;

    Object visit(Sum sum) throws Exception;

    Object visit(Subtraction subtraction) throws Exception;

    Object visit(Multiplication multiplication) throws Exception;

    Object visit(Division division) throws Exception;

    Object visit(Modulo modulo) throws Exception;

    Object visit(Power power) throws Exception;

    Object visit(NotOperator notOperator) throws Exception;

    Object visit(AndOperator andOperator) throws Exception;

    Object visit(OrOperator orOperator) throws Exception;

    Object visit(Equals equals) throws Exception;

    Object visit(GreaterEqual greaterEqual) throws Exception;

    Object visit(GreaterThan greaterThan) throws Exception;

    Object visit(LowerEqual lowerEqual) throws Exception;

    Object visit(LowerThan lowerThan) throws Exception;

    Object visit(NotEquals notEquals) throws Exception;

    Object visit(Equal equal) throws Exception;

    Object visit(VariableDeclaration variableDeclaration) throws Exception;

    Object visit(WhileStatement whileStatement) throws Exception;

    Object visit(ForStatement forStatement) throws Exception;

    Object visit(IfStatement ifStatement) throws Exception;

    Object visit(ReturnStatement returnStatement) throws Exception;

    Object visit(FunctionCallStatement functionCallStatement) throws Exception;

    Object visit(FunctionStatement functionStatement) throws Exception;

    Object visit(VariableClassDeclaration variableClassDeclaration) throws Exception;

    Object visit(FloorOperator floorOperator) throws Exception;

    Object visit(MethodCallStatement methodCallStatement) throws Exception;
}
