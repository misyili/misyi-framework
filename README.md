
- [misyi-framework](#misyi-framework)
  - [配置文件](#%E9%85%8D%E7%BD%AE%E6%96%87%E4%BB%B6)
    - [settings 文件](#settings-%E6%96%87%E4%BB%B6)
  - [misyi-framework-api](#misyi-framework-api)
  - [misyi-framework-core](#misyi-framework-core)
  - [misyi-framework-web](#misyi-framework-web)
  - [待办事项](#%E5%BE%85%E5%8A%9E%E4%BA%8B%E9%A1%B9)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->
<!-- doctoc README.md 即可生成目录 -->

# misyi-framework 
> 基础架构

在项目中使用,采用插拔式添加组件

## 配置文件

### [settings 文件](./file/settings.xml)

> 采用阿里云效私有仓库

## misyi-framework-api

> 公共 API 类库

## [misyi-framework-core](./document/framework-core.md)

> 核心组件类库

## [misyi-framework-web](./document/framework-web.md)

> web 拓展 

- 支持低侵入返回统一响应体
- 支持异常体系


## 待办事项

- 集成 SpringCloudAlibaba
- 集成 Druid 并进行密码加密
- PageHelper 分页查询集成
- 字符编码解析
- 跨域问题处理
- 配置中心
- 邮件发送
- HTTP工具包
- log 异步日志
- jwt 认证
- 非对称加密工具(RSA)
- 重复调用统一校验
- 文件上传下载工具, 并对接阿里sso
- 整合阿里 Excel 导出工具
- 多数据源
- 分布式事务
- 服务间异常传递
- 服务透传请求头
- 支持单点登录
- 整合 mybatisplus
- 整合 redisson 分布式锁
- 整合 redis
- 优化 MongoDB 组件
- 整合 RabbitMQ
- 整合 RoctetMQ
- 整合微信授权登录等
- 插拔式接入钉钉、企业微信报警
- 等等一大推框架必须的东西