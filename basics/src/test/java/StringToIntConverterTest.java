import org.junit.Assert;
import org.junit.Test;

public class StringToIntConverterTest {

    @Test
    public void parseStringTest() {
        String s1 = "839583098";
        String s2 = "-182495";
        String s3 = "0";
        int i1 = 839583098;
        int i2 = -182495;
        int i3 = 0;
        Assert.assertEquals(i1, StringToIntConverter.parseString(s1));
        Assert.assertEquals(i2, StringToIntConverter.parseString(s2));
        Assert.assertEquals(i3, StringToIntConverter.parseString(s3));
    }

    @Test
    public void parseStringExceptionsTest() {

        String s1 = "3.5";
        String s2 = "fgt";
        String s3 = "";

        try {
            int i1 = StringToIntConverter.parseString(s1);
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("String \"" + s1 + "\" cannot be converted to Integer", e.getLocalizedMessage());
            return;
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
        Assert.assertFalse(true);

        try {
            int i2 = StringToIntConverter.parseString(s2);
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("String \"" + s2 + "\" cannot be converted to Integer", e.getLocalizedMessage());
            return;
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
        Assert.assertFalse(true);

        try {
            int i3 = StringToIntConverter.parseString(s3);
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("String must not be empty", e.getLocalizedMessage());
            return;
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
        Assert.assertFalse(true);
    }
}
