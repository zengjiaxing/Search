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
public class Entry implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "entry_id", type = IdType.AUTO)
    private Integer entryId;

    private Integer infoId;


}
