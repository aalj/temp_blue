/**
 * JiTime.java
 * com.aalj35.aa11.thread
 *
 * Function： TODO 
 *
 *   ver     date      		author
 * ──────────────────────────────────
 *   		 2015年8月31日 		view
 *
 * Copyright (c) 2015, TNT All Rights Reserved.
*/

package com.aalj35.aa11.thread;
/**
 * ClassName:JiTime
 * Function: TODO ADD FUNCTION
 * Reason:	 TODO ADD REASON
 *
 * @author   view
 * @version  
 * @since    Ver 1.1
 * @Date	 2015年8月31日		下午3:41:35
 *
 * @see 	 

 */
public class JiTime extends Thread {
	int num = 0;
	
	
	public JiTime(int num) {
		this.num = num;
		// TODO Auto-generated constructor stub

	}
	@Override
	public void run() {
		try {
			Thread.sleep(num*1000*60);
			
			
			
			
			
			
			
		} catch (InterruptedException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		super.run();
		
	}

}

