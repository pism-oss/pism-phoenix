package cn.com.pism.phoenix.core.service;

import cn.com.pism.phoenix.core.entity.PmnxUser;
import cn.com.pism.phoenix.models.vo.page.PageReqVo;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserPageReqVo;
import cn.com.pism.phoenix.models.vo.user.resp.PmnxUserPageRespVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author perccyking
 * @since 24-08-25 18:15
 */
public interface PmnxUserService extends IService<PmnxUser> {

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
    String getPasswordByAccount(String account);

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
    Page<PmnxUserPageRespVo> page(PageReqVo<PmnxUserPageReqVo> reqVo);
}
