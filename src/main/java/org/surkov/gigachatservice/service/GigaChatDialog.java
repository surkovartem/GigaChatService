package org.surkov.gigachatservice.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.surkov.gigachatservice.utils.TokenManager;
import org.surkov.gigachatservice.config.GigaChatConfig;
import org.surkov.gigachatservice.utils.GigaChatApiClient;

import java.io.IOException;

/**
 * Фасад для взаимодействия с GigaChat API.
 * Отвечает за инициализацию компонента,
 * валидацию конфигурации и выполнение запросов к API.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GigaChatDialog {

    /**
     * Конфигурация GigaChat API, содержащая URL и другие параметры.
     */
    private final GigaChatConfig config;

    /**
     * Менеджер токенов, предоставляющий токен доступа для аутентификации в API.
     */
    private final TokenManager tokenManager;

    /**
     * Клиент для выполнения запросов к GigaChat API.
     */
    private final GigaChatApiClient apiClient;

    private final ObjectMapper objectMapper;

    /**
     * Инициализирует компонент.
     * Проверяет конфигурацию и получает начальный токен доступа.
     *
     * @throws IllegalStateException конфигурация некорректна
     *                               или не удалось получить токен доступа
     */
    @PostConstruct
    public void init() {
        validateConfig();
        try {
            tokenManager.fetchAccessToken();
        } catch (Exception e) {
            log.error("Не удалось инициализировать GigaChatDialog", e);
            throw new IllegalStateException("Не удалось инициализировать GigaChatDialog", e);
        }
    }

    /**
     * Проверяет корректность конфигурации.
     *
     * @throws IllegalStateException URL аутентификации не использует HTTPS
     */
    private void validateConfig() {
        if (!config.getAuthUrl().startsWith("https")) {
            throw new IllegalStateException("AUTH_URL должен использовать HTTPS");
        }
    }

    /**
     * Выполняет запрос к GigaChat API для получения ответа на основе переданных данных.
     *
     * @param systemPrompt Системный промпт, задающий контекст для анализа.
     * @param text         Текст резюме для анализа.
     * @param model        Модель для анализа резюме.
     * @return Ответ от API в виде строки.
     */
    public String getResponse(String systemPrompt, String text, String model) {
        String response = apiClient.getResponse(systemPrompt, text, model);
        log.info("Raw response: {}", response);

        if (response.startsWith("{")) { // Проверяем, начинается ли ответ с '{'
            // Вероятно, это JSON. Пытаемся парсить.
            try {
                JsonNode rootNode = objectMapper.readTree(response); // objectMapper снова нужен
                return rootNode.path("choices").get(0).path("message").path("content").asText();
            } catch (IOException e) {
                log.error("Error parsing JSON response: {}", e.getMessage(), e);
                // Обработка ошибки парсинга JSON. Можно вернуть весь response,
                //  можно выбросить исключение - зависит от требований.
                return response; // Or throw an exception
            }
        } else {
            return response;
        }
    }
}
