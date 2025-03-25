package org.surkov.gigachatservice.dto.hr_analyzer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO ответа для инсайтов и рекомендаций по резюме (/chat/insights).
 * Содержит сильные и слабые стороны кандидата, рекомендации и сводный вывод.
 */
public record InsightsResponse(
        @Schema(description = "Ключевые сильные стороны кандидата")
        List<String> strengths,

        @Schema(description = "Слабые стороны или пробелы кандидата")
        List<String> weaknesses,

        @Schema(description = "Рекомендации для улучшения профиля кандидата")
        List<String> recommendations,

        @JsonProperty("insight_summary")
        @Schema(description = "Связное резюме (insight) о кандидате и общие выводы")
        String insightSummary
) {
}
