package priv.howard.swaggerdemo.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import priv.howard.swaggerdemo.entity.User;

@Api(description = "测试Controller")
@RestController
public class MyController {

    @GetMapping("/test")
//    @RequestMapping(value = "hello",method = RequestMethod.GET)
    public String hello() {
        return "Test";
    }

//    Swagger只能扫描作为接口的参数或返回值的实体类
    @ResponseBody
    @GetMapping("/user")
    public User getUser() {
        return new User("1", "howard");
    }

    @ApiResponses({@ApiResponse(code = 200, message = "正常请求"), @ApiResponse(code = 403, message = "非法请求")})
//    @ApiOperation给出方法的描述，@ApiParam给出方法参数的描述
    @ApiOperation(value = "Hello单参数测试", notes = "返回Hello + 输入的信息")
    @GetMapping("/hello/{msg}")
//    @ApiParam描述GET方法单参数
    public String helloMsg(@ApiParam(name = "Hello请求的信息", example = "Howard") @PathVariable("msg") String msg) {
        return "Hello, " + msg;
    }

    @ResponseBody
    @ApiOperation(value = "Hello实体类测试", notes= "返回输入的实体类")
    @PostMapping("/hello")
    public User helloUser(@ApiParam(name = "实体类参数", required = true) User user) {
        user.setName(user.getName().toUpperCase());
        return user;
    }
}
