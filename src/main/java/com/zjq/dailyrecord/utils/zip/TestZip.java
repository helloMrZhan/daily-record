package com.zjq.dailyrecord.utils.zip;

import org.junit.Test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class TestZip {

	/**
	 * 测试打包本地的Navicat，输出为zip文件
	 * @throws Exception
	 */
	@Test
	public void test() throws Exception {
		 //Navicat路径
		 String inDir = "E:\\developer\\Navicat";
		 //打包后输出路径
		 String outDir = "E:\\developer\\NavicatZip\\Navicat.zip";
		 OutputStream fileOutputStream = new FileOutputStream(new File(outDir));
		 ZipUtils.toZip(inDir, fileOutputStream);
   }

    /**
     * 测试解压本地zip文件
     * @throws Exception
     */
    @Test
    public void readZip() throws Exception {
        //解压后路径
        String path_to_dest = "E:\\developer\\NavicatUnzip";
        //zip文件路径
        String fileZip = "E:\\developer\\NavicatZip\\Navicat.zip";
        ZipUtils.readZip(fileZip, path_to_dest);
    }
}