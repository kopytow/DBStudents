package org.db.students;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentStorageMap {

    private final Map<Long, Student> studentSrorageMap = new HashMap<>();
    private final StudentSurnameStorageMap surnameStorageMap = new StudentSurnameStorageMap();

    /**
     * Добавляет данные о студенте
     * @param student добавляемые данные
     * @return идентификатор
     */
    public Long createStudent(Student student) {
        Long nextId = getCurrentId();
        studentSrorageMap.put(nextId, student);
        surnameStorageMap.studentCreated(nextId, student.getSurname());
        return nextId;
    }

    /**
     * Обновление данных о студенте.
     * @param id идентификатор студента
     * @return истину, если операция выполнена успешно
     */
    public boolean updateStudent(Long id, Student student) {
        if (!studentSrorageMap.containsKey(id)) return false;
        surnameStorageMap.studentUpdated(id, studentSrorageMap.get(id).getSurname(), student.getSurname());
        studentSrorageMap.put(id, student);
        return true;
    }

    /**
     * Удаляет данные о студенте
     * @param id идентификатор
     * @return истину, если операция выполнена успешно
     */
    public boolean deleteStudent(Long id) {
        Student student = studentSrorageMap.remove(id);
        if (student != null) surnameStorageMap.studentDeleted(id, student.getSurname());
        return student != null;
    }

    private Long getCurrentId() {
        Long lastKey = studentSrorageMap.keySet().stream().reduce((a,b) ->b).orElseGet(() -> 0L);
        return lastKey + 1;
    }

    public void printAll() {
        System.out.println(studentSrorageMap);
    }

    public void printStatisticsByCourses(){
        Map<String, Long> data = studentSrorageMap.values().stream()
                .collect(Collectors.toMap(
                        Student::getCourse,
                        s -> 1L,
                        Long::sum
                ));
        data.forEach((key, value) -> System.out.println(key + " - " + value));
    }

    /**
     * Возвращает список всех студентов
     */
    public void search() {
        surnameStorageMap.getAllStudents()
                .forEach(id -> {
                    System.out.println(studentSrorageMap.get(id));
                });
    }

    /**
     * Возвращает список студентов, чья фамилия равна указанной.
     * @param surname список фамилий
     */
    public void search(String surname) {
        surnameStorageMap.getStudentWhoseSurnameIs(surname)
                .forEach(id -> {
                    System.out.println(studentSrorageMap.get(id));
                });
    }

    /**
     * Возвращает список студентов, чья фамилия между указанными.
     * @param surnameStart первая фамилия
     * @param surnameEnd последняя фамилия
     */
    public void search(String surnameStart, String surnameEnd) {
        surnameStorageMap.getAllStudentBySurnameLessOrEqualThan(surnameStart, surnameEnd)
                .forEach(id -> {
                    System.out.println(studentSrorageMap.get(id));
                });
    }

    public void printStatisticsByCities() {
        Map<String, Long> data = studentSrorageMap.values().stream()
                .collect(Collectors.toMap(
                        Student::getCity,
                        s -> 1L,
                        Long::sum
                ));
        data.forEach((key, value) -> System.out.println(key + " - " + value));
    }
}
