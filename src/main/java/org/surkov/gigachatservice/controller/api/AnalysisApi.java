package org.surkov.gigachatservice.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.surkov.gigachatservice.dto.ResumeAnalysisRequest;

/**
 * Интерфейс, определяющий API для анализа резюме.
 * Содержит Swagger-аннотации для автоматической генерации документации.
 */
public interface AnalysisApi {

    /**
     * Сообщение об успешном анализе резюме.
     */
    String RESUME_ANALYZED_SUCCESSFULLY = "Резюме успешно проанализировано";

    /**
     * Сообщение о неподдерживаемом типе файла или промпта.
     */
    String UNSUPPORTED_FILE_OR_PROMPT_TYPE =
            "Неподдерживаемый тип файла или неподдерживаемый тип промпта";

    /**
     * Сообщение о внутренней ошибке сервера.
     */
    String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера";

    @Operation(
            summary = "Анализ резюме",
            description = "Загрузите файл резюме для анализа."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = RESUME_ANALYZED_SUCCESSFULLY,
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class))
                    }
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = UNSUPPORTED_FILE_OR_PROMPT_TYPE,
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = INTERNAL_SERVER_ERROR,
                    content = @Content
            )}
    )
    @PostMapping(
            value = "/resume",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    ResponseEntity<String> analyzeResume(
            @RequestBody ResumeAnalysisRequest request
    );
}
