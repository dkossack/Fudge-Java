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
import java.io.IOException;

/**
 * The wire type definition for a int.
 */
final class IntWireType extends FudgeWireType {

  /**
   * Standard Fudge field type: int.
   * See {@link FudgeWireType#INT_TYPE_ID}.
   */
  public static final IntWireType INSTANCE = new IntWireType();

  /**
   * Restricted constructor.
   */
  private IntWireType() {
    super(FudgeWireType.INT_TYPE_ID, Integer.TYPE, 4);
  }

  //-------------------------------------------------------------------------
  @Override
  public Integer readValue(DataInput input, int dataSize) throws IOException {
    return input.readInt();
  }

  @Override
  public void writeValue(DataOutput output, Object value) throws IOException {
    output.writeInt((Integer) value);
  }

}
