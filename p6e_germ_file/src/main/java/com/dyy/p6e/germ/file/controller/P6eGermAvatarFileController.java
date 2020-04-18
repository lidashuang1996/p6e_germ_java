package com.dyy.p6e.germ.file.controller;

import com.dyy.p6e.germ.file.config.P6eGermFileConfig;
import com.dyy.p6e.germ.file.controller.support.P6eGermBaseController;
import com.dyy.p6e.germ.file.model.P6eGermFileResultModel;
import com.dyy.p6e.germ.file.model.P6eGermResultConfig;
import com.dyy.p6e.germ.file.model.P6eGermResultModel;
import com.dyy.p6e.germ.file.utils.P6eGermBasicsUtils;
import com.dyy.p6e.germ.file.utils.P6eGermImgUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 文件上传下载的接口
 */
@RestController
@RequestMapping("/file/avatar")
public class P6eGermAvatarFileController extends P6eGermBaseController {

    private String basePath;
    private P6eGermFileConfig.Setting setting;

    @Autowired
    public P6eGermAvatarFileController(P6eGermFileConfig config) {
        this.basePath = config.getBasePath();
        this.setting = config.getMap().get("avatar");
    }

    @PostMapping("/upload")
    public P6eGermResultModel upload(@RequestParam("file") MultipartFile multipartFile) {
        P6eGermResultModel result = null;
        try {
            loggerRequest("FILE UPDATE REQUEST");
            // 文件大小验证
            long multipartFileSize = multipartFile.getSize();
            if (setting != null && setting.getSize() != null && multipartFileSize > setting.getSize()) {
                result = P6eGermResultModel.create(P6eGermResultConfig.ERROR_FILE_MAX_LENGTH);
            } else {
                // 文件格式验证
                String multipartFileName = multipartFile.getOriginalFilename();
                String fileFormat = P6eGermBasicsUtils.fileFormatValidation(
                        multipartFileName, P6eGermBasicsUtils.formatCompile(setting.getFormat()));
                if (fileFormat == null) result = P6eGermResultModel.create(P6eGermResultConfig.ERROR_FILE_FORMAT);
                else {
                    // 文件验证
                    P6eGermImgUtils p6eGermImgUtils = P6eGermImgUtils.create(multipartFile.getInputStream());
                    if (!p6eGermImgUtils.isSquare())
                        result = P6eGermResultModel.create(P6eGermResultConfig.ERROR_FILE_NO_SQUARE);
                    else {
                        // 唯一 ID
                        String unique = P6eGermBasicsUtils.generateUnique();
                        // 当前日期的时间路径
                        String datePath = P6eGermBasicsUtils.currentDatePath();

                        String fileNameBig = "/big/" + datePath + "/" + unique + "." + fileFormat;
                        String fileNameSmall = "/small/" + datePath + "/" + unique + "." + fileFormat;

                        // 暴露的文件路径
                        String filePathBig = URLDecoder.decode(setting.getPath() + fileNameBig, "UTF-8");
                        String filePathSmall = URLDecoder.decode(setting.getPath() + fileNameSmall, "UTF-8");

                        // 写入的文件路径
                        String wFilePathBig = URLDecoder.decode(basePath + filePathBig, "UTF-8");
                        String wFilePathSmall = URLDecoder.decode(basePath + filePathSmall, "UTF-8");

                        // 写入数据到文件
                        p6eGermImgUtils.toBigImg(fileFormat, wFilePathBig);
                        p6eGermImgUtils.toSmallImg(fileFormat, wFilePathSmall);

                        logger("[ BIG ] FILE NAME: " + multipartFileName
                                + ", SIZE: " + multipartFileSize + " => " + wFilePathBig);
                        logger("[ SMALL ] FILE NAME: " + multipartFileName
                                + ", SIZE: " + multipartFileSize + " => " + wFilePathSmall);

                        result = P6eGermResultModel.create(P6eGermResultConfig.SUCCESS_FILE,
                                new P6eGermFileResultModel(multipartFileSize, multipartFileName, filePathBig, filePathSmall));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = P6eGermResultModel.create(P6eGermResultConfig.ERROR_FILE_UPDATE);
        } finally {
            loggerResponse("FILE UPDATE RESPONSE => " + result);
        }
        return result;
    }

    @GetMapping("/download")
    public P6eGermResultModel download(String path) {
        P6eGermResultModel result = null;
        FileInputStream fileInputStream = null;
        FileChannel fileChannel = null;
        try {
            loggerRequest("FILE DOWNLOAD REQUEST, FILE PATH : " + path);
            if (path == null || "".equals(path)) result = P6eGermResultModel.create(P6eGermResultConfig.ERROR_PARAM_EXCEPTION);
            else {
                File file = new File(basePath + path);
                if (!file.exists()) result = P6eGermResultModel.create(P6eGermResultConfig.ERROR_FILE_NO_EXISTS);
                else {
                    HttpServletResponse response = getResponse();
                    response.setHeader("content-type", "application/octet-stream");
                    response.setContentType("application/octet-stream");
                    response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
                    fileInputStream = new FileInputStream(file);
                    fileChannel = fileInputStream.getChannel();
                    int nGet;
                    int bufferSize = 2048;
                    byte[] byteArr = new byte[bufferSize];
                    // 创建直接缓冲区大小为 4096
                    ByteBuffer buff = ByteBuffer.allocateDirect(4096);
                    // 循环读取文件的内容到直接缓冲区，直到文件读取完毕
                    while(fileChannel.read(buff) != -1){
                        buff.flip();
                        while (buff.hasRemaining()) { // 循环读取数据直到缓冲区的数据读取完成
                            nGet = Math.min(buff.remaining(), bufferSize);
                            // read bytes from disk
                            buff.get(byteArr, 0, nGet);
                            // write bytes to output
                            response.getOutputStream().write(byteArr);
                        }
                        buff.clear();
                    }
                    response.getOutputStream().flush();
                    result = P6eGermResultModel.create(P6eGermResultConfig.SUCCESS_FILE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result = P6eGermResultModel.create(P6eGermResultConfig.ERROR_FILE_DOWNLOAD);
        } finally {
            loggerResponse("FILE DOWNLOAD RESPONSE => " + result);
            try {
                if (fileChannel != null) fileChannel.close();
                if (fileInputStream != null) fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (result.getCode() != 200) return result;
        else return null;
    }

}
