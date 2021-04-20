import org.jetbrains.annotations.NotNull;

public class Position {
    int line;
    int column;

    public Position()
    {
        this.column = 1;
        this.line = 1;
    }

    public Position(int column, int line)
    {
        this.line = line;
        this.column = column;
    }

    public Position(@NotNull Position pos)
    {
        this.column = pos.column;
        this.line = pos.line;
    }

    public int getLine() { return this.line; }

    public int getColumn() { return this.column; }

    public void setLine(int l) { this.line = l; }

    public void setColumn(int c) { this.column = c; }

    public void incrementColumn() { this.column++; }

    public void incrementLine() { this.line++; }

    public boolean isEqual( Object obj )
    {
        if( this == obj )
            return true;

        if( obj == null || getClass() != obj.getClass())
            return false;

        Position pos = (Position) obj;

        return this.line == pos.line && this.column == pos.column;
    }

}
