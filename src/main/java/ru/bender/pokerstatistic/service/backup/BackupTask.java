package ru.bender.pokerstatistic.service.backup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.bender.pokerstatistic.utils.archiver.ArchiveException;
import ru.bender.pokerstatistic.utils.archiver.Archiver;

import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;
import static ru.bender.pokerstatistic.utils.Utils.currentDate;
import static ru.bender.pokerstatistic.utils.Utils.now;

// TODO: 16.01.19 spring batch?
@Component
class BackupTask {

    private static final Logger LOG = LoggerFactory.getLogger(BackupTask.class);
    private static final String BACKUP_FOLDER_PATH = "db/backups/";
    private static final String BACKUP_TEMP_FILE_PATH = BACKUP_FOLDER_PATH + "backup.sql";
    private static final String ARCHIVE_NAME_PREFIX = "db";
    private static final int FIVE_DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24 * 5;
    private static final int MIN_DAYS_COUNT_BETWEEN_BACKUPS = 4;

    private final BackupDao backupDao;
    private final Archiver archiver;

    @Autowired
    public BackupTask(BackupDao backupDao, Archiver archiver) {
        this.backupDao = backupDao;
        this.archiver = archiver;
    }

    /**
     * // TODO: 16.01.19 прирвется ли поток при эксепшене?
     */
    @Scheduled(fixedDelay = FIVE_DAY_IN_MILLISECONDS)
    public void run() {
        LOG.debug("Start backup task");
        if (isNotTimeForNewBackup()) {
            return;
        }
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

    private boolean isNotTimeForNewBackup() {
        BackupItem recentBackup = backupDao
                .findFirstByDateTimeAfterOrderByDateTimeDesc(maxDateTimeOfRecentBackup());
        if (recentBackup == null) {
            return false;
        } else {
            LOG.debug("To small time since last backup - " + recentBackup);
            return true;
        }
    }

    private LocalDateTime maxDateTimeOfRecentBackup() {
        return now().minusDays(MIN_DAYS_COUNT_BETWEEN_BACKUPS);
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
        return ARCHIVE_NAME_PREFIX + currentDateString;
    }

}
