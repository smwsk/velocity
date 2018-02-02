package com.wsk.tool.gen.utils;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;

import com.wsk.tool.gen.entity.EntityClass;
import com.wsk.tool.gen.fram.Main;

public class VelocityTools {
	public static VelocityEngine engine;

	public VelocityTools() {
		engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.FILE_RESOURCE_LOADER_PATH, "vm");// 设置模板路径
		engine.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		engine.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");  
		engine.init();// 初始化路径
	}

	public String generate(Connection conn,String vName,Map<String, Object> map) {
		VelocityContext context = new VelocityContext();// 创建个模板上下文
		Template template = engine.getTemplate(vName);// 获取模板
		StringWriter sw = new StringWriter();
		List<EntityClass> list = new ArrayList<EntityClass>();
		Statement stmt = null;
		ResultSet rs = null;
		
		String sqlName= (String) map.get("sqlName");
		String js_path= (String) map.get("js_path");
		js_path=js_path.substring(js_path.indexOf("WebRoot")+8, js_path.length());
		js_path=js_path.replace("\\", "/");
		String jsp_path= (String) map.get("jsp_path");
		jsp_path=jsp_path.substring(jsp_path.indexOf("WebRoot")+8, jsp_path.length());
		jsp_path=jsp_path.replace("\\", "/");
		
		String entity_path= ((String)map.get("entity_path")).replace("\\", ".");
		String Entityname= ((String)map.get("entity_Name")).replace("\\", ".");
		String controller_path= ((String)map.get("controller_path")).replace("\\", ".");
		String dao_path= ((String)map.get("dao_path")).replace("\\", ".");
		String service_path= ((String)map.get("service_path")).replace("\\", ".");
		String serviceImpl_path= ((String)map.get("serviceImpl_path")).replace("\\", ".");
		String mapper_path= ((String)map.get("mapper_path")).replace("\\", ".");
		
		try {
			stmt = conn.createStatement();
			if(sqlName.contains(";")){
				sqlName=sqlName.substring(0, sqlName.indexOf(";"));
			}
			rs = stmt.executeQuery(sqlName);
			ResultSetMetaData rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) {
				int scale = rsmd.getScale(i);
				String key = rsmd.getColumnName(i).toLowerCase();
				int precision = rsmd.getPrecision(i);
				String type = ToolsUtils.getBaseTypeFromClassName(rsmd.getColumnClassName(i), scale, precision);
				String Oracletype = ToolsUtils.getOracleTypeFromClassName(rsmd.getColumnClassName(i), scale, precision);
				/** java属性 **/
				if (!key.equals("rowid")) {
					EntityClass entityClass = new EntityClass(type, Oracletype, key, lowerToUp(key));
					list.add(entityClass);
				}
			}
			String tableName=sqlName.toLowerCase();
			tableName=tableName.substring(tableName.indexOf("from")+5, tableName.length());
			if(tableName.indexOf(" ")>0)
			tableName=tableName.substring(0,tableName.indexOf(" "));
			
			context.put("entity_path", entity_path);
			context.put("controller_path", controller_path);
			context.put("dao_path", dao_path);
			context.put("service_path", service_path);
			context.put("serviceImpl_path", serviceImpl_path);
			context.put("mapper_path", mapper_path);
			context.put("js_path", js_path);
			context.put("jsp_path", jsp_path);
			context.put("s_entity", Entityname.toLowerCase());
			context.put("entity", Entityname);
			context.put("list", list);
			context.put("tableName", tableName);
			template.merge(context, sw);
			Main.result.append(Entityname+vName+"模板生成成功\n");
		} catch (Exception e) {
			Main.result.append(Entityname+vName+"模板生成异常,请检查sql语句\n");
			return null;
		}
		
		return sw.toString();
	}

	/** 首字母大写 */
	private static String lowerToUp(String str) {
		String resStr = str.replaceFirst(str.substring(0, 1), str.substring(0, 1).toUpperCase());
		return resStr;
	}
}
