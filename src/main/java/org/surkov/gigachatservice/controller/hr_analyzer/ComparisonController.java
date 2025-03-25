package org.surkov.gigachatservice.controller.hr_analyzer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.surkov.gigachatservice.controller.hr_analyzer.api.ComparisonApi;
import org.surkov.gigachatservice.dto.hr_analyzer.response.CompareRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.CompareResponse;
import org.surkov.gigachatservice.service.hr_analyzer.ComparisonService;

/**
 * Контроллер для обработки запросов сравнения резюме кандидатов.
 * Предоставляет API для выполнения сравнений между двумя кандидатами.
 * Валидация входных данных осуществляется с использованием аннотаций Bean Validation.
 *
 * @author surkov
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/hr-analyzer/comparison")
@RequiredArgsConstructor
public class ComparisonController implements ComparisonApi {

    /**
     * Сервис для сравнения двух резюме кандидатов.
     * Выполняет бизнес-логику сопоставления резюме.
     */
    private final ComparisonService comparisonService;

    /**
     * Сравнение двух резюме.
     * Сравнивает профили двух кандидатов и определяет, кто более предпочтителен.
     *
     * @param request Объект {@link CompareRequest}, содержащий идентификаторы двух резюме для сравнения.
     * @return Ответ {@link CompareResponse}, содержащий результат сравнения двух резюме.
     */
    @Override
    public ResponseEntity<CompareResponse> compare(final CompareRequest request) {
        CompareResponse result = comparisonService.compareCandidates(request);
        return ResponseEntity.ok(result);
    }
}
