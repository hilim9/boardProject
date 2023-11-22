package org.koreait.commons.configs;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.Configs;
import org.koreait.repositories.ConfigsRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {
    private final ConfigsRepository repository;

    public void delete(String code) { // code 값으로 삭제
        Configs configs = repository.findById(code).orElse(null);
        if (configs == null) { // 엔티티를 불러온다음 영속성 안에 넣어준뒤 제거
            return;
        }

        repository.delete(configs);
        repository.flush();
    }
}
