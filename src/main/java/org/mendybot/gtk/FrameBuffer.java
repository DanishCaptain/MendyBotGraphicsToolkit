package org.mendybot.gtk;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class FrameBuffer
{
  static
  {
    System.loadLibrary("MBFrameBuffer");
  }
  
  private String deviceName;
  private long deviceInfo; // Private data from JNI C
  private int width;
  private int height;
  private int bits;
  private int[] imgBuffer;
  private Object updateLock = new Object();
  private BufferedImage img;

  private native long openDevice(String device);

  private native void closeDevice(long di);

  private native int getDeviceWidth(long di);

  private native int getDeviceHeight(long di);

  private native int getDeviceBitsPerPixel(long di);

  private native boolean updateDeviceBuffer(long di, int[] buffer);

  public FrameBuffer(String deviceName)
  {
    this.deviceName = deviceName;
    deviceInfo = openDevice(deviceName);
    
    if (Math.abs(deviceInfo) < 10)
    {
      throw new IllegalArgumentException(
          "Init. for frame buffer " + deviceName + " failed with error code " + deviceInfo);
    }

    this.width = getDeviceWidth(deviceInfo);
    this.height = getDeviceHeight(deviceInfo);

    System.err.println("Open with " + deviceName + " (" + deviceInfo + ")");
    System.err.println("  width   " + getDeviceWidth(deviceInfo));
    System.err.println("  height  " + getDeviceHeight(deviceInfo));
    System.err.println("  bpp     " + getDeviceBitsPerPixel(deviceInfo));

    img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    imgBuffer = ((DataBufferInt) img.getRaster().getDataBuffer()).getBankData()[0];
//    imgBuffer = new int[width*height];
  }

  
  /**
   * Close the device.
   */
  public void close()
  {
    synchronized (deviceName)
    {
      closeDevice(deviceInfo);
      deviceInfo = 0;
      imgBuffer = null;
    }
  }

  public boolean updateScreen()
  {

    synchronized (deviceName)
    {
      if (deviceInfo == 0)
        return false;

      boolean ret;
      synchronized (updateLock)
      {

        imgBuffer = ((DataBufferInt) img.getRaster().getDataBuffer()).getBankData()[0];
        ret = updateDeviceBuffer(deviceInfo, imgBuffer);

        /*
        updateCount++;
        if (lastUpdate == 0)
          lastUpdate = System.currentTimeMillis();
        long now = System.currentTimeMillis();

        long diff = now - lastUpdate;

        if (diff >= 1000)
        {
          float fps = (1000f / diff) * updateCount;
          // System.err.println("FPS = "+fps);
          updateCount = 0;
          lastUpdate = now;
        }
        */

      }

      return ret;
    }
  }

  public void doStuff()
  {
    Graphics g = img.getGraphics();
    g.setColor(Color.BLUE);
    g.fillRect(0, 0, 800, 600);
    g.setColor(Color.WHITE);
    g.drawRect(100, 100, 100, 100);
    updateScreen();
  }

  
}
