import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;

public class DurationParserFormatterTest {

    DurationParserFormatter dpf = DurationParserFormatter.getInstance();

    @Test
    public void illegalArgumentExceptionTest() {
        try {
            dpf.parse(null);
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            dpf.parse("");
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            dpf.parse("1");
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            dpf.parse("d");
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            dpf.parse("1d50m2w");
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            dpf.parse("10msd");
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
        try {
            dpf.parse("w10d");
            Assert.assertTrue(false);
        } catch (IllegalArgumentException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void parseTest() {
        Assert.assertEquals(Duration.ofMinutes(0), dpf.parse("0w"));
        Assert.assertEquals(Duration.ofMinutes(700000), dpf.parse("700000m"));
        Assert.assertEquals(Duration.ofHours(504), dpf.parse("20d24h"));
        Assert.assertEquals(Duration.ofMillis(4810050), dpf.parse("1h20m10s50ms"));
        Assert.assertEquals(Duration.ofMillis(18937180010L), dpf.parse("24w50d27h68m700s10ms"));
    }

    @Test
    public void formatTest() {
        Assert.assertEquals("69w3d2h40m", dpf.format(Duration.ofMinutes(700000)));
        Assert.assertEquals("3w", dpf.format(Duration.ofHours(504)));
        Assert.assertEquals("1h20m10s50ms", dpf.format(Duration.ofMillis(4810050)));
        Assert.assertEquals("31w2d4h19m40s10ms", dpf.format(Duration.ofMillis(18937180010L)));
    }
}
