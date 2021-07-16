package com.jhmarryme.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.jhmarryme.validator.MyConstraint;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

/**
 * User对象
 *
 * @author JiaHao Wang
 * @date 2020/9/8 12:25
 */
@Data
public class User {

    public interface UserSimpleView {
    }

    public interface UserDetailView extends UserSimpleView {
    }

    @MyConstraint(message = "用户名不能为空")
    @JsonView(UserSimpleView.class)
    private String username;

    @NotBlank(message = "密码不能为空")
    @JsonView(UserDetailView.class)
    private String password;

    @JsonView(UserSimpleView.class)
    private String id;

    @Past(message = "生日必须是过去的时间")
    @JsonView(UserSimpleView.class)
    private Date birthday;

}
