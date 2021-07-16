package com.neuedu.util;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson.JSON;

class ObjectOutputUtilTest {
	ObjectOutputUtil<String> oou1;
	ObjectOutputUtil<String> oou2;
	ObjectInputStream ois;
	BufferedReader reader ;
	ObjectOutputUtilTest() throws IOException{
		oou1 = new ObjectOutputUtil<String>("test1.ser", ObjectOutputUtil.SERIALFORMAT);
		oou2 = new ObjectOutputUtil<String>("test1.json", ObjectOutputUtil.JSONFORMAT);
		reader = new BufferedReader(new FileReader("test1.json"));
	}
	@Test
	void testWriteSerialObject() {
		try {
			oou1.writeSerialObject("hello world");
			ois = new ObjectInputStream(new FileInputStream("test1.ser"));
			Object object = ois.readObject();
			//System.out.println(object);
			if(!object.equals("hello world")) {
				fail("writeSerial wrong");
			};
			
			
		} catch(EOFException e) {
			fail("EOF!");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Test
	void testWriteJsonObject() throws IOException {
		try {
			oou2.writeJsonObject("helloworld");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = reader.readLine();
		//System.out.println(JSON.parseObject(line,String.class));
		if(!"helloworld".equals((String)JSON.parseObject(line,String.class)))
		fail("fail");
	}

}
