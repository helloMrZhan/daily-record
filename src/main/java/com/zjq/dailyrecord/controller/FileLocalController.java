package com.zjq.dailyrecord.controller;

import com.zjq.dailyrecord.common.response.R;
import com.zjq.dailyrecord.file.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * 文件操作接口(本地)
 * @author zjq
 */
@RestController
@RequestMapping("/api/files")
@Api(tags = "文件操作接口(本地)")
public class FileLocalController {

    @PostMapping("/upload")
    @ApiOperation("上传文件")
    @ResponseBody
    public R uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String destination = "D:\\uploads\\" + file.getOriginalFilename();
            FileUtil.decompress(file, destination);
            return R.ok("文件上传成功");
        } catch (Exception e) {
            return R.fail("文件上传失败: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    @ApiOperation("下载文件")
    @ResponseBody
    public R downloadFile(@RequestParam("url") String url, HttpServletResponse response) {
        try {
            String destination = "D:\\downloads\\" + url.substring(url.lastIndexOf('/') + 1);
            FileUtil.downloadPicture(url, destination);
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=" + destination.substring(destination.lastIndexOf('\\') + 1));
            FileUtil.inputStreamToFile(new java.net.URL(url).openStream(), new File(destination));
            return R.ok("文件下载成功");
        } catch (Exception e) {
            return R.fail("文件下载失败: " + e.getMessage());
        }
    }

    @PostMapping("/copy")
    @ApiOperation("复制文件")
    @ResponseBody
    public R copyFile(@RequestParam("source") String source, @RequestParam("dest") String dest) {
        try {
            FileUtil.copyFileUsingFileChannels(source, dest);
            return R.ok("文件复制成功");
        } catch (Exception e) {
            return R.fail("文件复制失败: " + e.getMessage());
        }
    }

    @PostMapping("/write")
    @ApiOperation("写入文件")
    @ResponseBody
    public R writeFile(@RequestParam("filePath") String filePath, @RequestParam("content") String content) {
        try {
            FileUtil.WriteStringToFile(filePath, content);
            return R.ok("文件写入成功");
        } catch (Exception e) {
            return R.fail("文件写入失败: " + e.getMessage());
        }
    }

    @GetMapping("/read")
    @ApiOperation("读取文件")
    @ResponseBody
    public R<String> readFile(@RequestParam("filePath") String filePath) {
        try {
            String content = FileUtil.readFile(filePath);
            return R.ok(content, "文件读取成功");
        } catch (Exception e) {
            return R.fail("文件读取失败: " + e.getMessage());
        }
    }

    @PostMapping("/compress")
    @ApiOperation("压缩文件夹")
    @ResponseBody
    public R compressFolder(@RequestParam("srcPath") String srcPath, @RequestParam("dstPath") String dstPath) {
        try {
            FileUtil.compress(srcPath, dstPath);
            return R.ok("文件夹压缩成功");
        } catch (Exception e) {
            return R.fail("文件夹压缩失败: " + e.getMessage());
        }
    }

    @PostMapping("/decompress")
    @ApiOperation("解压文件")
    @ResponseBody
    public R decompressFile(@RequestParam("zipFile") String zipFile, @RequestParam("dstPath") String dstPath) {
        try {
            FileUtil.decompress(zipFile, dstPath);
            return R.ok("文件解压成功");
        } catch (Exception e) {
            return R.fail("文件解压失败: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除文件夹")
    @ResponseBody
    public R deleteFolder(@RequestParam("path") String path) {
        try {
            FileUtil.delete(path);
            return R.ok("文件夹删除成功");
        } catch (Exception e) {
            return R.fail("文件夹删除失败: " + e.getMessage());
        }
    }
}
