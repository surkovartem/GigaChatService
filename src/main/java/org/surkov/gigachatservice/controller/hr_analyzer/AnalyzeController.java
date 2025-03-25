package org.surkov.gigachatservice.controller.hr_analyzer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.surkov.gigachatservice.controller.hr_analyzer.api.AnalyzeApi;
import org.surkov.gigachatservice.dto.hr_analyzer.request.AnalyzeRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.AnalyzeResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.request.ClarityRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.ClarityResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.request.HighlightsRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.HighlightsResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.request.MatchRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.MatchResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.request.StabilityRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.StabilityResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.request.StructureRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.StructureResponse;
import org.surkov.gigachatservice.service.hr_analyzer.AnalyzeService;

/**
 * Основной контроллер для обработки REST-запросов анализа резюме.
 * Реализует все конечные точки, описанные в документации GigaChatService.
 * Валидация входных данных выполняется с помощью аннотаций Bean Validation.
 *
 * @author surkov
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/hr-analyzer/analyze")
@RequiredArgsConstructor
public class AnalyzeController implements AnalyzeApi {

    /**
     * Сервисный слой, выполняющий бизнес-логику анализа.
     */
    private final AnalyzeService analyzeService;

    /**
     * Комплексный анализ резюме.
     * Принимает резюме и опционально вакансию, возвращает агрегированные метрики качества резюме.
     *
     * @param request Объект {@link AnalyzeRequest}, содержащий данные для анализа.
     * @return Ответ {@link AnalyzeResponse}, содержащий результаты комплексного анализа.
     */
    @Override
    public ResponseEntity<AnalyzeResponse> analyze(final AnalyzeRequest request) {
        AnalyzeResponse result = analyzeService.analyzeComplex(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Анализ структуры резюме.
     * Проверяет наличие всех ключевых разделов в тексте резюме.
     *
     * @param request Объект {@link StructureRequest}, содержащий данные для анализа структуры.
     * @return Ответ {@link StructureResponse}, содержащий результаты анализа структуры резюме.
     */
    @Override
    public ResponseEntity<StructureResponse> structure(final StructureRequest request) {
        StructureResponse result = analyzeService.analyzeStructure(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Анализ ясности изложения.
     * Находит нечеткие, клишированные фразы и дает рекомендации по их улучшению.
     *
     * @param request Объект {@link ClarityRequest}, содержащий данные для анализа ясности изложения.
     * @return Ответ {@link ClarityResponse}, содержащий результаты анализа ясности изложения.
     */
    @Override
    public ResponseEntity<ClarityResponse> clarity(final ClarityRequest request) {
        ClarityResponse result = analyzeService.analyzeClarity(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Анализ стабильности карьерного пути.
     * Оценивает хронологию карьеры и выявляет потенциальные риски в стабильности.
     *
     * @param request Объект {@link StabilityRequest}, содержащий данные для анализа стабильности карьерного пути.
     * @return Ответ {@link StabilityResponse}, содержащий результаты анализа стабильности карьерного пути.
     */
    @Override
    public ResponseEntity<StabilityResponse> stability(final StabilityRequest request) {
        StabilityResponse result = analyzeService.analyzeStability(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Проверка соответствия вакансии.
     * Сравнивает резюме с описанием вакансии или желаемой должностью.
     *
     * @param request Объект {@link MatchRequest}, содержащий данные для проверки соответствия вакансии.
     * @return Ответ {@link MatchResponse}, содержащий результаты проверки соответствия вакансии.
     */
    @Override
    public ResponseEntity<MatchResponse> match(final MatchRequest request) {
        MatchResponse result = analyzeService.checkMatch(request);
        return ResponseEntity.ok(result);
    }

    /**
     * Выявление ключевых достижений.
     * Извлекает из резюме 2-5 главных достижений кандидата.
     *
     * @param request Объект {@link HighlightsRequest}, содержащий данные для выявления ключевых достижений.
     * @return Ответ {@link HighlightsResponse}, содержащий ключевые достижения кандидата.
     */
    @Override
    public ResponseEntity<HighlightsResponse> highlights(final HighlightsRequest request) {
        HighlightsResponse result = analyzeService.extractHighlights(request);
        return ResponseEntity.ok(result);
    }
}
