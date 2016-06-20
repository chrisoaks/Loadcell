package application;

import javafx.application.Platform;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPort;
import jssc.SerialPortList;

public class LoadCellListener implements SerialPortEventListener {
	StringBuilder message = new StringBuilder();
	public SerialPort serialPort;
	
	public LoadCellListener() {
		serialPort = new SerialPort("COM3");
		System.out.println("Serial Port Opened?" + serialPort.isOpened());			
		try {
			serialPort.openPort();
		
		System.out.println("Serial Port Opened?" + serialPort.isOpened());
		serialPort.setParams(230400,8,8,1);
		serialPort.addEventListener(this);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void serialEvent(SerialPortEvent event) {
		//System.out.println("Serieal Event called");


		if(event.isRXCHAR() && event.getEventValue() > 0){
	        try {
	            byte buffer[] = serialPort.readBytes();        
	            for (byte b: buffer) {
	                    if ( (b == '\r' || b == '\n') && message.length() > 0) {
	                        String toProcess = message.toString();
	                        float impactlbs = Float.parseFloat(toProcess);
	                        System.out.println("float: " + impactlbs);
	                        Platform.runLater(new Runnable() {
	                            @Override public void run() {
	                                Main.addToQueue(impactlbs);
	                           }
	                        });
	                        message.setLength(0);
	                    }
	                    else {
	                        message.append((char)b);
	                    }
	            }                
	        }
	        catch (Exception ex) {
	            System.out.println(ex);
	            System.out.println("serialEvent");
	        }
	        
		}// TODO Auto-generated method stub

	}

}
