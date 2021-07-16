package com.neuedu.util;

import static org.junit.jupiter.api.Assertions.fail;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ObjectInputUtilTest {
	ObjectInputUtil<String> oiu1;
	ObjectInputUtil<String> oiu2;
	ObjectOutputUtil<String> oou1;
	ObjectOutputUtil<String> oou2;
	ObjectOutputUtil<ArrayList<String>> oou3;
	ObjectInputUtilTest() throws FileNotFoundException{
		oou1 = new ObjectOutputUtil<String>("test2.ser",ObjectOutputUtil.SERIALFORMAT);
		oou2 = new ObjectOutputUtil<String>("test2.json",ObjectOutputUtil.JSONFORMAT);
		oou3 = new ObjectOutputUtil<ArrayList<String>>("test2.json",ObjectOutputUtil.JSONFORMAT);
		oiu1 = new ObjectInputUtil<String>("test2.ser", String.class, ObjectInputUtil.SERIALFORMAT);
		oiu2 = new ObjectInputUtil<String>("test2.json",String.class,ObjectInputUtil.JSONFORMAT);
		
		
	
	}
	@Test
	void testReadSerialObject() throws Exception {
		oou1.writeSerialObject("Hello test2!");
		String line = oiu1.readSerialObject();
		System.out.println(line);
		if(!line.equals("Hello test2!"))
		fail("Serial wrong");
	}

	@Test
	void testReadSingalJsonObject() throws Exception {
		oou2.writeJsonObject("Hello test2.json!");
		String line = oiu2.readSingalJsonObject();
		if(!"Hello test2.json!".equals(line))
		fail("singal json fail!");
	}

	@Test
	void testReadJsonCollection() throws Exception {
		ArrayList<String> strList = new ArrayList<String>();
		strList.add("hello");
		strList.add("world");
		oou3.writeJsonObject(strList);
		ArrayList<String> strList2 = oiu2.readJsonArrayList();
		System.out.println();
		if(!strList.equals(strList))
		fail("Not yet implemented");
	}

}
