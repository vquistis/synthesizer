/*
 * Copyright 2009 Phil Burk, Mobileer Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jsyn.io;

public interface AudioInputStream {
    public double read();

    /**
     * Try to fill the entire buffer.
     * 
     * @param buffer
     * @return number of samples read
     */
    public int read(double[] buffer);

    /**
     * Read from the stream. Block until some data is available.
     * 
     * @param buffer
     * @param start index of first sample in buffer
     * @param count number of samples to read, for example count=8 for 4 stereo frames
     * @return number of samples read
     */
    public int read(double[] buffer, int start, int count);

    public void close();

    /**
     * @return number of samples currently available to read without blocking
     */
    public int available();
}
