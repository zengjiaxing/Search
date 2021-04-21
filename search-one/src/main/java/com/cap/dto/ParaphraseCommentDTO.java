package com.cap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParaphraseCommentDTO {
    private Long paraphraseCommentId;

    private Long userId;

    private String paraphraseComment;

    private String commentDate;

    private Integer likeNum;

    private Long paraphraseId;

    private String username;

    private Integer sign;
}
