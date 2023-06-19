package br.com.vinma.agenda.room.converter;

import static br.com.vinma.agenda.model.TelephoneType.LANDLINE;

import androidx.room.TypeConverter;

import br.com.vinma.agenda.model.TelephoneType;

public class ConverterTelephoneType {

    @TypeConverter
    public static String toString(TelephoneType type) {
        return type.name();
    }

    @TypeConverter
    public static TelephoneType toTelephoneType(String typeName) {
        if(typeName == null){return LANDLINE;}
        return TelephoneType.valueOf(typeName);
    }
}
