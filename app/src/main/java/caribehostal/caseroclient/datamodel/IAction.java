package caribehostal.caseroclient.datamodel;

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

    @Column(nullable = true)
    @Convert(LocalDateConverter.class)
    LocalDateTime getDateAction();

    @Column(nullable = false)
    @Convert(LocalDateConverter.class)
    LocalDateTime getCheckIn();

    @Column(nullable = false)
    @Convert(LocalDateConverter.class)
    LocalDateTime getCheckOut();

    @Column(nullable = false)
    @Convert(ActionTypeConverter.class)
    ActionType getActionType();

    @Column(nullable = false)
    @Convert(ActionStateConverter.class)
    ActionState getActionState();
}
