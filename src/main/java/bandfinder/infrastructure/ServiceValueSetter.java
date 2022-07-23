package bandfinder.infrastructure;

import java.lang.reflect.Field;

public class ServiceValueSetter {
    public static void setAutoInjectableFieldValues(Object instance) {
        Field[] fields = instance.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutoInjectable.class)) {
                field.setAccessible(true);
                try {
                    field.set(instance, Injector.getImplementation(field.getType()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
