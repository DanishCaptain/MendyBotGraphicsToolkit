package org.mendybot.gtk;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FrameBufferTest
{

  @BeforeClass
  public static void setUpBeforeClass() throws Exception
  {
    System.setProperty("java.library.path", "/home/brian/devGitHub/MendyBotGraphicsToolkit");
    System.setProperty("java.library.path", ".");
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception
  {
  }

  @Before
  public void setUp() throws Exception
  {
  }

  @After
  public void tearDown() throws Exception
  {
  }

  @Test
  public final void testFrameBuffer()
  {
    FrameBuffer fb = new FrameBuffer("bks");
    fb.close();
  }

}
