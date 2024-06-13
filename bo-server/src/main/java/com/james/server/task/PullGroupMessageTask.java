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
package com.james.server.task;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.common.constant.IMRedisKey;
import com.james.common.enums.IMCmdType;
import com.james.common.model.IMRecvInfo;
import com.james.server.netty.IMServerGroup;
import com.james.server.netty.processor.AbstractMessageProcessor;
import com.james.server.netty.processor.ProcessorFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author james
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PullGroupMessageTask extends AbstractPullMessageTask {
    
    private final RedisTemplate redisTemplate;
    
    
    @Override
    public void pullMessage() throws InterruptedException {
        String key = String.join(":", IMRedisKey.IM_MESSAGE_GROUP_QUEUE, IMServerGroup.SERVER_ID + "");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode json = objectMapper.valueToTree(redisTemplate.opsForList().leftPop(key));
        while (json.size()>0) {
            try {
                IMRecvInfo recvInfo = objectMapper.treeToValue(json, IMRecvInfo.class);
                 AbstractMessageProcessor processor = ProcessorFactory.createProcessor(IMCmdType.GROUP_MESSAGE);
                processor.process(recvInfo);
                // 下一条消息
                json = objectMapper.valueToTree(redisTemplate.opsForList().leftPop(key));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }

}
