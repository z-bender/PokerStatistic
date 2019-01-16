package ru.bender.pokerstatistic.service.backup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bender.pokerstatistic.utils.archiver.ArchiveException;
import ru.bender.pokerstatistic.utils.archiver.Archiver;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;
import static ru.bender.pokerstatistic.utils.Utils.currentDate;

// TODO: 16.01.19 spring batch?
@Component
class BackupTask {

    private static final Logger LOG = LoggerFactory.getLogger(BackupTask.class);
    private static final String BACKUP_FOLDER_PATH = "db/backups/";
    private static final String BACKUP_TEMP_FILE_PATH = BACKUP_FOLDER_PATH + "backup.sql";
    private static final int FIVE_DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24 * 5;

    private final BackupDao backupDao;
    private final Archiver archiver;

    @Autowired
    public BackupTask(BackupDao backupDao, Archiver archiver) {
        this.backupDao = backupDao;
        this.archiver = archiver;
    }

    /**
     * TODO: запускаем таску при старте приложения и раз в пару дней
     * если прошлый бэкап был сделан менее суток назад, то таску не выполняем
     * в БД храним время записи, имя файла
     * <p>
     * // TODO: 16.01.19 прирвется ли поток при эксепшене?
     */
    @Scheduled(fixedDelay = FIVE_DAY_IN_MILLISECONDS)
    public void run() {
        LOG.debug("Start backup task");
        // TODO: 17.01.19 check last backup for 4 days
        backupDao.backup(BACKUP_TEMP_FILE_PATH);
        LOG.debug("Backup saved to temp file");
        try {
            String archiveName = archiver.archive(BACKUP_TEMP_FILE_PATH, getArchiveFilePath());
            backupDao.save(BackupItem.newInstance(archiveName));
            LOG.debug("Backup archived - " + archiveName);
        } catch (ArchiveException e) {
            LOG.error("Failed to create backup archive", e);
        }
    }

    private String getArchiveFilePath() {
        StringBuilder result = new StringBuilder(BACKUP_FOLDER_PATH)
                .append(generateArchiveName())
                .append(".")
                .append(archiver.getExtension());
        return result.toString();
    }

    private String generateArchiveName() {
        String currentDateString = currentDate().format(BASIC_ISO_DATE);
        return "db" + currentDateString;
    }

}
