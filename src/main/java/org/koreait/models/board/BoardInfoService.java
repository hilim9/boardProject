package org.koreait.models.board;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.BoardData;
import org.koreait.models.board.config.BoardConfigInfoService;
import org.koreait.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardInfoService {

    private final BoardDataRepository boardDataRepository;
    private final BoardConfigInfoService configInfoService;

    public BoardData get(Long seq) {
        return get(seq, "view");
    }

    public BoardData get(Long seq, String location) {

        BoardData data = boardDataRepository.findById(seq).orElseThrow(BoardDataNotFoundException::new);

        // 게시판 설정 조회 + 접근 권한체크
        configInfoService.get(data.getBoard().getBId(), location);

        return data;
    }
}
