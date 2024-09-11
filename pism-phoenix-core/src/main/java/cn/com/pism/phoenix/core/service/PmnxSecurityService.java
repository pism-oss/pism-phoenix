package cn.com.pism.phoenix.core.service;

import cn.com.pism.phoenix.models.bo.security.PmnxSecurityResourceCodeBo;

import java.util.List;

/**
 * @author perccyking
 * @since 24-08-27 02:24
 */
public interface PmnxSecurityService {

    /**
     * <p>
     * 获取角色的权限列表
     * </p>
     * by perccyking
     *
     * @param roleId :角色id
     * @return 角色权限列表
     * @since 24-09-08 23:50
     */
    List<String> getRolePermission(String roleId);

    /**
     * <p>
     * 获取用户角色列表
     * </p>
     * by perccyking
     *
     * @param userId : 用户id
     * @return 用户角色列表
     * @since 24-09-09 00:05
     */
    List<String> getUserRole(Long userId);

    /**
     * <p>
     * 获取系统白名单
     * </p>
     * by perccyking
     *
     * @return 系统白名单
     * @since 24-09-09 16:24
     */
    List<String> getWhitelist();

    /**
     * <p>
     * 获取系统黑名单
     * </p>
     * by perccyking
     *
     * @return 系统黑名单列表
     * @since 24-09-09 17:20
     */
    List<String> getBlacklist();

    /**
     * <p>
     * 获取系统资源权限代码
     * </p>
     * by perccyking
     *
     * @return 资源权限代码
     * @since 24-09-09 21:19
     */
    List<PmnxSecurityResourceCodeBo> getResourceCode();
}
