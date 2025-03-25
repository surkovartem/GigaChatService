package org.surkov.gigachatservice.service.hr_analyzer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.surkov.gigachatservice.service.GigaChatDialog;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Класс содержащий различные вспомогательные методы, используемые в процессе анализа резюме.
 * Методы этого класса помогают обрабатывать входные данные, формировать запросы и преобразовывать результаты.
 *
 * @author surkov
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AnalyzeUtils {

    /**
     * Фасад для взаимодействия с GigaChat API (выполнение запросов к модели).
     */
    private final GigaChatDialog gigaChatDialog;
    /**
     * ObjectMapper для обработки JSON (парсинг ответов и формирования истории).
     */
    private final ObjectMapper objectMapper;
    /**
     * Сервис для работы с историей запросов анализа резюме.
     */
    private final HistoryService historyService;

    /**
     * Формирует текст для ввода резюме, комбинируя основной текст резюме с дополнительной информацией о вакансии,
     * ожидаемой должности и уровне кандидата, если таковые предоставлены.
     *
     * @param resumeText       основной текст резюме, обязательный параметр.
     * @param vacancy          текст вакансии, может быть {@code null} или пустой строкой.
     * @param expectedPosition ожидаемая должность, используется, если {@code vacancy} не задан.
     * @param experienceLevel  уровень кандидата, может быть {@code null} или пустой строкой.
     * @return сформированная строка для ввода, содержащая текст резюме с дополнительными данными.
     */
    public String buildResumeInput(
            String resumeText,
            String vacancy,
            String expectedPosition,
            String experienceLevel
    ) {
        StringBuilder inputText = new StringBuilder("Текст резюме:\n" + resumeText);
        if (vacancy != null && !vacancy.isBlank()) {
            inputText.append("\nОписание вакансии:\n").append(vacancy);
        } else if (expectedPosition != null && !expectedPosition.isBlank()) {
            inputText.append("\nОжидаемая должность кандидата:\n").append(expectedPosition);
        }
        if (experienceLevel != null && !experienceLevel.isBlank()) {
            inputText.append("\nУровень кандидата: ").append(experienceLevel);
        }
        return inputText.toString();
    }

    /**
     * Формирует текст для сравнения двух резюме, комбинируя тексты обоих резюме и дополнительную информацию о вакансии.
     *
     * @param text1            Текст резюме кандидата A.
     * @param text2            Текст резюме кандидата B.
     * @param vacancy          Описание вакансии (если доступно).
     * @param expectedPosition Целевая должность кандидата (если доступна).
     * @return Входной текст для отправки в GigaChat API.
     */
    public String buildComparisonInput(
            String text1,
            String text2,
            String vacancy,
            String expectedPosition
    ) {
        StringBuilder inputText = new StringBuilder("Резюме кандидата A:\n");
        inputText
                .append(text1)
                .append("\nРезюме кандидата B:\n")
                .append(text2);

        if (vacancy != null && !vacancy.isBlank()) {
            inputText.append("\nОписание вакансии:\n").append(vacancy);
        } else if (expectedPosition != null && !expectedPosition.isBlank()) {
            inputText.append("\nЦелевая должность для сравнения:\n").append(expectedPosition);
        }

        return inputText.toString();
    }

    /**
     * Формирует текст для генерации инсайтов и рекомендаций на основе резюме.
     *
     * @param text             Основной текст резюме.
     * @param vacancy          Описание вакансии (если доступно).
     * @param expectedPosition Целевая должность кандидата (если доступна).
     * @return Входной текст для отправки в GigaChat API.
     */
    public String buildInsightInput(
            String text,
            String vacancy,
            String expectedPosition
    ) {
        StringBuilder inputText = new StringBuilder("Текст резюме:\n");
        inputText.append(text);

        if (vacancy != null && !vacancy.isBlank()) {
            inputText.append("\nОписание вакансии:\n").append(vacancy);
        } else if (expectedPosition != null && !expectedPosition.isBlank()) {
            inputText.append("\nЦелевая должность кандидата:\n").append(expectedPosition);
        }

        return inputText.toString();
    }

    /**
     * Универсальный метод обработки запроса к GigaChat API.
     *
     * @param endpoint     URL эндпоинта, для которого выполняется запрос.
     * @param text         Текст запроса, который будет отправлен в GigaChat API.
     * @param language     Код языка запроса. Если значение равно "en" (без учета регистра), используется {@code promptEn}, иначе — {@code promptRu}.
     * @param promptEn     Системный промпт для английской версии запроса.
     * @param promptRu     Системный промпт для русской версии запроса.
     * @param model        Модель GigaChat, которая будет использоваться для обработки запроса.
     * @param responseType Класс, в который будет десериализован JSON-ответ.
     * @return Объект типа {@code T}, полученный в результате десериализации JSON-ответа от GigaChat API.
     * @throws RuntimeException если происходит ошибка при десериализации JSON-ответа.
     */
    public <T> T processRequest(
            String endpoint,
            String text,
            String language,
            String promptEn,
            String promptRu,
            String model,
            Class<T> responseType
    ) {
        String systemPrompt = "en".equalsIgnoreCase(language) ? promptEn : promptRu;
        String jsonResponse = gigaChatDialog.getResponse(systemPrompt, text, model);
        T result = readJson(jsonResponse, responseType);
        historyService.saveHistory(endpoint, text, null, jsonResponse);
        return result;
    }

    /**
     * Декодирует строку, закодированную в Base64,
     * в обычный текст с использованием кодировки UTF-8.
     *
     * @param base64Text строка в формате Base64, которую необходимо декодировать.
     * @return декодированная строка в кодировке UTF-8.
     */
    public String decodeBase64(String base64Text) {
        return new String(Base64.getDecoder().decode(base64Text), StandardCharsets.UTF_8);
    }

    /**
     * Вспомогательный метод для десериализации JSON-строки в указанный класс DTO.
     *
     * @param json  JSON-строка.
     * @param clazz Класс, в объект которого нужно десериализовать JSON.
     * @param <T>   Тип возвращаемого DTO.
     * @return Объект типа T, полученный из JSON.
     */
    public <T> T readJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            log.error("Ошибка парсинга ответа JSON в {}: {}", clazz.getSimpleName(), e.getMessage());
            throw new RuntimeException("Failed to parse JSON response", e);
        }
    }
}
