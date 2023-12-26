
import java.util.List;

public class SampleClass {
    private int id;
    private String name;
    public static final double PI = 3.14159265359;
    private boolean active;
    private List<String> emailList;

    public SampleClass() {
        // Конструктор по умолчанию
    }

    public SampleClass(int id, String name, boolean active, List<String> emailList) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.emailList = emailList;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<String> getEmailList() {
        return emailList;
    }

    public void setEmailList(List<String> emailList) {
        this.emailList = emailList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void printInfo() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
    }

    public static void staticMethod() {
        System.out.println("This is a static method.");
    }

    public void processList(List<String> list) {
        if (list != null && !list.isEmpty()) {
            System.out.println("List contains elements:");
            for (String item : list) {
                System.out.println(item);
            }
        } else {
            System.out.println("List is empty or null.");
        }
    }

    private double calculateCircleArea(double radius) {
        return PI * radius * radius;
    }

    protected void protectedMethod() {
        System.out.println("This is a protected method.");
    }
}

