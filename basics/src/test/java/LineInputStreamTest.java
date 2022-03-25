import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

public class LineInputStreamTest  {

    @Test
    public void nextLineTest() throws Exception {

        System.out.println(System.getProperty("user.dir"));

        LineInputStream lis = new LineInputStream(new FileInputStream("LineInputStreamTest.txt"));

        Assert.assertEquals("first line", lis.nextLine());
        Assert.assertEquals("second line", lis.nextLine());
        Assert.assertEquals("third line", lis.nextLine());
        Assert.assertEquals("", lis.nextLine());
        Assert.assertEquals("fifth line", lis.nextLine());
        Assert.assertEquals(null, lis.nextLine());


        lis.close();
    }

    @Test
    public void nextLineInMemoryTest() throws Exception {
        LineInputStream lis = new LineInputStream(new ByteArrayInputStream("first line\nsecond line\n\nthird line".getBytes(StandardCharsets.UTF_8)));
        Assert.assertEquals("first line", lis.nextLine());
        Assert.assertEquals("second line", lis.nextLine());
        Assert.assertEquals("", lis.nextLine());
        Assert.assertEquals("third line", lis.nextLine());
        Assert.assertEquals(null, lis.nextLine());
    }
}
