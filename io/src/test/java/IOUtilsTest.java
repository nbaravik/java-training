import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class IOUtilsTest {

    @Test
    public void CopyTest() throws IOException {
        String testString = "Hello world";
        ByteArrayInputStream bis = new ByteArrayInputStream(testString.getBytes());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        IOUtils.copy(bis, bos);

        byte[] in = testString.getBytes(StandardCharsets.UTF_8);
        byte[] out = bos.toByteArray();
        Assert.assertArrayEquals(in, out);
        bis.close();
        bos.close();
    }

    @Test
    public void nextLineInMemoryTest() throws Exception {
        ByteArrayInputStream bis = new ByteArrayInputStream("first line\nsecond line\n\nthird line\n4444".getBytes(StandardCharsets.UTF_8));
        Assert.assertEquals("first line", IOUtils.nextLine(bis));
        Assert.assertEquals("second line", IOUtils.nextLine(bis));
        Assert.assertEquals("", IOUtils.nextLine(bis));
        Assert.assertEquals("third line", IOUtils.nextLine(bis));
        Assert.assertEquals("4444", IOUtils.nextLine(bis));
        Assert.assertEquals(null, IOUtils.nextLine(bis));
        bis.close();
    }
}
