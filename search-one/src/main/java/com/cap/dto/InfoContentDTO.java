package com.cap.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
public class InfoContentDTO {
    private Long infoContentId;

    private Long infoId;

    private Long userId;

    private String content;

    private Integer likeNum;

    private Integer sign;
}
