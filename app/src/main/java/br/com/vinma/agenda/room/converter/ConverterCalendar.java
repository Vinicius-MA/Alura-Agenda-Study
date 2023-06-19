package br.com.vinma.agenda.room.converter;

import androidx.room.TypeConverter;

import java.util.Calendar;

public class ConverterCalendar {

    @TypeConverter
    public Long toLong(Calendar calendar){
        if(calendar == null){return null;}
        return calendar.getTimeInMillis();
    }

    @TypeConverter
    public Calendar toCalendar(Long value){
        Calendar calendar = Calendar.getInstance();
        if(value != null){
            calendar.setTimeInMillis(value);
        }
        return calendar;
    }

}
