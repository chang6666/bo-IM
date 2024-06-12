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
package com.james.platform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.james.platform.interceptor.AuthInterceptor;
import com.james.platform.interceptor.XssInterceptor;

import lombok.AllArgsConstructor;

/**
 * @author james
 */
@Configuration
@AllArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

        private final AuthInterceptor authInterceptor;
        private final XssInterceptor xssInterceptor;

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(xssInterceptor)
                                .addPathPatterns("/**")
                                .excludePathPatterns("/error");
                registry.addInterceptor(authInterceptor)
                                .addPathPatterns("/**")
                                .excludePathPatterns("/api/login/", "/api/logout/", "/api/register/",
                                                "/api/refreshToken",
                                                "/swagger-resources/**", "/webjars/**", "/v3/**",
                                                "/swagger-ui.html/**");
        }

}
