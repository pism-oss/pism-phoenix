package cn.com.pism.phoenix.core.mapper;

import cn.com.pism.mybatis.core.mapper.ComMapper;
import cn.com.pism.phoenix.core.entity.PmnxUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author perccyking
 * @since 24-08-25 18:15
 */
@Mapper
public interface PmnxUserMapper extends ComMapper<PmnxUser> {
}