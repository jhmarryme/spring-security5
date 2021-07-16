package com.jhmarryme.web.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.jhmarryme.dto.User;
import com.jhmarryme.dto.UserQueryCondition;
import com.jhmarryme.exception.UserNotExistException;
import com.jhmarryme.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.swagger.annotations.*;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * description:
 *
 * @author JiaHao Wang
 * @date 2020/9/8 12:20
 */
@RestController
@RequestMapping("/user")
@Api("User demo")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 使用jwt替换默认令牌后, 由于通过JWT生成的Authentication对象里的principal为一个字符串而不是UserDetails对象的原因，请求下面这个接口将获取不到任何信息
     */
    @GetMapping("/me")
    public Object me(@AuthenticationPrincipal UserDetails user) {
        return user;
    }

    /***
     * JWT 情况下获取的principal是一个字符串
     * @param user
     */
    @GetMapping("/me/jwt")
    public Object getCurrentUserWithJwt(@AuthenticationPrincipal String user) {
        // 只获取User对象
        return user;
    }

    @GetMapping("/me/authentication")
    public Object getCurrentUserWithJwt(Authentication authentication, HttpServletRequest request) throws UnsupportedEncodingException {
        //从请求头中获取到JWT
        String token = StringUtils.substringAfter(request.getHeader("Authorization"), "Bearer ");
        //借助密钥对JWT进行解析,注意由于JWT生成时编码格式用的UTF-8（看源码可以追踪到）
        //但Jwts工具用到的默认编码格式不是，所以要设置其编码格式为UTF-8
        Claims claims = Jwts.parser()
                .setSigningKey(securityProperties.getOauth2().getJwtSigningKey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();
        //取出扩展信息，并打印
        String company = (String) claims.get("company");

        System.err.println(company);
        return authentication;
    }



    @GetMapping
    @JsonView(User.UserSimpleView.class)
    @ApiOperation(value = "根据条件查询用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, type = "String"),
            @ApiImplicitParam(name = "age", value = "起始年龄", required = true, type = "int"),
            @ApiImplicitParam(name = "ageTo", value = "结束年龄", required = true, type = "int")
    })
    public List<User> query(UserQueryCondition condition, @PageableDefault(page = 1, size = 15, sort = "username",
            direction = Sort.Direction.DESC) Pageable pageable) {

        // 使用反射工具类打印condition
//        System.out.println(ReflectionToStringBuilder.toString(condition, ToStringStyle.MULTI_LINE_STYLE));

//        System.out.println(ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));

        ArrayList<User> users = new ArrayList<>();
        users.add(new User());
        users.add(new User());
        users.add(new User());

        return users;
    }

    @GetMapping("/{id:\\d+}")
    @JsonView(User.UserDetailView.class)
    @ApiOperation(value = "获取指定用户详细信息")
    public User getInfo(@ApiParam("用户id") @PathVariable(name = "id") String userId) {

        LOGGER.info("/user/{id} userId: {}", userId);
        User user = new User();
        user.setUsername("wjh");
        user.setPassword("123");
        return user;
    }

    @PostMapping
    @JsonView(User.UserSimpleView.class)
    public User create(@Valid @RequestBody User user, BindingResult errors) {

        if (errors.hasErrors()) {
            errors.getAllErrors().forEach(objectError -> System.out.println(objectError.getDefaultMessage()));
        }

        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));

        user.setId("1");
        return user;
    }

    @PutMapping("/{id:\\d+}")
    public User update(@Valid @RequestBody User user, BindingResult errors) {

        LOGGER.info("update: {}", user);

        if (errors.hasErrors()) {
            errors.getFieldErrors().forEach(fieldError -> System.out.println(fieldError.getField() + " : " + fieldError.getDefaultMessage()));
        }

        System.out.println(ReflectionToStringBuilder.toString(user, ToStringStyle.MULTI_LINE_STYLE));

        return user;
    }

    @DeleteMapping("/{id:\\d+}")
    public void delete(@PathVariable String id) {

        throw new UserNotExistException(id);
//        System.out.println(id);
    }

}
