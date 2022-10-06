package uni.projects.lexer;

import uni.projects.reader.Position;
import org.jetbrains.annotations.NotNull;

public class Token {

    protected TokenTypes name;
    protected Position pos;
    protected String context;

    public Token(@NotNull TokenTypes t, @NotNull Position p)
    {
        this.name = t;
        this.pos = p;
        this.context = null;
    }

    public Token(@NotNull TokenTypes t, @NotNull Position p, String c)
    {
        this.name = t;
        this.pos = p;
        this.context = c;
    }

    public TokenTypes getName() { return this.name; }

    public Position getPos() { return this.pos; }

    public String getContext() { return this.context; }

    public void showDetails()
    {
        System.out.println("Name: " + this.name +  " | Position: (" + this.pos.getLine() + " ; " + this.pos.getColumn() + ")" );
    }

    public boolean isEqual(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Token t = (Token) obj;

        return this.name == t.getName() && this.pos.isEqual(t.getPos()) ;
    }
}
