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
package com.james.client.task;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.client.listener.MessageListenerMulticaster;
import com.james.common.constant.IMRedisKey;
import com.james.common.enums.IMListenerType;
import com.james.common.model.IMSendResult;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author james
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class PrivateMessageResultResultTask extends AbstractMessageResultTask {

    @Resource(name = "IMRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${im.result.batch:100}")
    private int batchSize;

    private final MessageListenerMulticaster listenerMulticaster;

    @Override
    public void pullMessage() {
        List<IMSendResult> results;
        do {
            results = loadBatch();
            if (!results.isEmpty()) {
                listenerMulticaster.multicast(IMListenerType.PRIVATE_MESSAGE, results);
            }
        } while (results.size() >= batchSize);
    }

    List<IMSendResult> loadBatch() {
        String key = StrUtil.join(":", IMRedisKey.IM_RESULT_PRIVATE_QUEUE, appName);
        // 这个接口redis6.2以上才支持
        // List<Object> list = redisTemplate.opsForList().leftPop(key, batchSize);
        List<IMSendResult> results = new LinkedList<>();
         ObjectMapper objectMapper = new ObjectMapper();
         JsonNode json = objectMapper.valueToTree(redisTemplate.opsForList().leftPop(key));
       // JSONObject jsonObject = (JSONObject) redisTemplate.opsForList().leftPop(key);
       while (json.size() > 0 && results.size() < batchSize) {
           try {
               results.add(objectMapper.treeToValue(json, IMSendResult.class));
           } catch (JsonProcessingException | IllegalArgumentException e) {
               e.printStackTrace();
           }
           json = objectMapper.valueToTree(redisTemplate.opsForList().leftPop(key));
       }
        return results;
    }

}
