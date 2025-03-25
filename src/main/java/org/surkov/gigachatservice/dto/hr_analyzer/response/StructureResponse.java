package org.surkov.gigachatservice.dto.hr_analyzer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO ответа для анализа структуры резюме (/chat/structure).
 * Показывает наличие основных разделов и общую оценку полноты структуры.
 */
public record StructureResponse(
        @Schema(description = "Наличие каждого из ключевых разделов резюме")
        Sections sections,

        @Schema(description = "Итоговая оценка полноты структуры резюме (0-100)")
        int score,

        @JsonProperty("missing_sections")
        @Schema(description = "Список отсутствующих разделов резюме")
        List<String> missingSections
) {
    /**
     * Вложенный DTO, содержащий булевы флаги для каждого ключевого раздела резюме.
     */
    public record Sections(
            @Schema(description = "Раздел 'Контакты' присутствует (true/false).")
            boolean contacts,

            @Schema(description = "Раздел 'Опыт работы' присутствует (true/false).")
            boolean experience,

            @Schema(description = "Раздел 'Образование' присутствует (true/false).")
            boolean education,

            @Schema(description = "Раздел 'Навыки' присутствует (true/false).")
            boolean skills,

            @Schema(description = "Раздел 'Курсы/Сертификаты' присутствует (true/false).")
            boolean courses,

            @JsonProperty("about_me")
            @Schema(description = "Раздел 'О себе' присутствует (true/false).")
            boolean aboutMe
    ) {
    }
}
