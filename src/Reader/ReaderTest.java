package Reader;

import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReaderTest {

    @Test
    public void readerTest() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("tests/readerTest.txt"));
        Reader r = new Reader(br);

        Assert.assertEquals(1, r.getPosition().getColumn());
        Assert.assertEquals(1, r.getPosition().getLine());
        Assert.assertEquals('t', r.getCurrent());

        r.consume();

        Assert.assertEquals(2, r.getPosition().getColumn());
        Assert.assertEquals('a', r.getCurrent());

        r.consume();
        r.consume();

        Assert.assertEquals(r.getCurrent(), '\n');
        Assert.assertEquals(0, r.getPosition().getColumn());
        Assert.assertEquals( 2, r.getPosition().getLine());

        r.consume();
        Assert.assertEquals(1, r.getPosition().getColumn());
        Assert.assertEquals('z', r.getCurrent());
    }

}