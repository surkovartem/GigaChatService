package org.surkov.gigachatservice.dto.hr_analyzer.response;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO для представления ошибки в ответе API.
 * Содержит один объект error с кодом и сообщением об ошибке.
 */
public record ErrorResponse(
        @Schema(description = "Объект с кодом и описанием ошибки")
        ErrorDetail error
) {
    /**
     * DTO с деталями ошибки: код и текстовое сообщение.
     */
    public record ErrorDetail(
            @Schema(description = "Код ошибки")
            String code,

            @Schema(description = "Сообщение об ошибке")
            String message
    ) {
    }
}
