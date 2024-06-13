package com.james.platform.listener;

import java.util.List;

import com.james.client.listener.MessageListener;
import com.james.platform.pojo.vo.GroupMessageVO;
import org.springframework.data.redis.core.RedisTemplate;

import com.james.client.annotation.IMListener;
import com.james.common.enums.IMListenerType;
import com.james.common.enums.IMSendCode;
import com.james.common.model.IMSendResult;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@IMListener(type = IMListenerType.GROUP_MESSAGE)
@AllArgsConstructor
public class GroupMessageListener implements MessageListener<GroupMessageVO> {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void process(List<IMSendResult> results) {
        for (IMSendResult<GroupMessageVO> result : results) {
            GroupMessageVO messageInfo = result.getData();
            if (result.getCode().equals(IMSendCode.SUCCESS.code())) {
                log.info("消息送达，消息id:{}，发送者:{},接收者:{},终端:{}", messageInfo.getId(), result.getSender().getId(),
                        result.getReceiver().getId(), result.getReceiver().getTerminal());
            }
        }
    }
}
