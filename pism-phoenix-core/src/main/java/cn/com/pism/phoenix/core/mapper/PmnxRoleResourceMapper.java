package cn.com.pism.phoenix.core.mapper;

import cn.com.pism.mybatis.core.mapper.ComMapper;
import cn.com.pism.phoenix.core.entity.PmnxRoleResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author  perccyking 
 * @since 24-09-12 13:20
 */
@Mapper
public interface PmnxRoleResourceMapper extends ComMapper<PmnxRoleResource> {

    /**
     * <p>
     * 根据角色id获取角色资源
     * </p>
     * by perccyking
     *
     * @param roleId : 角色id
     * @return 角色资源列表
     * @since 24-09-13 16:52
     */
    List<String> getRolePermission(@Param("roleId") Long roleId);
}