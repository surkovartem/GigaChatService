package org.surkov.gigachatservice.dto.hr_analyzer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO ответа для проверки соответствия вакансии (/chat/match).
 * Показывает процент соответствия и списки недостающих и дополнительных навыков.
 */
public record MatchResponse(
        @JsonProperty("match_score")
        @Schema(description = "Процент соответствия резюме требованиям вакансии (0-100)")
        int matchScore,

        @JsonProperty("missing_skills")
        @Schema(description = "Список навыков/требований, отсутствующих в резюме")
        List<String> missingSkills,

        @JsonProperty("extra_skills")
        @Schema(description = "Список навыков кандидата, не требуемых вакансией")
        List<String> extraSkills
) {
}
