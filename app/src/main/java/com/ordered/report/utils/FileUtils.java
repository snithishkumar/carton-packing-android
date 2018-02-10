package com.ordered.report.utils;

import android.content.Context;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Admin on 1/1/2018.
 */

public class FileUtils {
    private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;

    public static String toString(InputStream input) throws IOException {
        StringWriter sw = new StringWriter();
        copy(input, sw);
        return sw.toString();
    }

    private static void copy(InputStream input, Writer output) throws IOException {
        InputStreamReader in = new InputStreamReader(input);
        copyLarge(in, output);
    }

    private static long copyLarge(Reader input, Writer output)
            throws IOException {
        char[] buffer = new char[DEFAULT_BUFFER_SIZE];
        long count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
        return count;
    }
    public static String getResourcePath(Context context, String fileName){
        String appRootFolder =  DeviceConfig.getAppRootPath(context)+ File.separator+"database"+File.separator;
        File file = new File(appRootFolder);
        if(!file.exists()){
            file.mkdirs();
        }
        return appRootFolder+fileName;
    }
    public static void downloadStreamData(InputStream inputStream,String pathWithName) throws  Exception{
        FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = new FileOutputStream(pathWithName);
            IOUtils.copy(inputStream, fileOutputStream);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(e);
        }finally {
            if(fileOutputStream != null){
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }
    }


}
