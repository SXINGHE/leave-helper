# Swagger API 文档使用指南

## 访问 Swagger UI

启动应用后，访问以下 URL：

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **OpenAPI YAML**: http://localhost:8080/v3/api-docs.yaml

## API 分组

### 1. Maternity Leave (产假计算)
- `POST /api/v1/maternity-leave/calculateDate` - 计算产假日期
- `POST /api/v1/maternity-leave/calculateMoney` - 计算产假津贴

### 2. Calendar (日历管理)
- `GET /api/v1/calendar/calendar-types` - 获取所有日历类型
- `GET /api/v1/calendar/special-dates/{calendarCode}` - 获取特殊日期
- `POST /api/v1/calendar/setup-calendar` - 配置日历

### 3. Policy (政策管理)
- `POST /api/v1/policy/create` - 创建新政策
- `PUT /api/v1/policy/update/{id}` - 更新政策

## 配置说明

Swagger 配置文件位于：
- `src/main/java/com/ocbc/ms/config/OpenApiConfig.java`

可以在 `application.properties` 或 `application.yml` 中自定义 Swagger 路径：

```yaml
springdoc:
  api-docs:
    path: /v3/api-docs
  swagger-ui:
    path: /swagger-ui.html
    enabled: true
```

## 注解说明

所有 Controller 和 DTO 已添加以下 Swagger 注解：

- `@Tag` - API 分组标签
- `@Operation` - 接口操作描述
- `@ApiResponse` / `@ApiResponses` - 响应状态码说明
- `@Parameter` - 参数说明
- `@Schema` - 数据模型说明

## 下一步

所有注解中的 `description` 和 `example` 都使用了占位值，请根据实际业务需求更新这些说明。
