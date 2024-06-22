package cn.com.pism.phoenix.annotations.form;

import cn.com.pism.phoenix.annotations.model.FormOptionVo;

import java.util.List;

/**
 * @author perccyking
 * @since 24-06-10 10:52
 */
public interface FormOptionProvider {

    /**
     * <p>
     * 构建选项组
     * </p>
     * by perccyking
     *
     * @return 选项组
     * @since 24-06-10 11:55
     */
    List<FormOptionVo> build();
}
