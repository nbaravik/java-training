import org.junit.Assert;
import org.junit.Test;

public class ResourcesTest {

    Resource water = Resources.getWater(300);
    Resource milk = Resources.getMilk(200);
    Resource coffee = Resources.getCoffee(100);
    Resource sugar = Resources.getSugar(50);
    Resource cups = Resources.getCups(10);
    Resource cup = Resources.getCups(1);
    Resource cash = Resources.getCash(500);

    @Test
    public void formatTest() {
        Assert.assertEquals("300ml of water", water.format());
        Assert.assertEquals("200ml of milk", milk.format());
        Assert.assertEquals("100g of coffee beans", coffee.format());
        Assert.assertEquals("50g of sugar", sugar.format());
        Assert.assertEquals("10 disposable cups", cups.format());
        Assert.assertEquals("1 disposable cup", cup.format());
        Assert.assertEquals("$500 of money", cash.format());
    }

    @Test
    public void formatRequest() {
        Assert.assertEquals("Write how many ml of water do you want to add:", water.formatRequest());
        Assert.assertEquals("Write how many ml of milk do you want to add:", milk.formatRequest());
        Assert.assertEquals("Write how many grams of coffee beans do you want to add:", coffee.formatRequest());
        Assert.assertEquals("Write how many grams of sugar do you want to add:", sugar.formatRequest());
        Assert.assertEquals("Write how many disposable cups do you want to add:", cups.formatRequest());
    }

    @Test
    public void resourcesOperationsTest() {
        water.decreaseAmount(150);
        Assert.assertEquals(150, water.getAmount());
        coffee.increaseAmount(70);
        Assert.assertEquals(170, coffee.getAmount());

        Resource newCash = cash.withAmount(0);
        Assert.assertEquals(0, newCash.getAmount());
        Assert.assertEquals(500, cash.getAmount());
    }
}
