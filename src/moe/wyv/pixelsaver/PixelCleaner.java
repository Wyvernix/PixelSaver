package moe.wyv.pixelsaver;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class PixelCleaner {
	private BufferedImage source;
	private int[] xSpacings;
	private int[] ySpacings;
	
	
	public PixelCleaner(BufferedImage inputImage) {
		source = inputImage;
	}
	
	public void cleanImage() {
		calculateSpacings();
	}

	private void calculateSpacings() {
		LinkedList<Integer> builder = new LinkedList<Integer>();
		int count = 0;
		
		int[] lastLine = null;
		for (int y = 0; y < source.getHeight(); y ++) {
			int[] line = new int[source.getWidth()];
			for (int x = 0; x < line.length; x++) {
				line[x] = source.getRGB(x, y);
			}
			if (lastLine != null) {
				boolean isSame = true;
				for (int x = 0; x < line.length; x++) {
					if (!isSimilarColor(line[x], lastLine[x])) {
						isSame = false;
						break;
					}
				}
				if (isSame) {
					++count;
				} else {
					builder.add(++count);
					count = 0;
				}
			}
			
			lastLine = line;
		}
		builder.add(count);
		
		System.out.println(Arrays.toString(builder.toArray()));
		
		
		
//		for (int i = 0; i < source.getHeight(); i++) {
//			LinkedList<Integer> builder = new LinkedList<Integer>();
//			
//			int count = 0;
//			int lastPixel = source.getRGB(0, i);
////			builder.add(0);
//			
//			for (int j = 0; j < source.getWidth(); j++) {
//				int color = source.getRGB(j, i);
//				if (isSimilarColor(color, lastPixel)) {
//					++count;
//				} else {
//					builder.add(++count);
//					count = 0;
//				}
//				lastPixel = color;
//			}
//			builder.add(count);
//			
//			System.out.println(Arrays.toString(builder.toArray()));
//		}
		
		
		
//		System.out.println(Integer.toHexString(source.getRGB(5, 160)));
//		System.out.println(Integer.toHexString(source.getRGB(6, 160)));
//		System.out.println(Integer.toHexString(source.getRGB(7, 160)));
//		System.out.println(Integer.toHexString(source.getRGB(8, 160)));
//		System.out.println(Integer.toHexString(source.getRGB(9, 160)));
//		System.out.println(Integer.toHexString(source.getRGB(10, 160)));
//		System.out.println(Integer.toHexString(source.getRGB(11, 160)));
//		int test = source.getRGB(8, 160);
//		System.out.println(Integer.toHexString(test));
//		System.out.println(Integer.toHexString((test & 0x00ff0000) >> 16));
//		System.out.println(Integer.toHexString((test & 0x0000ff00) >> 8));
//		System.out.println(Integer.toHexString(test & 0x000000ff));
	}
	
	private boolean isSimilarColor(int a, int b) {
		final int safe = 16;
		
		int ared = (a & 0x00ff0000) >> 16;
		int agreen = (a & 0x0000ff00) >> 8;
		int ablue = a & 0x000000ff;
		int bred = (b & 0x00ff0000) >> 16;
		int bgreen = (b & 0x0000ff00) >> 8;
		int bblue = b & 0x000000ff;
		
		if (Math.abs(ared-bred) < safe &&
				Math.abs(agreen - bgreen) < safe &&
				Math.abs(ablue - bblue) < safe) {
			return true;
		}
		
		return false;
	}
}
