package caribehostal.caseroclient.datamodel;

import io.requery.Column;
import io.requery.Entity;
import io.requery.Generated;
import io.requery.Key;
import io.requery.ManyToOne;
import io.requery.Persistable;

import static io.requery.PropertyNameStyle.FLUENT_BEAN;

/**
 * Created by Fernando on 22/08/2017.
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
}
