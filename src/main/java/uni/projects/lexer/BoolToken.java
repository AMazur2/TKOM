package uni.projects.lexer;

import uni.projects.reader.Position;
import org.jetbrains.annotations.NotNull;

public class BoolToken extends Token {

    private boolean value;

    public BoolToken(@NotNull Position p, boolean b)
    {
        super(TokenTypes.BOOLEAN, p, null);
        this.value = b;
    }

    public BoolToken(@NotNull Position p, String c, boolean b)
    {
        super(TokenTypes.BOOLEAN, p, c);
        this.value = b;
    }

    public boolean getValue() { return this.value; }

    @Override
    public boolean isEqual(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        BoolToken t = (BoolToken) o;

        return this.name == t.getName() && this.pos.isEqual(t.getPos()) && this.value == t.getValue();
    }

    @Override
    public void showDetails()
    {
        System.out.println("Name: " + this.name +  " | Position: (" + this.pos.getLine() + " ; " + this.pos.getColumn()
                + ") | Value: " + this.value );
    }
}
