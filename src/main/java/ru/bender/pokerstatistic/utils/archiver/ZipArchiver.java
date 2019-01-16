package ru.bender.pokerstatistic.utils.archiver;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class ZipArchiver implements Archiver {

    private static final String ZIP_EXTENSION = "zip";

    /**
     * {@inheritDoc}
     */
    @Override
    public String archive(String sourceFilePath, String archiveFilePath) throws ArchiveException {
        File sourceFile = new File(sourceFilePath);
        File archiveFile = new File(archiveFilePath);
        try (
                FileOutputStream fos = new FileOutputStream(archiveFile);
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                FileInputStream fis = new FileInputStream(sourceFile)
        ) {
            ZipEntry zipEntry = new ZipEntry(sourceFile.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            return archiveFile.getName();
        } catch (IOException e) {
            throw new ArchiveException(e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExtension() {
        return ZIP_EXTENSION;
    }
}
