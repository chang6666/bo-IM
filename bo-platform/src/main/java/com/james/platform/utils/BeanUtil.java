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
package com.james.platform.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/** 
 * @author james
 */
public class BeanUtil {

    private BeanUtil() {

    }

    public static <T> T copyProperties(Object source, Class<T> clazz) {

        try {
            // 假设我们想要实例化一个无参构造函数的类
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            // 如果构造函数是私有的，需要设置为可访问
            constructor.setAccessible(true);
            T target = constructor.newInstance();
            copyProperties(source, target);
            return target;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException
                | InvocationTargetException e) {
            // 这里可以根据不同的异常类型做更精确的错误处理
            e.printStackTrace();
        }
        return null;
    }

    public static void copyProperties(Object source, Object target) {
        org.springframework.beans.BeanUtils.copyProperties(source, target);
    }

}
