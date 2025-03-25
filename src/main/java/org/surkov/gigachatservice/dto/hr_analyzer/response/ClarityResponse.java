package org.surkov.gigachatservice.dto.hr_analyzer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO ответа для анализа ясности изложения (/chat/clarity).
 * Содержит оценку "водянистости" текста и предложения по улучшению формулировок.
 */
public record ClarityResponse(
        @JsonProperty("clarity_score")
        @Schema(description = "Оценка четкости и конкретности резюме (0-100)")
        int clarityScore,

        @JsonProperty("found_phrases")
        @Schema(description = "Список обнаруженных шаблонных/нечетких фраз")
        List<String> foundPhrases,

        @Schema(description = "Предложения по улучшению для каждой найденной фразы")
        List<String> suggestions
) {
}
