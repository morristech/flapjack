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
package flapjack.layout;

/**
 * Basic implementation of the RecordLayout
 */
public class SimpleRecordLayout extends AbstractRecordLayout {
    private static final NoOpPaddingDescriptor NO_OP_PADDING = new NoOpPaddingDescriptor();

    public SimpleRecordLayout(String id) {
        super(id);
    }

    public void field(String name, int position, int length, PaddingDescriptor paddingDescriptor) {
        addFieldDefinition(new SimpleFieldDefinition(name, position, length, paddingDescriptor));
    }


    public void field(String name, int length) {
        field(name, offset, length);
    }

    public void field(String name, int position, int length) {
        field(name, position, length, NO_OP_PADDING);
    }

    public void field(String name, int length, PaddingDescriptor paddingDescriptor) {
        field(name, offset, length, paddingDescriptor);
    }
}
