package com.jawe.system.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface AliOssService {

    Map<String, String> policy(String path);

    /**
     * 创建存储空间
     */
    void createBucket();

    /**
     * 上传文件
     * @param file 文件对象
     * @return
     */
    String upload(MultipartFile file,String storagePath);

    /**
     * 下载文件
     * @throws IOException
     */
    void download(String fileName) throws IOException;

    /**
     * 列举文件
     */
    void listFile();

    /**
     * 删除文件
     */
    void deleteFile(String fileName);

}
