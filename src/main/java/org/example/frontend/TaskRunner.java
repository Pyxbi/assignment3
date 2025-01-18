package org.example.frontend;

import javafx.concurrent.Task;

import java.util.function.Consumer;

public class TaskRunner<T> {
    // Class to run asynchronous operations.

    // Will render a loading spinner while running task.
    // Will stop spinner after task is done.
    // Will show error alert if exception is encountered during task.

    private T result;

    // The task to be executed.
    private Task<T> task;

    // What to do after task succeed. This consumer accepts the result of the task.
    // Will NOT trigger if task fails, even if it has completed.
    private Consumer<T> onSuccess;

    public TaskRunner(TaskSupplier<T> taskSupplier) {
        task = new Task<>() {
            @Override
            protected T call() throws Exception {
                return taskSupplier.get();
            }
        };

        // Default onSuccess is just empty void method
        onSuccess = res -> {};

        // Set up event handler for task completion
        task.setOnSucceeded(success -> {
            // End loading
            SceneManager.getInstance().endLoading();

            result = task.getValue();
            onSuccess.accept(result);
        });

        // Set up event handler for task failure
        task.setOnFailed(fail -> {
            // End loading
            SceneManager.getInstance().endLoading();

            Throwable exception = task.getException();
            exception.printStackTrace();
            AlertManager.showError(exception.getMessage());
        });
    }

    public TaskRunner(TaskSupplier<T> taskSupplier, Consumer<T> onSuccess) {
        this(taskSupplier);
        this.onSuccess = onSuccess;
    }

    public T getResult() {
        return result;
    }

    public void run() {
        try {
            // Start loading
            SceneManager.getInstance().startLoading();

            // Start the task
            new Thread(task).start();

        } catch (Exception e) {
            // End loading
            SceneManager.getInstance().endLoading();

            e.printStackTrace();
            AlertManager.showError(e.getMessage());
        }
    }

    @FunctionalInterface
    public interface TaskSupplier<T> {
        T get() throws Exception;
    }
}