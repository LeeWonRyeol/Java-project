package ui;
import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

	

//������â
public class Contract_management extends JFrame{
	Vector<Vector<String>> rowData;
	JTable table;
	JTextField right_jtf[];
	JTextField left_jtf[];
	JComboBox<String> combo1; //���� �޺��ڽ�
	JComboBox<String> combo2; //�����ǰ �޺��ڽ�
	JComboBox<String> combo; //����� �޺��ڽ�
	public Contract_management() {
		setTitle("���� ���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		add(new North(), BorderLayout.NORTH);
		add(new Center(), BorderLayout.CENTER);
		
		setVisible(true);
		setSize(500,700);
	}
	
	class North extends JPanel{
		public North() {
			setLayout(new BorderLayout());
			add(new North_Center(),BorderLayout.CENTER);
			add(new North_South(), BorderLayout.SOUTH);
			
		}
		class North_Center extends JPanel{
			public North_Center() {
				setLayout(new GridLayout(1,2));
				JLabel label[] = new JLabel[7];		
				add(new North_Center_left());
				add(new North_Center_right());
			}
			class North_Center_left extends JPanel{
				public North_Center_left() {
					setLayout(new GridLayout(4,2));
					JLabel label[] = new JLabel[4];
					String label_text[] = {"���ڵ�:","�� �� ��:","�������:","�� �� ó:"};
					left_jtf = new JTextField[3];
					combo1 = new JComboBox<String>();
					Connection conn = null;
					PreparedStatement psmt = null;
					String customer_name = "Select name from customer";	//�� ���� ����ϴ� sql��
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(customer_name);
						ResultSet re = psmt.executeQuery();
						while(re.next()) {
							String name = re.getString("name");
							combo1.addItem(name);						//���� �޺��ڽ��� ������ �߰�
						}
					} catch (Exception e) {
						
					}
					//���� �׸��忡 �޺��ڽ�,�ؽ�Ʈ�ʵ� �߰�
					for(int i = 0 ; i < label.length ; i++) {
						label[i] = new JLabel(label_text[i]);
						add(label[i]);
						if(i==0) {
							left_jtf[i] = new JTextField(10);
							add(left_jtf[i]);
						
						}
						if(i==1) {
							add(combo1);
						}
						else if(i>1){
							i--;
							left_jtf[i] = new JTextField(10);
							add(left_jtf[i]);
							i++;
						}
					}
					//�޺��ڽ����� ���õ� ���� ���� ���� ��ȭ�� ���� ActionListener
					combo1.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							
							PreparedStatement psmt = null;
							Connection conn = null;
							
							String name = (String) combo1.getSelectedItem();	//�޺��ڽ��� ���õ� ������ �����´�
							String customer_info = "Select code,birth,tel from customer where name = '"+ name +"'";
							String setText[] = {"code","birth","tel"};
							try {
								conn = db.connect.makeConnection("customer");
								psmt = conn.prepareStatement(customer_info);
								ResultSet re = psmt.executeQuery();
								while(re.next()) {
									for(int i = 0 ; i < 3 ; i++) {		//�ڵ�,�������,����ó 3ĭ�� ������ �ٲ��ִ� �ݺ���
										String info = re.getString(setText[i]);
										left_jtf[i].setText(info);
										left_jtf[i].setEnabled(false);	//JTextFiled�� ��Ȱ��ȭ ��Ŵ *���������Ұ�*										
									}
								}
							} catch (Exception e1) {
								
							}
							Select_all();
						}
					});
					
					
					
					//�޺��ڽ����� ���õ� ���� ���� ���� ����� ���� sql��
					String name = (String) combo1.getSelectedItem();	//�޺��ڽ��� ���õ� ������ �����´�
					String customer_info = "Select code,birth,tel from customer where name = '"+ name +"'";
					String setText[] = {"code","birth","tel"};
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(customer_info);
						ResultSet re = psmt.executeQuery();
						while(re.next()) {
							for(int i = 0 ; i < 3 ; i++) {		//�ڵ�,�������,����ó 3ĭ�� ������ �ٲ��ִ� �ݺ���
								String info = re.getString(setText[i]);
								left_jtf[i].setText(info);
								left_jtf[i].setEnabled(false);	//JTextFiled�� ��Ȱ��ȭ ��Ŵ *���������Ұ�*										
							}
						}
					} catch (Exception e) {
						
					}
					
				}
			}
			
			class North_Center_right extends JPanel{
				public North_Center_right() {
					setLayout(new GridLayout(3,2));
					JLabel label[] = new JLabel[3];
					String label_text[] = {"�����ǰ:","���Աݾ�:","�������:"};
					right_jtf = new JTextField[2];
					
					combo2 = new JComboBox<String>();
					Connection conn = null;
					PreparedStatement psmt = null;
					String contract = "select distinct ContractName from contract ";	//�ߺ����� �����ǰ ���� ����ϴ� sql
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(contract);
						ResultSet re = psmt.executeQuery();
						while(re.next()) {
							String contract_name = re.getString("contractname");
							combo2.addItem(contract_name);								//�����ǰ �޺��ڽ��� �����ǰ���� �߰�
						}
					} catch (Exception e) {
						
					}
					//���� �׸��忡 �޺��ڽ�,�ؽ�Ʈ�ʵ� �߰�
					for(int i = 0 ; i < label.length ; i++) {
						label[i] = new JLabel(label_text[i]);
						add(label[i]);
						if(i==0) {
							add(combo2);
						}else if(i>0) {
							i--;
							right_jtf[i] = new JTextField(10);
							add(right_jtf[i]);
							i++;
						}
						
					}
					
				}
			}
		}
		
		class North_South extends JPanel{
			public North_South() {
				JLabel label = new JLabel("�����: ");
				combo = new JComboBox<String>();
				JButton btn[] = new JButton[4];
				String btn_text[] = {"����","����","���Ϸ�����","�ݱ�"};
				add(label);
				Connection conn = null;
				PreparedStatement psmt = null;
				String admin = "select name from admin ";
				try {
					conn = db.connect.makeConnection("customer");
					psmt = conn.prepareStatement(admin);
					ResultSet re = psmt.executeQuery();
					while(re.next()) {
						String admin_name = re.getString("name");
						combo.addItem(admin_name);
					}
				} catch (Exception e) {
					
				}
				add(combo);
				for(int i = 0 ; i < btn.length ; i++) {
					btn[i] = new JButton(btn_text[i]);
					add(btn[i]);
					//����,����,���Ϸ�����,�ݱ� ��ư�� ���� ActionListener�߰�
					btn[i].addActionListener(new MyActionListener());						
				}
			}
		}
	}
	
	class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			String pattern = "yyyy-mm-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			Date now = new Date();
			String nowString = sdf.format(now);
			
			//���Թ�ư�� ��������
			if(btn.getText().equals("����")) {									
				if(right_jtf[0].getText().equals("") || right_jtf[1].getText().equals("")) {
					JOptionPane.showMessageDialog(null,"��ĭ�� �����մϴ�!!","Error",JOptionPane.ERROR_MESSAGE);
				}else {
					Connection conn = null;
					PreparedStatement psmt = null;
					//���� ��¥ ��������
			
					String product = (String) combo2.getSelectedItem(); 					//�����ǰ ��������
					String admin = (String) combo.getSelectedItem(); 					//����ڸ� ��������					
					Calendar cal = Calendar.getInstance();//��¥
					String date = Integer.toString(cal.get(cal.YEAR))+"-"+Integer.toString(cal.get(cal.MONTH)+1)+"-"+Integer.toString(cal.get(cal.DATE));
					String sql = "insert into contract values(?,?,?,?,?,?)";
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(sql);
						psmt.setString(1, left_jtf[0].getText());
						psmt.setString(2, product);
						psmt.setInt(3, Integer.valueOf(right_jtf[0].getText()));
						psmt.setString(4, date);
						psmt.setInt(5, Integer.valueOf(right_jtf[1].getText()));
						psmt.setString(6, admin);
						int re = psmt.executeUpdate(); 
					} catch (Exception e1) {
						System.out.println("error");
					}
					
					Select_all();
				}
			}
			
			//������ư�� ��������
			else if(btn.getText().equals("����")) {			
				int selectrow = table.getSelectedRow();		//�����Ѱ� ��������
				Vector<String> v = rowData.get(selectrow);	//�����Ѱ��� ���Ϳ� �־���
				String code = v.get(0);						//�����Ѱ��� ���ڵ带 ������
				String product = v.get(1);					//�����Ѱ��� �����ǰ���� ������
				int var = JOptionPane.showConfirmDialog(null, code + "(" + product + ")�� �����Ͻðڽ��ϱ�?","������� ����",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);	//�������� ����������� ��ȭ����
				if(var == 0) {													//�� ��ư�� ������ �� ���� ���� 
					if(selectrow == -1) return;										//�����Ѱ��� ���� ��� ����
					DefaultTableModel m = (DefaultTableModel)table.getModel();	
					m.removeRow(selectrow);											//������ ���� ������
					
					Connection conn = null;
					PreparedStatement psmt = null;
					String sql = "delete from contract where customerCode=?";	//������ ���� �����ϴ� sql��
					try {
						conn = db.connect.makeConnection("customer");
						psmt = conn.prepareStatement(sql);
						psmt.setString(1,code);
						int re = psmt.executeUpdate();
					} catch (Exception e2) {
						
					}
				}
			}
			
			//���Ϸ����� ��ư�� ��������
			else if(btn.getText().equals("���Ϸ�����")) {
				JFrame j = new JFrame();
				FileDialog fdSave = new FileDialog(j, "��������", FileDialog.SAVE);	//������ �����ϱ����� dialog��ü ����
				fdSave.setVisible(true);				//����ȭ		
				String path = fdSave.getDirectory();	//���ϰ�� ��� *��ҹ�ư�� ������ null��ȯ
				String name = fdSave.getFile();
				
				if(path == null) {	//��ҹ�ư�� �������� 
					return;
				}
				File file = new File(path);
				
				BufferedWriter br = null;
				String customer_name = (String) combo1.getSelectedItem();	//�޺��ڽ��� ���õ� ���� ��������
				String code = left_jtf[0].getText();
				try {
					/*------------------------������ ���Ͽ� �Է�*--------------------------*/
					br = new BufferedWriter(new FileWriter(file + "/" + name));
					br.write("���� : " + customer_name +"(" + code + ")" +  "\r\n\n");
					br.flush();
					br.write("�����ǰ\t\t���Աݾ�\t\t������\t\t�������\t����ڸ�\r\n");
					br.flush();
					for(int i = 0 ; i < table.getRowCount() ; i++) {
						for(int k = 1 ; k < table.getColumnCount() ; k++) {
							br.write(table.getValueAt(i, k) + "\t");
							br.flush();
						}
						br.write("\r\n");
						br.flush();
					}
					/*--------------------------------------------------------------*/
					
					
				} catch (Exception e2) {
					
				}
			}
			
			//�ݱ��ư�� ��������
			else if(btn.getText().equals("�ݱ�")) {
				dispose();
			}
		}
	}
	
	class Center extends JPanel{
		public Center() {
			setLayout(new BorderLayout());
			add(new Center_North(), BorderLayout.NORTH);
			add(new Center_Center(), BorderLayout.CENTER);
		}
		class Center_North extends JPanel{
			public Center_North() {
				JLabel title = new JLabel("< �� ���� ��� ��Ȳ >");		//Ÿ��Ʋ �� ����
				add(title);						
			}
		}
		//�� ���� ��� ��Ȳ�� ��Ÿ���� JTABLE
		class Center_Center extends JPanel{
			public Center_Center() {
				rowData = new Vector<Vector<String>>();
				Vector<String> colData = new Vector<String>();
				String title[] = {"customerCode","contractName","regPrice","regDate","monthPrice","adminName"};
				for(int i = 0 ; i < title.length ; i++) {
					colData.add(title[i]);
				}
				table = new JTable(rowData,colData);				//������ �����Ȳ�� ��Ÿ���� JTable 
				JScrollPane jsp = new JScrollPane(table);			
				add(jsp);
				Select_all();
			}
		}
		//�� ���� ��� ��Ȳ�� ������ִ� SQL��
		
	}
	
	//������ ���� ���� ������ �����Ȳ�� ��Ÿ���ִ� �޼ҵ�
	void Select_all() {
		String code = left_jtf[0].getText(); //�� �ڵ带 ������
		String sql = "select * from contract where customerCode = ?  order by regdate asc"; 	//�� �ڵ忡 ���� �����Ȳ�� ������ִ� sql��
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			rowData.clear();		//rowData ����								
			conn = db.connect.makeConnection("customer");
			psmt = conn.prepareStatement(sql);
			psmt.setString(1,code);
			ResultSet re = psmt.executeQuery();
			while(re.next()) {
				Vector<String> v = new Vector<String>();
				for(int i = 0 ; i < 6 ; i++) {
					v.add(re.getString(i+1));	//��µ� ������ ����v�� �߰�	
				}
				rowData.add(v);		//rowData�� ����v �߰�
			}
			table.updateUI();		//���̺��� ������Ʈ����		
		} catch (Exception e) {
			
		}
	}
}
