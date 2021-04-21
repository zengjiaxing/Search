package com.cap.mapper;

import com.cap.pojo.Info;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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
public interface InfoMapper extends BaseMapper<Info> {
    @Options(useGeneratedKeys = true, keyProperty = "infoId", keyColumn = "info_id")
    @Insert("insert  into info (cn_name,en_name,slug,info_content_id) values (#{cnName},#{enName},#{slug},#{infoContentId})")
    int insert(Info info);
}
