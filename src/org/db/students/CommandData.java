package org.db.students;

import java.util.Arrays;

public class CommandData {
    private String[] strings;
    private int age;
    private long id;
    private String message;

    private CommandData() {
        this.strings = null;
        this.age = 0;
        this.id = 0L;
    }

    public static CommandData createDataWithIDOnly(long id) {
        CommandData data = new CommandData();
        data.id = id;
        return data;
    }

    public static CommandData createDataWith4StringsAndAge(String[] strings, Integer integers) {
        CommandData data = new CommandData();
        data.strings = strings;
        data.age = integers;
        data.id = 0L;
        return data;
    }

    public static CommandData createDataWith4StringsAgeAndId(String[] strings, Integer age, long id) {
        CommandData data = new CommandData();
        data.strings = strings;
        data.age = age;
        data.id = id;
        return data;
    }

    public static CommandData createDataWith2StringsOnly(String[] strings) {
        CommandData data = new CommandData();
        data.strings = strings;
        return data;
    }

    public static CommandData createDataWithMessageOnly(String message) {
        CommandData data = new CommandData();
        data.message = message;
        return data;
    }

    public String[] getStrings() {
        return strings;
    }

    public Integer getAge() {
        return age;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "CommandData{" +
                "strings=" + Arrays.toString(strings) +
                ", age=" + age +
                ", id=" + id +
                ", message='" + message + '\'' +
                '}';
    }
}
