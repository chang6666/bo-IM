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

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

/**
 * 群
 */
@Schema(description="群")
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_group")
public class Group implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @Schema(description="id")
    private Long id;

    /**
     * 群名字
     */
    @TableField(value = "`name`")
    @Schema(description="群名字")
    private String name;

    /**
     * 群主id
     */
    @TableField(value = "owner_id")
    @Schema(description="群主id")
    private Long ownerId;

    /**
     * 群头像
     */
    @TableField(value = "head_image")
    @Schema(description="群头像")
    private String headImage;

    /**
     * 群头像缩略图
     */
    @TableField(value = "head_image_thumb")
    @Schema(description="群头像缩略图")
    private String headImageThumb;

    /**
     * 群公告
     */
    @TableField(value = "notice")
    @Schema(description="群公告")
    private String notice;

    /**
     * 群备注
     */
    @TableField(value = "remark")
    @Schema(description="群备注")
    private String remark;

    /**
     * 是否已删除
     */
    @TableField(value = "deleted")
    @Schema(description="是否已删除")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(value = "created_time",fill = FieldFill.INSERT)
    @Schema(description="创建时间")
    private Date createdTime;

    private static final long serialVersionUID = 1L;
}