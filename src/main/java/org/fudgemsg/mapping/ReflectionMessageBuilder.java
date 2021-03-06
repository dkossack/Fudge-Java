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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.fudgemsg.FudgeRuntimeException;
import org.fudgemsg.MutableFudgeMsg;

/**
 * <p>Attempt to create an Fudge message containing values from Java-bean style getX
 * methods. If the nearest superclass supports ToFudgeMsgMessageBuilder then that will
 * be used to create the initial message that is supplemented by subclass getX
 * methods.</p>
 * 
 * <p>This has been superceded by the JavaBeanBuilder which uses the BeanUtils package
 * and will probably be removed from future releases.</p>
 * 
 * @param <T> class that can be serialized using this builder
 */
/* package */class ReflectionMessageBuilder<T> extends ReflectionBuilderBase<T> implements FudgeMessageBuilder<T> {

  /**
   * Creates a new {@link ReflectionMessageBuilder} for building messages from arbitrary Java objects. Always
   * succeeds, although the builder may only be capable of generating empty messages.
   * 
   * @param <T> class to generate messages for
   * @param clazz  the class to generate messages for
   * @return the builder, not null
   */
  /* package */static <T> ReflectionMessageBuilder<T> create(final Class<T> clazz) {
    FudgeMessageBuilder<? super T> builder = null;
    Class<? super T> superclazz = clazz;
    while ((superclazz = superclazz.getSuperclass()) != null) {
      builder = ToFudgeMsgMessageBuilder.create(superclazz);
      if (builder != null) {
        break;
      }
    }
    return new ReflectionMessageBuilder<T>(clazz, superclazz, builder);
  }

  //-------------------------------------------------------------------------
  /**
   * The base builder.
   */
  private final FudgeMessageBuilder<? super T> _baseBuilder;

  private ReflectionMessageBuilder(final Class<T> clazz, final Class<? super T> upstream,
      final FudgeMessageBuilder<? super T> baseBuilder) {
    super(clazz, "get", 0, upstream);
    _baseBuilder = baseBuilder;
  }

  //-------------------------------------------------------------------------
  @Override
  public MutableFudgeMsg buildMessage(final FudgeSerializer serializer, final T object) {
    //System.out.println ("ReflectionMessageBuilder::buildMessage (" + context + ", " + object + ")");
    final MutableFudgeMsg message;
    if (_baseBuilder != null) {
      message = _baseBuilder.buildMessage(serializer, object);
    } else {
      message = serializer.newMessage();
    }
    try {
      for (Map.Entry<String, Method> accessor : getMethods().entrySet()) {
        //System.out.println ("\t" + accessor.getValue ());
        serializer.addToMessage(message, accessor.getKey(), null, accessor.getValue().invoke(object));
      }
    } catch (IllegalArgumentException ex) {
      throw new FudgeRuntimeException("Unable to serialise " + object, ex);
    } catch (IllegalAccessException ex) {
      throw new FudgeRuntimeException("Unable to serialise " + object, ex);
    } catch (InvocationTargetException ex) {
      throw new FudgeRuntimeException("Unable to serialise " + object, ex);
    }
    return message;
  }

}
