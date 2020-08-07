<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

- [framework-web 使用文档](#framework-web-%E4%BD%BF%E7%94%A8%E6%96%87%E6%A1%A3)
  - [配置](#%E9%85%8D%E7%BD%AE)
  - [自定义统一返回](#%E8%87%AA%E5%AE%9A%E4%B9%89%E7%BB%9F%E4%B8%80%E8%BF%94%E5%9B%9E)
  - [统一异常处理](#%E7%BB%9F%E4%B8%80%E5%BC%82%E5%B8%B8%E5%A4%84%E7%90%86)
  - [参数校验处理](#%E5%8F%82%E6%95%B0%E6%A0%A1%E9%AA%8C%E5%A4%84%E7%90%86)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

# framework-web 使用文档

## 配置

- 针对 404 自定义异常返回所需配置
```properties
#出现错误时, 直接抛出异常
spring.mvc.throw-exception-if-no-handler-found=true
#不要为我们工程中的资源文件建立映射
spring.resources.add-mappings=false
```

## 自定义统一返回

> @WrapResponseAdvice

在 Controller 层贴上该注解, 则返回的对象会自动被包装.

- 示例代码

```java
@RestController
@RequestMapping("/test")
@WrapResponseAdvice
public class TestController {

    @PostMapping
    public String test() {
        return "测试成功";
    }
}
```

- 返回值

```json
{
    "requestId": "743221be83c74de18cefa6cd15d0f778",
    "code": "200",
    "message": "请求成功",
    "data": "测试成功",
    "timestamp": 1596710823040
}
```


## 统一异常处理

- 1006-非法请求


> 请求方式错误
```json
{
    "requestId": null,
    "code": "1006",
    "message": "请求方式错误, 请求路径: /test, 应为: POST, 不支持: GET",
    "data": null,
    "timestamp": 1596710905811
}
```

> 请求路径不存在, 需要在项目中配置以下[配置](#配置)才生效
```properties
#出现错误时, 直接抛出异常
spring.mvc.throw-exception-if-no-handler-found=true
#不要为我们工程中的资源文件建立映射
spring.resources.add-mappings=false
```
```json
{
    "requestId": null,
    "code": "1006",
    "message": "请求路径: /test/2 不存在",
    "data": null,
    "timestamp": 1596712802534
}
```


- 1008-非法参数
```text
// POST application/json 请求体缺失
{
    "requestId": "aef7a47af522456f9b950dfe4834b110",
    "code": "1008",
    "message": "Required request body is missing",
    "data": null,
    "timestamp": 1596711001530
}

```


## 参数校验处理

- Java 代码示例
```java
@Data
public class TestQuery {

    @NotNull
    private Long id;

    @NotBlank
    private String message;
}

```

- 校验示例

```text
{
    "requestId": "e25299b9283346cc842bf24522f79677",
    "code": "1013",
    "message": "message不能为空,id不能为null",
    "data": null,
    "timestamp": 1596711064350
}

会直接对字段名进行拼接, 如果手动填写 message, 则会进行如下提示

@NotBlank(message = "不正确")
private String message;

{
    "requestId": "e25299b9283346cc842bf24522f79677",
    "code": "1013",
    "message": "message不正确",
    "data": null,
    "timestamp": 1596711064350
}
```
