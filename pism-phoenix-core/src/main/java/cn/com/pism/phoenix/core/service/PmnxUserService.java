package cn.com.pism.phoenix.core.service;

import cn.com.pism.phoenix.core.entity.PmnxUser;
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
}
