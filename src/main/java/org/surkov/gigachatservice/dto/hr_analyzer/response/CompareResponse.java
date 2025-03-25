package org.surkov.gigachatservice.dto.hr_analyzer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * DTO ответа для сравнения двух резюме (/chat/compare).
 * Содержит сравнительные оценки по каждому критерию и сильные стороны каждого кандидата.
 */
public record CompareResponse(
        @JsonProperty("candidate_A_vs_B")
        @Schema(description = "Помесячно: кто лучше по структуре, ясности, стабильности, соответствию (A, B или equal)")
        CandidateComparison candidateComparison,

        @JsonProperty("strengths_A")
        @Schema(description = "Уникальные сильные стороны кандидата A")
        List<String> strengthsA,

        @JsonProperty("strengths_B")
        @Schema(description = "Уникальные сильные стороны кандидата B")
        List<String> strengthsB,

        @JsonProperty("comparison_summary")
        @Schema(description = "Краткое заключение, кто из кандидатов предпочтительнее и почему")
        String comparisonSummary
) {
    /**
     * Вложенный DTO для сравнительной оценки кандидатов A и B по критериям.
     */
    public record CandidateComparison(
            @Schema(description = "По какому кандидату резюме лучше структурировано ('A', 'B' или 'equal')")
            String structure,

            @Schema(description = "У кого резюме более четкое по изложению ('A', 'B' или 'equal')")
            String clarity,

            @Schema(description = "Чья карьера стабильнее ('A', 'B' или 'equal')")
            String stability,

            @Schema(description = "Кто лучше соответствует вакансии ('A', 'B' или 'equal')")
            String match
    ) {
    }
}
