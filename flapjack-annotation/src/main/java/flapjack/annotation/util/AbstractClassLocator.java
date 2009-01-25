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
package flapjack.annotation.util;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractClassLocator implements ClassLocator {
    public List<Class> locate(URL url, String packageName) {
        List<Class> classes = new ArrayList<Class>();
        findClasses(classes, url, packageName);
        return classes;
    }

    protected abstract void findClasses(List<Class> classes, URL url, String packageName);

    protected Class loadClass(String className) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass(className);
    }

}
