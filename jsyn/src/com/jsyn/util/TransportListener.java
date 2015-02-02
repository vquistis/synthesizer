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

package com.jsyn.util;

public interface TransportListener {
    /**
     * @param transportModel
     * @param framePosition position in frames
     */
    void positionChanged(TransportModel transportModel, long framePosition);

    /**
     * @param transportModel
     * @param state for example TransportModel.STATE_STOPPED
     */
    void stateChanged(TransportModel transportModel, int state);
}
