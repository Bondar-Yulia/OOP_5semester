import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ClassDescriptionPrinter {
    public static void printClassDescription(String className) throws ClassNotFoundException {
        Class<?> clazz = Class.forName(className);

        // Вывод имени класса
        System.out.println("Class Name: " + clazz.getSimpleName());

        // Вывод модификаторов класса
        int modifiers = clazz.getModifiers();
        System.out.println("Modifiers: " + Modifier.toString(modifiers));

        // Вывод информации о полях класса
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("Fields:");
        for (Field field : fields) {
            System.out.println("    " + Modifier.toString(field.getModifiers()) + " " + field.getName() +
                    ": " + field.getGenericType().getTypeName());
        }

    }

    public static void main(String[] args) {
        try {
            printClassDescription("SampleClass");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
