CREATE TABLE table_demo
(
    id          BIGINT                              NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    create_by   BIGINT                              NULL COMMENT '创建人id',
    update_by   BIGINT                              NULL COMMENT '更新人id',
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    dlt         TINYINT   DEFAULT b'0'              NULL COMMENT '逻辑删除标志位'
) COMMENT 'table demo';

-- TABLE(pmnx_form)
CREATE TABLE pmnx_form
(
    id          BIGINT           NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    form_id     VARCHAR(1024)    NULL COMMENT '表单id',
    field_id    VARCHAR(1024)    NULL COMMENT '字段id',
    field_value TEXT             NULL COMMENT '字段值',
    create_by   BIGINT           NULL COMMENT '创建人id',
    update_by   BIGINT           NULL COMMENT '更新人id',
    create_time BIGINT           NULL COMMENT '创建时间',
    update_time BIGINT           NULL COMMENT '更新时间',
    dlt         BIT DEFAULT b'0' NULL COMMENT '逻辑删除标志位'
)
    COMMENT '表单数据';

-- TABLE(pmnx_resource)
CREATE TABLE pmnx_resource
(
    id               BIGINT           NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    resource_code    VARCHAR(64)      NULL COMMENT '资源编码',
    resource_name    VARCHAR(512)     NULL COMMENT '资源名称',
    resource_type    VARCHAR(64)      NULL COMMENT '资源类型 0:接口，1:目录，2:按钮',
    resource_content VARCHAR(1024)    NULL COMMENT '资源内容',
    parent_id        BIGINT           NULL COMMENT '父级id',
    sort             INT              NULL COMMENT '排序',
    enabled          BIT DEFAULT b'1' NULL COMMENT '是否启用，1：启用，0：禁用，默认启用',
    create_by        BIGINT           NULL COMMENT '创建人id',
    update_by        BIGINT           NULL COMMENT '更新人id',
    create_time      BIGINT           NULL COMMENT '创建时间',
    update_time      BIGINT           NULL COMMENT '更新时间',
    dlt              BIT DEFAULT b'0' NULL COMMENT '逻辑删除标志位'
)
    COMMENT '资源';

-- TABLE(pmnx_role)
CREATE TABLE pmnx_role
(
    id          BIGINT           NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    role_code   VARCHAR(64)      NULL COMMENT '角色编码',
    role_name   VARCHAR(512)     NULL COMMENT '角色名称',
    enabled     BIT DEFAULT b'1' NULL COMMENT '是否启用，1：启用，0：禁用，默认启用',
    create_by   BIGINT           NULL COMMENT '创建人id',
    update_by   BIGINT           NULL COMMENT '更新人id',
    create_time BIGINT           NULL COMMENT '创建时间',
    update_time BIGINT           NULL COMMENT '更新时间',
    dlt         BIT DEFAULT b'0' NULL COMMENT '逻辑删除标志位'
)
    COMMENT '角色';

-- TABLE(pmnx_role_resource)
CREATE TABLE pmnx_role_resource
(
    id          BIGINT           NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    role_id     BIGINT           NULL COMMENT '角色id',
    resource_id BIGINT           NULL COMMENT '资源id',
    create_by   BIGINT           NULL COMMENT '创建人id',
    update_by   BIGINT           NULL COMMENT '更新人id',
    create_time BIGINT           NULL COMMENT '创建时间',
    update_time BIGINT           NULL COMMENT '更新时间',
    dlt         BIT DEFAULT b'0' NULL COMMENT '逻辑删除标志位'
)
    COMMENT '角色-资源关系';

-- TABLE(pmnx_user)
CREATE TABLE pmnx_user
(
    id          BIGINT           NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    account     VARCHAR(256)     NULL COMMENT '账号',
    email       VARCHAR(256)     NULL COMMENT '邮箱',
    password    VARCHAR(512)     NULL COMMENT '密码',
    enabled     BIT DEFAULT b'1' NULL COMMENT '是否启用，1：启用，0：禁用，默认启用',
    create_by   BIGINT           NULL COMMENT '创建人id',
    update_by   BIGINT           NULL COMMENT '更新人id',
    create_time BIGINT           NULL COMMENT '创建时间',
    update_time BIGINT           NULL COMMENT '更新时间',
    dlt         BIT DEFAULT b'0' NULL COMMENT '逻辑删除标志位'
)
    COMMENT '用户';

-- TABLE(pmnx_user_role)
CREATE TABLE pmnx_user_role
(
    id          BIGINT           NOT NULL COMMENT '主键id'
        PRIMARY KEY,
    user_id     BIGINT           NULL COMMENT '用户id',
    role_id     BIGINT           NULL COMMENT '角色id',
    create_by   BIGINT           NULL COMMENT '创建人id',
    update_by   BIGINT           NULL COMMENT '更新人id',
    create_time BIGINT           NULL COMMENT '创建时间',
    update_time BIGINT           NULL COMMENT '更新时间',
    dlt         BIT DEFAULT b'0' NULL COMMENT '逻辑删除标志位'
)
    COMMENT '用户-角色关系';


