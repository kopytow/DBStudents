package org.db.students;

public class Command {

    /**
     * Выбранное действие.
     */
    private final Action action;
    /**
     * Данные.
     */
    private CommandData data;

    public Command(Action action) {
        this.action = action;
    }

    public Command(Action action, CommandData data) {
        this.action = action;
        this.data = data;
    }

    public Action getAction() {
        return action;
    }

    public CommandData getData() {
        return data;
    }
}
