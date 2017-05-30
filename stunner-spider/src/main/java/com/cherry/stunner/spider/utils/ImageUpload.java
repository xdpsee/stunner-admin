package com.cherry.stunner.spider.utils;


import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

@Component
public class ImageUpload implements InitializingBean{

    @Value("${ak}")
    private String ACCESS_KEY;
    @Value("${sk}")
    private String SECRET_KEY;
    @Value("${bucket}")
    private String bucketName;
    private Auth auth;
    private Zone zone;
    private Configuration configuration;
    private UploadManager uploadManager;

    @Override
    public void afterPropertiesSet() throws Exception {
        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        zone = Zone.zone0();
        configuration = new Configuration(zone);
        uploadManager = new UploadManager(configuration);
    }

    private String getUpToken(String filename) {
        return auth.uploadToken(bucketName, filename, 3600, new StringMap().put("insertOnly", 1));
    }

    public HashAndKey upload(File file) {
        try {
            String sha1 = sha1(file);
            String ext = StringUtils.getFilenameExtension(file.getPath());
            final String filename = String.format("%s.%s", sha1, ext);

            Response res = uploadManager.put(file, filename, getUpToken(filename));
            if (res.isOK()) {
                String bodyStr = res.bodyString();
                HashAndKey result = JSONUtil.fromJsonString(bodyStr, HashAndKey.class);
                System.out.println(res.bodyString());
                return result;
            }
        } catch (QiniuException e) {
            Response r = e.response;
            System.out.println(r.toString());
            try {
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                //ignore
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String sha1(final File file) throws NoSuchAlgorithmException, IOException {
        final MessageDigest messageDigest = MessageDigest.getInstance("SHA1");

        try (InputStream is = new BufferedInputStream(new FileInputStream(file))) {
            final byte[] buffer = new byte[1024];
            for (int read; (read = is.read(buffer)) != -1;) {
                messageDigest.update(buffer, 0, read);
            }
        }

        try (Formatter formatter = new Formatter()) {
            for (final byte b : messageDigest.digest()) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }

    @Data
    public static class HashAndKey {
        private String hash;
        private String key;
    }

}