package com.cap.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author zjx
 * @since 2021-01-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Info implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "info_id", type = IdType.AUTO)
    private Integer infoId;

    private String esId;

    private String cnName;

    private String enName;

    private String slug;

    private String content;


}
