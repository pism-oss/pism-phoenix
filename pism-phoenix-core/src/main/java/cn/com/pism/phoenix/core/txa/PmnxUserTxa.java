package cn.com.pism.phoenix.core.txa;

import cn.com.pism.phoenix.core.entity.PmnxUser;
import cn.com.pism.phoenix.core.service.PmnxUserService;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserSaveReqVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author perccyking
 * @since 24-09-22 21:24
 */
@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
@Slf4j
public class PmnxUserTxa {

    private final PmnxUserService pmnxUserService;

    /**
     * <p>
     * 保存用户
     * </p>
     * by perccyking
     *
     * @param reqVo : 请求参数
     * @since 24-09-22 21:46
     */
    public void save(PmnxUserSaveReqVo reqVo) {
        PmnxUser pmnxUser = null;
        if (reqVo.getId() != null) {
            pmnxUser = pmnxUserService.getById(reqVo.getId());
        }
        if (pmnxUser == null) {
            pmnxUser = new PmnxUser();
        }

        pmnxUser.setAccount(reqVo.getAccount());
        pmnxUser.setEmail(reqVo.getEmail());
        pmnxUser.setEnabled(Boolean.TRUE);

        pmnxUserService.save(pmnxUser);
        //如果是新创建的用户需要设值默认的密码

    }
}
