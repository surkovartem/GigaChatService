package org.surkov.gigachatservice.controller.hr_analyzer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.surkov.gigachatservice.controller.hr_analyzer.api.InsightsApi;
import org.surkov.gigachatservice.dto.hr_analyzer.request.InsightsRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.InsightsResponse;
import org.surkov.gigachatservice.service.hr_analyzer.InsightsService;

/**
 * Контроллер для обработки запросов получения аналитических рекомендаций.
 * Предоставляет API для формирования инсайтов на основе анализа резюме.
 * Валидация входных данных осуществляется с использованием аннотаций Bean Validation.
 *
 * @author surkov
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/hr-analyzer/insight")
@RequiredArgsConstructor
public class InsightsController implements InsightsApi {

    /**
     * Сервис для формирования аналитических рекомендаций.
     * Генерирует инсайты на основе результатов анализа резюме.
     */
    private final InsightsService insightsService;

    /**
     * Метод для получения аналитических рекомендаций.
     * Формирует и возвращает аналитические выводы на основе переданного запроса.
     *
     * @param request Объект {@link InsightsRequest}, содержащий необходимые данные для генерации инсайтов.
     * @return Ответ {@link ResponseEntity}, содержащий объект {@link InsightsResponse} с результатами анализа.
     */
    @Override
    public ResponseEntity<InsightsResponse> getInsights(InsightsRequest request) {
        InsightsResponse response = insightsService.generateInsights(request);
        return ResponseEntity.ok(response);
    }
}
