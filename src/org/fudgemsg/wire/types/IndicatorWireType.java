/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and other contributors.
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
package org.fudgemsg.wire.types;

import java.io.DataInput;
import java.io.DataOutput;

import org.fudgemsg.types.IndicatorType;

/**
 * The type definition for a message-level indicator flag.
 */
final class IndicatorWireType extends FudgeWireType {

  /**
   * Standard Fudge field type: zero length indicator.
   * See {@link FudgeWireType#INDICATOR_TYPE_ID}.
   */
  public static final IndicatorWireType INSTANCE = new IndicatorWireType();

  /**
   * Restricted constructor.
   */
  private IndicatorWireType() {
    super(FudgeWireType.INDICATOR_TYPE_ID, IndicatorType.class, 0);
  }

  @Override
  public IndicatorType readValue(DataInput input, int dataSize) {
    return IndicatorType.INSTANCE;
  }

  @Override
  public void writeValue(DataOutput output, Object value) {
    // all data written in header, nothing to write here
  }

}
