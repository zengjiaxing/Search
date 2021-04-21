package com.cap.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParaphraseCommentLikeForm {
    private Long userId;
    private Long paraphrase_comment_id;
    private Integer sign;
}
