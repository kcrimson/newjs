package net.primitive.javascript.core.natives;

import com.google.common.base.Predicate;
import com.google.common.collect.Maps;
import java.util.Map;
import net.primitive.javascript.core.PropertyDescriptor;
import net.primitive.javascript.core.ScriptableObject;
import org.apache.commons.lang.StringUtils;

/**
 * 
 * Implementation of JavaScript Array prototype
 * @author jpalka@gmail.com
 */
public class JSArray extends ScriptableObject {

    public JSArray() {

        Map<String, PropertyDescriptor> array = Maps.filterKeys(associatedProperties, new Predicate<String>(){
            @Override
            public boolean apply(String t) {
                return StringUtils.isNumeric(t);
            }
        });
    }
    
}
