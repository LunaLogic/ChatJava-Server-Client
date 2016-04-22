import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;

public class ChatJava{
	private String ip="127.0.0.1";
	private int port=5000;
	private String username="��ʱ�û�";
	Socket chatsocket;
	PrintWriter writer;
	BufferedReader reader;
	public static void main(String args[]){
		ChatJava chat=new ChatJava();
		chat.go();
	}
	public void go(){
		//��������
		File help=new File("help.txt");
		ChatJava chat=new ChatJava();
		
		//�������
		JFrame frame=new JFrame("ChatJava");
		JDialog dialoghelp=new JDialog(frame,"ʹ�ð���");
		JMenuBar menubar=new JMenuBar();
		JMenu menu1=new JMenu("����");
		JMenu menu2=new JMenu("����");
		JMenuItem menuitem1_1=new JMenuItem("�Դ���ֵ��������");
		JMenuItem menuitem1_2=new JMenuItem("�رճ���");
		JMenuItem menuitem2_1=new JMenuItem("ʹ�ð���");
		JMenuItem menuitem2_2=new JMenuItem("��������Ϣ");
		
		JLabel labelstatus=new JLabel("��ǰ״̬��δ����");
		JLabel labelset1_1=new JLabel("������IP��˿����ã�");
		JLabel labelset1_2=new JLabel("IP��ַ��");
		JLabel labelset1_3=new JLabel("�˿ڣ�");
		JLabel labelset1_4=new JLabel("�û�����");
		JLabel labelset2_1=new JLabel("�ڶ�����ǩ�������");
		JLabel labelhelp=new JLabel();
		JButton buttonsend=new JButton("������Ϣ");
		JButton buttonsocket=new JButton("���沢����");
		JButton buttonclosedialog=new JButton("�رմ���");
		JTextField textfieldinput=new JTextField("");
		JTextField textfieldip=new JTextField("127.0.0.1");
		JTextField textfieldport=new JTextField("5000");
		JTextField textfieldname=new JTextField("��ʱ�û�");
		JTextArea textareachat=new JTextArea("��ӭʹ�ñ����������");
		JScrollPane scrollpane1=new JScrollPane(textareachat);
		
		JPanel panelstatus=new JPanel();		//status
		JPanel panelinfo_1=new JPanel();		//info_1
		JPanel panelinfo_2=new JPanel();		//info_2		
		JPanel panelinput=new JPanel();			//input
		
		JTabbedPane tabbedpane=new JTabbedPane();

		GridBagLayout gridbaglayout=new GridBagLayout();
		GridLayout gridlayoutset1=new GridLayout(15,1);
		GridLayout gridlayoutset2=new GridLayout(2,1);
		GridLayout gridlayoutsend=new GridLayout(2,1);
		GridBagConstraints constraints=new GridBagConstraints();{
			constraints.fill=GridBagConstraints.BOTH;
			constraints.gridwidth=0;
			constraints.weightx=1;
			constraints.weighty=0;
			gridbaglayout.setConstraints(panelstatus,constraints);
			constraints.gridwidth=1;
			constraints.weightx=0;
			constraints.weighty=1;
			gridbaglayout.setConstraints(tabbedpane,constraints);
			constraints.gridwidth=0;
			constraints.weightx=1;
			constraints.weighty=1;
			gridbaglayout.setConstraints(scrollpane1,constraints);
			constraints.gridwidth=0;
			constraints.weightx=1;
			constraints.weighty=0;
			gridbaglayout.setConstraints(panelinput,constraints);
		}
		
		//�������		
		tabbedpane.addTab("����",panelinfo_1);
		tabbedpane.addTab("���",panelinfo_2);
		
		frame.setJMenuBar(menubar);
		frame.setLayout(gridbaglayout);
		dialoghelp.getContentPane().add(labelhelp,BorderLayout.CENTER);
		dialoghelp.getContentPane().add(buttonclosedialog,BorderLayout.SOUTH);
		panelinfo_1.setLayout(gridlayoutset1);
		panelinfo_2.setLayout(gridlayoutset2);
		panelinput.setLayout(gridlayoutsend);
		frame.add(panelstatus);
		frame.add(tabbedpane);
		frame.add(scrollpane1);
		frame.add(panelinput);
		menubar.add(menu1);
		menubar.add(menu2);
		menu1.add(menuitem1_1);
		menu1.addSeparator();
		menu1.add(menuitem1_2);
		menu2.add(menuitem2_1);
		menu2.addSeparator();
		menu2.add(menuitem2_2);
		panelstatus.add(labelstatus);
		panelinfo_1.add(labelset1_1);
		panelinfo_1.add(labelset1_2);
		panelinfo_1.add(textfieldip);
		panelinfo_1.add(labelset1_3);
		panelinfo_1.add(textfieldport);
		panelinfo_1.add(labelset1_4);
		panelinfo_1.add(textfieldname);
		panelinfo_1.add(buttonsocket);
		panelinfo_2.add(labelset2_1);
		panelinput.add(textfieldinput);
		panelinput.add(buttonsend);
		textareachat.setLineWrap(true);
		textareachat.setEditable(false);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600,600);
		frame.setVisible(true);
		
