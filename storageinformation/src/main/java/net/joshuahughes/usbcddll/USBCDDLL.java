package net.joshuahughes.usbcddll;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface USBCDDLL extends Library{

	public static final int ok = 0;
	public static final int deviceIdError = 1;
	public static final int busy = 2;
	public static final int unknownError = 3;

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
}
