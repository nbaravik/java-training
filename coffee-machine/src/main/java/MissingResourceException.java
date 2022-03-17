public class MissingResourceException extends RuntimeException {

    private String resourceName;

    public MissingResourceException(String name) {
        this.resourceName = name;
    }

    public String getResourceName() {
        return resourceName;
    }
}
