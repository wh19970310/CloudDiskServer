package com.suyunpan.filesystem;

import java.io.*;

public class CopyFile {
    private boolean isFile = false;
    private File resource;
    private File target;
    private String resourcePath;
    private String targetPath;

    public boolean fileOperation() {
        try {
            if (isFile) {
                copyFile(resource, target);
            } else {
                copyFolder(resourcePath, targetPath);
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void copyFolder(String resource, String target) throws Exception {

        File resourceFile = new File(resource);
        File targetFile = new File(target);

        if(!targetFile.exists()){
            targetFile.mkdirs();
        }

        File[] resourceFiles = resourceFile.listFiles();
        if(resourceFiles.length == 0){
            new File(target + File.separator + resourceFile.getName()).mkdirs();
        }

        for (File file : resourceFiles) {

            File newFile = new File(targetFile.getAbsolutePath() + File.separator + resourceFile.getName());
            // 复制文件
            if (file.isFile()) {
                // 在 目标文件夹（B） 中 新建 源文件夹（A），然后将文件复制到 A 中
                // 这样 在 B 中 就存在 A
                if (!newFile.exists()) {
                    newFile.mkdirs();
                }
                File targetFile1 = new File(newFile.getAbsolutePath() + File.separator + file.getName());
                copyFile(file, targetFile1);
            }
            // 复制文件夹
            if (file.isDirectory()) {// 复制源文件夹
                String dir1 = file.getAbsolutePath();
                // 目的文件夹
                String dir2 = newFile.getAbsolutePath();
                copyFolder(dir1, dir2);
            }
        }

    }

    public void copyFile(File resource, File target) throws Exception {

        // 文件输入流并进行缓冲
        FileInputStream inputStream = new FileInputStream(resource);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        // 文件输出流并进行缓冲
        FileOutputStream outputStream = new FileOutputStream(target);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);

        // 缓冲数组
        byte[] bytes = new byte[8 * 1024];
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            bufferedOutputStream.write(bytes, 0, len);
        }
        // 刷新输出缓冲流
        bufferedOutputStream.flush();
        //关闭流
        bufferedInputStream.close();
        bufferedOutputStream.close();
        inputStream.close();
        outputStream.close();
    }

    public boolean isFile() {
        return isFile;
    }

    public void setFile(boolean file) {
        isFile = file;
    }

    public File getResource() {
        return resource;
    }

    public void setResource(File resource) {
        this.resource = resource;
    }

    public File getTarget() {
        return target;
    }

    public void setTarget(File target) {
        this.target = target;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public String getTargetPath() {
        return targetPath;
    }

    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }
}