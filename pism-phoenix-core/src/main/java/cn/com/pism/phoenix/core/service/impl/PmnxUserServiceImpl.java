package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.phoenix.core.entity.PmnxUser;
import cn.com.pism.phoenix.core.entity.PmnxUserRole;
import cn.com.pism.phoenix.core.mapper.PmnxUserMapper;
import cn.com.pism.phoenix.core.service.PmnxUserRoleService;
import cn.com.pism.phoenix.core.service.PmnxUserService;
import cn.com.pism.phoenix.models.exception.BizException;
import cn.com.pism.phoenix.models.vo.page.PageReqVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserDeleteReqVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserPageReqVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserResetPasswordReqVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserSaveReqVo;
import cn.com.pism.phoenix.models.vo.user.resp.PmnxUserPageRespVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author perccyking
 * @since 24-08-25 18:15
 */
@Service
@RequiredArgsConstructor
public class PmnxUserServiceImpl extends ServiceImpl<PmnxUserMapper, PmnxUser> implements PmnxUserService {

    private static final String DEFAULT_PASSWORD = "123456";

    private final PmnxUserRoleService pmnxUserRoleService;

    /**
     * <p>
     * 通过账号获取密码
     * </p>
     * by perccyking
     *
     * @param account : 账号
     * @return {@link String} 密码
     * @since 24-09-13 00:34
     */
    @Override
    public String getPasswordByAccount(String account) {
        return baseMapper.getPasswordByAccount(account);
    }

    /**
     * <p>
     * 分页获取用户列表
     * </p>
     * by perccyking
     *
     * @param reqVo : 请求参数
     * @return 分页用户列表
     * @since 24-09-22 03:36
     */
    @Override
    public Page<PmnxUserPageRespVo> page(PageReqVo<PmnxUserPageReqVo> reqVo) {
        Page<PmnxUserPageRespVo> page = new Page<>(reqVo.getPage(), reqVo.getSize());
        page.setRecords(baseMapper.page(page, reqVo.getParam()));
        return page;
    }

    /**
     * <p>
     * 保存用户
     * </p>
     * by perccyking
     *
     * @param reqVo : 请求参数
     * @since 24-09-22 21:46
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(PmnxUserSaveReqVo reqVo) {
        if (StringUtils.isBlank(reqVo.getAccount())) {
            throw new BizException("账号不能为空");
        }

        PmnxUser pmnxUser = null;
        if (reqVo.getId() != null) {
            pmnxUser = getById(reqVo.getId());
        }
        if (pmnxUser == null) {
            pmnxUser = new PmnxUser();
        }

        //账号唯一校验
        boolean exists = lambdaQuery()
                .eq(PmnxUser::getAccount, reqVo.getAccount())
                .eq(PmnxUser::isDlt, false)
                .ne(reqVo.getId() != null, PmnxUser::getId, reqVo.getId())
                .exists();
        if (exists) {
            throw new BizException("账号已存在");
        }

        pmnxUser.setAccount(reqVo.getAccount());
        pmnxUser.setEmail(reqVo.getEmail());
        if (reqVo.getEnabled() != null) {
            pmnxUser.setEnabled(reqVo.getEnabled());
        } else if (pmnxUser.getId() == null) {
            pmnxUser.setEnabled(Boolean.TRUE);
        }

        updateById(pmnxUser);

    }

    /**
     * <p>
     * 删除用户（逻辑删除）
     * </p>
     * by perccyking
     *
     * @param reqVo : 请求参数
     * @since 26-02-08 16:20
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(PmnxUserDeleteReqVo reqVo) {
        if (reqVo == null || CollectionUtils.isEmpty(reqVo.getIds())) {
            throw new BizException("请选择要删除的用户");
        }
        List<Long> ids = reqVo.getIds();

        //逻辑删除用户
        lambdaUpdate()
                .in(PmnxUser::getId, ids)
                .set(PmnxUser::isDlt, true)
                .set(PmnxUser::isEnabled, false)
                .update();

        //逻辑删除用户角色关系
        pmnxUserRoleService.lambdaUpdate()
                .in(PmnxUserRole::getUserId, ids)
                .set(PmnxUserRole::isDlt, true)
                .update();
    }

    /**
     * <p>
     * 重置用户密码
     * </p>
     * by perccyking
     *
     * @param reqVo : 请求参数
     * @since 26-02-08 16:20
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(PmnxUserResetPasswordReqVo reqVo) {

    }
}
