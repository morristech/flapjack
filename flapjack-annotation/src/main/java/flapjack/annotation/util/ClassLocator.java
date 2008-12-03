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
package flapjack.annotation.util;

import java.util.List;
import java.net.URL;


public interface ClassLocator {

    /**
     * Locate all classes found with the given URL and packagename
     *
     * @param url - url location of the package
     * @param packageName - the package name to match on
     * @return a list of classes found
     */
    List<Class> locate(URL url, String packageName);
}