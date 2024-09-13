package cn.com.pism.phoenix.core.service;

import cn.com.pism.mybatis.core.service.ComService;
import cn.com.pism.phoenix.core.entity.PmnxResource;
import cn.com.pism.phoenix.models.bo.security.PmnxSecurityResourceCodeBo;

import java.util.List;

/**
 * @author perccyking
 * @since 24-09-12 13:20
 */
public interface PmnxResourceService extends ComService<PmnxResource> {

    /**
     * <p>
     * 获取资源代码列表
     * </p>
     * by perccyking
     *
     * @return 资源代码列表
     * @since 24-09-13 16:48
     */
    List<PmnxSecurityResourceCodeBo> getResourceCode();
}
