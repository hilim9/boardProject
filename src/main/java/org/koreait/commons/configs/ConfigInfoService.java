package org.koreait.commons.configs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.Configs;
import org.koreait.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class ConfigInfoService { // 설정 조회 클래스



    private final ConfigsRepository repository;

    public <T> T get(String code, Class<T> clazz) {

        return get(code, clazz, null);
    }

    public <T> T get(String code, TypeReference<T> type) {
        return get(code, null, type);
    }

    public <T> T get(String code, Class<T> clazz, TypeReference<T> typeReference) {

            Configs configs = repository.findById(code).orElse(null);
            if (configs == null || StringUtils.hasText(configs.getValue())) {
                return null;
            }

            String value = configs.getValue();

            ObjectMapper om = new ObjectMapper();
            om.registerModule(new JavaTimeModule());

            try {

                T data = clazz == null ? om.readValue(value, typeReference) : om.readValue(value, clazz);

                return data;

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }

    }
}
