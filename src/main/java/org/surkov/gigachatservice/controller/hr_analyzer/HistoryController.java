package org.surkov.gigachatservice.controller.hr_analyzer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.surkov.gigachatservice.controller.hr_analyzer.api.HistoryApi;
import org.surkov.gigachatservice.dto.hr_analyzer.entry.HistoryEntryDto;
import org.surkov.gigachatservice.service.hr_analyzer.HistoryService;

/**
 * Контроллер для обработки запросов, связанных с историей анализа резюме.
 * Предоставляет методы для получения списка выполненных операций анализа резюме.
 * Валидация входных данных осуществляется с использованием аннотаций Bean Validation.
 *
 * @author surkov
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/api/hr-analyzer/history")
@RequiredArgsConstructor
public class HistoryController implements HistoryApi {

    /**
     * Сервис для работы с историей запросов анализа резюме.
     * Обеспечивает взаимодействие с базой данных для хранения и извлечения истории анализа.
     */
    private final HistoryService historyService;

    /**
     * Получение истории выполненных анализов.
     * Возвращает список сохраненных записей анализа (с возможностью фильтрации по дате и типу операции).
     *
     * @param endpoint Название конечной точки, для которой запрашивается история (например, 'analyze', 'structure' и т.п.).
     * @param from     Начальная дата периода для фильтрации результатов (в формате ISO 8601).
     * @param to       Конечная дата периода для фильтрации результатов (в формате ISO 8601).
     * @return Ответ {@link ResponseEntity}, содержащий список объектов {@link HistoryEntryDto},
     * представляющих записи истории анализа резюме.
     */
    @Override
    public ResponseEntity<Iterable<HistoryEntryDto>> history(
            final String endpoint,
            final String from,
            final String to) {
        Iterable<HistoryEntryDto> historyList = historyService.getHistory(endpoint, from, to);
        return ResponseEntity.ok(historyList);
    }
}
