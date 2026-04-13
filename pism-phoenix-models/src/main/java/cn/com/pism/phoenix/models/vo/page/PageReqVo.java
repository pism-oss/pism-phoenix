package cn.com.pism.phoenix.models.vo.page;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author perccyking
 * @since 24-09-22 03:38
 */
@Data
@Schema(description = "分页查询请求参数")
@NoArgsConstructor
public class PageReqVo<T> {

    /**
     * 查询参数
     */
    @Schema(description = "查询参数")
    private T param;

    /**
     * 翻页方向，true:向前，false:向后
     */
    @Schema(description = "是否向前翻页", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "true")
    private Boolean forward = Boolean.TRUE;

    /**
     * 分页大小
     */
    @Schema(description = "分页大小", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10")
    private Long size = 20L;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

}