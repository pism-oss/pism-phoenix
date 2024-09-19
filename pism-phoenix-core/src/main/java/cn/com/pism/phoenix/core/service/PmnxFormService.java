package cn.com.pism.phoenix.core.service;

import cn.com.pism.mybatis.core.service.ComService;
import cn.com.pism.phoenix.core.entity.PmnxForm;

import java.util.List;

/**
 * @author perccyking
 * @since 24-09-13 20:13
 */
public interface PmnxFormService extends ComService<PmnxForm> {


    /**
     * <p>
     * 刷新表单
     * </p>
     * by perccyking
     *
     * @since 24-09-18 15:39
     */
    void refreshForm();

    /**
     * <p>
     * 标记表单更新
     * </p>
     * by perccyking
     *
     * @param formId : 表单id
     * @since 24-09-19 22:35
     */
    void markUpdate(String formId);

    /**
     * <p>
     * 刷新表单数据
     * </p>
     * by perccyking
     *
     * @param formId : 表单id
     * @param form   : 表单对象
     * @since 24-09-19 22:46
     */
    void refreshForm(String formId, Object form);

    /**
     * <p>
     * 批量刷新表单
     * </p>
     * by perccyking
     *
     * @param forms : 表单
     * @since 24-09-19 23:38
     */
    void batchRefreshForm(List<Object> forms);
}
