package com.polytech.phrases;

public enum Phrases {
    ENTER_OPERATION("Введите тип математической операции (+, -, *, /, sqrt, fact):"),
    BAD_OPERATION("Неверный тип операции. Калькулятором поддерживаются: +, -, *, /, sqrt, fact."),
    ENTER_OPERATION_TIME("Введите время в секундах, которое Вы готовы ждать до получения ответа:"),
    BAD_OPERATION_TIME("Некорректное время. Введите время от 1 до 65 535 секунд"),
    ENTER_TWO_ARGUMENTS("Введите числа через пробел:"),
    BAD_TWO_ARGUMENTS("Аргументы введены неверно. Введите корректные числа."),
    ENTER_ARGUMENT("Введите число для вычисления долгой функции:"),
    BAD_ARGUMENT("Неверный аргумент. Введите корректное число."),
    SENT_FILE("Отправлен файл: "),
    CREATE_DIRECTORY("Директория для файлов успешно создана: "),
    FILE_RECEIVED("Файл успешно получен: "),
    FILE_OVERWRITTEN("Файл успешно получен и перезаписан: "),

    EXIT("-exit"),
    FILE("-file "),

    DESKTOP("Desktop");

    private final String phrase;

    private Phrases(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}