package org.surkov.gigachatservice.dto.hr_analyzer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO запроса для комплексного анализа резюме (/chat/analyze).
 */
public record AnalyzeRequest(
        @NotBlank
        @Schema(description = "Полный текст резюме кандидата", required = true)
        String text,

        @Schema(description = "Описание вакансии для сравнения (опционально)")
        String vacancy,

        @Schema(description = "Язык резюме и анализа (опционально, по умолчанию ru)")
        String language,

        @Schema(description = "Ожидаемая должность кандидата (опционально)")
        String expectedPosition,

        @Schema(description = "Предполагаемый уровень кандидата (опционально, напр. Senior)")
        String experienceLevel
) {
}