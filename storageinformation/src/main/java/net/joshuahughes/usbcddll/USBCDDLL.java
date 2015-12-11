package net.joshuahughes.usbcddll;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface USBCDDLL extends Library{
	public static final int DEVICE_COMMANDOK = 0;
	public static final int DEVICE_IDERROR = 1;
	public static final int DEVICE_BUSY = 2;
	public static final int DEVICE_UNKNOWERROR = 3;

	USBCDDLL instance = (USBCDDLL) Native.loadLibrary("USBCDDLL", USBCDDLL.class);
    public void InitUSBCDLibrary();
    public void CloseUSBCDLibrary();
    public void SetCDCallbackProc(int pointer);
    public int GetDeviceNumber();
    public int EnumDevice(int index);
    public void USBCDReset(int id);
    public void USBCDMoveto(int id,int position);
    public void USBCDGetCDDown(int id);
    public void USBCDGetCDUp(int id);
    public void USBCDLEDON(int id);
    public void USBCDLEDOFF(int id);
    public int USBCDGetStatus(int id);
    
	public static void main(String[] args) throws Exception
	{
		instance.InitUSBCDLibrary();
		instance.SetCDCallbackProc(96069);
    	System.out.println("successfully loaded USBCDDLL.dll.");
    	int id =instance.EnumDevice(0);
    	System.out.println(instance.GetDeviceNumber());
    	System.out.println(id);

    	instance.USBCDMoveto(id,100);
    	Thread.sleep(5000);
    	instance.USBCDGetCDUp(id);
    	Thread.sleep(2000);
    	instance.CloseUSBCDLibrary();
	}
}
