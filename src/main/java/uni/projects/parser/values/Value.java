package uni.projects.parser.values;

import uni.projects.parser.Expression;
import uni.projects.visitor.Visitable;
import uni.projects.visitor.Visitor;

public class Value implements Expression, Visitable {
    private String name;

    public Value( String n )
    {
        super();
        this.name = n;
    }

    public String getName() { return this.name; }

    @Override
    public Object accept(Visitor visitor) throws Exception {
        return visitor.visit(this);
    }
}
