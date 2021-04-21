package com.cap.mapper;

import com.cap.pojo.Paraphrase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjx
 * @since 2021-03-05
 */
@Mapper
@Repository
public interface ParaphraseMapper extends BaseMapper<Paraphrase> {

}
