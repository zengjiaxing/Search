package com.cap.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

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
public class InfoContent implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "info_content_id", type = IdType.AUTO)
    private Long infoContentId;

    private Long infoId;

    private Long userId;

    private String content;

    private Integer likeNum;


}
