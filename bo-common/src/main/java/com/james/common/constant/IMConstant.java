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
package com.james.common.constant;

/**
 * @author james
 */
public class IMConstant {

    private IMConstant() {
    }

    /**
     * 在线状态过期时间 600s
     */
    public static final long ONLINE_TIMEOUT_SECOND = 600;
    /**
     * 消息允许撤回时间 300s
     */
    public static final long ALLOW_RECALL_SECOND = 300;
}
