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

import java.util.Date;
import java.util.Objects;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.james.common.result.Result;
import com.james.platform.mapper.UserMapper;
import com.james.platform.pojo.dto.register.RegisterDto;
import com.james.platform.pojo.entity.User;
import com.james.platform.service.RegisterService;

import lombok.RequiredArgsConstructor;

/**
 * <p>
 * 注册实现类
 * </p>
 * @author James
 */
@Service
@RequiredArgsConstructor
public class RegisterServiceImpl extends ServiceImpl<UserMapper, User> implements RegisterService {

  
    private final PasswordEncoder passwordEncoder;

    @Override
    public Result register(final RegisterDto registerDto) {
       User userByUserName = getUserByUserName(registerDto.getUserName());
        
        if (!Objects.isNull(userByUserName)) {
            return Result.error("用户名已存在");
        }
        String encode = passwordEncoder.encode(registerDto.getPassword());
        User user = User.builder()
                .nickName(registerDto.getNickName())
                .userName(registerDto.getUserName())
                .password(encode)
                .createdTime(new Date())
                .build();
        this.save(
                user);
       return Result.OK();
    }

    @Override
    public User getUserByUserName(String userName) {
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(User::getUserName, userName);
        User user = this.getOne(lambdaQuery);
        return user;
    }

}