package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.mybatis.core.service.impl.ComServiceImpl;
import cn.com.pism.phoenix.core.entity.PmnxRoleResource;
import cn.com.pism.phoenix.core.mapper.PmnxRoleResourceMapper;
import cn.com.pism.phoenix.core.service.PmnxRoleResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author perccyking
 * @since 24-09-12 13:20
 */
@Service
public class PmnxRoleResourceServiceImpl extends ComServiceImpl<PmnxRoleResourceMapper, PmnxRoleResource> implements PmnxRoleResourceService {

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
    @Override
    public List<String> getRolePermission(Long roleId) {
        return baseMapper.getRolePermission(roleId);
    }
}
