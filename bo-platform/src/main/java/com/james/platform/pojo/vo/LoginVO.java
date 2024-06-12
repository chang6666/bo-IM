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
package com.james.platform.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description= "登录VO")
public class LoginVO {


    @Schema(description = "每次请求都必须在header中携带accessToken")
    private String accessToken;

    @Schema(description = "accessToken过期时间(秒)")
    private Integer accessTokenExpiresIn;

    @Schema(description = "accessToken过期后，通过refreshToken换取新的token")
    private String refreshToken;

    @Schema(description = "refreshToken过期时间(秒)")
    private Integer refreshTokenExpiresIn;

}
