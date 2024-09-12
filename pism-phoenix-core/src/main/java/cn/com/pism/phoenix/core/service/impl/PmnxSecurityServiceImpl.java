package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.phoenix.annotations.cache.PmnxLocalCache;
import cn.com.pism.phoenix.core.service.PmnxSecurityService;
import cn.com.pism.phoenix.models.bo.security.PmnxSecurityResourceCodeBo;
import org.springframework.stereotype.Service;

import java.util.List;

import static cn.com.pism.phoenix.models.constant.PmnxSecurityConstants.*;

/**
 * @author perccyking
 * @since 24-09-09 16:09
 */
@Service
public class PmnxSecurityServiceImpl implements PmnxSecurityService {


    @Override
    public List<String> getRolePermission(String roleId) {
        return List.of();
    }

    @Override
    public List<String> getUserRole(Long userId) {
        return List.of();
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
        return List.of();
    }
}
