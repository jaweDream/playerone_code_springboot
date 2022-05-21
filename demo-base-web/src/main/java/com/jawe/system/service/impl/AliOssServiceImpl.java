package com.jawe.system.service.impl;

import cn.hutool.core.date.DateTime;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.*;
import com.jawe.system.entity.AliOssEntity;
import com.jawe.system.service.AliOssService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 在创建这个AliOssServiceImpl的时候就应该把属性值装载好
 *
 * @author NieChangan
 */
@Service
public class AliOssServiceImpl implements AliOssService, InitializingBean {

    @Autowired
    private AliOssEntity aliOssEntity;

    /**
     * Endpoint以杭州为例，其它Region请按实际情况填写。
     */
    private String endpoint;
    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
     */
    private String accessKeyId;
    private String accessKeySecret;
    /**
     * <yourObjectName>从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
     */
    private String bucketName;


    /**
     * 初始化bean之后需要进行的操作
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        endpoint = aliOssEntity.getEndpoint();
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        accessKeyId = aliOssEntity.getAccessKeyId();
        accessKeySecret = aliOssEntity.getAccessKeySecret();
        bucketName = aliOssEntity.getBucketName();
    }

    public Map<String, String> policy(String path) {
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret, bucketName);
        // 用户上传文件时指定的前缀，指定当前日期
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now());
        String dir = path + "/" + date + "/";
        // host的格式为 bucketname.endpoint
        String host = "https://" + bucketName + "." + endpoint;
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);

            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            Map<String, String> respMap = new LinkedHashMap<>();
            respMap.put("accessId", accessKeyId);
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            //目录由前端自己规定
            respMap.put("dir", dir);
            respMap.put("host", host);
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));
            return respMap;
        } catch (Exception e) {
            // Assert.fail(e.getMessage());
            System.out.println(e.getMessage());
            return null;
        } finally {
            ossClient.shutdown();
        }
    }

    /**
     * 创建存储空间
     */
    @Override
    public void createBucket() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        //判断bucketName是否存在
        if (ossClient.doesBucketExist(bucketName)) {
            throw new RuntimeException(bucketName + "在对象存储的Bucket列表中已经存在");
        }
        // 创建存储空间。
        ossClient.createBucket(bucketName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 上传文件
     *
     * @param file 文件对象
     * @return
     */
    @Override
    public String upload(MultipartFile file, String storagePath) {
        //上传的地址
        String uploadUrl = null;
        try {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //判断bucketName是否存在
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置bucket的属性
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取上传的文件流
            InputStream inputStream = file.getInputStream();

            //构建日期的文件夹路径  avatar/2020/10/10/文件名
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //获取上传文件的全名称
            String original = file.getOriginalFilename();

            //获取UUID  a98059d4-633c-4b55-b310-93c997cf8038
            String fileName = UUID.randomUUID().toString().replaceAll("-", "");

            //获取上传文件的扩展名  meizi.jpg
            String fileType = original.substring(original.lastIndexOf("."));

            //拼接文件名称  a98059d4633c4b55b31093c997cf8038.png
            String newName = fileName + fileType;

            //生成文件夹   avatar/2020/10/10/a98059d4633c4b55b31093c997cf8038.png
            fileName = storagePath + "/" + datePath + "/" + newName;

            //如果想要实现图片预览的效果,一定要设置以下几点
            //1.设置文件的ACL(权限)  要么是公共读,要么是公共读写
            //2.一定要设置文本类型(image/jpg)
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //设置公共读权限
            objectMetadata.setObjectAcl(CannedAccessControlList.PublicRead);
            objectMetadata.setContentType(getcontentType(fileType));

            //每次上传得到的名字肯定是不能相同的,在java中如何让每次生成的名字不一样呢?
            //uuid  redis分布式ID 雪花算法   为了更加方便的区分,这边的文件格式yyyy/MM/dd+uuid
            ossClient.putObject(bucketName, fileName, inputStream, objectMetadata);

            // 关闭OSSClient。
            ossClient.shutdown();

            //默认十年不过期
            Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);

            //bucket名称  文件名   过期时间
            uploadUrl = ossClient.generatePresignedUrl(bucketName, fileName, expiration).toString();

            //获取url地址
            //uploadUrl = "https://" + bucketName + "." + endPoint + "/" + fileName;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return uploadUrl.substring(0, uploadUrl.indexOf("?"));
        //return uploadUrl;
//        String fileName = "";
//        try {
//            fileName = UUID.randomUUID().toString();
//            InputStream inputStream = file.getInputStream();
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentLength(inputStream.available());
//            objectMetadata.setCacheControl("no-cache");
//            objectMetadata.setHeader("Pragma", "no-cache");
//            objectMetadata.setContentType(file.getContentType());
//            objectMetadata.setContentDisposition("inline;filename=" + fileName);
//            fileName = storagePath + "/" + fileName;
//            // 上传文件
//            ossClient.putObject(ossConfiguration.getBucketName(), fileName, inputStream, objectMetadata);
//        } catch (IOException e) {
//            log.error("Error occurred: {}", e.getMessage(), e);
//        }
//        return fileName;
    }

    /**
     * 下载文件
     *
     * @param fileName
     * @throws IOException
     */
    @Override
    public void download(String fileName) throws IOException {
        // <yourObjectName>从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 调用ossClient.getObject返回一个OSSObject实例，该实例包含文件内容及文件元信息。
        OSSObject ossObject = ossClient.getObject(bucketName, objectName);
        // 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
        InputStream content = ossObject.getObjectContent();
        if (content != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                System.out.println("\n" + line);
            }
            // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            content.close();
        }
        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 列举文件
     */
    @Override
    public void listFile() {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // ossClient.listObjects返回ObjectListing实例，包含此次listObject请求的返回结果。
        ObjectListing objectListing = ossClient.listObjects(bucketName);
        // objectListing.getObjectSummaries获取所有文件的描述信息。
        for (OSSObjectSummary objectSummary : objectListing.getObjectSummaries()) {
            System.out.println(" - " + objectSummary.getKey() + "  " +
                    "(size = " + objectSummary.getSize() + ")");
        }

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    @Override
    public void deleteFile(String fileName) {
        // <yourObjectName>从OSS下载文件时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        String objectName = fileName;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }

    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpg";
    }

}
