package org.koreait.models.files;

import org.koreait.commons.exceptions.CommonException;

public class FileNotFoundException extends CommonException {

    public FileNotFoundException() {
        super("파일을 찾을수 없습니다");
    }
}
