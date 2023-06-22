package br.com.vinma.agenda.room.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.vinma.agenda.model.Telephone;

@Dao
public interface TelephoneDAO {
    @Query("SELECT * FROM `Telephone` " +
            "WHERE (studentId = :studentId AND LENGTH(number)>0)LIMIT 1")
    Telephone getFirstTelephone(int studentId);

    @Insert
    void save(Telephone... telephones);

    @Query("SELECT * FROM `Telephone`" +
            "WHERE studentId = :studentId")
    List<Telephone> getTelephonesFromStudent(int studentId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void update(Telephone... telephones);
}
