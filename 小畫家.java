
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JButton;

import java.awt.Point;//�e��
import java.awt.event.MouseEvent;//�e��
import java.awt.event.MouseMotionAdapter;//�e��
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;//�e��
import java.awt.Graphics;//�e��
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;//�e��

import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

import javax.swing.JColorChooser;
import java.awt.BasicStroke;
public class �p�e�a extends JFrame
  {

	private final JLabel statusBar;
	private JComboBox<String> toolComboBox;
	private final ArrayList<Point> points = new ArrayList<>();
    private ArrayList<paintObject>point = new ArrayList<>();
    private ArrayList<Savepaint>saveunfilled = new ArrayList<>();   
    private ArrayList<Savepaint>savefilled = new ArrayList<>();
	private String[] names= {"����","���u","����","�x��","�ꨤ�x��"};
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
	

	
  public static void main(String[] args)                      //�D�{��
	   {
	    
	      HW2_104403543 LayoutFrame = new HW2_104403543();
	      LayoutFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      LayoutFrame.setSize(500,500);
	      LayoutFrame.setVisible(true);	
	   }
	 
	 public �p�e�a()
	 {
		 super("�p�e�a");                                          //Title
		 setLayout(new BorderLayout());
		 PaintPanel paintpanel = new PaintPanel(); 
	     add(paintpanel,BorderLayout.CENTER);              //�[�J�e�O
		 paintpanel.setBackground(Color.WHITE);               //�e�O�C��]���զ�
		 MouseHandler mousehandler= new MouseHandler();           //Ū���ƹ���event
	       
	     paintpanel.addMouseListener( mousehandler);	            
	     paintpanel.addMouseMotionListener(mousehandler);
	     FuncPanel funcpanel = new FuncPanel();
	     add(funcpanel, BorderLayout.WEST);                 //�[�J�u��C
         statusBar = new JLabel("�W�X�e��");
         add(statusBar,BorderLayout.SOUTH);                 //�[�J�y�ЦC
         
         
        toolComboBox.addActionListener(new ActionListener()
         {		 
        		 public void actionPerformed(ActionEvent event)        		 //��@�XCOMBOBOX
        		 {
        	            kind = toolComboBox.getSelectedIndex();  //��o����F�ƻ�Ϊ�
        		 }
         }
       );
            
         smallJRadioButton.addItemListener(new RadioButtonHandler());  //��@�XRADIOBUTTON
         midumJRadioButton.addItemListener(new RadioButtonHandler());//��@�XRADIOBUTTON
         bigJRadioButton.addItemListener(new RadioButtonHandler());//��@�XRADIOBUTTON
         
         fillCheckBox.addItemListener(new ItemListener()   //��@�X��CHECKBOX       		 
         {
            public void itemStateChanged(ItemEvent event)
        	 {
        		 if(event.getStateChange() == ItemEvent.SELECTED)  //����O�_��
        			 fill = 1;                                       //���İ_�ӱϦ^��1
        		 else
        			 fill = 0;
        	 }
         }
        );
         
       
         foregroundButton.addActionListener(new ActionListener()  //��@�e����
		 {
	        public void actionPerformed(ActionEvent event)
	        {
	        	color1 = JColorChooser.showDialog(funcpanel,"��ܫe����",color1); //�X�{JColorChooser
	        	foregroundButton.setBackground(color1);    //�]�w�e������s�C��
	        }
		 });
         
         backgroundButton.addActionListener(new ActionListener()  //��@�I����
		 {
	        public void actionPerformed(ActionEvent event)
	        {
	        	color2 = JColorChooser.showDialog(funcpanel,"��ܫe����",color2);
	        	backgroundButton.setBackground(color2);   //�]�w�I������s�C��
	        	paintpanel.setBackground(color2);  //�]�w�I����
	        }
		 });
         
         eraseButton.addActionListener(new ActionListener()  //��@�M���e��
         {
              public void actionPerformed(ActionEvent event)
              {
                   	paintpanel.setBackground(Color.WHITE);
                   	color1 = Color.BLACK;       //�٭��l
                   	color2 = Color.WHITE;  //�٭��l
                   	foregroundButton.setBackground(null);
                   	backgroundButton.setBackground(null);
                   	points.clear();
            		point.clear();
        			saveunfilled.clear();
        			savefilled.clear();//�M���Ҧ��I
                   	x1=0;x2=0;y1=0;y2=0; //�M���Ҧ��I
                   	repaint();
 
               }
         });
         
	 }
	 public class PaintPanel extends JPanel  {               //�e��class


			public PaintPanel(){

				addMouseMotionListener(               //���mouse motion  event
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
				       Graphics2D g2d = (Graphics2D) g;  //��g�j���ܦ�g2d
				       g2d.setColor(color1);    //�]�w�e����
				       g2d.setStroke(new BasicStroke(size));
				       if (kind==0)    //����
				       {
				    	   for(Point point:points)
				           g2d.fillOval(point.x,point.y,size,size);
				       }
				       if (kind == 1)  //���u
				       {
				    	   if(fill==0)
				    	   {painting = new Savepaint(color1,BrushSize,new Line2D.Double(x1,y1,x2,y2));
				    	   saveunfilled.add(painting);}
				             
				    	   else
				    		{painting = new Savepaint(color1,BrushSize,new Line2D.Double(x1,y1,x2,y2));
				    	    savefilled.add(painting);}
				    	   
				       }
				       if(kind==2) //���
				       {
				           if(fill==0)
				             {painting = new Savepaint(color1,BrushSize,new Ellipse2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2)));
				             saveunfilled.add(painting);}
				           else
				             {painting = new Savepaint(color1,BrushSize,new Ellipse2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2)));
				             savefilled.add(painting);}
				       }
				         if(kind==3) //�x��
				         {
					           if(fill==0)
					             {painting = new Savepaint(color1,BrushSize,new Rectangle2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2)));
					             saveunfilled.add(painting);}
					           else
					             {painting = new Savepaint(color1,BrushSize,new Rectangle2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2)));
					             savefilled.add(painting);}
				         }
				         if(kind==4) //�ꨤ�x��
				         {
					           if(fill==0)
					             {painting = new Savepaint(color1,BrushSize,new  RoundRectangle2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2),10,10));
					             saveunfilled.add(painting);}
					           else
					             {painting = new Savepaint(color1,BrushSize,new  RoundRectangle2D.Double(Math.min(x1,x2),Math.min(y1,y2),Math.abs(x1-x2),Math.abs(y1-y2),10,10));
					             savefilled.add(painting);}
				         }
				         for (paintObject point : point)//point with p,�O�ڱqpaintObject�ԹL�Ӫ�object, �ΨӦs��paint�̭���Color c,�H��size,�H��Point p����m�y��
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
      private class MouseHandler extends MouseAdapter              //����y�Ъ�class
      {
    	  @Override
          public void mouseClicked(MouseEvent event)          //�ڱN�I�� �즲 ��mouse event���u�Ƭ��y�����
          {
             statusBar.setText(String.format("��Ц�m: (%d, %d)", 
                event.getX(), event.getY()));
          } 

          
          @Override
          public void mousePressed(MouseEvent event)
          {
             statusBar.setText(String.format("��Ц�m: (%d, %d)", 
                event.getX(), event.getY()));
             x1=event.getX();
             y1=event.getY();
          }

          
          @Override
          public void mouseReleased(MouseEvent event)
          {
             statusBar.setText(String.format("��Ц�m: (%d, %d)", 
                event.getX(), event.getY()));
             x2=event.getX();
             y2=event.getY();
             repaint();

          }

          
          @Override
          public void mouseEntered(MouseEvent event)
          {
             statusBar.setText(String.format("��Ц�m: (%d, %d)", 
                event.getX(), event.getY()));
          }

           @Override
          public void mouseMoved( MouseEvent event)
           {
           statusBar.setText( String.format("��Ц�m : ( %d, %d)", event.getX(), event.getY()));
          }
           @Override
           public void mouseExited(MouseEvent event)
           {
              statusBar.setText("�W�X�e��");
              
           }
           @Override
           public void mouseDragged(MouseEvent event)
           {
              statusBar.setText(String.format("��Ц�m: (%d, %d)", 
                 event.getX(), event.getY()));


           }     

      }     

      private class FuncPanel extends JPanel                       //�u��Cclass
      {


    	  private FuncPanel()       //FuncPanel�غc�{��
    	   {
    		     setLayout(new GridLayout(11,1));                            //��u��̩�bGridLayout
    		     MessageListener messageListener = new MessageListener();   //��ܥXJOptionPane��ShowMessage(���]�tComboBox)
    		     BoxListener boxListener = new BoxListener();               //��ܥXJOptionPane��ShowMessage(ComboBox)
    		  
    		     JLabel drawTool = new JLabel("[ø�Ϥu��]");
    		     JLabel brushSize = new JLabel("[����j�p]");
    		     toolComboBox = new JComboBox<String>(names);
    			 toolComboBox.setMaximumRowCount(3);
    			 add(drawTool);                         
    	         add(toolComboBox);                           //�[�JComboBox
    	         toolComboBox.addItemListener(boxListener);      //���X�T�������A��ܳQ�I�磌�󪺦W�r
    	         add(brushSize);
    	         
    	         smallJRadioButton = new JRadioButton("�p",true);
    	         midumJRadioButton = new JRadioButton("��",false);
    	         bigJRadioButton = new JRadioButton("�j",false);
    	         add(smallJRadioButton);                                              //�[�JRadioButton(�p)
    	         smallJRadioButton.addActionListener(messageListener);   //���X�T�������A��ܳQ�I�磌�󪺦W�r
    	         add(midumJRadioButton);                                //�[�J"��"RadioButton
    	         midumJRadioButton.addActionListener(messageListener); //���X�T�������A��ܳQ�I�磌�󪺦W�r
    	         add(bigJRadioButton);                              //�[�J"�j"RadioButton
    	         bigJRadioButton.addActionListener(messageListener);//���X�T�������A��ܳQ�I�磌�󪺦W�r
    	         radioGroup = new ButtonGroup();                 //�i�H�ڤJRadioButton��radioGroup
    	         radioGroup.add(smallJRadioButton);                   //�NRadioButton�[�JradioGroup
    	         radioGroup.add(midumJRadioButton);
    	         radioGroup.add(bigJRadioButton);
    	         
    	         fillCheckBox = new JCheckBox("��");
    	         add(fillCheckBox);                                //�[�JfillCheckBox(��)
    	         fillCheckBox.addActionListener(messageListener);  //���X�T�������A��ܳQ�I�磌�󪺦W�r
    	         
    	         foregroundButton = new JButton("�e����");
    	         backgroundButton = new JButton("�I����");
    	         eraseButton = new JButton("�M���e��");
    	         add(foregroundButton);                              //�[�J"�e����"button
    	         foregroundButton.addActionListener(messageListener); //���X�T�������A��ܳQ�I�磌�󪺦W�r
    	         add(backgroundButton);                               //�[�J"�I����"button
    	         backgroundButton.addActionListener(messageListener);//���X�T�������A��ܳQ�I�磌�󪺦W�r
    	         add(eraseButton);                                 //�[�J"�M���e��"button
    	         eraseButton.addActionListener(messageListener);    //���X�T�������A��ܳQ�I�磌�󪺦W�r
    	         
    		  
    	  }

      }
      
       private class MessageListener implements ActionListener//�q�X�I��T��(���FCOMBOBOX)
       {
    	   public void actionPerformed (ActionEvent event)
    	   {
              JOptionPane.showMessageDialog(null,"�A�I��F:  "+ event.getActionCommand(),"�T��",JOptionPane.INFORMATION_MESSAGE);     
    	   }    	   
       }
       private class BoxListener implements ItemListener//�q�XCOMBOBOX�I��T��
       {
    	   public void itemStateChanged(ItemEvent event)
    	   {
    		   String string = (String) event.getItem();
               if (event.getStateChange() == ItemEvent.SELECTED) {
                   JOptionPane.showMessageDialog(null, "�A�I��F  :  " + string, "�T��",
                           JOptionPane.INFORMATION_MESSAGE);}
    	   }
       }
       
       private class RadioButtonHandler implements ItemListener   //��@�XRADIOBUTTON���ʧ@
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
      
