package com.hasikiFire.networkmall.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * 用户注册 请求DTO
 *
 * @author hasikiFire
 */
@Data
public class UserRegisterReqDto {

    @Schema(description = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空！")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+).com$", message = "邮箱格式不正确！")
    private String email;

    @Schema(description = "昵称", required = true)
    @NotBlank(message = "昵称不能为空！")
    private String name;

    @Schema(description = "密码", required = true)
    @NotBlank(message = "密码不能为空！")
    private String password;

    @Schema(description = "邀请码", required = false)
    private String inviteCode;

    @Schema(description = "验证码", required = true)
    @NotBlank(message = "验证码不能为空！")
    @Pattern(regexp = "^\\d{4}$", message = "验证码格式不正确！")
    private String velCode;

    /**
     * 请求会话标识，用来标识图形验证码属于哪个会话
     */
    @Schema(description = "sessionId", required = true)
    @NotBlank
    @Length(min = 32, max = 32)
    private String sessionId;

}
