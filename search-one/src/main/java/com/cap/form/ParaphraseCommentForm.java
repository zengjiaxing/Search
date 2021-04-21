package com.cap.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParaphraseCommentForm {
    private Long userId;
    private String paraphraseComment;
    private Long paraphraseId;
}
