package com.cap.mapper;

import com.cap.pojo.Entry;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjx
 * @since 2021-01-08
 */
@Mapper
public interface EntryMapper extends BaseMapper<Entry> {

}
