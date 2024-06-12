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
package com.james.platform.service.impl;

import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.common.result.Result;
import com.james.common.utils.JwtUtil;
import com.james.platform.config.JwtProperties;
import com.james.platform.mapper.UserMapper;
import com.james.platform.pojo.dto.login.LoginDto;
import com.james.platform.pojo.entity.User;
import com.james.platform.pojo.vo.LoginVO;
import com.james.platform.service.LoginService;
import com.james.platform.session.UserSession;
import com.james.platform.utils.BeanUtil;

import lombok.RequiredArgsConstructor;

/**
 * @author James
 */
@Service
@RequiredArgsConstructor
public class LoginServiceImpl extends ServiceImpl<UserMapper, User> implements LoginService {
    
    private final PasswordEncoder passwordEncoder;

    private final JwtProperties jwtProperties;
    
    @Override
    public Result login(final LoginDto dto) {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(User::getUserName, dto.getUserName());
        User user = this.getOne(lambdaQuery);
        if (Objects.isNull(user)) {
            return Result.error("用户不存在");
        }
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            return Result.error("密码输入有误");
        }

        UserSession userSession = BeanUtil.copyProperties(user, UserSession.class);
        userSession.setUserId(user.getId());
        userSession.setTerminal(dto.getTerminal());

        ObjectMapper objectMapper = new ObjectMapper();
        String strJson;
        try {
            strJson = objectMapper.writeValueAsString(userSession);
            String accessToken = JwtUtil.sign(user.getId(), strJson, jwtProperties.getAccessTokenExpireIn(),
                    jwtProperties.getAccessTokenSecret());
            String refreshToken = JwtUtil.sign(user.getId(), strJson, jwtProperties.getRefreshTokenExpireIn(),
                    jwtProperties.getRefreshTokenSecret());
            LoginVO vo = new LoginVO();
            vo.setAccessToken(accessToken);
            vo.setAccessTokenExpiresIn(jwtProperties.getAccessTokenExpireIn());
            vo.setRefreshToken(refreshToken);
            vo.setRefreshTokenExpiresIn(jwtProperties.getRefreshTokenExpireIn());
            return Result.OK(vo);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Result.error("登录失败");
        }
    }

    

}
