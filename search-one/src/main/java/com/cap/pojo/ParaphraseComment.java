package com.cap.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * <p>
 *
 * </p>
 *
 * @author zjx
 * @since 2021-03-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ParaphraseComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "paraphrase_comment_id", type = IdType.AUTO)
    private Long paraphraseCommentId;

    private Long userId;

    private String paraphraseComment;

    private Date commentDate;

    private Integer likeNum;

    private Long paraphraseId;

}
