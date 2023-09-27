package org.db.students;

import java.util.Scanner;

public class Main {

    private final static StudentsCommandHandler STUDENTS_COMMAND_HANDLER = new StudentsCommandHandler();

    public static void main(String[] args) {
        while (true) {
            printMessage();
            Command command = readCommand();
            if (command.getAction() == Action.EXIT) return;
            else if (command.getAction() == Action.ERROR)
                System.out.println(command.getData().getMessage());
            else STUDENTS_COMMAND_HANDLER.processCommand(command);
        }
    }

    private static Command readCommand() {
        Scanner scanner = new Scanner(System.in);
        try {
            Integer actionCode = Integer.parseInt(scanner.nextLine());
            Action action = Action.createActionFromCode(actionCode);
            if (action.isRequiredAdditionalData()) {
                String data = scanner.nextLine();
                return new Command(action, STUDENTS_COMMAND_HANDLER.checkData(action, data));
            } else {
                return new Command(action);
            }
        } catch (Exception ex) {
            CommandData data = CommandData.createDataWithMessageOnly("Проблема обработки ввода " + ex.getMessage());
            return new Command(Action.ERROR, data);
        }
    }

    private static void printMessage() {
        System.out.println("------------------------------");
        System.out.println("0. Выход");
        System.out.println("1. Создание данных");
        System.out.println("2. Обновление данных");
        System.out.println("3. Удаление данных");
        System.out.println("4. Вывод статистики по курсам");
        System.out.println("5. Вывод статистики по городам");
        System.out.println("6. Поиск по фамилии");
        System.out.println("------------------------------");
    }

}