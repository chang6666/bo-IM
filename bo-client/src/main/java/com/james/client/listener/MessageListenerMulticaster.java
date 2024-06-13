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
package com.james.client.listener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.client.annotation.IMListener;
import com.james.common.enums.IMListenerType;
import com.james.common.model.IMSendResult;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONObject;

/**
 * @author james
 */
@Component
public class MessageListenerMulticaster {

    @Autowired(required = false)
    private List<MessageListener> messageListeners = Collections.emptyList();

    public void multicast(IMListenerType listenerType, List<IMSendResult> results) {
        if (CollUtil.isEmpty(results)) {
            return;
        }
        for (MessageListener listener : messageListeners) {
            IMListener annotation = listener.getClass().getAnnotation(IMListener.class);
            if (annotation != null
                    && (annotation.type().equals(IMListenerType.ALL) || annotation.type().equals(listenerType))) {
                results.forEach(result -> {
                    // 将data转回对象类型
                    if (result.getData() instanceof JSONObject) {
                        Type superClass = listener.getClass().getGenericInterfaces()[0];
                        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode tree = null;
                        try {
                            tree = objectMapper.readTree(result.getData().toString());
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                        result.setData(tree);
                    }
                });
                // 回调到调用方处理
                listener.process(results);
            }
        }
    }

}
