package com.wsk.tool.gen.fram;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.wsk.tool.gen.db.MyCon;
import com.wsk.tool.gen.utils.FileTools;
import com.wsk.tool.gen.utils.VelocityTools;
import java.awt.Font;

public class Main extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField sql;
	private JTextField EntityName;
	private JTextField basePath;
	private JTextField EntityPath;
	private JTextField DaoPath;
	private JTextField ServicePath;
	private JTextField ServiceImplPath;
	private JTextField ControllerPath;
	private JTextField MapperPath;
	private JCheckBox entity_ck;
	private JCheckBox dao_ck;
	private JCheckBox service_ck;
	private JCheckBox serviceImpl_ck;
	private JCheckBox controller_ck;
	private JCheckBox mapper_ck;
	private JTextField userName;
	private JTextField password;
	private JLabel lblUrl;
	private JTextField url;
	private JLabel lblDriver;
	private JTextField Driver;
	public static TextArea result;
	private JTextField JspPath;
	private JLabel lblJs;
	private JTextField JsPath;
	private JCheckBox jsp_ck;
	private JCheckBox js_ck;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 581, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		JLabel lblSql = new JLabel("sql语句:");
		lblSql.setBounds(10, 10, 54, 15);
		panel.add(lblSql);

		sql = new JTextField();
		sql.setText("select t.*, t.rowid from HD_OPTION t");
		sql.setBounds(84, 7, 353, 21);
		panel.add(sql);
		sql.setColumns(10);

		JButton button = new JButton("\u751F\u6210");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Thread(new Runnable() {
					public void run() {
						result.setText("");
						result.append("开始生成模板\n");
						String sql_name = sql.getText().toString();
						String base_path = basePath.getText().toString().trim();
						String entity_Name = EntityName.getText().toString().trim();
						String entity_path = EntityPath.getText().toString().trim();
						String controller_path = ControllerPath.getText().toString().trim();
						String dao_path = DaoPath.getText().toString().trim();
						String service_path = ServicePath.getText().toString().trim();
						String serviceImpl_path = ServiceImplPath.getText().toString().trim();
						String jsp_path = JspPath.getText().toString().trim();
						String js_path = JsPath.getText().toString().trim();
						String mapper_path = MapperPath.getText().toString().trim();
						String db_username = userName.getText().toString().trim();
						String db_password = password.getText().toString().trim();
						String db_url = url.getText().toString().trim();
						String db_driver = Driver.getText().toString().trim();
						Connection conn = MyCon.getConnection(db_url, db_username, db_password, db_driver);
						VelocityTools velocity = new VelocityTools();
						FileTools fileTools = new FileTools();
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("sqlName", sql_name);
						map.put("base_path", base_path);
						map.put("entity_Name", entity_Name);
						map.put("entity_path", entity_path);
						map.put("controller_path", controller_path);
						map.put("dao_path", dao_path);
						map.put("service_path", service_path);
						map.put("serviceImpl_path", serviceImpl_path);
						map.put("mapper_path", mapper_path);
						map.put("jsp_path", jsp_path);
						map.put("js_path", js_path);
						if (conn != null) {

							if (entity_ck.isSelected()) {
								result.append("开始生成Entity模板\n");
								String code = velocity.generate(conn, "Entity.vm", map);
								File EntityPath = new File(base_path + entity_path + "\\");
								fileTools.writeFile(EntityPath, entity_Name, ".java", code);
							}
							if (controller_ck.isSelected()) {
								result.append("开始生成Controller模板\n");
								String code = velocity.generate(conn, "Controller.vm", map);
								File EntityPath = new File(base_path + controller_path + "\\");
								fileTools.writeFile(EntityPath, entity_Name, "Controller.java", code);
							}
							if (dao_ck.isSelected()) {
								result.append("开始生成Dao模板\n");
								String code = velocity.generate(conn, "Dao.vm", map);
								File EntityPath = new File(base_path + dao_path + "\\");
								fileTools.writeFile(EntityPath, entity_Name, "Dao.java", code);
							}
							if (service_ck.isSelected()) {
								result.append("开始生成ServiceI模板\n");
								String code = velocity.generate(conn, "ServiceI.vm", map);
								File EntityPath = new File(base_path + service_path + "\\");
								fileTools.writeFile(EntityPath, entity_Name, "ServiceI.java", code);
							}
							if (serviceImpl_ck.isSelected()) {
								result.append("开始生成ServiceImpl模板\n");
								String code = velocity.generate(conn, "ServiceImpl.vm", map);
								File EntityPath = new File(base_path + serviceImpl_path + "\\");
								fileTools.writeFile(EntityPath, entity_Name, "ServiceImpl.java", code);
							}
							if (mapper_ck.isSelected()) {
								result.append("开始生成Mapper模板\n");
								String code = velocity.generate(conn, "Mapper.vm", map);
								File EntityPath = new File(base_path + mapper_path + "\\");
								fileTools.writeFile(EntityPath, entity_Name, "Mapper.xml", code);
							}
							if (js_ck.isSelected()) {
								result.append("开始生成ServiceImpl模板\n");
								String code = velocity.generate(conn, "js.vm", map);
								File EntityPath = new File(base_path + js_path + "\\");
								fileTools.writeFile(EntityPath, entity_Name.toLowerCase(), ".js", code);
							}
							if (jsp_ck.isSelected()) {
								result.append("开始生成jsp模板\n");
								String code = velocity.generate(conn, "jsp.vm", map);
								File EntityPath = new File(base_path + jsp_path + "\\");
								fileTools.writeFile(EntityPath, entity_Name.toLowerCase(), ".jsp", code);
							}
						}
					}
				}).start();
			}
		});
		button.setBounds(447, 6, 93, 23);
		panel.add(button);

		JLabel lblUsername = new JLabel("UserName:");
		lblUsername.setBounds(10, 40, 72, 15);
		panel.add(lblUsername);

		userName = new JTextField();
		userName.setText("TS_YLZ_HD");
		userName.setBounds(84, 37, 178, 21);
		panel.add(userName);
		userName.setColumns(10);

		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(309, 40, 66, 15);
		panel.add(lblPassword);

		password = new JTextField();
		password.setText("TS_YLZ_HD");
		password.setBounds(374, 37, 169, 21);
		panel.add(password);
		password.setColumns(10);

		lblUrl = new JLabel("Url:");
		lblUrl.setBounds(10, 71, 54, 15);
		panel.add(lblUrl);

		url = new JTextField();
		url.setText("jdbc:oracle:thin:@127.0.0.1:1521/orcl");
		url.setBounds(84, 68, 178, 21);
		panel.add(url);
		url.setColumns(10);

		lblDriver = new JLabel("Driver:");
		lblDriver.setBounds(321, 71, 54, 15);
		panel.add(lblDriver);

		Driver = new JTextField();
		Driver.setText("oracle.jdbc.driver.OracleDriver");
		Driver.setBounds(374, 68, 169, 21);
		panel.add(Driver);
		Driver.setColumns(10);

		JLabel lblEntityname = new JLabel("EntityName:");
		lblEntityname.setBounds(10, 107, 72, 15);
		panel.add(lblEntityname);

		EntityName = new JTextField();
		EntityName.setText("Hd_Options");
		EntityName.setBounds(84, 104, 178, 21);
		panel.add(EntityName);
		EntityName.setColumns(10);

		JLabel label = new JLabel("基础路径:");
		label.setBounds(10, 144, 72, 15);
		panel.add(label);

		basePath = new JTextField();
		basePath.setText("E:\\ylz\\workspace\\ylzHD\\src\\");
		basePath.setBounds(85, 141, 177, 21);
		panel.add(basePath);
		basePath.setColumns(10);

		JLabel lblEntity = new JLabel("Entity路径:");
		lblEntity.setBounds(303, 107, 72, 15);
		panel.add(lblEntity);

		EntityPath = new JTextField();
		EntityPath.setText("com\\ylz\\hd\\entity\\system");
		EntityPath.setBounds(374, 104, 169, 21);
		panel.add(EntityPath);
		EntityPath.setColumns(10);

		JLabel lblDao = new JLabel("Dao路径:");
		lblDao.setBounds(321, 144, 54, 15);
		panel.add(lblDao);

		DaoPath = new JTextField();
		DaoPath.setText("com\\ylz\\hd\\dao\\system");
		DaoPath.setBounds(374, 141, 169, 21);
		panel.add(DaoPath);
		DaoPath.setColumns(10);

		JLabel lblService = new JLabel("Service路径:");
		lblService.setBounds(10, 180, 72, 15);
		panel.add(lblService);

		ServicePath = new JTextField();
		ServicePath.setText("com\\ylz\\hd\\service\\system");
		ServicePath.setBounds(84, 177, 178, 21);
		panel.add(ServicePath);
		ServicePath.setColumns(10);

		JLabel lblServiceimpl = new JLabel("ServiceImpl路径:");
		lblServiceimpl.setBounds(272, 180, 103, 15);
		panel.add(lblServiceimpl);

		ServiceImplPath = new JTextField();
		ServiceImplPath.setText("com\\ylz\\hd\\service\\system\\impl");
		ServiceImplPath.setBounds(374, 177, 169, 21);
		panel.add(ServiceImplPath);
		ServiceImplPath.setColumns(10);

		JLabel lblController = new JLabel("Controller:");
		lblController.setBounds(10, 211, 72, 15);
		panel.add(lblController);

		ControllerPath = new JTextField();
		ControllerPath.setText("com\\ylz\\hd\\controller\\system");
		ControllerPath.setBounds(84, 208, 178, 21);
		panel.add(ControllerPath);
		ControllerPath.setColumns(10);

		JLabel lblMapper = new JLabel("Mapper路径:");
		lblMapper.setBounds(303, 211, 72, 15);
		panel.add(lblMapper);

		MapperPath = new JTextField();
		MapperPath.setText("config\\mybatis\\system");
		MapperPath.setBounds(374, 208, 169, 21);
		panel.add(MapperPath);
		MapperPath.setColumns(10);

		entity_ck = new JCheckBox("是否生成Entity");
		entity_ck.setSelected(true);
		entity_ck.setBounds(10, 265, 122, 23);
		panel.add(entity_ck);

		dao_ck = new JCheckBox("是否生产Dao");
		dao_ck.setSelected(true);
		dao_ck.setBounds(152, 265, 103, 23);
		panel.add(dao_ck);

		service_ck = new JCheckBox("是否生成Service");
		service_ck.setSelected(true);
		service_ck.setBounds(289, 265, 128, 23);
		panel.add(service_ck);

		serviceImpl_ck = new JCheckBox("是否生成ServiceImpl");
		serviceImpl_ck.setSelected(true);
		serviceImpl_ck.setBounds(10, 301, 139, 23);
		panel.add(serviceImpl_ck);

		controller_ck = new JCheckBox("是否生成Controller");
		controller_ck.setSelected(true);
		controller_ck.setBounds(152, 301, 139, 23);
		panel.add(controller_ck);

		mapper_ck = new JCheckBox("是否生成Mapper");
		mapper_ck.setSelected(true);
		mapper_ck.setBounds(289, 301, 119, 23);
		panel.add(mapper_ck);

		jsp_ck = new JCheckBox("是否生产Jsp");
		jsp_ck.setSelected(true);
		jsp_ck.setBounds(437, 265, 103, 23);
		panel.add(jsp_ck);

		js_ck = new JCheckBox("是否生成Js");
		js_ck.setSelected(true);
		js_ck.setBounds(437, 301, 103, 23);
		panel.add(js_ck);

		result = new TextArea();
		result.setText(
				"\r\nMysqlDriver:       com.mysql.jdbc.Driver      \r\nMysqlUrl:          jdbc:mysql://127.0.0.1:3306\r\nOracleDriver:      oracle.jdbc.driver.OracleDriver\r\nOracleUrl:         jdbc:oracle:thin:@127.0.0.1:1521/orcl\r\nSqlServerDriver:   com.microsoft.sqlserver.jdbc.SQLServerDriver\r\nSqlServerUrl：     jdbc:microsoft:sqlserver:127.0.0.1:1443;DatabaseName=mydb\r\n");
		result.setFont(new Font("华文楷体", Font.PLAIN, 12));
		result.setBounds(0, 342, 557, 104);
		panel.add(result);

		JLabel lblJsp = new JLabel("Jsp路径:");
		lblJsp.setBounds(28, 244, 54, 15);
		panel.add(lblJsp);

		JspPath = new JTextField();
		JspPath.setText("..\\WebRoot\\jsp\\systemsetting");
		JspPath.setBounds(83, 238, 179, 21);
		panel.add(JspPath);
		JspPath.setColumns(10);

		lblJs = new JLabel("Js路径:");
		lblJs.setBounds(321, 244, 54, 15);
		panel.add(lblJs);

		JsPath = new JTextField();
		JsPath.setText("..\\WebRoot\\js\\systemsetting");
		JsPath.setBounds(374, 236, 166, 21);
		panel.add(JsPath);
		JsPath.setColumns(10);

	}
}
