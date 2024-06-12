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
package com.james.platform.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.james.common.enums.ErrorMsgEnum;
import com.james.common.utils.JwtUtil;
import com.james.platform.config.JwtProperties;
import com.james.platform.exception.BizException;
import com.james.platform.session.UserSession;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author James
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {


     private final JwtProperties jwtProperties;

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) throws Exception {
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        //从 http 请求头中取出 token
        String token = request.getHeader("accessToken");
        if (StrUtil.isEmpty(token)) {
            log.error("未登录,url:{}", request.getRequestURI());
            throw new BizException(ErrorMsgEnum.NO_LOGIN.getMsg());
        }
        //验证 token
        if (!JwtUtil.checkSign(token, jwtProperties.getAccessTokenSecret())) {
            log.error("token已失效,url:{}", request.getRequestURI());
            throw new BizException(ErrorMsgEnum.INVALID_TOKEN.getMsg());
        }
        // 存放session
        String strJson = JwtUtil.getInfo(token);
        ObjectMapper objectMapper = new ObjectMapper();
        UserSession userSession = objectMapper.readValue(strJson, UserSession.class);
        request.setAttribute("session", userSession);
        return true;
    }


}
