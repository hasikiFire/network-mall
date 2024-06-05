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
public class UsersendEmailCodeDto {

    @Schema(description = "邮箱", required = true)
    @NotBlank(message = "邮箱不能为空！")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+).com$", message = "邮箱格式不正确！")
    private String email;

}
