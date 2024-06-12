/**
 * Copyright [2024] [bo-IM]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.james.platform.pojo.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 用户
 */
@Schema(description="用户")
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_user")
public class User implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @Schema(description="id")
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "user_name")
    @Schema(description="用户名")
    private String userName;

    /**
     * 用户昵称
     */
    @TableField(value = "nick_name")
    @Schema(description="用户昵称")
    private String nickName;

    /**
     * 用户头像
     */
    @TableField(value = "head_image")
    @Schema(description="用户头像")
    private String headImage;

    /**
     * 用户头像缩略图
     */
    @TableField(value = "head_image_thumb")
    @Schema(description="用户头像缩略图")
    private String headImageThumb;

    /**
     * 密码(明文)
     */
    @TableField(value = "`password`")
    @Schema(description="密码(明文)")
    private String password;

    /**
     * 性别 0:男 1:女
     */
    @TableField(value = "sex")
    @Schema(description="性别 0:男 1:女")
    private Boolean sex;

    /**
     * 用户类型 1:普通用户 2:审核账户
     */
    @TableField(value = "`type`")
    @Schema(description="用户类型 1:普通用户 2:审核账户")
    private Short type;

    /**
     * 个性签名
     */
    @TableField(value = "signature")
    @Schema(description="个性签名")
    private String signature;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    @Schema(description="最后登录时间")
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    @Schema(description="创建时间")
    private Date createdTime;

    private static final long serialVersionUID = 1L;
}