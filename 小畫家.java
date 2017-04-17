
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.Point;//畫布
import java.awt.event.MouseEvent;//畫布
import java.awt.event.MouseMotionAdapter;//畫布
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;//畫布
import java.awt.Graphics;//畫布
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;//畫布

import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JColorChooser;
import java.awt.BasicStroke;
public class 小畫家 extends JFrame
  {

	private final JLabel statusBar;
	private JComboBox<String> toolComboBox;
	private final ArrayList<Point> points = new ArrayList<>();
    private ArrayList<paintObject>point = new ArrayList<>();
    private ArrayList<Savepaint>saveunfilled = new ArrayList<>();   
    private ArrayList<Savepaint>savefilled = new ArrayList<>();
	private String[] names= {"筆刷","直線","橢圓形","矩形","圓角矩形"};
	private JRadioButton bigJRadioButton;
	private JRadioButton midumJRadioButton;
	private JRadioButton smallJRadioButton;
	private ButtonGroup radioGroup;
	private JCheckBox fillCheckBox; 
	private JButton foregroundButton;
	private JButton backgroundButton;
	private JButton eraseButton;
	private Color color1;
	private Color color2;
	private int size = 5,fill = 0,kind = 0;
	private int x1,y1,x2,y2;
	private Savepaint painting;
	private BasicStroke BrushSize = new BasicStroke(5);
	

	
  public static void main(String[] args)                      //主程式
	   {
	    
	      HW2_104403543 LayoutFrame = new HW2_104403543();
	      LayoutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      LayoutFrame.setSize(500,500);
	      LayoutFrame.setVisible(true);	
	   }
	 
	 public 小畫家()
	 {
		 super("小畫家");                                          //Title
		 setLayout(new BorderLayout());
		 PaintPanel paintpanel = new PaintPanel(); 
	     add(paintpanel,BorderLayout.CENTER);              //加入畫板
		 paintpanel.setBackground(Color.WHITE);               //畫板顏色設為白色
		 MouseHandler mousehandler= new MouseHandler();           //讀取滑鼠的event
	       
	     paintpanel.addMouseListener( mousehandler);	            
	     paintpanel.addMouseMotionListener(mousehandler);
	     FuncPanel funcpanel = new FuncPanel();
	     add(funcpanel, BorderLayout.WEST);                 //加入工具列
         statusBar = new JLabel("超出畫布");
         add(statusBar,BorderLayout.SOUTH);                 //加入座標列
         
         
        toolComboBox.addActionListener(new ActionListener()
         {		 
        		 public void actionPerformed(ActionEvent event)        		 //實作出COMBOBOX
        		 {
        	            kind = toolComboBox.getSelectedIndex();  //獲得選取了甚麼形狀
        		 }
         }
       );
            
         smallJRadioButton.addItemListener(new RadioButtonHandler());  //實作出RADIOBUTTON
         midumJRadioButton.addItemListener(new RadioButtonHandler());//實作出RADIOBUTTON
         bigJRadioButton.addItemListener(new RadioButtonHandler());//實作出RADIOBUTTON
         
         fillCheckBox.addItemListener(new ItemListener()   //實作出填滿CHECKBOX       		 
         {
            public void itemStateChanged(ItemEvent event)
        	 {
        		 if(event.getStateChange() == ItemEvent.SELECTED)  //獲取是否填滿
        			 fill = 1;                                       //有勾起來救回傳1
        		 else
        			 fill = 0;
        	 }
         }
        );
         
       
         foregroundButton.addActionListener(new ActionListener()  //實作前景色
		 {
	        public void actionPerformed(ActionEvent event)
	        {
	        	color1 = JColorChooser.showDialog(funcpanel,"選擇前景色",color1); //出現JColorChooser
	        	foregroundButton.setBackground(color1);    //設定前景色按鈕顏色
	        }
		 });
         
         backgroundButton.addActionListener(new ActionListener()  //實作背景色
		 {
	        public void actionPerformed(ActionEvent event)
	        {
	        	color2 = JColorChooser.showDialog(funcpanel,"選擇前景色",color2);
	        	backgroundButton.setBackground(color2);   //設定背景色按鈕顏色
	        	paintpanel.setBackground(color2);  //設定背景色
	        }
		 });
         
         eraseButton.addActionListener(new ActionListener()  //實作清除畫面
         {
              public void actionPerformed(ActionEvent event)
              {
                   	paintpanel.setBackground(Color.WHITE);
                   	color1 = Color.BLACK;       //還原初始
                   	color2 = Color.WHITE;  //還原初始
                   	foregroundButton.setBackground(null);
                   	backgroundButton.setBackground(null);
                   	points.clear();
            		point.clear();
        			saveunfilled.clear();
        			savefilled.clear();//清除所有點
                   	x1=0;x2=0;y1=0;y2=0; //清除所有點
                   	repaint();
 
               }
         });
         
	 }
	 public class PaintPanel extends JPanel  {               //畫布class


			public PaintPanel(){

				addMouseMotionListener(               //抓取mouse motion  event
						new MouseMotionAdapter()
						{
							@Override
							public void mouseDragged(MouseEvent event)   
							{
								points.add(event.getPoint());
							    repaint();              //repaint JFeame
							}
						}
					); 
			}
			@Override
			public void paintComponent(Graphics g)
			   {  				
				       super.paintComponent(g);             //clears drawing area
				       Graphics2D g2d = (Graphics2D) g;  //把g強制變成g2d
				       g2d.setColor(color1);    //設定前景色
				       g2d.setStroke(new BasicStroke(size));
				       if (kind==0)    //筆刷
				       {
				    	   for(Point point:points)
				           g2d.fillOval(point.x,point.y,size,size);
				       }
				       if (kind == 1)  //直線
				       {
				    	   if(fill==0)
				    	   {painting = new Savepaint(color1,BrushSize,new Line2D.Double(x1,y1,x2,y2));
				    	   saveunfilled.add(painting);}
				             
				    	   else
				    		{painting = new Savepaint(color1,BrushSize,new Line2D.Double(x1,y1,x2,y2));
				    	    savefilled.add(painting);}
				    	   
				       }
				       if(kind==2) //橢圓
				       {
				           if(fill==0)
				             {painting = new Savepaint(color1,BrushSize,new Ellipse2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2)));
				             saveunfilled.add(painting);}
				           else
				             {painting = new Savepaint(color1,BrushSize,new Ellipse2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2)));
				             savefilled.add(painting);}
				       }
				         if(kind==3) //矩形
				         {
					           if(fill==0)
					             {painting = new Savepaint(color1,BrushSize,new Rectangle2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2)));
					             saveunfilled.add(painting);}
					           else
					             {painting = new Savepaint(color1,BrushSize,new Rectangle2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2)));
					             savefilled.add(painting);}
				         }
				         if(kind==4) //圓角矩形
				         {
					           if(fill==0)
					             {painting = new Savepaint(color1,BrushSize,new  RoundRectangle2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2),10,10));
					             saveunfilled.add(painting);}
					           else
					             {painting = new Savepaint(color1,BrushSize,new  RoundRectangle2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2),10,10));
					             savefilled.add(painting);}
				         }
				         for (paintObject point : point)//point with p,是我從paintObject拉過來的object, 用來存取paint裡面的Color c,以及size,以及Point p的位置座標
				    	   {
				        	g2d.setColor(point.c);
				        	g2d.fillOval(point.p.x, point.p.y, point.Size ,point.Size); 
				        	
				           }
				        for(Savepaint s:saveunfilled){
				        	g2d.setPaint(s.getColor());
				        	g2d.setStroke(s.getStroke());
				        	g2d.draw(s.getShape());
				        }
				        for(Savepaint s:savefilled){
				        	g2d.setPaint(s.getColor());
				        	g2d.setStroke(s.getStroke());
				        	g2d.fill(s.getShape());
				        }
				       
			   }
		
	   }
      private class MouseHandler extends MouseAdapter              //抓取座標的class
      {
    	  @Override
          public void mouseClicked(MouseEvent event)          //我將點擊 拖曳 等mouse event都只化為座標顯示
          {
             statusBar.setText(String.format("游標位置: (%d, %d)", 
                event.getX(), event.getY()));
          } 

          
          @Override
          public void mousePressed(MouseEvent event)
          {
             statusBar.setText(String.format("游標位置: (%d, %d)", 
                event.getX(), event.getY()));
             x1=event.getX();
             y1=event.getY();
          }

          
          @Override
          public void mouseReleased(MouseEvent event)
          {
             statusBar.setText(String.format("游標位置: (%d, %d)", 
                event.getX(), event.getY()));
             x2=event.getX();
             y2=event.getY();
             repaint();

          }

          
          @Override
          public void mouseEntered(MouseEvent event)
          {
             statusBar.setText(String.format("游標位置: (%d, %d)", 
                event.getX(), event.getY()));
          }

           @Override
          public void mouseMoved( MouseEvent event)
           {
           statusBar.setText( String.format("游標位置 : ( %d, %d)", event.getX(), event.getY()));
          }
           @Override
           public void mouseExited(MouseEvent event)
           {
              statusBar.setText("超出畫布");
              
           }
           @Override
           public void mouseDragged(MouseEvent event)
           {
              statusBar.setText(String.format("游標位置: (%d, %d)", 
                 event.getX(), event.getY()));


           }     

      }     

      private class FuncPanel extends JPanel                       //工具列class
      {


    	  private FuncPanel()       //FuncPanel建構程式
    	   {
    		     setLayout(new GridLayout(11,1));                            //把工具們放在GridLayout
    		     MessageListener messageListener = new MessageListener();   //顯示出JOptionPane的ShowMessage(不包含ComboBox)
    		     BoxListener boxListener = new BoxListener();               //顯示出JOptionPane的ShowMessage(ComboBox)
    		  
    		     JLabel drawTool = new JLabel("[繪圖工具]");
    		     JLabel brushSize = new JLabel("[筆刷大小]");
    		     toolComboBox = new JComboBox<String>(names);
    			 toolComboBox.setMaximumRowCount(3);
    			 add(drawTool);                         
    	         add(toolComboBox);                           //加入ComboBox
    	         toolComboBox.addItemListener(boxListener);      //跳出訊息視窗，顯示被點選物件的名字
    	         add(brushSize);
    	         
    	         smallJRadioButton = new JRadioButton("小",true);
    	         midumJRadioButton = new JRadioButton("中",false);
    	         bigJRadioButton = new JRadioButton("大",false);
    	         add(smallJRadioButton);                                              //加入RadioButton(小)
    	         smallJRadioButton.addActionListener(messageListener);   //跳出訊息視窗，顯示被點選物件的名字
    	         add(midumJRadioButton);                                //加入"中"RadioButton
    	         midumJRadioButton.addActionListener(messageListener); //跳出訊息視窗，顯示被點選物件的名字
    	         add(bigJRadioButton);                              //加入"大"RadioButton
    	         bigJRadioButton.addActionListener(messageListener);//跳出訊息視窗，顯示被點選物件的名字
    	         radioGroup = new ButtonGroup();                 //可以芳入RadioButton的radioGroup
    	         radioGroup.add(smallJRadioButton);                   //將RadioButton加入radioGroup
    	         radioGroup.add(midumJRadioButton);
    	         radioGroup.add(bigJRadioButton);
    	         
    	         fillCheckBox = new JCheckBox("填滿");
    	         add(fillCheckBox);                                //加入fillCheckBox(填滿)
    	         fillCheckBox.addActionListener(messageListener);  //跳出訊息視窗，顯示被點選物件的名字
    	         
    	         foregroundButton = new JButton("前景色");
    	         backgroundButton = new JButton("背景色");
    	         eraseButton = new JButton("清除畫面");
    	         add(foregroundButton);                              //加入"前景色"button
    	         foregroundButton.addActionListener(messageListener); //跳出訊息視窗，顯示被點選物件的名字
    	         add(backgroundButton);                               //加入"背景色"button
    	         backgroundButton.addActionListener(messageListener);//跳出訊息視窗，顯示被點選物件的名字
    	         add(eraseButton);                                 //加入"清除畫面"button
    	         eraseButton.addActionListener(messageListener);    //跳出訊息視窗，顯示被點選物件的名字
    	         
    		  
    	  }

      }
      
       private class MessageListener implements ActionListener//秀出點選訊息(除了COMBOBOX)
       {
    	   public void actionPerformed (ActionEvent event)
    	   {
              JOptionPane.showMessageDialog(null,"你點選了:  "+ event.getActionCommand(),"訊息",JOptionPane.INFORMATION_MESSAGE);     
    	   }    	   
       }
       private class BoxListener implements ItemListener//秀出COMBOBOX點選訊息
       {
    	   public void itemStateChanged(ItemEvent event)
    	   {
    		   String string = (String) event.getItem();
               if (event.getStateChange() == ItemEvent.SELECTED) {
                   JOptionPane.showMessageDialog(null, "你點選了  :  " + string, "訊息",
                           JOptionPane.INFORMATION_MESSAGE);}
    	   }
       }
       
       private class RadioButtonHandler implements ItemListener   //實作出RADIOBUTTON的動作
       {
    	   public void itemStateChanged(ItemEvent event)
    	   {
    		   if (event.getSource() == smallJRadioButton)
    		   {size = 5;
    		   BrushSize = new BasicStroke(5); }
    		   else if (event.getSource() == midumJRadioButton)
    		   {size = 10;
    		   BrushSize = new BasicStroke(10); }
    		   else if (event.getSource() == bigJRadioButton)
    		   {size = 15;
    		   BrushSize = new BasicStroke(15); }
    		   
    	   }
       }
        
  }
      
