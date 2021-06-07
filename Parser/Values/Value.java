package Parser.Values;

import Parser.Expression;
import Visitor.Visitable;
import Visitor.Visitor;

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
