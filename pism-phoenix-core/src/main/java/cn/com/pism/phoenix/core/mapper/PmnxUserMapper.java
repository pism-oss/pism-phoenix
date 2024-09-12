package cn.com.pism.phoenix.core.mapper;

import cn.com.pism.mybatis.core.mapper.ComMapper;
import cn.com.pism.phoenix.core.entity.PmnxUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author perccyking
 * @since 24-08-25 18:15
 */
@Mapper
public interface PmnxUserMapper extends ComMapper<PmnxUser> {

    /**
     * <p>
     * 通过账号获取密码
     * </p>
     * by perccyking
     *
     * @param account : 账号
     * @return {@link String} 密码
     * @since 24-09-13 00:35
     */
    String getPasswordByAccount(@Param("account") String account);
}