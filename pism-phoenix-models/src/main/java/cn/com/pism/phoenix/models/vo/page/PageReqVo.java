package cn.com.pism.phoenix.models.vo.page;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
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
     * 页码
     */
    @Schema(description = "页码", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "页码不能为空")
    private Long page;

    /**
     * 分页大小
     */
    @Schema(description = "分页大小", requiredMode = Schema.RequiredMode.REQUIRED, example = "10")
    @NotNull(message = "分页大小不能为空")
    private Long size;

    public PageReqVo(Long page, Long size) {
        this.page = page;
        this.size = size;
    }
}