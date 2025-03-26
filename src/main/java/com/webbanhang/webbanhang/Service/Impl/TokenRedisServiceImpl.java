package com.webbanhang.webbanhang.Service.Impl;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webbanhang.webbanhang.Model.ProductModel;
import com.webbanhang.webbanhang.Model.Token;
import com.webbanhang.webbanhang.Model.UserModel;
import com.webbanhang.webbanhang.Service.ITokenRedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenRedisServiceImpl implements ITokenRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Token getToken(String email) {
        String key = String.format("access_token:%s", email);
        try {
            log.info("Đang lấy dữ liệu từ cache Redis...");
            String json = (String) redisTemplate.opsForValue().get(key);
            return json != null ? objectMapper.readValue(json, new TypeReference<Token>() {
            }) : null;
        } catch (Exception e) {
            log.error("Lỗi khi đọc dữ liệu từ Redis: ", e);
            return null;
        }
    }

    @Override
    public void addTokenRedis(String email, Token token) {
        String key = String.format("access_token:%s", email);
        try {
            String json = objectMapper.writeValueAsString(token);
            redisTemplate.opsForValue().set(key, json, Duration.ofMinutes(2));
            log.info("Dữ liệu đã được lưu vào Redis với key: {}", key);
        } catch (Exception e) {
            log.error("Lỗi khi lưu dữ liệu vào Redis: ", e);
        }
    }

    @Override
    public void deleteTokenRedis(String email) {
        try {
            String key = String.format("*%s*", email);
            Set<String> keys = redisTemplate.keys(key);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.info("Đã xóa toàn bộ cache Redis chứa {}", email);
            } else {
                log.info("Không có cache nào chứa '{}' để xóa", email);
            }
        } catch (Exception e) {
            log.error("Lỗi khi xóa cache Redis: {}", e.getMessage(), e);
        }
    }
}
