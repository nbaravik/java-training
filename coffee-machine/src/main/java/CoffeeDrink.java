public class CoffeeDrink {

    private String name;
    private Resource[] resources;
    private Resource price;

    public CoffeeDrink(String name, Resource price, Resource... resources) {
        this.name = name;
        this.price = price;
        this.resources = resources;
    }

    public Resource[] getResources() {
        return resources;
    }

    public Resource getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}