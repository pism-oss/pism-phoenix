package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.phoenix.annotations.cache.PmnxLocalCache;
import cn.com.pism.phoenix.core.entity.PmnxUserRole;
import cn.com.pism.phoenix.core.service.PmnxResourceService;
import cn.com.pism.phoenix.core.service.PmnxRoleResourceService;
import cn.com.pism.phoenix.core.service.PmnxSecurityService;
import cn.com.pism.phoenix.core.service.PmnxUserRoleService;
import cn.com.pism.phoenix.models.bo.security.PmnxSecurityResourceCodeBo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.com.pism.phoenix.models.constant.PmnxSecurityConstants.*;

/**
 * @author perccyking
 * @since 24-09-09 16:09
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PmnxSecurityServiceImpl implements PmnxSecurityService {

    private final PmnxResourceService pmnxResourceService;

    private final PmnxRoleResourceService pmnxRoleResourceService;

    private final PmnxUserRoleService pmnxUserRoleService;

    @Override
    public List<String> getRolePermission(String roleId) {
        try {
            return pmnxRoleResourceService.getRolePermission(Long.valueOf(roleId));
        } catch (NumberFormatException e) {
            log.error("getRolePermission error", e);
        }
        return List.of();
    }

    @Override
    public List<String> getUserRole(Long userId) {
        return pmnxUserRoleService
                .lambdaQuery()
                .eq(PmnxUserRole::getUserId, userId)
                .select(PmnxUserRole::getRoleId)
                .list()
                .stream()
                .map(item -> String.valueOf(item.getRoleId()))
                .distinct()
                .toList();
    }

    @Override
    @PmnxLocalCache(WHITE_LIST_LOCAL_CACHE_KEY)
    public List<String> getWhitelist() {
        return List.of();
    }

    @Override
    @PmnxLocalCache(BLACK_LIST_LOCAL_CACHE_KEY)
    public List<String> getBlacklist() {
        return List.of();
    }

    @Override
    @PmnxLocalCache(RESOURCE_CODE_LOCAL_CACHE_KEY)
    public List<PmnxSecurityResourceCodeBo> getResourceCode() {
        return pmnxResourceService.getResourceCode();
    }
}
