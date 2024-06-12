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

/**
 * @author james
 */
public enum ErrorMsgEnum {

    XSS_PARAM_ERROR(1002, "请输入合法内容"),
    INVALID_TOKEN(1001, "token无效"),
    NO_LOGIN(1000, "用户未登录"),
    ILLEGAL_ARGUMENT(2000, "请勿重复提交"),
    BODY_NOT_MATCH(400, "请求的数据格式不符!"),
    SIGNATURE_NOT_MATCH(401, "请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),
    PARAMETER_ERROR(600, "请求参数有误");

    private String msg;
    private int code;

    private ErrorMsgEnum(int code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public static String getMsg(int code) {
        for (ErrorMsgEnum c : ErrorMsgEnum.values()) {
            if (c.getCode() == code) {
                return c.msg;
            }
        }
        return null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
