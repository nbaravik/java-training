import java.lang.reflect.Field;
import java.util.ResourceBundle;

public class Main {

    public static void main(String[] args) throws Exception {
        Bean bean = getBean(Bean.class);
        System.out.println(bean);

        Main main = getBean(Main.class);
        System.out.println(main);
    }

    public static <T> T getBean(Class<T> type) throws Exception {
        Field[] fields = type.getDeclaredFields();
        ResourceBundle rb = ResourceBundle.getBundle("application");
        T obj = type.getDeclaredConstructor().newInstance();
        for (Field field : fields) {
            Property[] annotations = field.getAnnotationsByType(Property.class);
            if (annotations.length != 1) {
                continue;
            }
            field.setAccessible(true);
            String key = annotations[0].value();
            String value = rb.getString(key);
            field.set(obj, value);
        }
        return obj;
    }

    public String toString() {
        return "this is main class";
    }
}
