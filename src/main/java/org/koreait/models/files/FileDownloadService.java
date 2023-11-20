package org.koreait.models.files;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.FileInfo;
import org.springframework.stereotype.Service;

import java.io.*;


/**
 * 파일 다운로드 서비스를 제공하는 클래스.
 * 지정된 ID를 사용하여 FileInfoService를 통해 파일 정보를 가져온 후,
 * 해당 파일을 읽어서 HTTP 응답 스트림에 쓰는 역할을 수행.
 * 다운로드할 파일의 이름과 형식을 설정하고, 파일의 크기와 캐시 관련 헤더도 설정하여
 * 파일 다운로드를 처리.
 */
@Service
@RequiredArgsConstructor
public class FileDownloadService {

    private final HttpServletResponse response;
    private final FileInfoService infoService;

    public void download(Long id) {

        // 지정된 id를 가지고 FileInfoService를 통해 FileInfo 객체를 가져온다
        FileInfo item = infoService.get(id);
        String filePath = item.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) { // 파일이 존재하지 않는 경우
            throw new FileNotFoundException();
        }


        /**
         * 기본적으로 windows 한글 인코딩 방식은 2byte
         * 서버는 3byte이므로 한글 인식하지 못한다
         * 인코딩 방식을 2byte로 변경해야 한글을 인식한다
         */
        String fileName = item.getFileName(); // 파일 이름
        try {
            fileName = new String(fileName.getBytes(), "ISO8859_1"); // 2byte 유니코드
        } catch (UnsupportedEncodingException e) {}

        try(FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis)) {

            OutputStream out = response.getOutputStream();

            // HTTP 응답 헤더 설정
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName); // 다운로드 설정
            response.setHeader("Content-Type", "application/octet-stream"); // 파일 형식
            response.setHeader("Cache-Control", "must-revalidate"); // 캐시 제어 (캐시 갱신)
            response.setHeader("Pragma", "public"); // 프라그마 설정 (오래된 브라우저)
            response.setIntHeader("Expires", 0); // 브라우저가 만료되지 않게 설정
            response.setHeader("Content-Length", "" + file.length()); // 파일 크기 설정

            while(bis.available() > 0) {
                out.write(bis.read());
            }

            out.flush();
        } catch (IOException e) {
            e.printStackTrace(); // 입출력 예외 발생 시 예외 정보 출력
        }

    }
}
