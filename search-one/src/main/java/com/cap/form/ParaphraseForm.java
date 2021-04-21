package com.cap.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParaphraseForm {
    private Long infoId;

    private String paraphraseTitle;

    private String paraphraseContent;

    private Integer order;

}
