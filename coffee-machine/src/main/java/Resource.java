public class Resource {

    private String name;
    private int amount;
    private String unit;

    public Resource(String name, int amount, String unit) {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public String getUnit() {
        return unit;
    }

    public Resource withAmount(int amount) {
        return new Resource(this.name, amount, this.unit);
    }

    public void increaseAmount(int someAmount) {
        this.amount += someAmount;
    }

    public void decreaseAmount(int someAmount) {
        this.amount -= someAmount;
    }

    public String format() {
        return getAmount() + getUnit() + " of " + getName();
    }

    public String formatRequest() {
        return "Write how many " + unit + " of " + name + " do you want to add:";
    }
}
