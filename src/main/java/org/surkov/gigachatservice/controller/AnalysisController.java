package org.surkov.gigachatservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.surkov.gigachatservice.GigaChatDialog;
import org.surkov.gigachatservice.controller.api.AnalysisApi;
import org.surkov.gigachatservice.dto.ResumeAnalysisRequest;
import org.surkov.gigachatservice.enumiration.GigaModelType;
import org.surkov.gigachatservice.enumiration.PromptType;
import org.surkov.gigachatservice.utils.GigaModel;
import org.surkov.gigachatservice.utils.SystemPrompt;

import java.nio.charset.StandardCharsets;

/**
 * Контроллер для обработки запросов, связанных с анализом резюме.
 * Реализует интерфейс {@link AnalysisApi}.
 */
@Slf4j
@RestController
@RequestMapping("/api/analysis")
@RequiredArgsConstructor
public class AnalysisController implements AnalysisApi {

    /**
     * Сервис для анализа резюме.
     */
    private final GigaChatDialog gigaChatDialog;

    /**
     * Анализ резюме.
     *
     * @param request запрос.
     * @return Текст анализа резюме.
     */
    @Override
    public ResponseEntity<String> analyzeResume(final ResumeAnalysisRequest request) {
        try {
            // Декодируем Base64
            String decodedText = new String(
                    java.util.Base64.getDecoder().decode(request.getResumeText()),
                    StandardCharsets.UTF_8
            );

            if (decodedText.isEmpty()) {
                return ResponseEntity.badRequest().body("Текст резюме не должен быть пустым.");
            }

            String analysisResult = gigaChatDialog.getResponse(
                    getSystemPrompt(request.getPromptType()),
                    decodedText,
                    getGigaModel(request.getModelType())
            );
            return ResponseEntity.ok(analysisResult);
        } catch (Exception e) {
            log.error("Ошибка при обработке запроса: {}", e.getMessage());
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    /**
     * Возвращает системный промпт на основе указанного типа.
     *
     * @param promptType Тип системного промпта для анализа резюме.
     * @return строка с системным промптом
     * @throws IllegalArgumentException тип промпта не поддерживается
     */
    private String getSystemPrompt(final PromptType promptType) {
        return switch (promptType) {
            case BASE_ANALYSIS -> SystemPrompt.BASE_ANALYSIS_PROMPT;
            case JUNIOR_ANALYSIS -> SystemPrompt.JUNIOR_ANALYSIS_PROMPT;
            case MIDDLE_ANALYSIS -> SystemPrompt.MIDDLE_ANALYSIS_PROMPT;
            case SENIOR_ANALYSIS -> SystemPrompt.SENIOR_ANALYSIS_PROMPT;
        };
    }

    /**
     * Возвращает модель GigaChat на основе указанного типа.
     *
     * @param modelType Модель для анализа.
     * @return строка с наименованием модели
     * @throws IllegalArgumentException тип модели не поддерживается
     */
    private String getGigaModel(final GigaModelType modelType) {
        return switch (modelType) {
            case GIGA_MODEL_LITE -> GigaModel.GIGA_MODEL_LITE;
            case GIGA_MODEL_PRO -> GigaModel.GIGA_MODEL_PRO;
            case GIGA_MODEL_MAX -> GigaModel.GIGA_MODEL_MAX;
        };
    }
}
