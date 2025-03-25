package org.surkov.gigachatservice.entity.hr_analyzer;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;

/**
 * Сущность истории анализа резюме.
 * Хранит параметры запроса, тип эндпоинта, время и результат анализа в виде JSON.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "analysis_history")
public class History {

    /**
     * Уникальный идентификатор записи истории (генерируется автоматически).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Наименование эндпоинта (API метода), который был вызван (например, "/chat/structure").
     */
    private String endpoint;

    /**
     * Превью текста резюме (первые несколько символов, для удобства).
     */
    @Column(name = "resume_preview", length = 200)
    private String resumePreview;

    /**
     * Превью текста вакансии (если было предоставлено, первые несколько символов).
     */
    @Column(name = "vacancy_preview", length = 200)
    private String vacancyPreview;

    /**
     * Время выполнения запроса (в UTC).
     */
    private Instant timestamp;

    /**
     * JSON ответа анализа, сохраненный как текст.
     */
    @Lob
    @Column(name = "response_json")
    private String responseJson;
}
