package main.java.ru.example.examle2;

/**
 * @author NAgafonov
 */
public class Worker {
    private String name;
    private Integer age;
    private WorkerPosition position;

    public Worker(String name, Integer age, WorkerPosition position) {
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public WorkerPosition getPosition() {
        return position;
    }

    public Integer getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Worker{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append(", position=").append(position);
        sb.append('}');
        return sb.toString();
    }
}
