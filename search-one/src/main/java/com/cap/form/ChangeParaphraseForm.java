package com.cap.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeParaphraseForm {
    private Long paraphraseId;
    private String paraphraseContent;
}
