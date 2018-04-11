package org.mendybot.gtk;

public class MendyConsole
{
  private GraphicsContext context = new GraphicsContext();
  public MendyConsole()
  {
  }

  private void stop()
  {
    context.stop();
  }
  
  private void doStuff()
  {
    context.doStuff();
  }

  public static void main(String[] args) {
    MendyConsole console = new MendyConsole();
    
    
    console.doStuff();
    try
    {
      Thread.sleep(5000);
    }
    catch (InterruptedException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    console.stop();
  }

}
