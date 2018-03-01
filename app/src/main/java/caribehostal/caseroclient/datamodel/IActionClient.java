package caribehostal.caseroclient.datamodel;

import javax.annotation.Nullable;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.Persistable;

import static io.requery.PropertyNameStyle.FLUENT_BEAN;

/**
 * @author Fernando
 */
@Entity(propertyNameStyle = FLUENT_BEAN)
public interface IActionClient extends Persistable{
    @Key
    @Generated
    @Column(nullable = false)
    int getId();

    @Column(nullable = false)
    @ManyToOne
    Client getClient();

    @Column(nullable = false)
    @ManyToOne
    Action getAction();

    @Column(nullable = true)
    @Nullable
    String getResponseCode();
}
