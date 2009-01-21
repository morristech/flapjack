/**
 * Copyright 2008 Dan Dudley
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License. 
 */
package flapjack.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds on to a collection of ValueConverters.
 */
public class TypeConverter {
    private Map converters = new HashMap();

    public TypeConverter() {
        registerConverter(new BooleanTextValueConverter());
        registerConverter(new IntegerTextValueConverter());
        registerConverter(new LongTextValueConverter());
        registerConverter(new DoubleTextValueConverter());
        registerConverter(new FloatTextValueConverter());
        registerConverter(new BigIntegerTextValueConverter());
        registerConverter(new BigDecimalTextValueConverter());
        registerConverter(new StringValueConverter());
//        registerConverter(new IntegerBinaryValueConverter());
    }

    /**
     * Call to register additional ValueConverters
     *
     * @param valueConverter - the ValueConverter to be registered
     */
    public void registerConverter(ValueConverter valueConverter) {
        Class[] types = valueConverter.types();
        if (types == null || types.length == 0) {
            throw new IllegalArgumentException(valueConverter.getClass().getName() + " does NOT have the class types it supports");
        }
        for (int i = 0; i < types.length; i++) {
            converters.put(types[i], valueConverter);
        }
    }

    /**
     * Attempts to convert the given bytes to the class type given
     *
     * @param clazz - the class to convert to
     * @param bytes - the bytes to be converted
     * @return the result of the coversion attempt
     * @throws IllegalArgumentException thrown when an error is encoutered during convesion
     */
    public Object convert(Class clazz, byte[] bytes) throws IllegalArgumentException {
        ValueConverter converter = (ValueConverter) converters.get(clazz);
        if (converter == null) {
            throw new IllegalArgumentException("No " + ValueConverter.class.getName() + " registered for types " + clazz.getName());
        }
        try {
            return converter.convert(bytes);
        } catch (RuntimeException err) {
            throw new IllegalArgumentException("Problem converting \"" + new String(bytes) + "\" to " + clazz.getName(), err);
        }
    }

}
