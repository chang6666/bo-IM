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
 * 群消息
 */
@Schema(description="群消息")
@Data
@Accessors(chain = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "t_group_message")
public class GroupMessage implements Serializable {
    /**
     * id
     */
    @TableId(value = "id", type = IdType.INPUT)
    @Schema(description="id")
    private Long id;

    /**
     * 群id
     */
    @TableField(value = "group_id")
    @Schema(description="群id")
    private Long groupId;

    /**
     * 发送用户id
     */
    @TableField(value = "send_id")
    @Schema(description="发送用户id")
    private Long sendId;

    /**
     * 发送用户昵称
     */
    @TableField(value = "send_nick_name")
    @Schema(description="发送用户昵称")
    private String sendNickName;

    /**
     * 接收用户id,逗号分隔，为空表示发给所有成员
     */
    @TableField(value = "recv_ids")
    @Schema(description="接收用户id,逗号分隔，为空表示发给所有成员")
    private String recvIds;

    /**
     * 发送内容
     */
    @TableField(value = "content")
    @Schema(description="发送内容")
    private String content;

    /**
     * 被@的用户id列表，逗号分隔
     */
    @TableField(value = "at_user_ids")
    @Schema(description="被@的用户id列表，逗号分隔")
    private String atUserIds;

    /**
     * 是否回执消息
     */
    @TableField(value = "receipt")
    @Schema(description="是否回执消息")
    private Byte receipt;

    /**
     * 回执消息是否完成
     */
    @TableField(value = "receipt_ok")
    @Schema(description="回执消息是否完成")
    private Byte receiptOk;

    /**
     * 消息类型 0:文字 1:图片 2:文件 3:语音 4:视频 10:系统提示
     */
    @TableField(value = "`type`")
    @Schema(description="消息类型 0:文字 1:图片 2:文件 3:语音 4:视频 10:系统提示")
    private Boolean type;

    /**
     * 状态 0:未发出 1:已送达  2:撤回 3:已读
     */
    @TableField(value = "`status`")
    @Schema(description="状态 0:未发出 1:已送达  2:撤回 3:已读")
    private Boolean status;

    /**
     * 发送时间
     */
    @TableField(value = "send_time")
    @Schema(description="发送时间")
    private Date sendTime;

    private static final long serialVersionUID = 1L;
}