package ru.bender.pokerstatistic.utils.archiver;

public interface Archiver {

    /**
     * Archive the file
     *
     * @param sourceFileName path to source file
     * @param archiveFileName path to out archive
     * @return archive filename without path
     */
    String archive(String sourceFileName, String archiveFileName) throws ArchiveException;

    /**
     * @return extension for archive file (ex: "zip", "rar", etc)
     */
    String getExtension();

}
