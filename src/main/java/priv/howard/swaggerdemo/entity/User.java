package priv.howard.swaggerdemo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "用户User", description = "用户信息的实体类")
public class User {
    /**
     *  Swagger只能扫描作为接口的参数或返回值的实体类
     *  通过@ApiModel对实体类进行信息标注，@ApiModelProperty对属性进行备注
     */
    @ApiModelProperty(value = "用户ID", example = "1")
    private String id;
    @ApiModelProperty(value = "用户名", example = "Howard")
    private String name;

    public User() {
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
