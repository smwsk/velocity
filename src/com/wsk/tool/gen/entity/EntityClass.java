package com.wsk.tool.gen.entity;

public class EntityClass {
	private String type;
	private String OracleType;
	private String filed;
	private String Capital;//首字母大写
	public EntityClass() {
		super();
	}
	public EntityClass(String type, String oracleType, String filed, String capital) {
		super();
		this.type = type;
		OracleType = oracleType;
		this.filed = filed;
		Capital = capital;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOracleType() {
		return OracleType;
	}
	public void setOracleType(String oracleType) {
		OracleType = oracleType;
	}
	public String getFiled() {
		return filed;
	}
	public void setFiled(String filed) {
		this.filed = filed;
	}
	public String getCapital() {
		return Capital;
	}
	public void setCapital(String capital) {
		Capital = capital;
	}
	
 }
