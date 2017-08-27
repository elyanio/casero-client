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
        return value.format(formatter);
    }

    @Override
    public LocalDateTime convertToMapped(Class<? extends LocalDateTime> type, String value) {
        return LocalDateTime.parse(value, formatter);
    }
}
