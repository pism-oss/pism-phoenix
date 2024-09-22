package cn.com.pism.phoenix.core.mapper;

import cn.com.pism.mybatis.core.mapper.ComMapper;
import cn.com.pism.phoenix.core.entity.PmnxUser;
import cn.com.pism.phoenix.models.vo.user.req.PmnxUserPageReqVo;
import cn.com.pism.phoenix.models.vo.user.resp.PmnxUserPageRespVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author perccyking
 * @since 24-08-25 18:15
 */
@Mapper
public interface PmnxUserMapper extends ComMapper<PmnxUser> {

    /**
     * <p>
     * 通过账号获取密码
     * </p>
     * by perccyking
     *
     * @param account : 账号
     * @return {@link String} 密码
     * @since 24-09-13 00:35
     */
    String getPasswordByAccount(@Param("account") String account);

    /**
     * <p>
     * 分页查询用户列表
     * </p>
     * by perccyking
     *
     * @param page  : 分页参数
     * @param param : 过滤条件
     * @return 分页列表
     * @since 24-09-22 15:56
     */
    List<PmnxUserPageRespVo> page(Page<PmnxUserPageRespVo> page, @Param("param") PmnxUserPageReqVo param);
}