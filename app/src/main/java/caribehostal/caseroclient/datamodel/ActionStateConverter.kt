package caribehostal.caseroclient.datamodel;

import io.requery.converter.EnumOrdinalConverter

/**
 * Created by Fernando on 21/08/2017.
 */
class ActionStateConverter: EnumOrdinalConverter<ActionState>(ActionState::class.java) {
}