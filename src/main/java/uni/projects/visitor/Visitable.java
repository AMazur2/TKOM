package uni.projects.visitor;

public interface Visitable {

    Object accept(Visitor visitor) throws Exception;
}
