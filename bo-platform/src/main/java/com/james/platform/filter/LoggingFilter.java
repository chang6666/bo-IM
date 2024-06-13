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
package com.james.platform.filter;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.james.common.utils.CachedTimeUtil;
import com.james.platform.utils.LogMaskingUtil;

import cn.hutool.core.util.IdUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

/**
 * @author James
 */
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private final ExecutorService virtualThreadExecutor = Executors.newVirtualThreadPerTaskExecutor();


    @Value("${spring.threads.virtual.enabled:false}")
    private Boolean isLoggingEnabled;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Long startTime = CachedTimeUtil.currentTimeMillis();
        CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);

        CachedBodyHttpServletResponse cachedBodyHttpServletResponse = new CachedBodyHttpServletResponse(response);

        String traceId = IdUtil.fastUUID();

        if (isLoggingEnabled) {
            virtualThreadExecutor.execute(() -> logRequestDetails(traceId,cachedBodyHttpServletRequest));
        } else {
            logRequestDetails(traceId,cachedBodyHttpServletRequest);
        }

      

        filterChain.doFilter(cachedBodyHttpServletRequest, cachedBodyHttpServletResponse);

        logger.info("trace_id: {}, Response Status: {}",traceId, response.getStatus());
        String responseBody = cachedBodyHttpServletResponse.getContent();
        copyBodyToResponse(response, responseBody);
        long duration = CachedTimeUtil.currentTimeMillis() - startTime;
        logger.info("trace_id: {}, Response Body: {}, took {} ms", traceId, responseBody, duration);
    }

    private void copyBodyToResponse(HttpServletResponse response, String responseBody) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(responseBody.getBytes());
        outputStream.flush();
    }

    private void logRequestDetails(String traceId, HttpServletRequest request) {
        try {
            // Log the request URI and HTTP method
            logger.info("trace_id: {}, Request URI: {}, HTTP Method: {}", traceId, request.getRequestURI(),
                    request.getMethod());

            // Log the request headers
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                logger.info("trace_id: {}, Header: {} = {}", traceId, headerName, request.getHeader(headerName));
            }

            // Log the request parameters
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {
                String parameterName = parameterNames.nextElement();
                String maskSensitiveData = LogMaskingUtil.maskSensitiveData(parameterName,request.getParameter(parameterName),false);
                logger.info("trace_id: {}, Parameter: {} = {}", traceId, parameterName,
                        maskSensitiveData);
            }

            // Log the request body (for POST/PUT requests)
            if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
                String requestBody = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode tree = objectMapper.readTree(requestBody);
                // 将JsonNode转换为ObjectNode
                ObjectNode objectNode = (ObjectNode) tree;
                Iterator<Entry<String, JsonNode>> fields = tree.fields();
                while (fields.hasNext()) {
                    Entry<String, JsonNode> field = fields.next();
                    String key = field.getKey();
                    String value = tree.get(key).asText();
                    String maskSensitiveData = LogMaskingUtil.maskSensitiveData(key, value);
                    objectNode.put(key, maskSensitiveData);
                }
                logger.info("trace_id: {}, Request Body: {}", traceId, objectNode);
            }
        } catch (IOException e) {
            logger.error("trace_id: {}, Error logging request details", traceId, e);
        }
    }
    
    

    private static class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

        private byte[] cachedBody;

        public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
            super(request);
            cacheInputStream(request);
        }

        private void cacheInputStream(HttpServletRequest request) throws IOException {
            InputStream requestInputStream = request.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = requestInputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            this.cachedBody = byteArrayOutputStream.toByteArray();
        }

        @Override
        public ServletInputStream getInputStream() {
            return new CachedBodyServletInputStream(this.cachedBody);
        }

        @Override
        public BufferedReader getReader() {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
            return new BufferedReader(new InputStreamReader(byteArrayInputStream));
        }
    }

    private static class CachedBodyServletInputStream extends ServletInputStream {

        private final ByteArrayInputStream byteArrayInputStream;

        public CachedBodyServletInputStream(byte[] cachedBody) {
            this.byteArrayInputStream = new ByteArrayInputStream(cachedBody);
        }

        @Override
        public boolean isFinished() {
            return this.byteArrayInputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int read() {
            return this.byteArrayInputStream.read();
        }
    }

    private static class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

        private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        private final ServletOutputStream servletOutputStream = new CachedBodyServletOutputStream(outputStream);
        private final PrintWriter writer = new PrintWriter(servletOutputStream);

        public CachedBodyHttpServletResponse(HttpServletResponse response) {
            super(response);
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return servletOutputStream;
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        public String getContent() {
            writer.flush();
            return outputStream.toString();
        }

        private static class CachedBodyServletOutputStream extends ServletOutputStream {

            private final ByteArrayOutputStream outputStream;

            public CachedBodyServletOutputStream(ByteArrayOutputStream outputStream) {
                this.outputStream = outputStream;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void write(int b) {
                outputStream.write(b);
            }

            @Override
            public void setWriteListener(WriteListener listener) {
                throw new UnsupportedOperationException("Unimplemented method 'setWriteListener'");
            }
        }
    }
}
