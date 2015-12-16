package net.joshuahughes.usbcddll;


public class SingularlyConnectedDevice {
	static
	{
		USBCDDLL.instance.InitUSBCDLibrary();
	}
	public static void USBCDReset()
	{
		for(int index=0;index<USBCDDLL.instance.GetDeviceNumber();index++)
			USBCDDLL.instance.USBCDReset(USBCDDLL.instance.EnumDevice(index));
	}
	public static void USBCDMoveto(int position)
	{
		for(int index=0;index<USBCDDLL.instance.GetDeviceNumber();index++)
			USBCDDLL.instance.USBCDMoveto(USBCDDLL.instance.EnumDevice(index),position);
	}
	public static void USBCDGetCDDown()
	{
		for(int index=0;index<USBCDDLL.instance.GetDeviceNumber();index++)
			USBCDDLL.instance.USBCDGetCDDown(USBCDDLL.instance.EnumDevice(index));
	}
	public static void USBCDGetCDUp()
	{
		for(int index=0;index<USBCDDLL.instance.GetDeviceNumber();index++)
			USBCDDLL.instance.USBCDGetCDUp(USBCDDLL.instance.EnumDevice(index));
	}
	public static void USBCDLEDON()
	{
		for(int index=0;index<USBCDDLL.instance.GetDeviceNumber();index++)
			USBCDDLL.instance.USBCDLEDON(USBCDDLL.instance.EnumDevice(index));
	}
	public static void USBCDLEDOFF()
	{
		for(int index=0;index<USBCDDLL.instance.GetDeviceNumber();index++)
			USBCDDLL.instance.USBCDLEDOFF(USBCDDLL.instance.EnumDevice(index));
	}
	public static int USBCDGetStatus()
	{
		for(int index=0;index<USBCDDLL.instance.GetDeviceNumber();)
			return USBCDDLL.instance.USBCDGetStatus(USBCDDLL.instance.EnumDevice(index));
		return Integer.MIN_VALUE;
	}
}
