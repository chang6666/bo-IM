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
package com.james.platform.generator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;

/**
 * 代码生成器
 * 
 * @author james
 */
public class CodeGenerator {

        private static String URL = "jdbc:mysql://localhost:3306/bo_im?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai";

        private static String USERNAME = "root";

        private static String PASSWORD = "root";

        public static void main(String[] args) {
                System.out.println(System.getProperty("user.dir"));
                System.exit(0);

                FastAutoGenerator.create(
                                URL,
                                USERNAME,
                                PASSWORD)
                                // 全局配置
                                .globalConfig((scanner, builder) -> {
                                        builder.author(scanner.apply("请输入作者名称？"));
                                        builder.outputDir(System.getProperty("user.dir") + "");
                                        builder.enableSpringdoc();
                                
                                })
                                // 包配置
                                .packageConfig((scanner, builder) -> builder.parent(scanner.apply("请输入包名？")))
                        // 策略配置
                        .strategyConfig((scanner, builder) -> builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                                .entityBuilder()
                                .enableLombok()
                                .addTableFills(
                                        new Column("create_time", FieldFill.INSERT)
                                )
                                .build())
                        // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                        .templateEngine(new VelocityTemplateEngine())
                        .execute();
        }

        // 处理 all 情况
        protected static List<String> getTables(String tables) {
                return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
        }
}
