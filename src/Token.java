import org.jetbrains.annotations.NotNull;

public class Token {

    private Tokens name;
    private Position pos;
    private String info;

    public Token(@NotNull Tokens t, @NotNull Position p, String s)
    {
        this.name = t;
        this.pos = p;
        this.info = s;
    }

    public Tokens getName() { return this.name; }

    public Position getPos() { return this.pos; }

    public String getInfo() { return this.info; }

    public boolean isEqual(Object obj)
    {
        if(this == obj)
            return true;

        if(obj == null || getClass() != obj.getClass())
            return false;

        Token t = (Token) obj;

        if( this.info != null )
            return this.name == t.getName() && this.pos.isEqual(t.getPos()) && this.info.equals(t.getInfo()) ;
        else if( t.getInfo() == null )
            return this.name == t.getName() && this.pos.isEqual(t.getPos()) ;
        else
            return false;
    }
}
