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
package com.james.platform.exception;

import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.cglib.proxy.UndeclaredThrowableException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.james.common.enums.ErrorMsgEnum;
import com.james.common.result.Result;

import cn.hutool.json.JSONException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 * 
 * @author james
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义的业务异常
     * 
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Result bizExceptionHandler(HttpServletRequest req, BizException e) {
        log.error("发生业务异常！原因是：{}", e.getErrorMsg());
        return Result.error(e.getErrorCode(), e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * 
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Result<Object> exceptionHandler(HttpServletRequest req, NullPointerException e) {
        log.error("发生空指针异常" + "！原因是:", e);
        return Result.error(ErrorMsgEnum.BODY_NOT_MATCH.getCode(), ErrorMsgEnum.BODY_NOT_MATCH.getMsg());
    }

     /**
     * 数据解析错误
     **/
    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public Result handleMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error("全局异常捕获:msg:{}", e.getMessage());
        Throwable t = e.getCause();
        if (t instanceof JSONException) {
            t = t.getCause();
            if (t instanceof DateTimeParseException) {
                return Result.error(ErrorMsgEnum.PARAMETER_ERROR.getCode(), "日期格式不正确");
            }
            return Result.error(ErrorMsgEnum.PARAMETER_ERROR.getCode(), "数据格式不正确");
        }
        return Result.error(ErrorMsgEnum.PARAMETER_ERROR.getMsg());
    }


    /**
     * 处理其他异常
     * 
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, Exception e) {
        if (e instanceof BizException) {
            BizException ex = (BizException) e;
            log.error("全局异常捕获:msg:{},log:{},{}", ex.getMessage(), e);
            return Result.error(ex.errorCode, ex.getMessage());
        } else if (e instanceof UndeclaredThrowableException) {
            BizException ex = (BizException) e.getCause();
            log.error("全局异常捕获:msg:{},log:{},{}", ex.getMessage(), e);
            return Result.error(ex.errorCode, ex.getMessage());
        } else {
            log.error("全局异常捕获:msg:{},{}", e.getMessage(), e);
            return Result.error(ErrorMsgEnum.PARAMETER_ERROR.getMsg());
        }
    }

    /**
     * 处理参数校验不正确异常
     * 
     * @param req
     * @param
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Result exceptionHandler(HttpServletRequest req, MethodArgumentNotValidException e) {

        List<Object> list = new LinkedList<Object>();
        // 解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            list.add(error.getDefaultMessage());
            log.error(error.getDefaultMessage());
        }
        return Result.error(ErrorMsgEnum.PARAMETER_ERROR.getCode(),
                list.toString());
    }
}
