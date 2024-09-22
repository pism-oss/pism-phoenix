package cn.com.pism.phoenix.models.vo.page;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

/**
 * @author perccyking
 * @since 24-09-21 23:30
 */
@Data
@Schema(description = "分页响应对象")
@NoArgsConstructor
public class PageRespVo<T> {


    /**
     * 查询数据列表
     */
    @Schema(description = "数据列表")
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    @Schema(description = "数据总行数")
    private long total = 0;

    /**
     * 每页显示条数，默认 10
     */
    @Schema(description = "每页显示条数，默认 10")
    private long size = 10;

    /**
     * 当前页
     */
    @Schema(description = "当前页")
    private long current = 1;

    /**
     * 总页数
     */
    @Schema(description = "总页数")
    private long pages;

    public PageRespVo(Page<T> page) {
        if (page != null) {
            this.records = page.getRecords();
            this.total = page.getTotal();
            this.size = page.getSize();
            this.current = page.getCurrent();
            this.pages = page.getPages();
        }
    }

    public static<T> PageRespVo<T> of(Page<T> page){
        return new PageRespVo<>(page);
    }

}
