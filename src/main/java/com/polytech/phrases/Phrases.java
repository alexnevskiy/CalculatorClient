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
    SEND_MESSAGE("Сообщение отправлено. Его идентификационный номер: "),
    RECEIVED_MESSAGE("Получено сообщение. Его id: "),

    OPERATION_ERROR("Отправленная операция не поддерживается сервером."),
    ZERO_DIVISION_ERROR("Вторым аргументом был 0, а на него делить нельзя."),
    INVALID_ARG_ERROR("Некорректно введённый аргумент(-ы)."),
    IDENTIFIER_REPEAT_ERROR("Ранее указанный идентификатор уже используется в другой операции."),
    OUT_OF_BOUNDS_ERROR("Полученный ответ превышает максимальный размер."),
    TIME_OUT_ERROR("Сервер не успел рассчитать ответ за отведённое время."),
    GROVE_STREET_FAMILIES_ERROR("Теперь Вы часть Grove street family. Вы только что поделили 1992 на 4."),

    ARGUMENTS_ERROR("Неправильно введённые аргументы. Через пробел введите адрес сервера и его порт."),
    SERVER_ERROR("Такого сервера или порта не существует."),

    EXIT("-exit");

    private final String phrase;

    private Phrases(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}