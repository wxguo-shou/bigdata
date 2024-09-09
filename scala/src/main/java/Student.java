/**
 * @author name 婉然从物
 * @create 2023-10-21 15:43
 */
public class Student {
    private String name;
    private Integer age;
    private static String school = "atguigu";

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void printInfo(){
        System.out.println(this.name + " " + this.age + " " + school);
    }
}
