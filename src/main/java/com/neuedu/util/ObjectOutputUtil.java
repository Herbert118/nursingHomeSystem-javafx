package com.neuedu.util;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * @author 刘海波
 *
 * @param <T>
 * 
 * @description: 
 * 仍待测试, 同时, 显然的, 泛型没有太大必要
 * @date:2021-7-14
 */
public class ObjectOutputUtil<T extends Serializable> {
	public BufferedWriter writer;
	public ObjectOutputStream ous;
	public static final int SERIALFORMAT = 1;
	public static final int JSONFORMAT = 2;
	public ObjectOutputUtil(String path, int choice) {
		
		switch(choice) {
		case SERIALFORMAT:
			try {
				//don't use BufferedOutputStream inside!
				ous = new ObjectOutputStream(new FileOutputStream(path));
			} catch (FileNotFoundException e) {
				
				e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		case JSONFORMAT:
			try {
				writer = new BufferedWriter(new FileWriter(path));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
		
	}
	
	public void writeSerialObject(T t) throws Exception {
		if(ous == null) {
			throw new Exception("wrong choice!");
		}
		ous.writeObject(t);
	}
	
	public void writeJsonObject(T t) throws Exception {
		if(writer == null) {
			throw new Exception("wrong choice");
		}
		writer.write(JSON.toJSONString(t));
		writer.flush();
	}
}
