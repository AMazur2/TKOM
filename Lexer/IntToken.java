package Lexer;

import Reader.Position;
import org.jetbrains.annotations.NotNull;

public class IntToken extends Token {

    private int value;

    public IntToken(@NotNull Position pos, int value)
    {
        super(TokenTypes.INT, pos, null);
        this.value = value;
    }

    public IntToken(@NotNull Position pos, String c, int value)
    {
        super(TokenTypes.INT, pos, c);
        this.value = value;
    }

    public int getValue() { return this.value; }

    @Override
    public boolean isEqual(Object o)
    {
        if(this == o)
            return true;

        if(o == null || getClass() != o.getClass())
            return false;

        IntToken t = (IntToken) o;

        return this.name == t.getName() && this.pos.isEqual(t.getPos()) && this.value == t.getValue();
    }

    @Override
    public void showDetails()
    {
        System.out.println("Name: " + this.name +  " | Position: (" + this.pos.getLine() + " ; " + this.pos.getColumn()
                + ") | Value: " + this.value );
    }
}
