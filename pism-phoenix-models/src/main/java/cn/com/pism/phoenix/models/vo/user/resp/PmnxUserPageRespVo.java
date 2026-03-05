package cn.com.pism.phoenix.models.vo.user.resp;

import cn.com.pism.phoenix.models.vo.user.PmnxUserVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author perccyking
 * @since 24-09-22 03:11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Schema(description = "用户分页对象")
public class PmnxUserPageRespVo extends PmnxUserVo {


}
