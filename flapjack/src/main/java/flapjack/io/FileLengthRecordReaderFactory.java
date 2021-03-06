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
package flapjack.io;

import java.io.File;

/**
 * At runtime determines what implementation of the RecordReader should be used based on the file to be processed.
 * <p/>
 * If the record is large it will used the MappedRecordReader otherwise it will use the NioRecordReader.
 * <p/>
 * The default small file limit is 50 Mb.
 */
public class FileLengthRecordReaderFactory implements RecordReaderFactory {
    private static final int DEFAULT_SMALL_FILE_LIMIT = 50;
    private static final long MEGABYTE = 1048576L;

    private NioRecordReaderFactory nioFactory;
    private MappedRecordReaderFactory mappedFactory;
    private int smallFileLimit = DEFAULT_SMALL_FILE_LIMIT;
    private FileUtil fileUtil = new FileUtilImpl();

    public FileLengthRecordReaderFactory(int recordLength) {
        nioFactory = new NioRecordReaderFactory(recordLength);
        mappedFactory = new MappedRecordReaderFactory(recordLength);
    }

    public RecordReader build(File file) {
        if (convertToMegaBytes(fileUtil.length(file)) <= smallFileLimit) {
            return nioFactory.build(file);
        }
        return mappedFactory.build(file);
    }


    private long convertToMegaBytes(long bytes) {
        return (bytes / MEGABYTE);
    }

    /**
     * The limit on file size to determine what implementation should be used for RecordReader.
     *
     * @param smallFileLimit - number of MegaBytes
     */
    public void setSmallFileLimit(int smallFileLimit) {
        this.smallFileLimit = smallFileLimit;
    }

    protected void setFileUtil(FileUtil fileUtil) {
        this.fileUtil = fileUtil;
    }
}
