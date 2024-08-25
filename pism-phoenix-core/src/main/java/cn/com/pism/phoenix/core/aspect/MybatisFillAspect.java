package cn.com.pism.phoenix.core.aspect;

import cn.com.pism.mybatis.core.aspect.EntityFillAspect;
import cn.com.pism.phoenix.core.common.ComEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author perccyking
 * @since 24-08-25 01:59
 */
@Component
@Aspect
@Slf4j
public class MybatisFillAspect extends EntityFillAspect {

    @Override
    protected void doEnhance(Object o) {
        if (o instanceof ComEntity comEntity) {

            //当前系统时间
            Date currentDate = new Date();

            //更新时间
            comEntity.setUpdateTime(currentDate);

            try {
                //实体主键id为空，并且没有设置创建时间，对创建时间补充，注：实体更新的时候，有主键id可能没有创建时间
                if (comEntity.getId() == null && comEntity.getCreateTime() == null) {
                    comEntity.setCreateTime(currentDate);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
