package org.surkov.gigachatservice.dto.hr_analyzer.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * DTO одной записи истории анализа.
 * Включает идентификатор, информацию о запросе (эндпоинт, превью текста, время)
 * и сохраненный результат (ответ) анализа.
 */
public record HistoryEntryDto(
        @Schema(description = "Уникальный ID записи истории")
        long id,
        @Schema(description = "Детали запроса анализа (эндпоинт, отрывки текста, время)")
        RequestInfo request,
        @Schema(description = "Результат анализа (JSON) как сохраненный ответ")
        Object response
) {
    /**
     * Вложенный класс с информацией о запросе, который был выполнен.
     */
    public record RequestInfo(
            @Schema(description = "Эндпоинт, который вызывался (например, '/chat/match')")
            String endpoint,
            @Schema(description = "Превью текста резюме (первые ~50 символов)")
            String textPreview,
            @Schema(description = "Превью текста вакансии (первые ~50 символов, если было)")
            String vacancyPreview,
            @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
            @Schema(description = "Время запроса в формате ISO 8601 (UTC)")
            Instant timestamp
    ) {
    }
}
