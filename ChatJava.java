import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.*;

public class ChatJava{
	private String ip="127.0.0.1";
	private int port=5000;
	private String username="临时用户";
	Socket chatsocket;
	PrintWriter writer;
	BufferedReader reader;
	public static void main(String args[]){
		ChatJava chat=new ChatJava();
		chat.go();
	}
	public void go(){
		//变量声明
		File help=new File("help.txt");
		ChatJava chat=new ChatJava();
		
		//组件声明
		JFrame frame=new JFrame("ChatJava");
		JDialog dialoghelp=new JDialog(frame,"使用帮助");
		JMenuBar menubar=new JMenuBar();
		JMenu menu1=new JMenu("连接");
		JMenu menu2=new JMenu("帮助");
		JMenuItem menuitem1_1=new JMenuItem("以储存值建立连接");
		JMenuItem menuitem1_2=new JMenuItem("关闭程序");
		JMenuItem menuitem2_1=new JMenuItem("使用帮助");
		JMenuItem menuitem2_2=new JMenuItem("开发者信息");
		
		JLabel labelstatus=new JLabel("当前状态：未连接");
		JLabel labelset1_1=new JLabel("服务器IP与端口设置：");
		JLabel labelset1_2=new JLabel("IP地址：");
		JLabel labelset1_3=new JLabel("端口：");
		JLabel labelset1_4=new JLabel("用户名：");
		JLabel labelset2_1=new JLabel("第二个标签里的内容");
		JLabel labelhelp=new JLabel();
		JButton buttonsend=new JButton("发送信息");
		JButton buttonsocket=new JButton("保存并连接");
		JButton buttonclosedialog=new JButton("关闭窗口");
		JTextField textfieldinput=new JTextField("");
		JTextField textfieldip=new JTextField("127.0.0.1");
		JTextField textfieldport=new JTextField("5000");
		JTextField textfieldname=new JTextField("临时用户");
		JTextArea textareachat=new JTextArea("欢迎使用本聊天软件。");
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
		
		//组件设置		
		tabbedpane.addTab("网络",panelinfo_1);
		tabbedpane.addTab("外观",panelinfo_2);
		
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
		
		//线程设置
		class ChatThread extends Thread{
			public void run(){
				String s;
				try{
					chatsocket=new Socket(ip,port);
					labelstatus.setText("当前状态：已连接");
					writer=new PrintWriter(chatsocket.getOutputStream());
					System.out.println("1");
					InputStreamReader stream=new InputStreamReader(chatsocket.getInputStream());
					System.out.println("2");
					reader=new BufferedReader(stream);
					textareachat.append("\n\r"+"已连接。");
					String message=reader.readLine();
					System.out.println("4");
					while((s=reader.readLine())!=null){
						System.out.println("Message: "+s);
						textareachat.append("\n\r"+s);
					}
				}catch(UnknownHostException ex){
					JOptionPane.showMessageDialog(frame,"填入IP地址有误，请重新填写！","错误",JOptionPane.ERROR_MESSAGE);
				}catch(IOException ex){
					JOptionPane.showMessageDialog(frame,"服务器端未开启或连接错误，请稍后重试！","错误",JOptionPane.ERROR_MESSAGE);
				}finally{
					textareachat.append("\n\r"+"连接失败。");
				}
			}
		}

		//内部监听类设置
		class ButtonSendListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				try{
					writer.println(username+"："+textfieldinput.getText());
					writer.flush();
				}catch(Exception ex){ex.printStackTrace();}
				textfieldinput.setText("");
				textfieldinput.requestFocus();
			}
		}
		class ButtonSocketListener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				textareachat.append("\n\r"+"连接中……");
				try{
					ip=textfieldip.getText();
					port=Integer.parseInt(textfieldport.getText());
					username=textfieldname.getText();
					ChatThread chatcom=new ChatThread();
					chatcom.start();
				}catch(NumberFormatException ex){
					JOptionPane.showMessageDialog(frame,"填入端口号有误，请重新填写！","错误",JOptionPane.ERROR_MESSAGE);
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
						JOptionPane.showMessageDialog(frame,"文件不可读！","错误",JOptionPane.ERROR_MESSAGE);
				}					
				else
					JOptionPane.showMessageDialog(frame,"文件已被移动位置或删除！","错误",JOptionPane.ERROR_MESSAGE);
			}
		}
		class Menu2_2Listener implements ActionListener{
			public void actionPerformed(ActionEvent e){
				JOptionPane.showMessageDialog(frame,"——","开发者信息",JOptionPane.ERROR_MESSAGE);
			}
		}
		//注册内部监听类
		buttonsend.addActionListener(new ButtonSendListener());
		buttonsocket.addActionListener(new ButtonSocketListener());
		menuitem1_1.addActionListener(new Menu1_1Listener());
		menuitem1_2.addActionListener(new Menu1_2Listener());
		menuitem2_1.addActionListener(new Menu2_1Listener());
		menuitem2_2.addActionListener(new Menu2_2Listener());
		
	}
		
}