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
package org.fudgemsg.mapping;

/**
 * An unmodifiable decorator wrapping an object dictionary.
 * <p>
 * This class is not-modifiable rather than immutable.
 */
public final class UnmodifiableFudgeObjectDictionary extends FudgeObjectDictionary {

  /**
   * Creates a new dictionary wrapping another instance.
   * 
   * @param underlying  the instance to pass non-overridden method calls to, not null
   */
  public UnmodifiableFudgeObjectDictionary(final FudgeObjectDictionary underlying) {
    super(underlying);
  }

  //-------------------------------------------------------------------------
  /**
   * Always throws an exception - this is an immutable dictionary.
   * 
   * @param defaultBuilderFactory  the factory to use
   */
  @Override
  public void setDefaultBuilderFactory(final FudgeBuilderFactory defaultBuilderFactory) {
    throw new UnsupportedOperationException("setDefaultBuilderFactory called on an immutable dictionary");
  }

  /**
   * Always throws an exception - this is an immutable dictionary.
   * 
   * @param <T> Java type of the objects created by the builder
   * @param clazz  the Java class to register the builder against
   * @param builder  the builder to register
   */
  @Override
  public <T> void addObjectBuilder(final Class<T> clazz, final FudgeObjectBuilder<? extends T> builder) {
    throw new UnsupportedOperationException("addObjectBuilder called on an immutable dictionary");
  }

  /**
   * Always throws an exception - this is an immutable dictionary.
   * 
   * @param <T> Java type of the objects processed by the builder
   * @param clazz  the Java class to register the builder against
   * @param builder  builder to register
   */
  @Override
  public <T> void addMessageBuilder(final Class<T> clazz, final FudgeMessageBuilder<? super T> builder) {
    throw new UnsupportedOperationException("addMessageBuilder called on an immutable dictionary");
  }

  /**
   * Always throws an exception - this is an immutable dictionary.
   * 
   * @param <T> Java type of the objects processed by the builder
   * @param clazz  the Java class to register the builder against
   * @param builder  builder to register
   */
  @Override
  public <T> void addBuilder(final Class<T> clazz, final FudgeBuilder<T> builder) {
    throw new UnsupportedOperationException("addMessageBuilder called on an immutable dictionary");
  }

}
