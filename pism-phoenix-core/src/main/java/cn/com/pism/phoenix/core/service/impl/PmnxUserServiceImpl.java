package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.phoenix.core.entity.PmnxUser;
import cn.com.pism.phoenix.core.mapper.PmnxUserMapper;
import cn.com.pism.phoenix.core.service.PmnxUserService;
import cn.com.pism.phoenix.models.vo.page.PageReqVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserPageReqVo;
import cn.com.pism.phoenix.models.vo.user.resp.PmnxUserPageRespVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author perccyking
 * @since 24-08-25 18:15
 */
@Service
public class PmnxUserServiceImpl extends ServiceImpl<PmnxUserMapper, PmnxUser> implements PmnxUserService {

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
}
