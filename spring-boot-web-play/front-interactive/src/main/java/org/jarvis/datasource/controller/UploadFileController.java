package org.jarvis.consumer.controller;

import org.jarvis.consumer.model.MultipartFileParam;
import org.jarvis.consumer.model.Result;
import org.jarvis.web.service.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * author:tennyson date:2020/6/24
 **/
@RestController
@RequestMapping(value = "/upload")
public class UploadFileController {
    Logger logger = LoggerFactory.getLogger(UploadFileController.class);
    @Autowired
    private StorageService storageService;

    /**
     * 上传文件
     *
     * @param file
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/save")
    public Map<String, String> uploadChunk(@RequestParam MultipartFile file) throws IOException {
        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //合并文件
        RandomAccessFile raFile = null;
        BufferedInputStream inputStream = null;
        try {
            File dirFile = new File("D:\\WorkSpaces", originalFilename);
            //以读写的方式打开目标文件
            raFile = new RandomAccessFile(dirFile, "rw");
            raFile.seek(raFile.length());
            inputStream = new BufferedInputStream(file.getInputStream());
            byte[] buf = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buf)) != -1) {
                raFile.write(buf, 0, length);
            }
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (raFile != null) {
                    raFile.close();
                }
            } catch (Exception e) {
                throw new IOException(e.getMessage());
            }
        }
        //以下信息没用。比较扯淡
        //初始化返回信息
        Map<String, String> map = new HashMap<String, String>();
        String result = "";
        int res = -1;
        //返回提示信息
        map.put("result", result);
        return map;
    }

    @PostMapping(value = "/v1/single")
    public String uploadSingle(@RequestParam MultipartFile multipartFile, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/uploadFile");
        String formatedTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of(ZoneOffset.of("+8").getId())).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        File folder = new File(realPath + formatedTime);
        if (!folder.exists()) {
            folder.mkdir();
        }
        String oldName = multipartFile.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.indexOf("."));
        try {
            multipartFile.transferTo(new File(folder, newName));
            String filePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/uploadFile/" + formatedTime + newName;
            return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "upload failure";
    }

    @PostMapping(value = "v1/batch")
    public String uploadBatch(@RequestParam(name = "uploadFile") MultipartFile[] multipartFiles, HttpServletRequest request) {
        String realPath = request.getSession().getServletContext().getRealPath("/uploadFile");
        String formatedTime = LocalDateTime.ofInstant(Instant.now(), ZoneId.of(ZoneOffset.of("+8").getId())).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        File folder = new File(realPath + formatedTime);
        if (!folder.exists()) {
            folder.mkdir();
        }
        Consumer<MultipartFile> action = file -> {
            String oldName = file.getOriginalFilename();
            assert oldName != null;
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.indexOf("."));
            try {
                file.transferTo(new File(folder, newName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        Arrays.stream(multipartFiles).forEach(action);
        return "upload success";
    }

    /**
     * 上传文件
     *
     * @param param
     * @param request
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/v2/large")
    @ResponseBody
    public Result<String> fileUpload(MultipartFileParam param, HttpServletRequest request) {
//        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//        String parentId = request.getParameter("parentId");
//        if (isMultipart) {
//            logger.info("上传文件start。");
//            try {
//                storageService.uploadFileRandomAccessFile(param);
//            } catch (IOException e) {
//                e.printStackTrace();
//                logger.error("文件上传失败。{}", param.toString());
//                return new Result<>("上传失败");
//            }
//            logger.info("上传文件end。");
//        }
        return new Result<>("上传成功");
    }

    /**
     * 秒传判断，断点判断
     *
     * @return
     */
    @RequestMapping(value = "checkFileMd5", method = RequestMethod.POST)
    @ResponseBody
    public Object checkFileMd5(String md5) throws IOException {
//        Object processingObj = stringRedisTemplate.opsForHash().get(Constants.FILE_UPLOAD_STATUS, md5);
//        if (processingObj == null) {
//            return new ResultVo(ResultStatus.NO_HAVE);
//        }
//        String processingStr = processingObj.toString();
//        boolean processing = Boolean.parseBoolean(processingStr);
//        String value = stringRedisTemplate.opsForValue().get(Constants.FILE_MD5_KEY + md5);
//        if (processing) {
//            return new ResultVo(ResultStatus.IS_HAVE, value);
//        } else {
//            File confFile = new File(value);
//            byte[] completeList = FileUtils.readFileToByteArray(confFile);
//            List<String> missChunkList = new LinkedList<>();
//            for (int i = 0; i < completeList.length; i++) {
//                if (completeList[i] != Byte.MAX_VALUE) {
//                    missChunkList.add(i + "");
//                }
//            }
        return new Result<>("ResultStatus.ING_HAVE, missChunkList");
    }
}
