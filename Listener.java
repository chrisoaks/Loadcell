package application;

import javafx.application.Platform;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import jssc.SerialPort;

public class Listener implements SerialPortEventListener {
	private StringBuilder message = new StringBuilder();
	private SerialPort serialPort;
	private String id;
	public Listener(String port, String _id) {
		id=_id;
		serialPort = new SerialPort(port);
		//System.out.println("Serial Port" + port + " Opened?" + serialPort.isOpened());			
		try {
			serialPort.openPort();
		
		//System.out.println("Serial Port" + port + " Opened?" + serialPort.isOpened());
		if(id=="load") serialPort.setParams(230400,8,8,1);
		if(id=="ard") serialPort.setParams(9600,8,1,0);
		serialPort.addEventListener(this);
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void serialEvent(SerialPortEvent event) {
		//System.out.println("Serial Event called");


		if(event.isRXCHAR() && event.getEventValue() > 0){
	        try {
	            byte buffer[] = serialPort.readBytes();        
	            for (byte b: buffer) {
	                    if ( (b == '\r' || b == '\n') && message.length() > 0) {
	                        String toProcess = message.toString();
	                        float number = Float.parseFloat(toProcess);
	                        //System.out.println(id + ": " + number);
	                        Platform.runLater(new Runnable() {
	                            @Override public void run() {
	                                Controller.addToQueue(number, id);
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
	            //System.out.println(ex);
	            //System.out.println("serialEvent");
	        }
	        
		}// TODO Auto-generated method stub

	}
	public void close() {
		try {
			serialPort.closePort();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
