package org.surkov.gigachatservice.service.hr_analyzer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.surkov.gigachatservice.dto.hr_analyzer.response.CompareRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.CompareResponse;
import org.surkov.gigachatservice.utils.GigaModel;
import org.surkov.gigachatservice.utils.SystemPrompt;

/**
 * Сервис для сравнения двух резюме кандидатов.
 * Предоставляет возможность сравнить резюме по различным критериям и получить результаты сравнения.
 *
 * @author surkov
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ComparisonService {

    /**
     * Класс содержащий различные вспомогательные методы,
     * используемые в процессе анализа резюме.
     */
    private final AnalyzeUtils analyzeUtils;

    /**
     * Сравнивает два резюме кандидатов (эндпоинт /chat/compare).
     *
     * @param request Запрос с текстами двух резюме и дополнительными параметрами.
     * @return Ответ с результатами сравнения двух резюме.
     */
    public CompareResponse compareCandidates(CompareRequest request) {
        String decodedTextResumeA = analyzeUtils.decodeBase64(request.text1());
        String decodedTextResumeB = analyzeUtils.decodeBase64(request.text2());
        String inputText = analyzeUtils.buildComparisonInput(
                decodedTextResumeA,
                decodedTextResumeB,
                request.vacancy(),
                request.expectedPosition()
        );
        return analyzeUtils.processRequest(
                "/chat/compare",
                inputText,
                request.language(),
                SystemPrompt.COMPARE_PROMPT_EN,
                SystemPrompt.COMPARE_PROMPT_RU,
                GigaModel.GIGA_MODEL_MAX,
                CompareResponse.class
        );
    }
}
