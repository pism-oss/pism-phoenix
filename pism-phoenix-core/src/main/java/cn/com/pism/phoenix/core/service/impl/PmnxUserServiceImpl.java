package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.phoenix.core.entity.PmnxUser;
import cn.com.pism.phoenix.core.mapper.PmnxUserMapper;
import cn.com.pism.phoenix.core.service.PmnxUserService;
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
}
