package cn.com.pism.phoenix.core.service.impl;

import cn.com.pism.mybatis.core.service.impl.ComServiceImpl;
import cn.com.pism.phoenix.core.entity.PmnxResource;
import cn.com.pism.phoenix.core.mapper.PmnxResourceMapper;
import cn.com.pism.phoenix.core.service.PmnxResourceService;
import org.springframework.stereotype.Service;

/**
 * @author perccyking
 * @since 24-09-12 13:20
 */
@Service
public class PmnxResourceServiceImpl extends ComServiceImpl<PmnxResourceMapper, PmnxResource> implements PmnxResourceService {

}
