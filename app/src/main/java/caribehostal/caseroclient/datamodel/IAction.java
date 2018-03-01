package caribehostal.caseroclient.datamodel;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;

import io.requery.Column;
import io.requery.Convert;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.Persistable;

import static io.requery.PropertyNameStyle.FLUENT_BEAN;

/**
 * Created by Fernando on 16/08/2017.
 */
@Entity(propertyNameStyle = FLUENT_BEAN)
public interface IAction extends Persistable {

    @Key
    @Generated
    @Column(nullable = false)
    int getId();

    @Column(nullable = false)
    @Convert(LocalDateTimeConverter.class)
    LocalDateTime getSendTime();

    @Column(nullable = true)
    @Convert(LocalDateTimeConverter.class)
    LocalDateTime getResponseTime();

    @Column(nullable = false)
    @Convert(LocalDateConverter.class)
    LocalDate getCheckIn();

    @Column(nullable = false)
    @Convert(LocalDateConverter.class)
    LocalDate getCheckOut();

    @Column(nullable = false)
    @Convert(ActionTypeConverter.class)
    ActionType getActionType();

    @Column(nullable = false)
    @Convert(ActionStateConverter.class)
    ActionState getActioState();

    @Column(nullable = false)
    boolean isUnread();
}
