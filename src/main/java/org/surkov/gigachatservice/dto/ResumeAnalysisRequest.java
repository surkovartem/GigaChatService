package org.surkov.gigachatservice.dto;

import lombok.Data;
import org.surkov.gigachatservice.enumiration.GigaModelType;
import org.surkov.gigachatservice.enumiration.PromptType;

@Data
public class ResumeAnalysisRequest {
    private PromptType promptType;
    private GigaModelType modelType;
    private String resumeText;
}