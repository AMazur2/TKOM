package Visitor;

public interface Visitable {

    Object accept(Visitor visitor) throws Exception;
}
