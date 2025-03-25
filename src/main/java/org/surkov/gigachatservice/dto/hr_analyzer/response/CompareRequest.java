package org.surkov.gigachatservice.dto.hr_analyzer.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO запроса для сравнения двух резюме (/chat/compare).
 */
public record CompareRequest(
        @NotBlank
        @Schema(description = "Текст резюме кандидата A", required = true)
        String text1,

        @NotBlank
        @Schema(description = "Текст резюме кандидата B", required = true)
        String text2,

        @Schema(description = "Описание вакансии для сравнения (опционально)")
        String vacancy,

        @Schema(description = "Целевая должность для сравнения (опционально)")
        String expectedPosition,

        @Schema(description = "Язык анализа сравнения (опционально, по умолчанию ru)")
        String language
) {
}
