package cn.com.pism.phoenix.core.service;

import cn.com.pism.mybatis.core.service.ComService;
import cn.com.pism.phoenix.core.entity.PmnxRoleResource;

import java.util.List;

/**
 * @author perccyking
 * @since 24-09-12 13:20
 */
public interface PmnxRoleResourceService extends ComService<PmnxRoleResource> {


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
    List<String> getRolePermission(Long roleId);
}
