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
 * 私聊消息
 */
@Schema(description="私聊消息")
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_private_message")
public class PrivateMessage implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @Schema(description="id")
    private Long id;

    /**
     * 发送用户id
     */
    @TableField(value = "send_id")
    @Schema(description="发送用户id")
    private Long sendId;

    /**
     * 接收用户id
     */
    @TableField(value = "recv_id")
    @Schema(description="接收用户id")
    private Long recvId;

    /**
     * 发送内容
     */
    @TableField(value = "content")
    @Schema(description="发送内容")
    private String content;

    /**
     * 消息类型 0:文字 1:图片 2:文件 3:语音 10:系统提示
     */
    @TableField(value = "`type`")
    @Schema(description="消息类型 0:文字 1:图片 2:文件 3:语音 10:系统提示")
    private Boolean type;

    /**
     * 状态 0:未读 1:已读 2:撤回
     */
    @TableField(value = "`status`")
    @Schema(description="状态 0:未读 1:已读 2:撤回")
    private Boolean status;

    /**
     * 发送时间
     */
    @TableField(value = "send_time")
    @Schema(description="发送时间")
    private Date sendTime;

    private static final long serialVersionUID = 1L;
}