package br.com.vinma.agenda.room;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import br.com.vinma.agenda.model.Student;
import br.com.vinma.agenda.room.dao.StudentDAO;

@Database(entities={Student.class}, version=3, exportSchema=false)
public abstract class AgendaDataBase extends RoomDatabase {

    private static final String NAME = "agenda.db";
    private static AgendaDataBase instance;

    public abstract StudentDAO getRoomStudentDao();

    public synchronized static AgendaDataBase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context, AgendaDataBase.class, NAME)
                    .allowMainThreadQueries()
                    .addMigrations(new Migration(1, 2) {
                        @Override
                        public void migrate(@NonNull SupportSQLiteDatabase database) {
                            database.execSQL("ALTER TABLE student ADD COLUMN surname TEXT");
                        }
                    },
                            // Revert the migration from db v1 to v2 (remove surname)
                            new Migration(2, 3) {
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
                            })
                    .build();
        }
        return instance;
    }
}
