import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class GraphicsManager3_csubra2 extends JFrame implements ActionListener, MouseListener{
	
		//Data Members
		private Polygon3[] shapes;
		private Polygon3[] p;
		private int n;
		private Polygon3 poly = new Polygon3();
		int count = -1;
		
		JPanel jp = new JPanel( new BorderLayout() ); //the JPanel dialog box
		
		JPanel jp1 = new JPanel(); //first JPanel to store 5 buttons
		JButton jb1 = new JButton("Load File");
		JButton jb2 = new JButton("Save File");
		JButton jb3 = new JButton("  Create ");
		JButton jb4 = new JButton("  Scrap  ");
		JButton jb5 = new JButton("  Keep   ");
		
		JPanel jp2 = new JPanel(new GridLayout(2,1)); //second JPanel in grid layout
		
		JPanel jp3 = new JPanel(){
			public void paint(Graphics g)
			{
				super.paint(g);
				if(!(poly == null))
				{
					Dimension[] di = poly.getDimension(); 
					for(int i=0; i<poly.getSides() - 1; i++)
					{
						g.setColor(Color.BLACK);
						g.drawLine(di[i].width,di[i].height, di[i+1].width, di[i+1].height);
					}
					
					if(poly.getSides() > 2)
					{
						g.setColor(Color.GREEN);
						g.drawLine(di[0].width,di[0].height, di[di.length-1].width, di[di.length-1].height);
					}
				}
			}
	
		}; //third JPanel for drawing
	
		JPanel jp4 = new JPanel(); //fourth JPanel to contain 2 buttons
		JButton jb6 = new JButton("<");
		JButton jb7 = new JButton(">");
	
		Graphics g;
	
		public static void main(String[] args) {
			// TODO Auto-generated method stub
	
			GraphicsManager3_csubra2 gm3 = new GraphicsManager3_csubra2();
			
		}
		
		GraphicsManager3_csubra2()
		{
			shapes = new Polygon3[0];
			p = new Polygon3[1];
			n=0;
			
			this.setDefaultCloseOperation(EXIT_ON_CLOSE);
			this.setSize(800,500);
			this.setVisible(true);
			
			jp.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
			jp1.setBorder(BorderFactory.createLineBorder(Color.RED, 4));
			Border border = jp1.getBorder();
			Border margin = new EmptyBorder(8,8,8,8);
			jp1.setBorder(new CompoundBorder(margin, border));
			jp1.setPreferredSize(new Dimension(400,300));
			
			Box box = Box.createVerticalBox(); 
			box.add(Box.createVerticalStrut(100));
		    box.add(jb1);
		    box.add(Box.createVerticalStrut(10));
		    box.add(jb2);
		    box.add(Box.createVerticalStrut(10));
		    box.add(jb3);
		    box.add(Box.createVerticalStrut(10));
		    box.add(jb4);
		    box.add(Box.createVerticalStrut(10));
		    box.add(jb5);
		    box.add(Box.createVerticalStrut(50));
		    jp1.add(box);  
		    
			jp.add(jp1,BorderLayout.WEST);
			
			jp2.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
			Border border2 = jp2.getBorder();
			Border margin2 = new EmptyBorder(8,8,8,8);
			jp2.setBorder(new CompoundBorder(margin2, border2));
			jp2.setPreferredSize(new Dimension(380,100));
			
			jp3.setBorder(BorderFactory.createLineBorder(Color.PINK, 4));
			Border border3 = jp3.getBorder();
			Border margin3 = new EmptyBorder(8,8,8,8);
			jp3.setBorder(new CompoundBorder(margin3, border3));
			jp3.setPreferredSize(new Dimension(380,50));
			jp2.add(jp3, BorderLayout.NORTH);
			
			jp4.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 4));
			Border border4 = jp4.getBorder();
			Border margin4 = new EmptyBorder(8,8,8,8);
			jp4.setBorder(new CompoundBorder(margin4, border4));
			jp4.setPreferredSize(new Dimension(380,50));
			
			Box box2 = Box.createHorizontalBox(); 
			box2.add(Box.createVerticalStrut(180));
		    box2.add(jb6);
		    box2.add(Box.createHorizontalStrut(20));
		    box2.add(jb7);
		    jp4.add(box2);
			jp2.add(jp4, BorderLayout.SOUTH);
			
			jp.add(jp2,BorderLayout.EAST);
			this.add(jp);
			
			jb1.addActionListener(this);
			jb2.addActionListener(this);
			jb3.addActionListener(this);
			jb4.addActionListener(this);
			jb5.addActionListener(this);
			jb6.addActionListener(this);
			jb7.addActionListener(this);
			
			jp3.addMouseListener(this);
			
		}
		
		//method add takes Polygon object as a parameter and returns nothing
		void add(Polygon3 polygon)
		{
			Polygon3[] shapes2 = new Polygon3[shapes.length+1];
			for(int i=0; i<shapes.length; i++)
			{
				shapes2[i] = shapes[i];
			}
			shapes2[shapes.length] = polygon;
			shapes = new Polygon3[shapes2.length];
			for(int i=0; i<shapes2.length; i++)
			{
			    shapes[i] = shapes2[i];
			}
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		   //Load File actions
			if(e.getSource() == jb1)
			{
				try 
				{
					Scanner scanner = new Scanner(new File("src/shapes.dat"));
					shapes=new Polygon3[scanner.nextInt()];
			        for(int i=0;i<shapes.length;i++)
			        {
			        	shapes[i]=new Polygon3();
			        	if(scanner.hasNextInt()){
			        		shapes[i].setSides(scanner.nextInt());
			        	}
		        		Dimension loadd[]=new Dimension[shapes[i].getSides()];
			        	for(int j=0;j<shapes[i].getSides();j++){
			        		if(scanner.hasNextInt()){
				        		loadd[j]=new Dimension(scanner.nextInt(),scanner.nextInt());
				        	}	
			        	}
			        	shapes[i].setDimension(loadd);
			        }
			        scanner.close();
				} 
				
				catch (FileNotFoundException fnf) 
				{
					// TODO Auto-generated catch block
					fnf.printStackTrace();
				}
			}
			
			//Save File actions
			if(e.getSource() == jb2)
			{	
				DataOutputStream output;
				try 
				{
					output = new DataOutputStream(new FileOutputStream("src/shapes.dat"));
					output.writeInt(shapes.length);
					 for (int i = 0; i < shapes.length; i++) 
					 {
				            output.writeInt(shapes[i].getSides());
				            Dimension saved[]=shapes[i].getDimension();
				            for(int j=0; j<shapes[i].getSides(); j++)
				            {
				            	output.writeInt(saved[j].width);
				            	output.writeInt(saved[j].height);
				            }
				      }
				     output.close();
				} 
				catch (FileNotFoundException fnf) 
				{
					// TODO Auto-generated catch block
					fnf.printStackTrace();
				} 
				catch (IOException ie) 
				{
					// TODO Auto-generated catch block
					ie.printStackTrace();
				}
		       
			}
			
			//Create button actions
			if(e.getSource() == jb3)
			{
				poly = null;
				poly= new Polygon3();
				jp3.repaint();
				revalidate();
				jp3.setBorder(BorderFactory.createLineBorder(Color.PINK, 4));
				jp3.setBackground(Color.WHITE);
				jp3.setEnabled(true);
			}
			
			//Scrap button actions
			if(e.getSource() == jb4)
			{
				poly = null;
				poly= new Polygon3();
				jp3.repaint();
				revalidate();
				jp3.setBorder(BorderFactory.createLineBorder(Color.PINK, 4));
				jp3.setBackground(Color.GRAY);
				jp3.setEnabled(false);
			}
			
			//Keep button actions
			if(e.getSource() == jb5)
			{
				add(poly);
				poly = null;
				poly= new Polygon3();
				jp3.repaint();
				revalidate();
				jp3.setBorder(BorderFactory.createLineBorder(Color.PINK, 4));
				jp3.setBackground(Color.WHITE);
				jp3.setEnabled(true);
			}
			
			// < button actions
			if(e.getSource() == jb6)
			{
				if((shapes == null)||(shapes.length == 0))
					System.out.println("No Polygon3s to view!");
		        else
		        {
			         count--;
			         if(count<0)
			         {
			          count= shapes.length-1;
			          poly= shapes[count];
			          jp3.paint(g);
			         }
			     
			         else 
			         {
			           poly= shapes[count];
			           jp3.paint(g);
			      
			          }
			         
		        }				
			}
			
			// > button actions
			if(e.getSource() == jb7)
			{
				if((shapes == null)||(shapes.length == 0) || count == shapes.length )
					System.out.println("No Polygon3s to view!");
			     else
			     {
			      count++;
			      if (count > shapes.length-1)
			      { 
			       count=0;
			       poly= shapes[count];
			       jp3.paint(g);
			      }
			      else 
			      {
			       poly= shapes[count];
			       jp3.paint(g);
			      }
			      
			     }
			}
		}
	
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == jp3)
			{
				if(jp3.getBackground() == Color.WHITE)
				{
					poly.extend(new Dimension(e.getX(),e.getY()));
					
				}
				
			}
			g = jp3.getGraphics();
			if(poly.getSides() > 1)
			{
				jp3.paint(g);
			}
		}
	
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
			
	}

