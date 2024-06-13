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
package com.james.common.constant;

/**
 * @author james
 */
public class IMRedisKey {

    private IMRedisKey() {
    }

    /**
     * im-server最大id,从0开始递增
     */
    public static final String IM_MAX_SERVER_ID = "im:max_server_id";
    /**
     * 用户ID所连接的IM-server的ID
     */
    public static final String IM_USER_SERVER_ID = "im:user:server_id";
    /**
     * 未读私聊消息队列
     */
    public static final String IM_MESSAGE_PRIVATE_QUEUE = "im:message:private";
    /**
     * 未读群聊消息队列
     */
    public static final String IM_MESSAGE_GROUP_QUEUE = "im:message:group";
    /**
     * 私聊消息发送结果队列
     */
    public static final String IM_RESULT_PRIVATE_QUEUE = "im:result:private";
    /**
     * 群聊消息发送结果队列
     */
    public static final String IM_RESULT_GROUP_QUEUE = "im:result:group";

}
