package caribehostal.caseroclient.datamodel;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import io.requery.Converter;
import io.requery.Nullable;

/**
 * Created by rainermf on 11/2/2017.
 */

public class LocalDateConverter implements Converter<LocalDate, String> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Class<LocalDate> getMappedType() {
        return LocalDate.class;
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
    public String convertToPersisted(LocalDate value) {
        return value.format(formatter);
    }

    @Override
    public LocalDate convertToMapped(Class<? extends LocalDate> type, String value) {
        return LocalDate.parse(value, formatter);
    }
}
