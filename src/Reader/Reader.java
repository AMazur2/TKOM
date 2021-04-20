package Reader;

import org.jetbrains.annotations.NotNull;
import java.io.BufferedReader;
import java.io.IOException;

public class Reader {

    private BufferedReader reader;
    private char current;
    private Position pos;

    public Reader(@NotNull BufferedReader br) throws IOException
    {
        this.reader = br;
        this.current = (char) br.read();
        this.pos = new Position();
    }

    public Position getPosition() { return this.pos; }

    public char getCurrent() { return this.current; }

    public void setCurrent( char ch ) { this.current = ch; }

    public void consume() throws IOException
    {
        setCurrent((char) reader.read());
        pos.incrementColumn();

        if(getCurrent() == '\n')
        {
            pos.incrementLine();
            pos.setColumn(0);       //w następnym wywołaniu tej funkcji dopiero ustawimy się na pierwszym znaku w linii
        }
    }

}
