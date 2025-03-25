package org.surkov.gigachatservice.service.hr_analyzer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.surkov.gigachatservice.dto.hr_analyzer.entry.HistoryEntryDto;
import org.surkov.gigachatservice.entity.hr_analyzer.History;
import org.surkov.gigachatservice.repository.hr_analyzer.HistoryRepository;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Сервис для работы с историей запросов анализа резюме.
 * Обеспечивает получение, сохранение и формирование предпросмотра записей истории.
 *
 * @author surkov
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class HistoryService {

    /**
     * Репозиторий для сохранения и чтения истории анализов.
     */
    private final HistoryRepository historyRepository;
    /**
     * ObjectMapper для обработки JSON (парсинг ответов и формирования истории).
     */
    private final ObjectMapper objectMapper;

    /**
     * Сохраняет запись истории в базу данных.
     *
     * @param endpoint     эндпоинт запроса (напр. "/chat/structure").
     * @param resumeText   текст резюме (для превью).
     * @param vacancyText  текст вакансии или должности (для превью, может быть null).
     * @param responseJson JSON ответа анализа.
     */
    public void saveHistory(
            String endpoint,
            String resumeText,
            String vacancyText,
            String responseJson
    ) {
        History history = new History();
        history.setEndpoint(endpoint);
        history.setResumePreview(makePreview(resumeText));
        history.setVacancyPreview(vacancyText != null ? makePreview(vacancyText) : null);
        history.setTimestamp(Instant.now());
        history.setResponseJson(responseJson);
        historyRepository.save(history);
    }

    /**
     * Возвращает историю запросов анализа резюме, с учетом фильтрации по эндпоинту и времени.
     *
     * @param endpoint фильтр по типу анализа (эндпоинту) или null.
     * @param from     начальная граница времени (ISO-строка) или null.
     * @param to       конечная граница времени (ISO-строка) или null.
     * @return список записей истории (в виде DTO) удовлетворяющих фильтру.
     */
    public List<HistoryEntryDto> getHistory(
            String endpoint,
            String from,
            String to
    ) {
        Instant fromTime = null;
        Instant toTime = null;
        try {
            if (from != null) fromTime = Instant.parse(from);
            if (to != null) toTime = Instant.parse(to);
        } catch (Exception e) {
            log.warn("Неверный формат параметров from/to: {}", e.getMessage());
            // Неверный формат дат - игнорируем фильтр по времени
            fromTime = null;
            toTime = null;
        }

        List<History> records;
        if (endpoint != null && !endpoint.isBlank() && fromTime != null && toTime != null) {
            records = historyRepository.findByEndpointAndTimestampBetweenOrderByTimestampDesc(endpoint, fromTime, toTime);
        } else if (endpoint != null && !endpoint.isBlank()) {
            records = historyRepository.findByEndpointOrderByTimestampDesc(endpoint);
        } else if (fromTime != null && toTime != null) {
            records = historyRepository.findByTimestampBetweenOrderByTimestampDesc(fromTime, toTime);
        } else {
            // Если фильтры не заданы - возвращаем последние N записей (например, 10)
            records = historyRepository.findTop10ByOrderByTimestampDesc();
        }

        // Конвертируем сущности History в DTO HistoryEntryDto
        return records.stream().map(history -> {
            // Парсим сохраненный JSON ответа обратно в объект (Map) для вывода
            Object responseObject;
            try {
                responseObject = objectMapper.readValue(history.getResponseJson(),
                        new TypeReference<LinkedHashMap<String, Object>>() {
                        });
            } catch (Exception e) {
                // В случае ошибки парсинга, возвращаем сырую строку
                responseObject = history.getResponseJson();
            }
            HistoryEntryDto.RequestInfo requestInfo = new HistoryEntryDto.RequestInfo(
                    history.getEndpoint(),
                    history.getResumePreview(),
                    history.getVacancyPreview(),
                    history.getTimestamp()
            );
            return new HistoryEntryDto(history.getId(), requestInfo, responseObject);
        }).toList();
    }

    /**
     * Формирует превью текста для сохранения в истории.
     * Ограничивает длину текста до 50 символов и добавляет многоточие, если текст длиннее.
     *
     * @param text Исходный текст для формирования предпросмотра.
     * @return Преобразованный текст длиной до 50 символов с добавлением многоточия, если текст длиннее.
     */
    private String makePreview(String text) {
        int previewLength = 50;
        String trimmed = text.trim();
        return trimmed.length() <= previewLength ? trimmed : trimmed.substring(0, previewLength) + "...";
    }
}