/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Farley Tran
 */
public class ImageFileFilter extends FileFilter
{
	public boolean accept(File file)
	{
		if(file.isDirectory())
		{
			return true;
		}
		String name = file.getName();
		String extension = getFileExtension(name);
		if (extension == null)
		{
			return false;
		}
		
		if(extension.equals("png"))
		{
			return true;
		}
		return false;
	}


	public String getDescription()
	{
		return "Image files (.png)";
	}
        	public String getFileExtension(String name)
	{
		int pointIndex = name.lastIndexOf(".");
		if(pointIndex == -1)
		{
			return null;
		}
		if(pointIndex == name.length() - 1)
		{
			return null;
		}
		return name.substring(pointIndex+1, name.length());
	}
}
