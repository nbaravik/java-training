import java.util.*;

public class CoffeeMachine {

    private Resource[] stuff;
    private Resource cash;

    private Map<String, Resource> resourceMap = new HashMap<>();

    public CoffeeMachine(Resource cash, Resource... stuff) {
        this.cash = cash;
        this.stuff = stuff;
        for (Resource nextItem : stuff) {
            resourceMap.put(nextItem.getName(), nextItem);
        }
        resourceMap.put(cash.getName(), cash);
    }

    // resources that needed to make a drink
    public List<Resource> getStuffResources() {
        return Arrays.asList(this.stuff);
    }

    // all resources (stuff + cash)
    public List<Resource> getAllResources() {
        List<Resource> allResources = new ArrayList<>();
        allResources.addAll(Arrays.asList(stuff));
        allResources.add(cash);
        return allResources;
    }

    public List<Resource> remaining() {
        return getAllResources();
    }

    public int take() {
        int cash = this.cash.getAmount();
        this.cash.decreaseAmount(cash);
        return cash;
    }

    public void fill(Resource ...refillableResources) {

        for (Resource item : refillableResources) {
            Resource nextResource = resourceMap.get(item.getName());
            if (nextResource == null) {
                continue;
            }
            nextResource.increaseAmount(item.getAmount());
        }
    }

    public void buy(CoffeeDrink coffeeDrink) throws OutOfResourceException {

        checkIfEnoughResources(coffeeDrink);
        Resource[] drinkResources = coffeeDrink.getResources();
        for (Resource item : drinkResources) {
            Resource nextResource = resourceMap.get(item.getName());
            nextResource.decreaseAmount(item.getAmount());
        }
        Resource cash = resourceMap.get(coffeeDrink.getPrice().getName());
        cash.increaseAmount(coffeeDrink.getPrice().getAmount());
    }

    private void checkIfEnoughResources(CoffeeDrink coffeeDrink) throws OutOfResourceException {

        Resource[] resources = coffeeDrink.getResources();

        for (Resource item : resources) {
            Resource nextResource = resourceMap.get(item.getName());
            if (nextResource == null) {
                throw new MissingResourceException(item.getName());
            }
            if (item.getAmount() > resourceMap.get(item.getName()).getAmount()) {
                throw new OutOfResourceException(item.getName());
            }
        }
    }


}