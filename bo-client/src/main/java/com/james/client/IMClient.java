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
package com.james.client;

import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import com.james.client.sender.IMSender;
import com.james.common.enums.IMTerminalType;
import com.james.common.model.IMGroupMessage;
import com.james.common.model.IMPrivateMessage;

import lombok.AllArgsConstructor;

/**
 * @author james
 */
@Configuration
@AllArgsConstructor
public class IMClient {
    private final IMSender imSender;

    /**
     * 判断用户是否在线
     *
     * @param userId 用户id
     */
    public Boolean isOnline(Long userId) {
        return imSender.isOnline(userId);
    }

    /**
     * 判断多个用户是否在线
     *
     * @param userIds 用户id列表
     * @return 在线的用户列表
     */
    public List<Long> getOnlineUser(List<Long> userIds) {
        return imSender.getOnlineUser(userIds);
    }

    /**
     * 判断多个用户是否在线
     *
     * @param userIds 用户id列表
     * @return 在线的用户终端
     */
    public Map<Long, List<IMTerminalType>> getOnlineTerminal(List<Long> userIds) {
        return imSender.getOnlineTerminal(userIds);
    }

    /**
     * 发送私聊消息（发送结果通过MessageListener接收）
     *
     * @param message 私有消息
     */
    public <T> void sendPrivateMessage(IMPrivateMessage<T> message) {
        imSender.sendPrivateMessage(message);
    }

    /**
     * 发送群聊消息（发送结果通过MessageListener接收）
     *
     * @param message 群聊消息
     */
    public <T> void sendGroupMessage(IMGroupMessage<T> message) {
        imSender.sendGroupMessage(message);
    }

}
