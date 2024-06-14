package com.james.platform.listener;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.james.client.annotation.IMListener;
import com.james.client.listener.MessageListener;
import com.james.common.enums.IMListenerType;
import com.james.common.enums.IMSendCode;
import com.james.common.enums.MessageStatus;
import com.james.common.model.IMSendResult;
import com.james.platform.pojo.entity.PrivateMessage;
import com.james.platform.pojo.vo.PrivateMessageVO;
import com.james.platform.service.PrivateMessageService;

import cn.hutool.core.collection.CollUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author james
 */
@Slf4j
@IMListener(type = IMListenerType.PRIVATE_MESSAGE)
public class PrivateMessageListener implements MessageListener<PrivateMessageVO> {
    
    @Lazy
    @Autowired
    private PrivateMessageService privateMessageService;

    @Override
    public void process(List<IMSendResult> results) {
        Set<Long> messageIds = new HashSet<>();
        for (IMSendResult<PrivateMessageVO> result : results) {
            PrivateMessageVO messageInfo = result.getData();
            // 更新消息状态,这里只处理成功消息，失败的消息继续保持未读状态
            if (result.getCode().equals(IMSendCode.SUCCESS.code())) {
                messageIds.add(messageInfo.getId());
                log.info("消息送达，消息id:{}，发送者:{},接收者:{},终端:{}", messageInfo.getId(), result.getSender().getId(),
                        result.getReceiver().getId(), result.getReceiver().getTerminal());
            }
        }
        // 批量修改状态
        if (CollUtil.isNotEmpty(messageIds)) {
            UpdateWrapper<PrivateMessage> updateWrapper = new UpdateWrapper<>();
            updateWrapper.lambda().in(PrivateMessage::getId, messageIds)
                    .eq(PrivateMessage::getStatus, MessageStatus.UNSEND.code())
                    .set(PrivateMessage::getStatus, MessageStatus.SENDED.code());
            privateMessageService.update(updateWrapper);
        }
    }
}
