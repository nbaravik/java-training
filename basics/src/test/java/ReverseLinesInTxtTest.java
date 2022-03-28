import org.junit.Assert;
import org.junit.Test;

public class ReverseLinesInTxtTest {

    @Test
    public void reverse() {
        Assert.assertEquals("eno", ReverseLinesInTxt.reverse("one"));
        Assert.assertEquals("owT", ReverseLinesInTxt.reverse("Two"));
        Assert.assertEquals("Eerht", ReverseLinesInTxt.reverse("threE"));
        Assert.assertEquals("RuoF", ReverseLinesInTxt.reverse("FouR"));
        Assert.assertEquals("", ReverseLinesInTxt.reverse(""));
    }
}
