package moe.wyv.pixelsaver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class PixelSaver {
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("usage: java PixelSaver <image>");
			System.exit(1);
		}
		
		//search for files
		Queue<File> fileQueue = new LinkedList<File>();
		for (int i = 0; i < args.length; i++) {
			enqueueFiles(fileQueue, new File(args[i]));
		}
		
		//process files
		if (!fileQueue.isEmpty()) {
			BufferedImage image = null;
			while (!fileQueue.isEmpty()) {
				File thisFile = fileQueue.remove();
				System.out.println("\nProcessing "+thisFile+"...");
				
				try {
					image = ImageIO.read(thisFile);
					if (image == null) throw new IOException();
				} catch (IOException e) {
					System.out.println(thisFile+" is not an image file.");
					break;
				}
				
				new PixelCleaner(image).cleanImage();
				//TODO clean image
			}
		} else {
			System.out.println("No valid files found.");
		}
	}
	
	/**
	 * Recursively enqueues all files in a directory.
	 * 
	 * @param queue file queue
	 * @param file location of file or directory
	 */
	private static void enqueueFiles(Queue<File> queue, File file) {
		if (file.isFile()) {
			queue.add(file);
		} else if (file.isDirectory()) {
			File[] children = file.listFiles();
			if (children.length > 0) {
				for (int i = 0; i < children.length; i++) {
					enqueueFiles(queue, children[i]);
				}
			}
		}
	}
}
