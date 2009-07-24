/**
 * Copyright 2008-2009 the original author or authors.
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

import java.util.Arrays;


public class FloatBinaryValueConverterTest extends ValueConverterTestCase {
    private FloatBinaryValueConverter converter;

    public void setUp() {
        converter = new FloatBinaryValueConverter();
    }

    public void test_toBytes() {
        assertEquals(binary(1.0f), converter.toBytes(new Float(1.0f)));
        assertEquals(new byte[0], converter.toBytes(null));
    }

    public void test_toDomain() {
        assertEquals(new Float(1.0f), converter.toDomain(binary(1.0f)));
    }

    public void test_toDomain_NotEnoughData() {
        try {
            converter.toDomain(new byte[3]);
            fail();
        } catch (IllegalArgumentException err) {
            assertEquals("There are not enough bytes expected 4 got 3 bytes", err.getMessage());
        }
    }

    public void test_toDomain_NullByteArray() {
        try {
            converter.toDomain(null);
            fail();
        } catch (IllegalArgumentException err) {
            assertEquals("Byte array given was null", err.getMessage());
        }
    }

    public void test_types() {
        assertTrue(Arrays.equals(new Class[]{float.class, Float.class}, converter.types()));
    }
}
