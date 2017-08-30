package caribehostal.caseroclient.datamodel;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import io.requery.Converter;
import io.requery.Nullable;

/**
 * Created by rainermf on 11/2/2017.
 */

public class LocalDateTimeConverter implements Converter<LocalDateTime, String> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Class<LocalDateTime> getMappedType() {
        return LocalDateTime.class;
    }

    @Override
    public Class<String> getPersistedType() {
        return String.class;
    }

    @Nullable
    @Override
    public Integer getPersistedSize() {
        return null;
    }

    @Override
    public String convertToPersisted(LocalDateTime value) {
        if(value != null){
            return value.format(formatter);
        }else{
            return "null";
        }

    }

    @Override
    public LocalDateTime convertToMapped(Class<? extends LocalDateTime> type, String value) {
        if(!value.equals("null")){
            return LocalDateTime.parse(value, formatter);
        }else{
            return null;
        }
    }
}
