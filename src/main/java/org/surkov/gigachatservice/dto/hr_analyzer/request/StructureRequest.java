package org.surkov.gigachatservice.dto.hr_analyzer.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO запроса для анализа структуры резюме (/chat/structure).
 */
public record StructureRequest(
        @NotBlank
        @Schema(description = "Полный текст резюме", required = true)
        String text,

        @Schema(description = "Язык резюме (опционально, по умолчанию ru)")
        String language
) {
}
