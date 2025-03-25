package org.surkov.gigachatservice.dto.hr_analyzer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO ответа для комплексного анализа резюме (/chat/analyze).
 * Содержит агрегированные оценки и выводы по резюме.
 */
public record AnalyzeResponse(
        @JsonProperty("structure_score")
        @Schema(description = "Оценка полноты структуры резюме (0-100)")
        int structureScore,

        @JsonProperty("clarity_score")
        @Schema(description = "Оценка ясности изложения в резюме (0-100)")
        int clarityScore,

        @JsonProperty("stability_score")
        @Schema(description = "Оценка стабильности карьеры (0-100)")
        int stabilityScore,

        @JsonProperty("match_score")
        @Schema(description = "Оценка соответствия вакансии (0-100, или null если вакансия не задана)")
        Integer matchScore,

        @JsonProperty("key_achievements")
        @Schema(description = "Список ключевых достижений кандидата")
        List<String> keyAchievements,

        @JsonProperty("missing_sections")
        @Schema(description = "Список идентификаторов разделов, отсутствующих в резюме")
        List<String> missingSections,

        @JsonProperty("risk_factors")
        @Schema(description = "Список рисков, обнаруженных в резюме (например, пробелы в стаже)")
        List<String> riskFactors,

        @Schema(description = "Краткое заключение о качестве резюме с рекомендациями")
        String summary
) {
}
