package com.xinlan.multi;

import java.util.Random;

public class ShowDemo {

	public static void main(String[] args) {
		new ShowDemo().mainRun();
	}
	
	Object lock = new Object();
	Object tokeObj = new Object();
	int doFile = 0;
	
	public void mainRun(){
		System.out.println("boss do something...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("employee 1 do file 1");
				try {
					Thread.sleep(1000+new Random().nextInt(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized(lock){
					doFile++;
				}
				
				System.out.println("employee 1 complete!");
				synchronized(lock){
					lock.notifyAll();
				}
				
			}
		}).start();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("employee 2 do file 2");
				try {
					Thread.sleep(1000+new Random().nextInt(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized(lock){
					doFile++;
					
				}
				System.out.println("employee 2 complete!");
				synchronized(lock){
					lock.notifyAll();
				}
				
			}
		}).start();
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("employee 3 do file 3");
				try {
					Thread.sleep(1000+new Random().nextInt(2000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				synchronized(lock){
					doFile++;
				}
				System.out.println("employee 3 complete!");
				synchronized(lock){
					lock.notifyAll();
				}
			}
		}).start();
		
		
		System.out.println("boss check file");
		
		synchronized(lock){
			while(doFile<3){
				try {
					lock.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}//end while
		}
		
		
		System.out.println("boss work complete!!");
		
	}
}//end class
