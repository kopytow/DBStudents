package org.db.students;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * Выполняемые пользователем действия.
 */
public enum Action {
    EXIT(0, false) {
        @Override
        public String toString() {
            return "Выход";
        }
    },
    CREATE(1, true) {
        @Override
        public String toString() {
            return "Создать";
        }
    },
    UPDATE(2, true) {
        @Override
        public String toString() {
            return "Обновить";
        }
    },
    DELETE(3, true) {
        @Override
        public String toString() {
            return "Удалить";
        }
    },
    COURSE_STATISTICS(4, false) {
        @Override
        public String toString() {
            return "Статистика по курсам";
        }
    },
    STATISTICS_BY_CITIES(5, false) {
        @Override
        public String toString() {
            return "Статистика по городам";
        }
    },
    SEARCH_BY_SURNAME(6, true) {
        @Override
        public String toString() {
            return "Поиск по фамилии";
        }
    },
    ERROR(-1, false) {
        @Override
        public String toString() {
            return "Ошибка";
        }
    };

    /**
     * Код действия.
     */
    private final Integer code;
    /**
     * Требование дополнительных данных.
     */
    private final boolean requiredAdditionalData;

    Action(Integer code, boolean requiredAdditionalData) {
        this.code = code;
        this.requiredAdditionalData = requiredAdditionalData;
    }

    public Integer getCode() {
        return code;
    }

    public boolean isRequiredAdditionalData() {
        return requiredAdditionalData;
    }

    public static Action createActionFromCode(Integer code) {
        return Stream.of(Action.values())
                .filter(action -> Objects.equals(action.getCode(), code))
                .findFirst()
                .orElseGet(() -> {
                    System.out.println("Неизвестное действие " + code);
                    return Action.ERROR;
                });
    }

}
