package com.james.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.james.client.IMClient;
import com.james.common.constant.IMConstant;
import com.james.common.enums.MessageStatus;
import com.james.common.model.IMPrivateMessage;
import com.james.common.model.IMUserInfo;
import com.james.platform.mapper.PrivateMessageMapper;
import com.james.platform.pojo.dto.message.PrivateMessageDTO;
import com.james.platform.pojo.entity.PrivateMessage;
import com.james.platform.pojo.vo.PrivateMessageVO;
import com.james.platform.service.PrivateMessageService;
import com.james.platform.session.UserSession;
import com.james.platform.utils.SensitiveFilterUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrivateMessageServiceImpl extends ServiceImpl<PrivateMessageMapper, PrivateMessage> implements PrivateMessageService {


}

