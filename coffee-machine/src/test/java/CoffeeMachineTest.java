import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class CoffeeMachineTest {

    @Test
    public void testFill() {
        CoffeeMachine coffeeMachine = new CoffeeMachine(
                Resources.getCash(550),

                Resources.getWater(400),
                Resources.getMilk(540),
                Resources.getCoffee(120),
                Resources.getSugar(50),
                Resources.getCups(9)
        );

        Resource water = Resources.getWater(1000);
        Resource milk = Resources.getMilk(1000);
        Resource coffee = Resources.getCoffee(100);
        Resource sugar = Resources.getSugar(50);
        Resource cups = Resources.getCups(0);

        coffeeMachine.fill(water, milk, coffee, sugar, cups);
        List<Resource> resources = coffeeMachine.getAllResources();
        for (Resource item : resources) {
            switch (item.getName()) {
                case Resources.WATER -> Assert.assertEquals(1400, item.getAmount());
                case Resources.MILK -> Assert.assertEquals(1540, item.getAmount());
                case Resources.COFFEE -> Assert.assertEquals(220, item.getAmount());
                case Resources.SUGAR -> Assert.assertEquals(100, item.getAmount());
                case Resources.CUPS -> Assert.assertEquals(9, item.getAmount());
                case Resources.CASH -> Assert.assertEquals(550, item.getAmount());
            }
        }
    }

    @Test
    public void testTake() {
        CoffeeMachine coffeeMachine = new CoffeeMachine(
                Resources.getCash(550),

                Resources.getWater(400),
                Resources.getMilk(540),
                Resources.getCoffee(120),
                Resources.getSugar(50),
                Resources.getCups(9)
        );

        int cash = coffeeMachine.take();
        Assert.assertEquals(550, cash);
        List<Resource> resources = coffeeMachine.getAllResources();
        for (Resource item : resources) {
            if (item.getName().equalsIgnoreCase(Resources.CASH)) {
                Assert.assertEquals(0, item.getAmount());
            }
        }
    }

    @Test
    public void testRemaining() {
        CoffeeMachine coffeeMachine = new CoffeeMachine(
                Resources.getCash(700),

                Resources.getWater(1400),
                Resources.getMilk(1540),
                Resources.getCoffee(120),
                Resources.getSugar(100),
                Resources.getCups(20)
        );

        List<Resource> remaining = coffeeMachine.remaining();
        for (Resource item : remaining) {
            switch (item.getName()) {
                case Resources.WATER -> Assert.assertEquals(1400, item.getAmount());
                case Resources.MILK -> Assert.assertEquals(1540, item.getAmount());
                case Resources.COFFEE -> Assert.assertEquals(120, item.getAmount());
                case Resources.SUGAR -> Assert.assertEquals(100, item.getAmount());
                case Resources.CUPS -> Assert.assertEquals(20, item.getAmount());
                case Resources.CASH -> Assert.assertEquals(700, item.getAmount());
            }
        }
    }

    @Test
    public void testBuy() throws OutOfResourceException {
        CoffeeMachine coffeeMachine = new CoffeeMachine(
                Resources.getCash(700),

                Resources.getWater(1400),
                Resources.getMilk(1540),
                Resources.getCoffee(120),
                Resources.getSugar(100),
                Resources.getCups(20)
        );

        CoffeeDrink coffeeDrink = new CoffeeDrink(
                "some drink",
                Resources.getCash(10),

                Resources.getWater(100),
                Resources.getMilk(50),
                Resources.getCoffee(20),
                Resources.getSugar(10),
                Resources.getCups(1)
        );

        coffeeMachine.buy(coffeeDrink);
        List<Resource> remaining = coffeeMachine.getAllResources();
        for (Resource item : remaining) {
            switch (item.getName()) {
                case Resources.WATER -> Assert.assertEquals(1300, item.getAmount());
                case Resources.MILK -> Assert.assertEquals(1490, item.getAmount());
                case Resources.COFFEE -> Assert.assertEquals(100, item.getAmount());
                case Resources.SUGAR -> Assert.assertEquals(90, item.getAmount());
                case Resources.CUPS -> Assert.assertEquals(19, item.getAmount());
                case Resources.CASH -> Assert.assertEquals(710, item.getAmount());
            }
        }
    }

    @Test
    public void buyFailedTest() {
        CoffeeMachine coffeeMachine = new CoffeeMachine(
                Resources.getCash(700),

                Resources.getWater(1000),
                Resources.getMilk(1000),
                Resources.getCoffee(15),
                Resources.getSugar(100),
                Resources.getCups(20)
        );

        CoffeeDrink coffeeDrink = new CoffeeDrink(
                "some drink",
                Resources.getCash(10),

                Resources.getWater(100),
                Resources.getMilk(50),
                Resources.getCoffee(20),
                Resources.getSugar(10),
                Resources.getCups(1)
        );
        try {
            coffeeMachine.buy(coffeeDrink);
        } catch (OutOfResourceException e) {
            Assert.assertEquals(Resources.COFFEE, e.getResourceName());
            Assert.assertTrue(true);
        }
    }
}