package caribehostal.caseroclient.datamodel;

import io.requery.converter.EnumOrdinalConverter

/**
 * Created by Fernando on 16/08/2017.
 */
class ActionTypeConverter : EnumOrdinalConverter<ActionType>(ActionType::class.java)