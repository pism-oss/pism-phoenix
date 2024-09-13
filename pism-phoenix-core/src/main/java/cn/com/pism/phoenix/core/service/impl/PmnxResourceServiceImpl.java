package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.mybatis.core.service.impl.ComServiceImpl;
import cn.com.pism.phoenix.core.entity.PmnxResource;
import cn.com.pism.phoenix.core.mapper.PmnxResourceMapper;
import cn.com.pism.phoenix.core.service.PmnxResourceService;
import cn.com.pism.phoenix.models.bo.security.PmnxSecurityResourceCodeBo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author perccyking
 * @since 24-09-12 13:20
 */
@Service
public class PmnxResourceServiceImpl extends ComServiceImpl<PmnxResourceMapper, PmnxResource> implements PmnxResourceService {

    /**
     * <p>
     * 获取资源代码列表
     * </p>
     * by perccyking
     *
     * @return 资源代码列表
     * @since 24-09-13 16:48
     */
    @Override
    public List<PmnxSecurityResourceCodeBo> getResourceCode() {
        return baseMapper.getResourceCode();
    }
}
