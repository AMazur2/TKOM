package Lexer;

import Reader.Position;
import org.jetbrains.annotations.NotNull;

public class MessToken extends Token {

    private String mess;

    public MessToken(@NotNull Position p, String s)
    {
        super(TokenTypes.STRING, p, null);
        this.mess = s;
    }

    public MessToken(@NotNull Position p, String c, String s)
    {
        super(TokenTypes.STRING, p, c);
        this.mess = s;
    }

    public String getMess() { return mess; }

    @Override
    public boolean isEqual(Object o)
    {
        if (this == o)
            return true;

        if (o == null || getClass() != o.getClass())
            return false;

        MessToken t = (MessToken) o;

        return this.name == t.getName() && this.pos.isEqual(t.getPos()) && this.mess.equals(t.getMess());
    }

    @Override
    public void showDetails()
    {
        System.out.println("Name: " + this.name +  " | Position: (" + this.pos.getLine() + " ; " + this.pos.getColumn()
                + ") | Mess: " + this.mess );
    }
}
