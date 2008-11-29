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
package flapjack.annotation;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class scans the given packages for classes that contain the flapjack.annotation.Record annotation.
 */
public class RecordPackageClassScanner {
    private List<String> packages = new ArrayList<String>();

    public List<Class> scan() {
        List<Class> classes = new ArrayList<Class>();
        for (String packageName : packages) {
            for (Class clazz : findClassesInPackage(packageName)) {
                if (hasRecordAnnoation(clazz))
                    classes.add(clazz);
            }
        }
        return classes;
    }

    private boolean hasRecordAnnoation(Class clazz) {
        return clazz.getAnnotation(Record.class) != null;
    }

    private List<Class> findClassesInPackage(String packageName) {
        List<Class> classes = new ArrayList<Class>();
        URL url = getClass().getClassLoader().getResource(convertPackageToPath(packageName));
        if (url == null) {
            throw new IllegalArgumentException("Could not find package \""+packageName+"\"");
        }
        try {
            File dir = new File(url.toURI());
            String[] classFiles = dir.list(new FilenameFilter() {
                public boolean accept(File file, String filename) {
                    return filename.endsWith(".class");
                }
            });
            for (String className : classFiles) {
                classes.add(Class.forName(packageName + "." + className.replace(".class", "")));
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return classes;
    }

    private String convertPackageToPath(String packageName) {
        return packageName.replace('.', '/');
    }

    public void setPackages(List<String> packages) {
        this.packages.addAll(packages);
    }
}