class Polygon3
{
		//Data Members of class Polygon2
		private int id;
		private int sides = 0;
		private Dimension[] d = new Dimension[0];
		
		//method setSides takes int as a parameter and returns nothing
		void setSides(int n)
		{
			sides = n;
		}
		
		//method setDimension takes Dimension array as a parameter and returns nothing
		void setDimension(Dimension[] dimension)
		{
			d = dimension;
		}
		
		//method getSides takes no parameter and returns int 
		int getSides()
		{
			return sides;
		}
		
		//method getDimension takes no parameter and returns Dimension array
		Dimension[] getDimension()
		{
			return d;
		}
		
		//method clear takes no parameter and returns nothing
		void  clear()
		{
			sides = 0;
			d = new Dimension[0];
		}
		
		//method extend takes dimension object as a parameter and returns nothing
		void extend(Dimension dimension)
		{
			sides = sides+1;
			
	        Dimension[] dime1 = new Dimension[d.length+1];
	        
			for (int i =0; i< d.length; i++)
			{
				dime1[i] = d[i];
			}
			
			dime1[d.length]=dimension;
			
			d = new Dimension[d.length + 1];
			for(int i = 0; i< dime1.length; i++)
			{
				d[i] = dime1[i];
			}		
		}
		
		//method retract takes no parameter and returns nothing
		void retract()
		{
			sides = sides-1;
			
			Dimension[] dime2 = new Dimension[d.length - 1];
			
			for (int i=0; i< d.length-1; i++)
			{
				dime2[i]= d[i];
			}
			
			d = new Dimension[d.length - 1];
			for(int i = 0; i< dime2.length; i++)
			{
				d[i] = dime2[i];
			}
				
		}

}
