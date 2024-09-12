package cn.com.pism.phoenix.core.config.security;

import cn.com.pism.phoenix.core.service.PmnxSecurityService;
import cn.com.pism.phoenix.core.util.CacheUtil;
import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static cn.com.pism.phoenix.models.constant.PmnxSecurityConstants.*;

/**
 * @author perccyking
 * @since 24-08-27 00:53
 */
@Component
@RequiredArgsConstructor
public class SaTokenStpInterfaceImpl implements StpInterface {

    private final PmnxSecurityService pmnxSecurityService;

    private final CacheUtil cacheUtil;

    private static final TypeReference<List<String>> LIST_STRING_TYPE_REFERENCE = new TypeReference<>() {
    };

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {

        return cacheUtil.getOrUpdate(String.format(USER_PERMISSION_CACHE_KEY, loginId), () -> {
            List<String> permissionList = new ArrayList<>();
            //获取当前登录人的角色列表
            List<String> roleList = getRoleList(loginId, loginType);
            if (CollectionUtils.isNotEmpty(roleList)) {
                for (String roleId : roleList) {
                    permissionList.addAll(getRolePermission(roleId));
                }
            }
            return permissionList;
        }, StpUtil.getSessionTimeout() + (60 * 60), TimeUnit.SECONDS, LIST_STRING_TYPE_REFERENCE);
    }

    public List<String> getRolePermission(String roleId) {
        return cacheUtil.getOrUpdate(ROLE_PERMISSION_CACHE_KEY + roleId,
                () -> pmnxSecurityService.getRolePermission(roleId),
                StpUtil.getSessionTimeout() + (60 * 60), TimeUnit.SECONDS, LIST_STRING_TYPE_REFERENCE);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return cacheUtil.getOrUpdate(String.format(USER_ROLE_CACHE_KEY, loginId),
                () -> pmnxSecurityService.getUserRole((Long) loginId),
                StpUtil.getSessionTimeout() + (60 * 60), TimeUnit.SECONDS, LIST_STRING_TYPE_REFERENCE);

    }

}
