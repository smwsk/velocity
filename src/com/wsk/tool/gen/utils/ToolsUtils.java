package com.wsk.tool.gen.utils;
import org.apache.commons.lang.StringUtils;

public class ToolsUtils {

	
	public static  String orclTypeToJavaType(String str){
		
		String resStr = str;

		if("varchar2".equalsIgnoreCase(str) || "char".equalsIgnoreCase(str) || "varchar".equalsIgnoreCase(str)){

			resStr = "String";

		}else if("number".equalsIgnoreCase(str)){ 

			resStr = "Integer";

		}else if("bigint".equalsIgnoreCase(str)){ 

			resStr = "Integer";

		}else if("smallint".equalsIgnoreCase(str)){ 

			resStr = "Integer";

		}else if("bigint".equalsIgnoreCase(str)){ 

			resStr = "Integer";

		}else if("decimal".equalsIgnoreCase(str)){ 

			resStr = "Integer";

		}else if("timestmp".equalsIgnoreCase(str)){
				
			resStr = "Date";
			
		}else{
			resStr = "unknow type";
		}
		
		return resStr;
	}
	
	/** */
	public static String getBaseTypeFromClassName(String className,Integer scale,int precision){

		String resStr = className;

		if(StringUtils.isNotBlank(className) && scale != null){
			
			String[] strs = className.split("\\.");
			resStr = strs[strs.length-1];
			
			if(StringUtils.isNotBlank(resStr)){
				
				if("BigDecimal".equalsIgnoreCase(resStr)){
					
					if(scale == 0){
						
						if(precision > 9){
							resStr = "Integer";
						}else{
							resStr = "Integer";
						}
					}else{
						resStr = "Integer";
					}
				}else if("Blob".equalsIgnoreCase(resStr) || "Clob".equalsIgnoreCase(resStr)){
					resStr = "String";
				}
				else if("OPAQUE".equalsIgnoreCase(resStr) ){
					resStr = "String";
				}
				else if("Timestamp".equalsIgnoreCase(resStr)){
					resStr = "Date";
				}
			}
		}
		return resStr;
	}
	public static String getOracleTypeFromClassName(String className,Integer scale,int precision){

		String resStr = className;

		if(StringUtils.isNotBlank(className) && scale != null){
			
			String[] strs = className.split("\\.");
			resStr = strs[strs.length-1];
			
			if(StringUtils.isNotBlank(resStr)){
				
				if("BigDecimal".equalsIgnoreCase(resStr)){
					
					if(scale == 0){
						
						if(precision > 9){
							
							resStr = "NUMERIC";
						}else{
							resStr = "NUMERIC";
						}
					}else{
						resStr = "NUMERIC";
					}
				}else if("Blob".equalsIgnoreCase(resStr) || "Clob".equalsIgnoreCase(resStr)){
					
					resStr = "Clob";
				}
				else if("OPAQUE".equalsIgnoreCase(resStr) ){
					resStr = "VARCHAR";
				}
				else if("Timestamp".equalsIgnoreCase(resStr)){
					resStr = "DATE";
				}
				else if("String".equalsIgnoreCase(resStr)){
					resStr = "VARCHAR";
				}
			}
		}
		return resStr;
	}
	
}
