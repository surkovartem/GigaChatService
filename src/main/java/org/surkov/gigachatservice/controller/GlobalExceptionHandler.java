package org.surkov.gigachatservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.surkov.gigachatservice.dto.hr_analyzer.response.ErrorResponse;
import org.surkov.gigachatservice.exception.ApiRequestException;
import org.surkov.gigachatservice.exception.AuthenticationException;

/**
 * Глобальный обработчик исключений для централизованной обработки ошибок.
 * Перехватывает исключения сервисного уровня и возвращает понятные JSON-ответы с кодами ошибок.
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Код ошибки для сбоев внешнего API.
     */
    private static final String API_ERROR_CODE = "API_ERROR";
    /**
     * Код ошибки для проблем аутентификации при обращении к внешнему API.
     */
    private static final String AUTH_ERROR_CODE = "AUTH_ERROR";
    /**
     * Код ошибки для внутренних непредвиденных исключений.
     */
    private static final String INTERNAL_ERROR_CODE = "INTERNAL_ERROR";

    /**
     * Обрабатывает исключения запросов к внешнему API GigaChat.
     * Возвращает ошибку 502 Bad Gateway с соответствующим сообщением.
     */
    @ExceptionHandler(ApiRequestException.class)
    public ResponseEntity<ErrorResponse> handleApiRequestException(ApiRequestException e) {
        log.error("Ошибка при запросе к внешнему API: {}", e.getMessage(), e);
        ErrorResponse error = new ErrorResponse(
                new ErrorResponse.ErrorDetail(
                        API_ERROR_CODE, "Ошибка при выполнении запроса к внешнему сервису GigaChat."
                )
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }

    /**
     * Обрабатывает ошибки аутентификации при работе с GigaChat API.
     * Возвращает ошибку 500 Internal Server Error с кодом AUTH_ERROR.
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
        log.error("Ошибка аутентификации GigaChat API: {}", e.getMessage(), e);
        ErrorResponse error = new ErrorResponse(
                new ErrorResponse.ErrorDetail(
                        AUTH_ERROR_CODE, "Не удалось аутентифицироваться в GigaChat API"
                )
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    /**
     * Обрабатывает прочие непредвиденные исключения.
     * Возвращает ошибку 500 Internal Server Error с кодом INTERNAL_ERROR.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        log.error("Внутренняя ошибка сервера: {}", e.getMessage(), e);
        ErrorResponse error = new ErrorResponse(
                new ErrorResponse.ErrorDetail(
                        INTERNAL_ERROR_CODE, "Unexpected server error. Please contact support."
                )
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
