package org.surkov.gigachatservice.dto.hr_analyzer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO запроса для проверки соответствия вакансии (/chat/match).
 * Требует указать текст вакансии и/или название должности для сравнения.
 */
public record MatchRequest(
        @NotBlank
        @Schema(description = "Полный текст резюме", required = true)
        String text,

        @Schema(description = "Полное описание вакансии (опционально)")
        String vacancy,

        @Schema(description = "Желаемая должность или роль (опционально)")
        String expectedPosition,

        @Schema(description = "Язык анализа соответствия (опционально, по умолчанию ru)")
        String language
) {
    @AssertTrue(message = "Должно быть указано хотя бы vacancy или expectedPosition")
    private boolean isVacancyOrPositionProvided() {
        return (vacancy != null && !vacancy.isBlank())
                || (expectedPosition != null && !expectedPosition.isBlank());
    }
}
