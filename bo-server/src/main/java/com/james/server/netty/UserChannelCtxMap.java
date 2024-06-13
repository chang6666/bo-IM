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
package com.james.server.netty;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

/**
 * @author james
 */
public class UserChannelCtxMap {


    private static Map<Long, Map<Integer, ChannelHandlerContext>> userChannelCtxMap = new ConcurrentHashMap();
    

    public static void addChannelCtx(Long userId, Integer channel, ChannelHandlerContext ctx) {
        userChannelCtxMap.computeIfAbsent(userId, k -> new ConcurrentHashMap<>()).put(channel, ctx);
    }
    
    public static void removeChannelCtx(Long userId, Integer terminal) {
        if (userId != null && terminal != null && userChannelCtxMap.containsKey(userId)) {
            Map<Integer, ChannelHandlerContext> userChannelMap = userChannelCtxMap.get(userId);
            userChannelMap.remove(terminal);
        }
    }

    public static ChannelHandlerContext getChannelCtx(Long userId, Integer terminal) {
        if (userId != null && terminal != null && userChannelCtxMap.containsKey(userId)) {
            Map<Integer, ChannelHandlerContext> userChannelMap = userChannelCtxMap.get(userId);
            if (userChannelMap.containsKey(terminal)) {
                return userChannelMap.get(terminal);
            }
        }
        return null;
    }

    public static Map<Integer, ChannelHandlerContext> getChannelCtx(Long userId) {
        if (userId == null) {
            return null;
        }
        return userChannelCtxMap.get(userId);
    }

}
