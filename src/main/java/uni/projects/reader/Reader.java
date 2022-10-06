package uni.projects.reader;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;

public class Reader {

    private BufferedReader reader;
    private char current;
    private Position pos;
    private StringBuilder context;
    private int maxContextLength;

    public Reader(@NotNull BufferedReader br, int mcl) throws IOException {
        this.reader = br;
        this.current = (char) br.read();
        this.pos = new Position();
        this.maxContextLength = mcl;
        this.context = new StringBuilder();
    }

    public Position getPosition() {
        return this.pos;
    }

    public char getCurrent() {
        return this.current;
    }

    public String getContext() {
        return this.context.toString();
    }

    public StringBuilder getContextBuilder() {
        return this.context;
    }

    public void setCurrent(char ch) {
        this.current = ch;
    }

    public void consume() throws IOException {
        if (getCurrent() != '\n' && getCurrent() != 21 && getCurrent() != '\r') {
            if (this.context.length() == this.maxContextLength)
                this.context.deleteCharAt(0);

            this.context.append(getCurrent());
        }
        setCurrent((char) reader.read());

        pos.incrementColumn();

        if (getCurrent() == '\r') {
            reader.mark(1);
            char ch = (char) reader.read();
            if (ch == '\n')
                setCurrent(ch);
            else
                reader.reset();

            this.context.delete(0, context.length());
            pos.incrementLine();
            pos.setColumn(0);
        } else if (getCurrent() == '\n' || getCurrent() == 21)  // NEL 0x15
        {
            this.context.delete(0, context.length());
            pos.incrementLine();
            pos.setColumn(0);       //w następnym wywołaniu tej funkcji dopiero ustawimy się na pierwszym znaku w linii
        }
    }

}
