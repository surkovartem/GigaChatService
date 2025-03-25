package org.surkov.gigachatservice.repository.hr_analyzer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.surkov.gigachatservice.entity.hr_analyzer.History;

import java.time.Instant;
import java.util.List;

/**
 * Репозиторий для доступа к записям истории анализа резюме.
 * Предоставляет методы поиска с возможной фильтрацией по эндпоинту и диапазону времени.
 */
@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    /**
     * Находит последние 10 записей истории,
     * отсортированные по времени выполнения (новейшие первыми).
     */
    List<History> findTop10ByOrderByTimestampDesc();

    /**
     * Находит все записи по заданному эндпоинту,
     * отсортированные по времени (новейшие первыми).
     */
    List<History> findByEndpointOrderByTimestampDesc(String endpoint);

    /**
     * Находит все записи в заданном диапазоне времени,
     * отсортированные по времени (новейшие первыми).
     */
    List<History> findByTimestampBetweenOrderByTimestampDesc(Instant start, Instant end);

    /**
     * Находит все записи по эндпоинту и диапазону времени,
     * отсортированные по времени (новейшие первыми).
     */
    List<History> findByEndpointAndTimestampBetweenOrderByTimestampDesc(String endpoint, Instant start, Instant end);
}
