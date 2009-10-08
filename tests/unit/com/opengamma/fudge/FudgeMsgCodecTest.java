/**
 * Copyright (C) 2009 - 2009 by OpenGamma Inc.
 *
 * Please see distribution for license.
 */
package com.opengamma.fudge;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import org.junit.Test;

/**
 * A test class that will encode and decode a number of different Fudge messages
 * to test that encoding and decoding works properly.
 *
 * @author kirk
 */
public class FudgeMsgCodecTest {
  private final Random _random = new Random();
  
  @Test
  public void allNames() throws IOException {
    FudgeMsg inputMsg = FudgeMsgTest.createMessageAllNames();
    FudgeMsg outputMsg = cycleMessage(inputMsg);
    
    assertNotNull(outputMsg);
    
    FudgeUtils.assertAllFieldsMatch(inputMsg, outputMsg);
  }
  
  @Test
  public void variableWidthColumnSizes() throws IOException {
    FudgeMsg inputMsg = new FudgeMsg();
    inputMsg.add("100", new byte[100]);
    inputMsg.add("1000", new byte[1000]);
    inputMsg.add("10000", new byte[100000]);

    FudgeMsg outputMsg = cycleMessage(inputMsg);
    
    assertNotNull(outputMsg);
    
    FudgeUtils.assertAllFieldsMatch(inputMsg, outputMsg);
  }
  
  @Test
  public void subMsg() throws IOException {
    FudgeMsg inputMsg = new FudgeMsg();
    FudgeMsg sub1 = new FudgeMsg();
    sub1.add("bibble", "fibble");
    sub1.add(827, "Blibble");
    FudgeMsg sub2 = new FudgeMsg();
    sub2.add("bibble9", 9837438);
    sub2.add(828, 82.77f);
    inputMsg.add("sub1", sub1);
    inputMsg.add("sub2", sub2);

    FudgeMsg outputMsg = cycleMessage(inputMsg);
    
    assertNotNull(outputMsg);
    
    FudgeUtils.assertAllFieldsMatch(inputMsg, outputMsg);
  }
  
  @Test
  public void unknown() throws IOException {
    FudgeMsg inputMsg = new FudgeMsg();
    inputMsg.add("unknown", new UnknownFudgeFieldValue(new byte[10], FudgeTypeDictionary.INSTANCE.getUnknownType(200)));
    FudgeMsg outputMsg = cycleMessage(inputMsg);
    FudgeUtils.assertAllFieldsMatch(inputMsg, outputMsg);
  }
  
  protected byte[] createRandomArray(int length) {
    byte[] bytes = new byte[length];
    _random.nextBytes(bytes);
    return bytes;
  }

  @Test
  public void fixedWidthByteArrays() throws IOException {
    FudgeMsg inputMsg = new FudgeMsg();
    inputMsg.add("byte[4]", createRandomArray(4));
    inputMsg.add("byte[8]", createRandomArray(8));
    inputMsg.add("byte[16]", createRandomArray(16));
    inputMsg.add("byte[20]", createRandomArray(20));
    inputMsg.add("byte[32]", createRandomArray(32));
    inputMsg.add("byte[64]", createRandomArray(64));
    inputMsg.add("byte[128]", createRandomArray(128));
    inputMsg.add("byte[256]", createRandomArray(256));
    inputMsg.add("byte[512]", createRandomArray(512));
    
    inputMsg.add("byte[28]", createRandomArray(28));
    
    FudgeMsg outputMsg = cycleMessage(inputMsg);
    FudgeUtils.assertAllFieldsMatch(inputMsg, outputMsg);
  }

  protected static FudgeMsg cycleMessage(FudgeMsg msg) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    DataOutputStream dos = new DataOutputStream(baos);
    FudgeStreamEncoder.writeMsg(dos, msg);
    
    byte[] content = baos.toByteArray();
    
    ByteArrayInputStream bais = new ByteArrayInputStream(content);
    DataInputStream dis = new DataInputStream(bais);
    FudgeMsgEnvelope outputMsgEnvelope = FudgeStreamDecoder.readMsg(dis);
    assertNotNull(outputMsgEnvelope);
    assertNotNull(outputMsgEnvelope.getMessage());
    return outputMsgEnvelope.getMessage();
  }

}
