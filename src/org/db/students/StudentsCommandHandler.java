package org.db.students;

public class StudentsCommandHandler {

    private final StudentStorageMap studentStorageMap = new StudentStorageMap();

    public void processCommand(Command command) {
        Action action = command.getAction();
        switch (action) {
            case CREATE -> processCreateCommand(command);
            case UPDATE -> processUpdateCommand(command);
            case DELETE -> processDeleteCommand(command);
            case COURSE_STATISTICS -> processStatisticsByCoursesCommand();
            case STATISTICS_BY_CITIES -> processStatisticsByCitiesCommand();
            case SEARCH_BY_SURNAME -> processSearchCommand(command);
            default -> System.out.println("Действие " + action.name() + " не поддерживается.");
        }
        System.out.println(
                "Обработка команды. Действие: " + command.getAction()
              + ", данные: " + command.getData()
        );
    }

    private void processStatisticsByCitiesCommand() {
        studentStorageMap.printStatisticsByCities();
    }

    private void processSearchCommand(Command command) {
        String[] surnames = command.getData().getStrings();
        if (surnames.length == 1 && surnames[0].isEmpty()) {
            studentStorageMap.search();
            return;
        }
        if (surnames.length == 1) {
            studentStorageMap.search(surnames[0]);
            return;
        }
        if (surnames.length == 2) studentStorageMap.search(surnames[0], surnames[1]);
    }

    private void processStatisticsByCoursesCommand() {
        studentStorageMap.printStatisticsByCourses();
    }

    private void processCreateCommand(Command command) {
        String[] data = command.getData().getStrings();
        Student student = new Student(data[0], data[1], data[2], data[3], command.getData().getAge());
        studentStorageMap.createStudent(student);
        studentStorageMap.printAll();
    }

    private void processUpdateCommand(Command command) {
        String[] data = command.getData().getStrings();
        Long id = command.getData().getId();
        Integer age = command.getData().getAge();
        Student student = new Student(data[1], data[2], data[3], data[4], age);
        studentStorageMap.updateStudent(id, student);
        studentStorageMap.printAll();
    }

    private void processDeleteCommand(Command command) {
        Long id = command.getData().getId();
        studentStorageMap.deleteStudent(id);
        studentStorageMap.printAll();
    }

    public CommandData checkData(Action action, String data) throws WrongTypeAndNumberOfParametersException {
        CommandData commandData;
        switch (action) {
            case CREATE -> commandData = checkCreateCommandData(data);
            case UPDATE -> commandData = checkUpdateCommandData(data);
            case DELETE -> commandData = checkDeleteCommandData(data);
            case SEARCH_BY_SURNAME -> commandData = checkSearchCommandData(data);
            default -> commandData = CommandData.createDataWithMessageOnly("Действие " + action.name() + " не поддерживается.");
        }
        return commandData;
    }

    private CommandData checkSearchCommandData(String data) throws WrongTypeAndNumberOfParametersException {
        String[] strings = data.split(",");
        if (strings.length > 2)
            throw new WrongTypeAndNumberOfParametersException(
                    "Формат данных для действия \""
                  + Action.SEARCH_BY_SURNAME
                  + "\" либо пустая строка, либо фамилия(строка), либо фамилия1(строка),фамилия2(строка)."
            );
        return CommandData.createDataWith2StringsOnly(strings);
    }

    private CommandData checkDeleteCommandData(String data) throws WrongTypeAndNumberOfParametersException {
        if (!data.matches("\\d+"))
            throw new WrongTypeAndNumberOfParametersException(
                    "Формат данных для действия \""
                  + Action.DELETE
                  + "\" идентификатор(целое)."
            );
        long id = Long.parseLong(data);
        return CommandData.createDataWithIDOnly(id);
    }

    private CommandData checkUpdateCommandData(String data) throws WrongTypeAndNumberOfParametersException {
        String[] strings = data.split(",");
        if (strings.length != 6 || !strings[0].matches("\\d+") || !strings[5].matches("\\d+"))
            throw new WrongTypeAndNumberOfParametersException(
                    "Формат данных для действия \""
                            + Action.UPDATE
                            + "\" идентификатор(целое),фамилия(строка),имя(строка),курс(строка),город(строка),возраст(целое)."
            );
        long id = Long.parseLong(strings[0]);
        int age = Integer.parseInt(strings[5]);
        return CommandData.createDataWith4StringsAgeAndId(strings, age, id);
    }

    private CommandData checkCreateCommandData(String data) throws WrongTypeAndNumberOfParametersException {
        String[] strings = data.split(",");
        if (strings.length != 5 || !strings[4].matches("\\d+"))
            throw new WrongTypeAndNumberOfParametersException(
                "Формат данных для действия \""
              + Action.CREATE
              + "\" фамилия(строка),имя(строка),курс(строка),город(строка),возраст(целое)."
            );
        int age = Integer.parseInt(strings[4]);
        return CommandData.createDataWith4StringsAndAge(strings, age);
    }
}
