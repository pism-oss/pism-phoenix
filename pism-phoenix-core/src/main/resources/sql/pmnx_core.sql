CREATE TABLE table_demo
(
    id          BIGINT                              NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    create_by   BIGINT                              NULL COMMENT '创建人id',
    update_by   BIGINT                              NULL COMMENT '更新人id',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    dlt         BIT       DEFAULT b'0'              NULL COMMENT '逻辑删除标志位'
) COMMENT 'table demo';

-- TABLE(pmnx_user)
CREATE TABLE pmnx_user
(
    id          BIGINT                              NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    account     VARCHAR(256)                        NULL COMMENT '账号',
    email       VARCHAR(256)                        NULL COMMENT '邮箱',
    password    VARCHAR(512)                        NULL COMMENT '密码',
    enabled     BIT       DEFAULT b'1'              NULL COMMENT '是否启用，1：启用，0：禁用，默认启用',
    create_by   BIGINT                              NULL COMMENT '创建人id',
    update_by   BIGINT                              NULL COMMENT '更新人id',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    dlt         BIT       DEFAULT b'0'              NULL COMMENT '逻辑删除标志位'
) COMMENT '用户表';

CREATE TABLE pmnx_user_attr_config
(
    id            BIGINT                              NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    attr_key      VARCHAR(256)                        NULL COMMENT '属性key',
    attr_name     VARCHAR(256)                        NULL COMMENT '属性名',
    attr_type     INT                                 NULL COMMENT '属性类型0：文本，1：长文本，2：时间，3：单选，4：多选',
    attr_validate VARCHAR(256)                        NULL COMMENT '属性验证',
    create_by     BIGINT                              NULL COMMENT '创建人id',
    update_by     BIGINT                              NULL COMMENT '更新人id',
    create_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time   TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    dlt           BIT       DEFAULT b'0'              NULL COMMENT '逻辑删除标志位'
) COMMENT '用户属性配置';

CREATE TABLE pmnx_user_attr_value
(
    id          BIGINT                              NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    user_id     BIGINT                              NULL COMMENT '用户id',
    attr_key    VARCHAR(256)                        NULL COMMENT '属性key',
    attr_value  TEXT                                NULL COMMENT '属性值',
    create_by   BIGINT                              NULL COMMENT '创建人id',
    update_by   BIGINT                              NULL COMMENT '更新人id',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    dlt         BIT       DEFAULT b'0'              NULL COMMENT '逻辑删除标志位'
) COMMENT '用户属性值';

-- TABLE(pmnx_form)
CREATE TABLE pmnx_form
(
    id          BIGINT                              NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    form_id     VARCHAR(1024)                       NULL COMMENT '表单id',
    field_id    VARCHAR(1024)                       NULL COMMENT '字段id',
    field_value TEXT                                NULL COMMENT '字段值',
    create_by   BIGINT                              NULL COMMENT '创建人id',
    update_by   BIGINT                              NULL COMMENT '更新人id',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    dlt         BIT       DEFAULT b'0'              NULL COMMENT '逻辑删除标志位'
)
    COMMENT '表单数据' ENGINE = InnoDB;

