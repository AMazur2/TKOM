package uni.projects.reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

public class ReaderTest {

    @Test
    public void readerRTest() throws IOException {
        String data = "ta\rz";
        BufferedReader br = new BufferedReader(new StringReader(data));
        Reader r = new Reader(br, 10);

        Assert.assertEquals(1, r.getPosition().getColumn());
        Assert.assertEquals(1, r.getPosition().getLine());
        Assert.assertEquals('t', r.getCurrent());

        r.consume();

        Assert.assertEquals(2, r.getPosition().getColumn());
        Assert.assertEquals('a', r.getCurrent());
        Assert.assertEquals("t", r.getContext());

        r.consume();

        Assert.assertEquals(r.getCurrent(), '\r');
        Assert.assertEquals(0, r.getPosition().getColumn());
        Assert.assertEquals(2, r.getPosition().getLine());

        r.consume();

        Assert.assertEquals(1, r.getPosition().getColumn());
        Assert.assertEquals('z', r.getCurrent());
        Assert.assertEquals("", r.getContext());

    }

    @Test
    public void readerNTest() throws IOException {
        String data = "ta\nz";
        BufferedReader br = new BufferedReader(new StringReader(data));
        Reader r = new Reader(br, 10);

        Assert.assertEquals(1, r.getPosition().getColumn());
        Assert.assertEquals(1, r.getPosition().getLine());
        Assert.assertEquals('t', r.getCurrent());

        r.consume();

        Assert.assertEquals(2, r.getPosition().getColumn());
        Assert.assertEquals('a', r.getCurrent());
        Assert.assertEquals("t", r.getContext());

        r.consume();

        Assert.assertEquals(r.getCurrent(), '\n');
        Assert.assertEquals(0, r.getPosition().getColumn());
        Assert.assertEquals(2, r.getPosition().getLine());

        r.consume();

        Assert.assertEquals(1, r.getPosition().getColumn());
        Assert.assertEquals('z', r.getCurrent());
        Assert.assertEquals("", r.getContext());
    }

    @Test
    public void readerRNTest() throws IOException {
        String data = "ta\r\nz";
        BufferedReader br = new BufferedReader(new StringReader(data));
        Reader r = new Reader(br, 10);

        Assert.assertEquals(1, r.getPosition().getColumn());
        Assert.assertEquals(1, r.getPosition().getLine());
        Assert.assertEquals('t', r.getCurrent());

        r.consume();

        Assert.assertEquals(2, r.getPosition().getColumn());
        Assert.assertEquals('a', r.getCurrent());
        Assert.assertEquals("t", r.getContext());

        r.consume();

        Assert.assertEquals(r.getCurrent(), '\n');
        Assert.assertEquals(0, r.getPosition().getColumn());
        Assert.assertEquals(2, r.getPosition().getLine());

        r.consume();

        Assert.assertEquals(1, r.getPosition().getColumn());
        Assert.assertEquals('z', r.getCurrent());
        Assert.assertEquals("", r.getContext());
    }

}