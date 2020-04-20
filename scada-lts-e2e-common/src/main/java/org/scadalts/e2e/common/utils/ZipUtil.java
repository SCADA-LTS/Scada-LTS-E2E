package org.scadalts.e2e.common.utils;

import lombok.extern.log4j.Log4j2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Log4j2
public class ZipUtil {

    public static File zip(File toZip) {

        try {
            return _zip(toZip);
        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
            return null;
        }
    }

    private static File _zip(File toZip) throws IOException {
        String zipFile = toZip.getName()+".zip";
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zipOut = new ZipOutputStream(fos);

        FileInputStream fis = new FileInputStream(toZip);
        ZipEntry zipEntry = new ZipEntry(toZip.getName());
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        zipOut.close();
        fis.close();
        fos.close();
        return new File(zipFile);
    }
}
