package cn.com.pism.phoenix.core.common;

import cn.com.pism.phoenix.core.util.AuthUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import static cn.com.pism.phoenix.core.common.ComEntity.*;

/**
 * @author perccyking
 * @since 26-03-08 23:09
 */
@Slf4j
@Component
public class PmnxMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        long currentTimeMillis = System.currentTimeMillis();
        Long loginId = AuthUtil.getLoginId();
        strictInsertFill(metaObject, FIELD_CREATE_BY, Long.class, loginId);
        strictInsertFill(metaObject, FIELD_CREATE_TIME, Long.class, currentTimeMillis);
        strictInsertFill(metaObject, FIELD_UPDATE_BY, Long.class, loginId);
        strictInsertFill(metaObject, FIELD_UPDATE_TIME, Long.class, currentTimeMillis);
        strictInsertFill(metaObject, FIELD_DELETED, Boolean.class, Boolean.FALSE);

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        long currentTimeMillis = System.currentTimeMillis();
        Long loginId = AuthUtil.getLoginId();
        Object deleted = getFieldValByName(FIELD_DELETED, metaObject);
        if (deleted != null) {
            if ((Boolean) deleted) {
                // 已删除
                strictInsertFill(metaObject, FIELD_DELETE_BY, Long.class, loginId);
                strictInsertFill(metaObject, FIELD_DELETE_TIME, Long.class, currentTimeMillis);
            } else {
                // 未删除
                strictInsertFill(metaObject, FIELD_UPDATE_BY, Long.class, loginId);
                strictInsertFill(metaObject, FIELD_UPDATE_TIME, Long.class, currentTimeMillis);
            }
        }
    }
}
