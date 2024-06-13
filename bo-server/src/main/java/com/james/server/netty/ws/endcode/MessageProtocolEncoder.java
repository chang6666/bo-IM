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
package com.james.server.netty.ws.endcode;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.common.model.IMSendInfo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 消息编码器
 * @author james
 */
public class MessageProtocolEncoder extends MessageToMessageEncoder<IMSendInfo> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, IMSendInfo sendInfo, List<Object> list)
            throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String text = objectMapper.writeValueAsString(sendInfo);
        TextWebSocketFrame frame = new TextWebSocketFrame(text);
        list.add(frame);
    }
}
