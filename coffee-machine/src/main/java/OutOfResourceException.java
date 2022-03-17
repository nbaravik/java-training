public class OutOfResourceException extends  Exception {

    private String resourceName;

    public OutOfResourceException(String name) {
        this.resourceName = name;
    }

    public String getResourceName() {
        return resourceName;
    }
}
