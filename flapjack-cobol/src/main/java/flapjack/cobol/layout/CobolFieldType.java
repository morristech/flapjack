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
package flapjack.cobol.layout;


public class CobolFieldType {
    public static final CobolFieldType INTEGER = new CobolFieldType();
    public static final CobolFieldType DECIMAL = new CobolFieldType();
    public static final CobolFieldType ALPHA_NUMERIC = new CobolFieldType();

    private CobolFieldType() {
    }

    public String toString() {
        if (this == INTEGER) {
            return "INTEGER";
        } else if (this == DECIMAL) {
            return "DECIMAL";
        } else {
            return "ALPHA_NUMERIC";
        }
    }
}
