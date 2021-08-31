package designpatterns.decorator;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

class DeadlineNotValidException extends Exception {
    public DeadlineNotValidException(LocalDateTime deadline) {
        super(String.format("The deadline %s has already passed", deadline));
    }
}

interface ITask {
    LocalDateTime getDeadline();

    int getPriority();

    String getCategory();
}

class SimpleTask implements ITask {
    String category, name, description;

    public SimpleTask(String category, String name, String description) {
        this.category = category;
        this.name = name;
        this.description = description;
    }


    @Override
    public LocalDateTime getDeadline() {
        return LocalDateTime.MAX;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("STask{");
        sb.append("category='").append(category).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

abstract class TaskDecorator implements ITask {
    ITask iTask;

    public TaskDecorator(ITask iTask) {
        this.iTask = iTask;
    }
}

class PriorityTask extends TaskDecorator {
    int priority;

    public PriorityTask(ITask iTask, int priority) {
        super(iTask);
        this.priority = priority;
    }


    @Override
    public LocalDateTime getDeadline() {
        return iTask.getDeadline();
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getCategory() {
        return iTask.getCategory();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PriorityTask{");
        sb.append("priority=").append(priority);
        sb.append('}');
        return sb.toString();
    }
}

class TimeTaskDecorator extends TaskDecorator {
    LocalDateTime deadline;

    public TimeTaskDecorator(ITask iTask, LocalDateTime deadline) {
        super(iTask);
        this.deadline = deadline;
    }

    @Override
    public LocalDateTime getDeadline() {
        return deadline;
    }

    @Override
    public int getPriority() {
        return iTask.getPriority();
    }

    @Override
    public String getCategory() {
        return iTask.getCategory();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TimeTaskDecorator{");
        sb.append("deadline=").append(deadline);
        sb.append('}');
        return sb.toString();
    }
}

class TaskFactory {

    public static ITask createTask(String line) throws DeadlineNotValidException {
        String[] parts = line.split(",");
        String category = parts[0];
        String name = parts[1];
        String description = parts[2];
        SimpleTask task = new SimpleTask(category, name, description);
        if (parts.length == 3) {
            return task;
        } else if (parts.length == 4) {
            try {
                int priority = Integer.parseInt(parts[3]);
                return new PriorityTask(task, priority);
            } catch (Exception e) {
                LocalDateTime deadline = LocalDateTime.parse(parts[3]);
                checkDeadline(deadline);
                return new TimeTaskDecorator(task, deadline);
            }
        } else {
            LocalDateTime deadline = LocalDateTime.parse(parts[3]);
            checkDeadline(deadline);
            int priority = Integer.parseInt(parts[4]);
            return new PriorityTask(new TimeTaskDecorator(task, deadline), priority);
        }
    }

    private static void checkDeadline(LocalDateTime deadline) throws DeadlineNotValidException {
        if (deadline.isBefore(LocalDateTime.now())) {
            throw new DeadlineNotValidException(deadline);
        }
    }
}

class TaskManager {

    public void readTasks(InputStream inputStream) {

    }

    public void printTasks(OutputStream os, boolean includePriority, boolean includeCategory) {

    }

}

public class TasksManagerTest {

    public static void main(String[] args) {

        TaskManager manager = new TaskManager();

        System.out.println("Tasks reading");
        manager.readTasks(System.in);
        System.out.println("By categories with priority");
        manager.printTasks(System.out, true, true);
        System.out.println("-------------------------");
        System.out.println("By categories without priority");
        manager.printTasks(System.out, false, true);
        System.out.println("-------------------------");
        System.out.println("All tasks without priority");
        manager.printTasks(System.out, false, false);
        System.out.println("-------------------------");
        System.out.println("All tasks with priority");
        manager.printTasks(System.out, true, false);
        System.out.println("-------------------------");

    }
}
