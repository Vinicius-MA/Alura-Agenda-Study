package br.com.vinma.agenda.room;

import androidx.annotation.NonNull;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

class AgendaMigrations {
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        // Add surname
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE student ADD COLUMN surname TEXT");
        }
    };
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        // Revert the migration from db v1 to v2 (remove surname)
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 1. Create a new table with desired information (id, name, phone, email)
            database.execSQL("CREATE TABLE IF NOT EXISTS `Student_New` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `phone` TEXT, `email` TEXT)");
            // 2. Copy all data from old table to new table
            database.execSQL("INSERT INTO Student_New (id, name, phone, email) SELECT id, name, phone, email FROM Student");
            // 3. Delete the old table
            database.execSQL("DROP TABLE Student");
            // 4. Rename the new table
            database.execSQL("ALTER TABLE Student_New RENAME TO Student");
        }
    };
    private static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        // Add dateCreated
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE student ADD COLUMN dateCreated INTEGER");
        }
    };
    private static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        // Add dateModified
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE IF NOT EXISTS `Student_New` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `landlinePhone` TEXT, `mobilePhone` TEXT, `email` TEXT, `dateCreated` INTEGER)");
            database.execSQL("INSERT INTO `Student_New` (id, name, landlinePhone, email, dateCreated) SELECT id, name, phone, email, dateCreated FROM Student");
            database.execSQL("DROP TABLE Student");
            database.execSQL("ALTER TABLE Student_New RENAME TO Student");
        }
    };

    protected static final Migration[] MIGRATIONS_ALL = {
        MIGRATION_1_2,
        MIGRATION_2_3,
        MIGRATION_3_4,
        MIGRATION_4_5
    };
}
