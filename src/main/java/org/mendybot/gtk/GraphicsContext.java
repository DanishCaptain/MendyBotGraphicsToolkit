package org.mendybot.gtk;

public class GraphicsContext
{
  private FrameBuffer fb = new FrameBuffer("/dev/fb0");

  public void stop()
  {
    fb.close();
  }

  public void doStuff()
  {
    fb.doStuff();
  }

}
