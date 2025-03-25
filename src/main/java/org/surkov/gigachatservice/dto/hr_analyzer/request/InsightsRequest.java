package org.surkov.gigachatservice.dto.hr_analyzer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO запроса для генерации инсайтов и рекомендаций по резюме (/chat/insights).
 */
public record InsightsRequest(
        @NotBlank
        @Schema(description = "Полный текст резюме", required = true)
        String text,

        @Schema(description = "Описание вакансии (опционально)")
        String vacancy,

        @Schema(description = "Целевая должность кандидата (опционально)")
        String expectedPosition,

        @Schema(description = "Язык результата анализа (опционально, по умолчанию ru)")
        String language
) {
}
