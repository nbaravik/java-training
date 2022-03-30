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
  public void parseStringNumberFormatExceptionTest() {

        String s = "abcd";
        try {
            int i = StringToIntConverter.parseString(s);
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("String \"" + s + "\" cannot be converted to Integer", e.getLocalizedMessage());
          return;
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
        Assert.assertFalse(true);
  }

    @Test
    public void parseStringMaxValueExceptionTest() {

        String s = "5999666222";
        try {
            int i4 = StringToIntConverter.parseString(s);
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("String \"" + s + "\" cannot be converted. Integer.MAX_VALUE exceeded.", e.getLocalizedMessage());
            return;
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
        Assert.assertFalse(true);
    }

    @Test
    public void parseStringMinValueExceptionTest() {

        String s = "-8978674747565";
        try {
            int i = StringToIntConverter.parseString(s);
        } catch (NumberFormatException e) {
            Assert.assertTrue(true);
            Assert.assertEquals("String \"" + s + "\" cannot be converted. Value is less than Integer.MIN_VALUE.", e.getLocalizedMessage());
            return;
        } catch (Exception e) {
            Assert.assertFalse(true);
        }
        Assert.assertFalse(true);
    }
}
