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
package com.james.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageStatus {

    UNSEND(0, "未送达"),
    SEND(1, "已送达"),
    RECALL(2, "撤回"),
    READED(3,"已读");


    private final Integer code;

    private final String desc;


    public  Integer getCode() {
        return code;
    }

    public static String getDesc(Integer code) {
        String desc = new String();
        if (code == null) {
            return desc;
        }
        for (int i = 0; i < MessageStatus.values().length; i++) {
            if (code == MessageStatus.values()[i].getCode()) {
                desc = MessageStatus.values()[i].desc;
                break;
            }
        }
        return desc;
    }

}
