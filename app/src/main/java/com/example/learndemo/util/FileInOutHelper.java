
package com.example.learndemo.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class FileInOutHelper {

    private static final int K = 1024;
    private static final int INDEX5 = 5;
    private static final int INDEX6 = 6;
    
    private FileInOutHelper() {
    }
    

    public static File setupOrOpenFile(String aPath){
    	String name = aPath.substring(aPath.lastIndexOf("/")+1);
    	String path = aPath.substring(0,aPath.lastIndexOf("/")+1);
    	//Log.i("FileInOutHelper", "name = " + name + "path = " + path);
    	return setupOrOpenFile(path, name);
    }
    
    public static File setupOrOpenFile(String aPath, String aFileName) {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
            .getExternalStorageState())) {
            file = new File(aPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            if (!aPath.endsWith("/")) {
                aPath += "/";
            }
            file = new File(aPath + aFileName);
            if (!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return file;
    }


    public static boolean renameFile(String aPath, String aCurrentName,
                                     String aReName) {
        File currentFile = null;
        File reFile = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
            .getExternalStorageState())) {
            if (!aPath.endsWith("/")) {
                aPath += "/";
            }
            currentFile = new File(aPath + aCurrentName);
            reFile = new File(aPath + aReName);
            if (currentFile.exists()) {
                currentFile.renameTo(reFile);
            }

        }
        return false;
    }

  
    public static byte[] loadFdisk(String aPath, String aFileName) {
        if (!aPath.endsWith("/")) {
            aPath += "/";
        }
        return loadFdisk(aPath);
    }

  
    public static byte[] loadFdisk(String aPath) {
        byte[] temp = null;
        File file = null;

        file = new File(aPath);
        if (!file.exists() && file.length() <= 0 && file.isDirectory()) {
            return temp;
        }
        temp = new byte[(int) file.length()];
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
            fis.read(temp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return temp;
        }
        return temp;
    }


    public static boolean fileIsExists(String aPath, String aFileName) {
        if (!aPath.endsWith("/")) {
            aPath += "/";
        }
        return fileIsExists(aPath + aFileName);
    }

 
    public static boolean fileIsExists(String aPath) {
        File file = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
            .getExternalStorageState())) {
            file = new File(aPath);
            if (file.exists()) {
                return true;
            }
        }
        return false;
    }


    public static boolean addToFile(String aPath, String aFileName,
                                    byte[] aData, int aLength) {
        if (!aPath.endsWith("/")) {
            aPath += "/";
        }
        return addToFile(aPath + aFileName, aData, aLength);

    }


    public static boolean addToFile(String aPath, byte[] aData, int aLength) {
        File file = new File(aPath);
        if (file.exists()) {
            if (aPath.indexOf(Environment
                .getExternalStorageDirectory()
                .getPath()) != -1) {
                if (readSDCardRemain() < aLength) {
                    return false;
                }
            } else {
                if (readSystemRemain() < aLength) {
                    return false;
                }
            }
            DataOutputStream dos;
            try {
            	//2012827 dyj 
                dos = new DataOutputStream(new FileOutputStream(aPath, false));
                if (aLength == 0) {
                    dos.write(aData);
                } else {
                    dos.write(aData, 0, aLength);
                }
                dos.flush();
                file.setLastModified(System.currentTimeMillis());
                dos.close();
                return true;
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }

  
    public static int deleteFile(String aPath, String aFileName) {
        
        if (!aPath.endsWith("/")) {
            aPath += "/";
        }
        return deleteFile(aPath + aFileName);
    }


    public static int deleteFile(String aPath) {
        File file = new File(aPath);
        if (file.exists()) {
            file.delete();
            return 0;
        }
        return -1;
    }


    public static long readSDCardRemain() {

        if (Environment.MEDIA_MOUNTED.equals(Environment
            .getExternalStorageState())) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSize();
            long availCount = sf.getAvailableBlocks();
            return availCount * blockSize;
        }
        return -1;
    }

 
    public static long readSystemRemain() {
        File root = Environment.getRootDirectory();
        StatFs sf = new StatFs(root.getPath());
        long blockSize = sf.getBlockSize();
        long availCount = sf.getAvailableBlocks();
        return availCount * blockSize;
    }


    public static void copy(File aDesFile, File aSrcFile) throws IOException {
        FileInputStream input = new FileInputStream(aSrcFile);
        BufferedInputStream inBuff = new BufferedInputStream(input);

        FileOutputStream output = new FileOutputStream(aDesFile);
        BufferedOutputStream outBuff = new BufferedOutputStream(output);

        byte[] b = new byte[INDEX5 * K];
        int len;
        // TODO
        while ((len = inBuff.read(b)) != -1) {
            outBuff.write(b, 0, len);
        }

        outBuff.flush();

        inBuff.close();
        outBuff.close();
        output.close();
        input.close();
    }


    synchronized public static File createWithoutOverwriteExistFile(String aPath)
                                                                                 throws IOException {
        String fileName = aPath
            .substring(aPath.lastIndexOf(File.separatorChar) + 1);
        String dir = aPath.substring(
            0,
            aPath.lastIndexOf(File.separatorChar) + 1);
        String fileNameSuffix = "_";
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String fileNameWithoutSuffix = fileName.substring(0, fileName
            .lastIndexOf("."));
        int duplicateCount = 1;
        File file = new File(aPath);

        while (file.exists()) {
            String tmpString;
            tmpString = fileNameWithoutSuffix + fileNameSuffix + duplicateCount;
            aPath = dir + tmpString + suffix;
            file = new File(aPath);
            duplicateCount++;
        }

        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.mkdirs();
        }
        file.createNewFile();
        parentFile = null;

        return file;
    }


    synchronized public static File createWithOverwriteExistFile(String aPath)
                                                                              throws IOException {
        File file = new File(aPath);

        if (!file.exists()) {
            File parentFile = file.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            parentFile = null;
        } else {
            file.delete();
        }

        file.createNewFile();

        return file;
    }
    
    public static boolean createNewFile(File aFile) {
    	boolean fileCreateSuccessful = false;
    	if (aFile == null) {
	        return false;
        }
        if (!aFile.exists()) {
            File parentFile = aFile.getParentFile();
            if (parentFile != null) {
                parentFile.mkdirs();
            }
            parentFile = null;
        }
        try {
	        fileCreateSuccessful = aFile.createNewFile();
        } catch (IOException e) {
	        e.printStackTrace();
	        fileCreateSuccessful = false;
        }
        return fileCreateSuccessful;
    }

   
    synchronized public static void deleteDir(File aDir) throws IOException {
        if (aDir != null && aDir.isDirectory()) {
            File[] entries = aDir.listFiles();
            int sz = entries.length;

            for (int i = 0; i < sz; i++) {
                if (entries[i].isDirectory()) {
                    deleteDir(entries[i]);
                } else {
                    entries[i].delete();
                }
            }
        }

        aDir.delete();
    }

    synchronized public static void unZip(InputStream aIs, String aOutPutDir,
                                          boolean aOverride)
                                                            throws IOException {
        ZipInputStream zipInputStream = new ZipInputStream(aIs);

        ZipEntry zipEntry;

        zipEntry = zipInputStream.getNextEntry();
        File file = new File(aOutPutDir);

        file.mkdirs();
        while (null != zipEntry) {
            if (zipEntry.isDirectory()) {
                String name = zipEntry.getName();
                name = name.substring(0, name.length() - 1);

                if (null != name && "" != name) {
                    file = new File(aOutPutDir + File.separator + name);
                    file.mkdir();
                }
            } else {
                String filePath = aOutPutDir + File.separatorChar
                                  + zipEntry.getName();

                file = new File(filePath);
                if (aOverride) {
                    file = createWithOverwriteExistFile(filePath);

                    writeFile(zipInputStream, file);
                } else {
                    if (!file.exists()) {
                        file.createNewFile();

                        writeFile(zipInputStream, file);
                    }
                }
            }
            zipEntry = zipInputStream.getNextEntry();
        }
        zipInputStream.close();
    }


    synchronized public static void writeFile(InputStream aIs, File aDesFile)
                                                                             throws IOException {
        FileOutputStream out = new FileOutputStream(aDesFile);
        byte[] b = new byte[INDEX6 * K];
        int len = aIs.read(b);

        out.write(b, 0, len);
        while ((len = aIs.read(b)) > 0) {
            out.write(b, 0, len);
            out.flush();
        }
        out.close();
    }


    synchronized public static void unZip(String aZipFilePath,
                                          String aOutPutDir, boolean aOverride)
                                                                               throws IOException {
        InputStream is = new FileInputStream(new File(aZipFilePath));

        unZip(is, aOutPutDir, aOverride);

        is.close();
    }

    public static void installApk(Context aContext, String aUri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(
            Uri.fromFile(new File(aUri)),
            "application/vnd.android.package-archive");
        aContext.startActivity(intent);
    }

	public static final String STR_SLASH_MNT = "/mnt";
    public static String toCaseSensitivePath(String aInsensitvePath) {
    	if (aInsensitvePath == null) {
	        return null;
        }
        String convertedPath = new String();
        File file = new File(aInsensitvePath);
        if (!file.exists()) {
            
            return aInsensitvePath;
        }
        File parentFile = file.getParentFile();
        if (null != parentFile) {
            String[] subPaths = parentFile.list();
            
            if (null == subPaths) {
               
                return aInsensitvePath;
            }
            for (int i = 0; i < subPaths.length; i++) {
                convertedPath = file.getParent() + "/" + subPaths[i];
                if (0 == convertedPath.compareToIgnoreCase(aInsensitvePath)) {
                   
                    return toCaseSensitivePath(file.getParent()) + "/"
                           + subPaths[i];
                }
            }
        } else {
            
            return aInsensitvePath;
        }

       
        return aInsensitvePath;
    }

    public static void clearCache(File file){
        if (file.isDirectory()){
            File[] fileSon = file.listFiles();
            for (int i=0; i<fileSon.length; i++){
                clearCache(fileSon[i]);
            }
        }

        file.delete();
    }
}
