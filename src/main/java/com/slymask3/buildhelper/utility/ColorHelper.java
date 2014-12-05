package com.slymask3.buildhelper.utility;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ColorHelper {
	
	public static String colorEveryWord(String msg, String color) {
		// Output will be at least as long as input
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
	
	public static String toColorLower(int meta) {
		if (meta == 0) {
			return "white";
		} else if (meta == 1) {
			return "orange";
		} else if (meta == 2) {
			return "magenta";
		} else if (meta == 3) {
			return "light blue";
		} else if (meta == 4) {
			return "yellow";
		} else if (meta == 5) {
			return "lime";
		} else if (meta == 6) {
			return "pink";
		} else if (meta == 7) {
			return "gray";
		} else if (meta == 8) {
			return "light gray";
		} else if (meta == 9) {
			return "cyan";
		} else if (meta == 10) {
			return "purple";
		} else if (meta == 11) {
			return "blue";
		} else if (meta == 12) {
			return "brown";
		} else if (meta == 13) {
			return "green";
		} else if (meta == 14) {
			return "red";
		} else if (meta == 15) {
			return "black";
		}
		
		return null;
	}
	
	public static String toColor(int meta) {
		if (meta == 0) {
			return "White";
		} else if (meta == 1) {
			return "Orange";
		} else if (meta == 2) {
			return "Magenta";
		} else if (meta == 3) {
			return "Light Blue";
		} else if (meta == 4) {
			return "Yellow";
		} else if (meta == 5) {
			return "Lime";
		} else if (meta == 6) {
			return "Pink";
		} else if (meta == 7) {
			return "Gray";
		} else if (meta == 8) {
			return "Light Gray";
		} else if (meta == 9) {
			return "Cyan";
		} else if (meta == 10) {
			return "Purple";
		} else if (meta == 11) {
			return "Blue";
		} else if (meta == 12) {
			return "Brown";
		} else if (meta == 13) {
			return "Green";
		} else if (meta == 14) {
			return "Red";
		} else if (meta == 15) {
			return "Black";
		}
		
		return null;
	}
	
	public static String toColor2(int meta) {
		if (meta == 0) {
			return "\u00a7fWhite\u00a7f";
		} else if (meta == 1) {
			return "\u00a76Orange\u00a7f";
		} else if (meta == 2) {
			return "\u00a7dMagenta\u00a7f";
		} else if (meta == 3) {
			return "\u00a7bLight Blue\u00a7f";
		} else if (meta == 4) {
			return "\u00a7eYellow\u00a7f";
		} else if (meta == 5) {
			return "\u00a7aLime\u00a7f";
		} else if (meta == 6) {
			return "\u00a7dPink\u00a7f";
		} else if (meta == 7) {
			return "\u00a78Gray\u00a7f";
		} else if (meta == 8) {
			return "\u00a77Light Gray\u00a7f";
		} else if (meta == 9) {
			return "\u00a73Cyan\u00a7f";
		} else if (meta == 10) {
			return "\u00a75Purple\u00a7f";
		} else if (meta == 11) {
			return "\u00a71Blue\u00a7f";
		} else if (meta == 12) {
			return "\u00a78Brown\u00a7f";
		} else if (meta == 13) {
			return "\u00a72Green\u00a7f";
		} else if (meta == 14) {
			return "\u00a7cRed\u00a7f";
		} else if (meta == 15) {
			return "\u00a70Black\u00a7f";
		}
		
		return null;
	}
	
	public static int toMeta(String color) {
		if (color.equalsIgnoreCase("white")) {
			return 0;
		} else if (color.equalsIgnoreCase("orange")) {
			return 1;
		} else if (color.equalsIgnoreCase("magenta")) {
			return 2;
		} else if (color.equalsIgnoreCase("lightblue")) {
			return 3;
		} else if (color.equalsIgnoreCase("yellow")) {
			return 4;
		} else if (color.equalsIgnoreCase("lime")) {
			return 5;
		} else if (color.equalsIgnoreCase("pink")) {
			return 6;
		} else if (color.equalsIgnoreCase("gray")) {
			return 7;
		} else if (color.equalsIgnoreCase("lightgray")) {
			return 8;
		} else if (color.equalsIgnoreCase("cyan")) {
			return 9;
		} else if (color.equalsIgnoreCase("purple")) {
			return 10;
		} else if (color.equalsIgnoreCase("blue")) {
			return 11;
		} else if (color.equalsIgnoreCase("brown")) {
			return 12;
		} else if (color.equalsIgnoreCase("green")) {
			return 13;
		} else if (color.equalsIgnoreCase("red")) {
			return 14;
		} else if (color.equalsIgnoreCase("black")) {
			return 15;
		}
		
		return 0;
	}
}