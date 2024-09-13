package cn.com.pism.phoenix.core.mapper;

import cn.com.pism.mybatis.core.mapper.ComMapper;
import cn.com.pism.phoenix.core.entity.PmnxResource;
import cn.com.pism.phoenix.models.bo.security.PmnxSecurityResourceCodeBo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author perccyking
 * @since 24-09-12 13:20
 */
@Mapper
public interface PmnxResourceMapper extends ComMapper<PmnxResource> {

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