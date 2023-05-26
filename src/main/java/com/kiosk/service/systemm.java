package com.kiosk.service;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kiosk.model.Kiosk;
import com.kiosk.repo.kioskRepository;

import com.fazecast.jSerialComm.SerialPort;


@Component
public class systemm implements CommandLineRunner {

	@Autowired
	private kioskRepository kioskRepository;

	@Override
	public void run(String... args) throws Exception {



//		Kiosk syss= kioskRepository.findByIpAddress();
//		if (syss!=null) {
//			throw new IOException("sorry device error");  
//		}
//		

		//User Name
		Kiosk syss = new Kiosk();
	//	Kiosk sys = new Kiosk();
		syss.setBrandName(System.getProperty("os.name"));
		syss.setModelName(InetAddress.getLocalHost().getHostName());
		syss.setCpu(System.getenv("PROCESSOR_IDENTIFIER"));
		
		//C hard disk
		File file = new File("c:");
	    long totalSpace = file.getTotalSpace(); //total disk space in bytes.
			    	
		long total1=totalSpace /1024 /1024/1024;
		String totalSpace1=String.valueOf(total1);
		syss.setHdd(totalSpace1);
		
		//Ram
		long ram= ((com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize();
	    long sizekb = ram /1000;
	    long sizemb = sizekb / 1000;
	    long sizegb = sizemb / 1000 ;
	    String s=String.valueOf(sizegb);

	    syss.setRamMemory(s);
	    
	    // screen size
	       try {
	        	 System.setProperty("java.awt.headless", "false");
	        Dimension size= Toolkit.getDefaultToolkit().getScreenSize();
	        
          // width will store the width of the screen
          int width = (int)size.getWidth();
     
          // height will store the height of the screen
          int height = (int)size.getHeight();
          
          syss.setScreenSize(width+"*"+height);
		 } catch (Exception e) {

	            e.printStackTrace();

	        }
		// Ip and Mac
		InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();

            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
    		
    		syss.setIpAddress(ip.getHostAddress());
    		syss.setMacAddress(sb.toString());

        } catch (Exception e) {

            e.printStackTrace();

        }
        
        //Port
		SerialPort [] AvailablePorts = SerialPort.getCommPorts();
	     // use the for loop to print the available serial ports
	     for(SerialPort S : AvailablePorts)
		syss.setPort(S.getSystemPortName());
 
        syss.setActive(true);
        
		kioskRepository.save(syss);
        
		}
//	}
}
