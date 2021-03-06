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
package flapjack.model;

import flapjack.layout.FieldDefinition;
import flapjack.layout.SimpleFieldDefinition;
import flapjack.parser.FieldData;
import flapjack.util.ReverseValueConverter;
import flapjack.util.TypeConverter;
import flapjack.util.ValueConverter;
import org.jmock.Mock;
import org.jmock.cglib.MockObjectTestCase;

import java.util.Arrays;


public class SingleFieldMappingTest extends MockObjectTestCase {
    private SingleFieldMapping mapping;
    private BinaryFieldFactory binaryFactory;
    private DomainFieldFactory domainFactory;
    private Mock mockTypeConverter;
    private Mock valueConverter;
    private FieldDefinition fieldDefinition;

    protected void setUp() throws Exception {
        super.setUp();
        mockTypeConverter = mock(TypeConverter.class);
        mapping = new SingleFieldMapping("1", "2", null);
        domainFactory = mapping.getDomainFieldFactory();
        binaryFactory = mapping.getBinaryFieldFactory();
        valueConverter = mock(ValueConverter.class);
        fieldDefinition = (FieldDefinition) mock(FieldDefinition.class).proxy();
    }

    public void test_domainFieldFactory_CustomValueConverter() {
        SingleFieldMapping mapping = new SingleFieldMapping("1", "2", ReverseValueConverter.class);
        DomainFieldFactory domainFactory = mapping.getDomainFieldFactory();

        ListMap listMap = new ListMap();
        listMap.put("1", new FieldData(null, "value".getBytes()));

        mockTypeConverter.expects(once()).method("isRegistered").with(eq(ReverseValueConverter.class)).will(returnValue(true));
        mockTypeConverter.expects(once()).method("find").with(eq(ReverseValueConverter.class)).will(returnValue(valueConverter.proxy()));
        valueConverter.expects(once()).method("toDomain").with(eq("value".getBytes())).will(returnValue("domain"));

        assertEquals("domain", domainFactory.build(listMap, String.class, (TypeConverter) mockTypeConverter.proxy()));
    }

    public void test_domainFieldFactory() {
        ListMap listMap = new ListMap();
        listMap.put("1", new FieldData(fieldDefinition, "value".getBytes()));

        mockTypeConverter.expects(once()).method("type").with(eq(String.class), eq(fieldDefinition)).will(returnValue(valueConverter.proxy()));
        valueConverter.expects(once()).method("toDomain").with(eq("value".getBytes())).will(returnValue("domain"));

        assertEquals("domain", domainFactory.build(listMap, String.class, (TypeConverter) mockTypeConverter.proxy()));
    }

    public void test_binaryFieldFactory_Null() {
        SimpleFieldDefinition fieldDefinition = new SimpleFieldDefinition("id", 0, 0);

        FieldByteMap byteMap = binaryFactory.build(null, (TypeConverter) mockTypeConverter.proxy(), Arrays.asList(new Object[]{fieldDefinition}));
        assertEquals("", new String(byteMap.get(fieldDefinition)));
    }

    public void test_binaryFieldFactory() {
        SimpleFieldDefinition fieldDefinition = new SimpleFieldDefinition("id", 0, 0);

        mockTypeConverter.expects(once()).method("type").with(eq(String.class), eq(fieldDefinition)).will(returnValue(valueConverter.proxy()));
        valueConverter.expects(once()).method("toBytes").with(eq("value")).will(returnValue("binary".getBytes()));

        FieldByteMap byteMap = binaryFactory.build("value", (TypeConverter) mockTypeConverter.proxy(), Arrays.asList(new Object[]{fieldDefinition}));
        assertEquals("binary", new String(byteMap.get(fieldDefinition)));
    }

    public void test_binaryFieldFactory_CustomValueConverter() {
        mapping = new SingleFieldMapping("1", "2", ReverseValueConverter.class);
        domainFactory = mapping.getDomainFieldFactory();
        binaryFactory = mapping.getBinaryFieldFactory();
        SimpleFieldDefinition fieldDefinition = new SimpleFieldDefinition("id", 0, 0);

        mockTypeConverter.expects(once()).method("isRegistered").with(eq(ReverseValueConverter.class)).will(returnValue(true));
        mockTypeConverter.expects(once()).method("find").with(eq(ReverseValueConverter.class)).will(returnValue(valueConverter.proxy()));
        valueConverter.expects(once()).method("toBytes").with(eq("value")).will(returnValue("binary".getBytes()));

        FieldByteMap byteMap = binaryFactory.build("value", (TypeConverter) mockTypeConverter.proxy(), Arrays.asList(new Object[]{fieldDefinition}));
        assertEquals("binary", new String(byteMap.get(fieldDefinition)));
    }

    public void test_binaryFieldFactory_CustomValueConverterNotRegistered() {
        mapping = new SingleFieldMapping("1", "2", ReverseValueConverter.class);
        domainFactory = mapping.getDomainFieldFactory();
        binaryFactory = mapping.getBinaryFieldFactory();
        SimpleFieldDefinition fieldDefinition = new SimpleFieldDefinition("id", 0, 0);

        mockTypeConverter.expects(once()).method("isRegistered").with(eq(ReverseValueConverter.class)).will(returnValue(false));

        try {
            binaryFactory.build("value", (TypeConverter) mockTypeConverter.proxy(), Arrays.asList(new Object[]{fieldDefinition}));
            fail();
        } catch (IllegalArgumentException err) {
            assertEquals("Could not find a " + ReverseValueConverter.class.getName() + " registered! Are you sure you registered " + ReverseValueConverter.class.getName() + " in the TypeConverter?", err.getMessage());
        }
    }

}
