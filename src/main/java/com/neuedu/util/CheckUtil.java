package com.neuedu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

/**
 * @author 刘海波
 * @description 工具类， 封装了各种检验方法，用正则表达式来来检验
 *
 */
public class CheckUtil {
    public static boolean checkNotBlank(String string){
        if (string!=null){
            if(!string.isBlank()){
                return true;
            }
        }
        return false;
    }
    public static boolean checkName(String name){
        if(name == null){
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean checkPassword(String password){
        if(password == null){
            return false;
        }
        else{
            //必须包含字母和数字, 且不小于6位
            return password.matches("^(?=.*?[0-9])(?=.*?[a-z]).{6,}$");
        }
    }
    public static boolean checkId(String id){
    if(id == null){
        return false;
    }
    else{
        return true;
    }
    }

    public static boolean checkBirthDate(String birthDate){
        boolean convertSuccess = true;
        if(birthDate == null){
            return false;
        }
        else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setLenient(true);
            try {
                format.parse(birthDate);
            } catch (ParseException e) {
                convertSuccess = false;
            }

            return convertSuccess;
        }
    }
    
    public static boolean checkBirthDate(LocalDate birthDate){
        boolean convertSuccess = true;
        if(birthDate == null){
            return false;
        }
        else {
        	return birthDate.isBefore(LocalDate.now());
        }
    }

    public static boolean checkPhoneNumber(String phoneNumber){
        if(phoneNumber == null){
            return false;
        }
        else{
            //总之很强就是了
            return phoneNumber.matches("^\\s*(?:\\+?(\\d{1,3}))?[-. (]*(\\d{3})[-. )]*(\\d{3})[-. ]*(\\d{4})(?: *x(\\d+))?\\s*$");
        }
    }
    public static boolean checkIDNumber(String IDNumber){
        if(IDNumber == null){
            return false;
        }
        else{
            return IDNumber.matches("(^\\d{15}$)|(^\\d{17}([0-9]|X)$)");
        }
    }

    public static boolean checkSex(String sex) {
        if(sex.equals("男")||sex.equals("女")){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean checkPosition(String position) {
        if(position != null){
            if(position.equals("医生")||position.equals("护士")||position.equals("护工")){
                return true;
            }
        }
            return false;

    }

    public static boolean checkSpeciality(String speciality) {
        if(speciality!=null){
            return true;
        }
        else{
            return false;
        }
    }

	public static boolean checkDescription(String description) {
		if(description!= null) {
			return true;
		}
		else {
			return false;
		}
		
	}

	public static boolean checkType(String type) {
		if(type != null) {
		if(type.equals("ward")||type.equals("specialRoom")) {
			return true;
		}
		}
		return false;
	}
	public static boolean checkTypeCN(String type) {
		return type!=null ;
	}

	public static boolean checkContainNumber(String containNumber) {
		if(containNumber!=null) {
			if(containNumber.matches("^[1-9][0-9]*$")) {
				return true;
			}
		}
		return false;
	}
}
