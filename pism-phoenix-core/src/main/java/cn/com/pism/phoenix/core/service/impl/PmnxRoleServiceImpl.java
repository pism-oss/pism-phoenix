package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.mybatis.core.service.impl.ComServiceImpl;
import cn.com.pism.phoenix.core.entity.PmnxRole;
import cn.com.pism.phoenix.core.mapper.PmnxRoleMapper;
import cn.com.pism.phoenix.core.service.PmnxRoleService;
import org.springframework.stereotype.Service;

/**
 * @author perccyking
 * @since 24-09-12 13:20
 */
@Service
public class PmnxRoleServiceImpl extends ComServiceImpl<PmnxRoleMapper, PmnxRole> implements PmnxRoleService {

}
