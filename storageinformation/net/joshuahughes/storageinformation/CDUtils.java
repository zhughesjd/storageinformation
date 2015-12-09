package net.joshuahughes.storageinformation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.filechooser.FileSystemView;
 
public class CDUtils {
 
  private CDUtils() {  }
 
  public static void open(String drive) {
 
try {
        File file = File.createTempFile("realhowto",".vbs");
        file.deleteOnExit();
        FileWriter fw = new java.io.FileWriter(file);
        String vbs = "Set wmp = CreateObject(\"WMPlayer.OCX\") \n"
                   + "Set cd = wmp.cdromCollection.getByDriveSpecifier(\""
                   + drive + "\") \n"
                   + "cd.Eject";
        fw.write(vbs);
        fw.close();
        Runtime.getRuntime().exec("wscript " + file.getPath()).waitFor();
    }
    catch(Exception e){
        e.printStackTrace();
    }
  }
 
  public static void close(String drive) {
 
try {
        File file = File.createTempFile("realhowto",".vbs");
        file.deleteOnExit();
        FileWriter fw = new FileWriter(file);
        // to close a CD, we need eject two times!
        String vbs = "Set wmp = CreateObject(\"WMPlayer.OCX\") \n"
                   + "Set cd = wmp.cdromCollection.getByDriveSpecifier(\""
                   + drive + "\") \n"
                   + "cd.Eject \n "
                   + "cd.Eject ";
        fw.write(vbs);
        fw.close();
        Runtime.getRuntime().exec("wscript " + file.getPath()).waitFor();
 
    }
    catch(Exception e){
        e.printStackTrace();
    }
  }
 
  public static void main(String[] args){
 
	  FileSystem fs = FileSystems.getDefault();
	  FileSystemView fsv = FileSystemView.getFileSystemView();
	  
	  for (Path rootPath : fs.getRootDirectories())
	  {
	      try
	      {
	          FileStore store = Files.getFileStore(rootPath);
	          System.out.println(rootPath + ": " + store.type());
	          System.out.println("Drive Name: "+ rootPath);
	          System.out.println("Description: "+fsv.getSystemTypeDescription(new File(rootPath.toString())));
	          
	      }
	      catch (IOException e)
	      {
	          System.out.println(rootPath + ": " + "<error getting store details>");
	      }
          System.out.println();
	  }  
   javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
               null, "Press OK to open CD", "CDUtils",
               javax.swing.JOptionPane.DEFAULT_OPTION);
    CDUtils.open("F:\\");
    javax.swing.JOptionPane.showConfirmDialog((java.awt.Component)
         null, "Press OK to close CD", "CDUtils",
         javax.swing.JOptionPane.DEFAULT_OPTION);
    CDUtils.close("F:\\");
 
  }
}
