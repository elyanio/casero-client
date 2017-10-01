package caribehostal.caseroclient.datamodel;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Key;
import io.requery.Persistable;

import static io.requery.PropertyNameStyle.FLUENT_BEAN;

/**
 * Created by Fernando on 16/08/2017.
 */
@Entity(propertyNameStyle = FLUENT_BEAN)
public interface IDevelop extends Persistable {
    @Key
    @Column(nullable = false)
    String getCell();

    @Column(nullable = false)
    String getName();
}
