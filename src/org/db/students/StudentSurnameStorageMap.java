package org.db.students;

import java.util.*;
import java.util.stream.Collectors;

public class StudentSurnameStorageMap {

    private final TreeMap<String, Set<Long>> surnameTreeMap = new TreeMap<>();

    /**
     * Сохраняет идентификатор созданного студента
     * @param id идентификатор
     * @param surname фамилия студента
     */
    public void studentCreated(Long id, String surname) {
        Set<Long> existingIds = surnameTreeMap.getOrDefault(surname, new HashSet<>());
        existingIds.add(id);
        surnameTreeMap.put(surname, existingIds);
    }

    /**
     * Удаляет идентификатор удалённого студента
     * @param id идентификатор
     * @param surname фамилия студента
     */
    public void studentDeleted(Long id, String surname) {
        surnameTreeMap.get(surname).remove(id);
    }

    public void studentUpdated(Long id, String oldSurname, String newSurname) {
        studentDeleted(id, oldSurname);
        studentCreated(id, newSurname);
    }

    /**
     * Возвращает множество идентификаторов студентов, чьи фамилии меньше или равны указанной.
     * @param surname фамилия
     * @return множество идентификаторов
     */
    public Set<Long> getStudentBySurnameLessOrEqualThan(String surname) {
        return surnameTreeMap.headMap(surname, true)
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Возвращает множество идентификаторов студентов, чьи фамилии в списке межу указанными.
     * @param surnameStart первая фамилия
     * @param surnameEnd последняя фамилия
     * @return множество идентификаторов
     */
    public Set<Long> getAllStudentBySurnameLessOrEqualThan(String surnameStart, String surnameEnd) {
        return surnameTreeMap.subMap (surnameStart,true,surnameEnd,true)
                .values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Возвращает список студентов, чьи фамилии соответствуют указанной.
     * @param surname фамилия
     * @return список
     */
    public Set<Long> getStudentWhoseSurnameIs(String surname) {
        return surnameTreeMap.entrySet().stream()
                .filter(e ->surname.equals(e.getKey()))
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    /**
     * Возвращает список всех студентов.
     * @return список
     */
    public Set<Long> getAllStudents() {
        return surnameTreeMap.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }
}
