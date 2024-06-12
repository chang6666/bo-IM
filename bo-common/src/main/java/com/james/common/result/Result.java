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
package com.james.common.result;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.james.common.constant.CommonConstant;
import com.james.common.utils.CachedTimeUtil;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "返回公共参数")
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 成功标志
     */
    @Schema(name =  "成功标志")
    private boolean success = true;

    /**
     * 返回处理消息
     */
    @Schema(name =  "返回处理消息")
    private String message = "操作成功！";

    /**
     * 返回代码
     */
    @Schema(name =  "返回代码")
    private Integer code = 200;

    /**
     * 返回数据对象 data
     */
    @Schema(name =  "返回数据对象")
    private T data;

    /**
     * 时间戳
     */
    @Schema(name =  "时间戳")
    private long timestamp = CachedTimeUtil.currentTimeMillis();

    public Result() {
    }

    public Result<T> success(String message) {
        this.message = message;
        this.code = CommonConstant.SC_OK_200;
        this.success = true;
        return this;
    }

    public static <T> Result<T> OK() {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setMessage("成功");
        return r;
    }

    public static <T> Result<T> OK(T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> OK(String msg, T data) {
        Result<T> r = new Result<T>();
        r.setSuccess(true);
        r.setCode(CommonConstant.SC_OK_200);
        r.setMessage(msg);
        r.setData(data);
        return r;
    }

    public static <T> Result<T> error(String msg) {
        return error(CommonConstant.SC_INTERNAL_SERVER_ERROR_500, msg);
    }

    public static <T> Result<T> error(int code, String msg) {
        Result<T> r = new Result<T>();
        r.setCode(code);
        r.setMessage(msg);
        r.setSuccess(false);
        return r;
    }

    public Result<T> error500(String message) {
        this.message = message;
        this.code = CommonConstant.SC_INTERNAL_SERVER_ERROR_500;
        this.success = false;
        return this;
    }

    @JsonIgnore
    private String onlTable;

}
