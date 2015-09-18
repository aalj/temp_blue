/**
 * GIFFrameManager.java
 * com.example.gifplay
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015-8-28 		我的文档
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
 */

package com.example.gifview;

import java.util.Vector;

import android.graphics.Bitmap;

/**
 * ClassName:GIFFrameManager Function: TODO ADD FUNCTION Reason: TODO ADD REASON
 * 
 * @author 我的文档
 * @version
 * @since Ver 1.1
 * @Date 2015-8-28 下午1:03:31
 * 
 * @see
 */
public class GIFFrameManager {
	private Vector<Bitmap> frames;
	private int index;

	public GIFFrameManager() {
		frames = new Vector<Bitmap>(1);
		index = 0;
	}

	public void addImage(Bitmap image) {
		frames.addElement(image);
	}

	public int size() {
		return frames.size();
	}

	public Bitmap getImage() {
		if (size() == 0) {
			return null;
		} else {
			return (Bitmap) frames.elementAt(index);
		}
	}

	public void nextFrame() {
		if (index + 1 < size()) {
			index++;
		} else {
			index = 0;
		}
	}

	public static GIFFrameManager CreateGifImage(byte bytes[]) {
		try {
			GIFFrameManager GF = new GIFFrameManager();
			Bitmap image = null;
			GIFEncoder gifdecoder = new GIFEncoder(bytes);
			for (; gifdecoder.moreFrames(); gifdecoder.nextFrame()) {
				try {
					image = gifdecoder.decodeImage();
					if (GF != null && image != null) {
						GF.addImage(image);
					}
					continue;
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
			gifdecoder.clear();
			gifdecoder = null;
			return GF;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
