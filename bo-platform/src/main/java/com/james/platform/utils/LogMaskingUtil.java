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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 日志脱敏工具
 * @author James
 */
public class LogMaskingUtil {

    //邮箱
    private static final Pattern EMAIL_PATTERN = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
    //  手机
    private static final Pattern PHONE_PATTERN = Pattern.compile("\\b(\\d{3})\\d{4}(\\d{4})\\b");
    //身份证
    private static final Pattern ID_PATTERN = Pattern.compile("\\b(\\d{6})\\d{8}(\\d{4})\\b");
    //姓名
    private static final Pattern NAME_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]{2,}", Pattern.CASE_INSENSITIVE);
    //密码
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("(?<=.{2}).(?=.*.{2})");
    //车牌
    private static final Pattern PLATE_PATTERN = Pattern.compile("[\\u4e00-\\u9fa5]{1}[A-Z0-9]{6}");
    //银行卡
    private static final Pattern BANK_CARD_PATTERN = Pattern.compile("\\b(\\d{4})\\d{12}(\\d{4})\\b");
    // 座机
    private static final Pattern LANDLINE_PATTERN = Pattern.compile("\\b(\\d{3,4}-)?\\d{7,8}\\b");
    // 地址
    private static final Pattern ADDRESS_PATTERN = Pattern.compile(".{5,}(?=.{4})");


    public static String maskSensitiveData(String name,String data,Boolean isMask) {
        if (data == null) {
            return null;
        }
        Pattern pattern;
        pattern =  Pattern.compile("email", Pattern.CASE_INSENSITIVE);
        Matcher matcher;
        matcher = pattern.matcher(name);
       if (matcher.find()) {
           data = EMAIL_PATTERN.matcher(data).replaceAll("***@***.com");
           return data;
        }
        pattern = Pattern.compile("phone", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            data = PHONE_PATTERN.matcher(data).replaceAll("$1****$2");
            return data;

        }
        pattern = Pattern.compile("id", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            data = ID_PATTERN.matcher(data).replaceAll("$1********$2");
            return data;
        }
        pattern = Pattern.compile("name", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            data = NAME_PATTERN.matcher(data).replaceAll("$0**");
            return data;
        }
        pattern = Pattern.compile("password", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            data = PASSWORD_PATTERN.matcher(data).replaceAll("*");
            return data;
        }

        pattern = Pattern.compile("plate", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            data = PLATE_PATTERN.matcher(data).replaceAll("***$0");
            return data;
        }

        pattern = Pattern.compile("bankCard", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            data = BANK_CARD_PATTERN.matcher(data).replaceAll("$1************$2");
            return data;
        }

        pattern = Pattern.compile("landline", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            data = LANDLINE_PATTERN.matcher(data).replaceAll("$1****");
            return data;
        }

        pattern = Pattern.compile("address", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(name);
        if (matcher.find()) {
            data = ADDRESS_PATTERN.matcher(data).replaceAll("***$0");
            return data;
        }
        return data;
    }


    public static String maskSensitiveData(String name, String data) {
        if (data == null) {
            return null;
        }
        switch (name) {
            case "email":
                return EMAIL_PATTERN.matcher(data).replaceAll("***@***.com");
            case "phone":
                return PHONE_PATTERN.matcher(data).replaceAll("$1****$2");
            case "id":
                return ID_PATTERN.matcher(data).replaceAll("$1********$2");
            case "name":
                return NAME_PATTERN.matcher(data).replaceAll("$0**");
            case "password":
                return PASSWORD_PATTERN.matcher(data).replaceAll("*");
            case "plate":
                return PLATE_PATTERN.matcher(data).replaceAll("***$0");
            case "bankCard":
                return BANK_CARD_PATTERN.matcher(data).replaceAll("$1************$2");
            case "landline":
                return LANDLINE_PATTERN.matcher(data).replaceAll("$1****");
            case "address":
                return ADDRESS_PATTERN.matcher(data).replaceAll("***$0");
            default:
                return data;
        }
    }


    public static void main(String[] args) {
        String maskSensitiveData = maskSensitiveData("CnbcnsiPASSWORD", "Cnbaisj1314.", false);
        System.out.println(maskSensitiveData);
    }

}