		//�߳�����
		class ChatThread extends Thread{
			public void run(){
				String s;
				try{
					chatsocket=new Socket(ip,port);
					labelstatus.setText("��ǰ״̬��������");
					writer=new PrintWriter(chatsocket.getOutputStream());
					System.out.println("1");
					InputStreamReader stream=new InputStreamReader(chatsocket.getInputStream());
					System.out.println("2");
					reader=new BufferedReader(stream);
					textareachat.append("\n\r"+"�����ӡ�");
					String message=reader.readLine();
					System.out.println("4");
					while((s=reader.readLine())!=null){
						System.out.println("Message: "+s);
						textareachat.append("\n\r"+s);
					}
				}catch(UnknownHostException ex){
					JOptionPane.showMessageDialog(frame,"����IP��ַ������������д��","����",JOptionPane.ERROR_MESSAGE);
				}catch(IOException ex){
					JOptionPane.showMessageDialog(frame,"��������δ���������Ӵ������Ժ����ԣ�","����",JOptionPane.ERROR_MESSAGE);
				}finally{
					textareachat.append("\n\r"+"����ʧ�ܡ�");
				}
			}
		}

		//�ڲ�����������
		class ButtonSendListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				try{
					writer.println(username+"��"+textfieldinput.getText());
					writer.flush();
				}catch(Exception ex){ex.printStackTrace();}
				textfieldinput.setText("");
				textfieldinput.requestFocus();
			}
		}
		class ButtonSocketListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				textareachat.append("\n\r"+"�����С���");
				try{
					ip=textfieldip.getText();
					port=Integer.parseInt(textfieldport.getText());
					username=textfieldname.getText();
					ChatThread chatcom=new ChatThread();
					chatcom.start();
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(frame,"����˿ں�������������д��","����",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		class Menu1_1Listener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				ChatThread chatcom=new ChatThread();
				chatcom.start();
			}
		}
		class Menu1_2Listener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		}
		class Menu2_1Listener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				if(help.exists()==true){
					if(help.canRead()==true){
						try{
							FileInputStream fis=new FileInputStream("help.txt");
							InputStreamReader dis=new InputStreamReader(fis);
							BufferedReader reader=new BufferedReader(dis);
							String s;
							StringBuffer t=new StringBuffer();
							while((s=reader.readLine())!=null){
								t.append("\n\r"+s);
							}
							labelhelp.setText(t.toString());
							dialoghelp.pack();
							dialoghelp.setVisible(true);
							dis.close();
						}catch(IOException i){
							System.out.println(i);
						}
					}
					else
						JOptionPane.showMessageDialog(frame,"�ļ����ɶ���","����",JOptionPane.ERROR_MESSAGE);
				}					
				else
					JOptionPane.showMessageDialog(frame,"�ļ��ѱ��ƶ�λ�û�ɾ����","����",JOptionPane.ERROR_MESSAGE);
			}
		}
		class Menu2_2Listener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(frame,"����","��������Ϣ",JOptionPane.ERROR_MESSAGE);
			}
		}
		//ע���ڲ�������
		buttonsend.addActionListener(new ButtonSendListener());
		buttonsocket.addActionListener(new ButtonSocketListener());
		menuitem1_1.addActionListener(new Menu1_1Listener());
		menuitem1_2.addActionListener(new Menu1_2Listener());
		menuitem2_1.addActionListener(new Menu2_1Listener());
		menuitem2_2.addActionListener(new Menu2_2Listener());
		
	}
		
}