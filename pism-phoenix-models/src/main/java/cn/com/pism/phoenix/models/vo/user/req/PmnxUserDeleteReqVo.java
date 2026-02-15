package cn.com.pism.phoenix.models.vo.user.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @author perccyking
 * @since 26-02-08 16:15
 */
@Data
@Schema(description = "用户删除请求参数")
public class PmnxUserDeleteReqVo {

    /**
     * 用户id列表
     */
    @Schema(description = "用户id列表")
    private List<Long> ids;
}
