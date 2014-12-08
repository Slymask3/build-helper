package com.slymask3.buildhelper.utility;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColorHelper {
	
	public static String colorEveryWord(String msg, String color) {
	    StringBuilder builder = new StringBuilder(msg.length());

	    for (int i = 0; i < msg.length(); i++)
	    {
	        char c = msg.charAt(i);
	        switch (c)
	        {
	            case ' ': builder.append(" " + color); break;
	            default: builder.append(c); break;
	        }
	    }
	    return builder.toString();
	}
}