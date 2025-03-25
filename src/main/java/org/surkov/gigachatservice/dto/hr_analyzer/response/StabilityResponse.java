package org.surkov.gigachatservice.dto.hr_analyzer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO ответа для анализа стабильности карьерного пути (/chat/stability).
 * Содержит реконструированную карьерную хронологию и оценку стабильности карьеры.
 */
public record StabilityResponse(
        @JsonProperty("career_path")
        @Schema(description = "Хронология карьерного пути (год и должность на тот момент)")
        List<CareerStep> careerPath,

        @JsonProperty("stability_score")
        @Schema(description = "Оценка стабильности карьерного пути (0-100)")
        int stabilityScore,

        @Schema(description = "Список предупреждений о рисках в карьере (например, частые смены работы)")
        List<String> warnings
) {
    /**
     * Вложенный DTO, представляющий один этап карьеры (год и должность).
     */
    public record CareerStep(
            @Schema(description = "Год соответствующего этапа карьеры.")
            int year,

            @Schema(description = "Должность, занимаемая в указанном году.")
            String position
    ) {
    }
}
