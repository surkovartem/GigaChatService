package org.surkov.gigachatservice.service.hr_analyzer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.surkov.gigachatservice.dto.hr_analyzer.request.InsightsRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.InsightsResponse;
import org.surkov.gigachatservice.utils.GigaModel;
import org.surkov.gigachatservice.utils.SystemPrompt;

/**
 * Сервис для генерации аналитических рекомендаций и инсайтов на основе резюме.
 * Использует вспомогательные утилиты для декодирования и подготовки данных,
 * а также отправляет запросы к GigaChat API для получения итоговых рекомендаций.
 *
 * @author surkov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InsightsService {

    /**
     * Класс содержащий различные вспомогательные методы,
     * используемые в процессе анализа резюме.
     */
    private final AnalyzeUtils analyzeUtils;

    /**
     * Генерирует инсайты и рекомендации на основе резюме (эндпоинт /chat/insights).
     *
     * @param request Запрос с текстом резюме и дополнительными параметрами.
     * @return Ответ с рекомендациями и инсайтами на основе резюме.
     */
    public InsightsResponse generateInsights(InsightsRequest request) {
        String decodedTextResume = analyzeUtils.decodeBase64(request.text());
        String inputText = analyzeUtils.buildInsightInput(
                decodedTextResume,
                request.vacancy(),
                request.expectedPosition()
        );
        return analyzeUtils.processRequest(
                "/chat/insights",
                inputText,
                request.language(),
                SystemPrompt.INSIGHTS_PROMPT_EN,
                SystemPrompt.INSIGHTS_PROMPT_RU,
                GigaModel.GIGA_MODEL_PRO,
                InsightsResponse.class
        );
    }
}
