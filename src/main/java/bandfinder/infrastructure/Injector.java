package bandfinder.infrastructure;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Injector {
    private static final Set<ClassItem> classes = new HashSet<>();
    private static final Map<Class<?>, Object> singletonInstances = new java.util.HashMap<>();

    public static void injectSingleton(Class<?> interfaceClass, Class<?> implementationClass) {
        validate(interfaceClass, implementationClass);

        classes.add(new ClassItem(interfaceClass, implementationClass, Scope.SINGLETON));
    }

    public static void injectScoped(Class<?> interfaceClass, Class<?> implementationClass) {
        validate(interfaceClass, implementationClass);

        classes.add(new ClassItem(interfaceClass, implementationClass, Scope.SCOPED));
    }

    public static <T> T getImplementation(Class<?> interfaceClass) {
        ClassItem classItem = classes.stream()
                .filter(c -> c.interfaceClass.equals(interfaceClass))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No implementation found for " + interfaceClass.getName()));

        if (classItem.scope == Scope.SINGLETON) {
            if (!singletonInstances.containsKey(interfaceClass)) {
                singletonInstances.put(interfaceClass, createInstance(classItem));
            }

            return (T) singletonInstances.get(interfaceClass);
        }

        return (T) createInstance(classItem);
    }

    private static <T> T createInstance(ClassItem classItem) {
        try {
            return (T) classItem.implementationClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not create instance of class " + classItem.implementationClass.getName(), e);
        }
    }

    private static void validate(Class<?> interfaceClass, Class<?> implementationClass) {
        if(!interfaceClass.isInterface()){
            throw new IllegalArgumentException("Interface class must be an interface");
        }

        if(implementationClass.isInterface()){
            throw new IllegalArgumentException("Implementation class must not be an interface");
        }

        if(!interfaceClass.isAssignableFrom(implementationClass)){
            throw new IllegalArgumentException("Implementation class must implement interface");
        }

        if(classes.stream().findAny().filter(x -> x.interfaceClass.equals(interfaceClass)).isPresent()){
            throw new IllegalArgumentException("Implementation class already registered");
        }
    }

    static class ClassItem {
        Class<?> interfaceClass;
        Class<?> implementationClass;
        Scope scope;

        public ClassItem(Class<?> interfaceClass, Class<?> implementationClass, Scope scope) {
            this.interfaceClass = interfaceClass;
            this.implementationClass = implementationClass;
            this.scope = scope;
        }
    }

    enum Scope {
        SINGLETON,
        SCOPED
    }
}

