package org.surkov.gigachatservice.service.hr_analyzer;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.surkov.gigachatservice.dto.hr_analyzer.request.AnalyzeRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.ClarityRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.HighlightsRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.MatchRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.StabilityRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.request.StructureRequest;
import org.surkov.gigachatservice.dto.hr_analyzer.response.AnalyzeResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.ClarityResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.HighlightsResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.MatchResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.StabilityResponse;
import org.surkov.gigachatservice.dto.hr_analyzer.response.StructureResponse;
import org.surkov.gigachatservice.utils.GigaModel;
import org.surkov.gigachatservice.utils.SystemPrompt;

/**
 * Сервисный слой, реализующий бизнес-логику анализа резюме.
 * Для каждого типа анализа формирует системный промпт, обращается к GigaChat API
 * и сохраняет результат в базу (история).
 *
 * @author surkov
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AnalyzeService {

    /**
     * Класс содержащий различные вспомогательные методы,
     * используемые в процессе анализа резюме.
     */
    private final AnalyzeUtils analyzeUtils;

    /**
     * Выполняет комплексный анализ резюме (эндпоинт /chat/analyze).
     *
     * @param request Запрос с текстом резюме и дополнительными параметрами.
     * @return Ответ с результатами комплексного анализа резюме.
     */
    public AnalyzeResponse analyzeComplex(AnalyzeRequest request) {
        String decodedTextResume = analyzeUtils.decodeBase64(request.text());
        String inputText = analyzeUtils.buildResumeInput(
                decodedTextResume,
                request.vacancy(),
                request.expectedPosition(),
                request.experienceLevel()
        );
        return analyzeUtils.processRequest(
                "/chat/analyze",
                inputText,
                request.language(),
                SystemPrompt.ANALYZE_PROMPT_EN,
                SystemPrompt.ANALYZE_PROMPT_RU,
                GigaModel.GIGA_MODEL_PRO,
                AnalyzeResponse.class
        );
    }

    /**
     * Выполняет анализ структуры резюме (эндпоинт /chat/structure).
     *
     * @param request Запрос с текстом резюме и языком.
     * @return Ответ с результатами анализа структуры резюме.
     */
    public StructureResponse analyzeStructure(StructureRequest request) {
        String decodedTextResume = analyzeUtils.decodeBase64(request.text());
        return analyzeUtils.processRequest(
                "/chat/structure",
                decodedTextResume,
                request.language(),
                SystemPrompt.STRUCTURE_PROMPT_EN,
                SystemPrompt.STRUCTURE_PROMPT_RU,
                GigaModel.GIGA_MODEL_LITE,
                StructureResponse.class);
    }

    /**
     * Выполняет анализ ясности изложения резюме (эндпоинт /chat/clarity).
     *
     * @param request Запрос с текстом резюме и языком.
     * @return Ответ с результатами анализа ясности изложения резюме.
     */
    public ClarityResponse analyzeClarity(ClarityRequest request) {
        String decodedTextResume = analyzeUtils.decodeBase64(request.text());
        return analyzeUtils.processRequest(
                "/chat/clarity",
                decodedTextResume,
                request.language(),
                SystemPrompt.CLARITY_PROMPT_EN,
                SystemPrompt.CLARITY_PROMPT_RU,
                GigaModel.GIGA_MODEL_PRO,
                ClarityResponse.class);
    }

    /**
     * Выполняет анализ стабильности карьерного пути (эндпоинт /chat/stability).
     *
     * @param request Запрос с текстом резюме и языком.
     * @return Ответ с результатами анализа стабильности карьерного пути.
     */
    public StabilityResponse analyzeStability(StabilityRequest request) {
        String decodedTextResume = analyzeUtils.decodeBase64(request.text());
        return analyzeUtils.processRequest(
                "/chat/stability",
                decodedTextResume,
                request.language(),
                SystemPrompt.STABILITY_PROMPT_RU,
                SystemPrompt.STABILITY_PROMPT_RU,
                GigaModel.GIGA_MODEL_LITE,
                StabilityResponse.class
        );
    }

    /**
     * Проверяет соответствие резюме заданной вакансии (эндпоинт /chat/match).
     *
     * @param request Запрос с текстом резюме и дополнительными параметрами.
     * @return Ответ с результатами проверки соответствия резюме вакансии.
     */
    public MatchResponse checkMatch(MatchRequest request) {
        String decodedTextResume = analyzeUtils.decodeBase64(request.text());
        String inputText = analyzeUtils.buildResumeInput(
                decodedTextResume,
                request.vacancy(),
                request.expectedPosition(),
                null
        );
        return analyzeUtils.processRequest(
                "/chat/match",
                inputText,
                request.language(),
                SystemPrompt.MATCH_PROMPT_EN,
                SystemPrompt.MATCH_PROMPT_RU,
                GigaModel.GIGA_MODEL_PRO,
                MatchResponse.class
        );
    }

    /**
     * Выделяет ключевые достижения из резюме (эндпоинт /chat/highlights).
     *
     * @param request Запрос с текстом резюме и языком.
     * @return Ответ с ключевыми достижениями из резюме.
     */
    public HighlightsResponse extractHighlights(HighlightsRequest request) {
        String decodedTextResume = analyzeUtils.decodeBase64(request.text());
        return analyzeUtils.processRequest(
                "/chat/highlights",
                decodedTextResume,
                request.language(),
                SystemPrompt.HIGHLIGHTS_PROMPT_EN,
                SystemPrompt.HIGHLIGHTS_PROMPT_RU,
                GigaModel.GIGA_MODEL_MAX,
                HighlightsResponse.class
        );
    }
}
