import java.io.*;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*; 
class BrainBooster extends Frame
{
	static Statement st;
	TextField playerName;
	Button start;
	Choice level;
	BrainBooster()
	{
		playerName=new TextField(30);
		start=new Button("Start");
		start.setBounds(100,100,50,20);
		level=new Choice();
		level.add("level 1,3 questions");
		level.add("level 2,5 questions");
		level.add("level 3,10 questions");
		setTitle("Welcome to brain boosters");
		setSize(500,500);
		setVisible(true);
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
			Label lable=new Label("Welcome to the Brain Boosters ");		//creating new title as label we will add it on panel
			lable.setFont(new Font("Comic Sans Ms",Font.PLAIN,30));
			Panel welcomeTitle=new Panel();
			welcomeTitle.setBackground(Color.yellow);
			welcomeTitle.add(lable);										//adding lable to panel
			Panel getDetail=new Panel();									//new Panel of getting name and detail						
			getDetail.setLayout(new BoxLayout(getDetail,BoxLayout.Y_AXIS));
			Panel name=new Panel();
			name.setLayout(new FlowLayout(FlowLayout.LEFT));				// panel for player name
			name.add(new Label("Enter Player name: "));
			name.add(playerName);																//adding player panel
			Panel levelDetail=new Panel();
			levelDetail.setLayout(new FlowLayout(FlowLayout.LEFT));
			levelDetail.add(new Label("Select Level:             "));
			getDetail.add(name);
			//ImageIcon icon=new ImageIcon("C:\\Users\\test user\\Desktop\\Brain Booster Project\\a.jpg");
			//name.setIcon(icon);
		levelDetail.add(level);
		getDetail.add(levelDetail);
		Panel submit=new Panel();
		submit.add(start);
		add(welcomeTitle);
		add(getDetail);
		add(submit);
		start.addActionListener(new ActionListener()			//adding action listener to submit button
		{	
			public void actionPerformed(ActionEvent e)
			{
				new Rules(playerName.getText(),level.getSelectedIndex());
				dispose();
			}
		});
	
	}
	static ResultSet getResultSet(String s)throws Exception            //static method to get the resultset of any querry passed in it as a string
	{
		return(st.executeQuery(s));
	}
	
	public static void main(String [] args)throws Exception
	{
		
		
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");										//creating odbc bridge ie connectiong to database
        // creating connection to the data base
        Connection con = DriverManager.getConnection("jdbc:odbc:questions","","");
		st = con.createStatement();
		new BrainBooster();											// creating first login page
	}
}
class Rules
{
	Rules(String playerName,int level)
	{
		new GameWindow(playerName,level);
	}
}
class GameWindow extends Frame
{
	int score=0;
	TextField timer;
	ResultSet rs;
	ResultSet rs2;
	ResultSet rs3;
	Statement st;
	Boolean flag=false;
	final String playerName;
	int quesCount;
	int curQuesCount=1;
	TextArea question;
	ButtonGroup optionGroup=null;
	Panel optionPane;
	Panel header;
	Boolean submitAction=false;
	Boolean interupt=false;
	Button option1;
	Button option2;
	Button option3;
	Button option4;
	String correctAnswer;
	Button submit;
	Button quit;
	Button lifeline1;
	Button lifeline2;
	GameWindow(final String playerName,int level)
	{
		this.playerName=playerName;
		//t.start();
		timer=new TextField(50);
		timer.setEditable(false);
		question=new TextArea(5,100);
		question.setEditable(false);
		option1=new Button();
		option2=new Button();
		option3=new Button();
		option4=new Button();
		if(level==0)													// setting question counts passed by privious page (level Chooser)
		{
			quesCount=3;
		}
		else if(level==1)
		{
			quesCount=5;
		}
		else
		{
			quesCount=10;
		}
		setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
		setVisible(true);
		setSize(1280,600);
		System.out.println("adding");
		lifeline1=new Button("Fifty-Fifty");
		lifeline2=new Button("Flip The Question");
		submit=new Button("Submit & Next");
		quit=new Button("Quit Game");
		header=new Panel();												// creating header panel shows player name
		header.setLayout(new FlowLayout(FlowLayout.LEFT));
		header.add(new Label("Player Name: "+playerName));
		header.setPreferredSize(new Dimension(800,10));
		System.out.println("adding body");
			Panel body=new Panel();											//panel for question box and options
			body.setLayout(new BoxLayout(body,BoxLayout.Y_AXIS));
			body.setPreferredSize(new Dimension(800,200));
			body.add(question);
			optionPane=new Panel();													// panel for displaying options
				optionPane.setLayout(new GridLayout(2,2,20,5));			
				optionPane.setPreferredSize(new Dimension(800,200));
				optionPane.setBackground(Color.GREEN);
				body.add(Box.createRigidArea(new Dimension(0,50)));
				body.add(optionPane);										// adding body contents
		Panel lifeLine=new Panel();														//lifeline panel	
		lifeLine.setLayout(new BoxLayout(lifeLine,BoxLayout.Y_AXIS));
		lifeLine.setPreferredSize(new Dimension(150,220));
		lifeLine.setBackground(Color.RED);
		lifeLine.add(Box.createRigidArea(new Dimension(0,30)));
		lifeLine.add(new Label("Choose a LifeLine"));
		lifeLine.add(Box.createRigidArea(new Dimension(0,30)));
		lifeLine.add(lifeline1);		
		lifeLine.add(Box.createRigidArea(new Dimension(0,30)));
		lifeLine.add(lifeline2);
		lifeLine.add(Box.createRigidArea(new Dimension(0,30)));
			Panel CenterArea=new Panel();									//center panel works as super panel contains your body panel as well lifeline panel
			CenterArea.setLayout(new FlowLayout(FlowLayout.LEFT));
			CenterArea.setBackground(Color.YELLOW);
			CenterArea.setPreferredSize(new Dimension(800,400));
			CenterArea.add(body);
			CenterArea.add(Box.createRigidArea(new Dimension(250,0)));
			CenterArea.add(lifeLine);
			System.out.println("adding lifeline");
		add(header);																// adding all the panels in main Frame
		add(timer);
		add(CenterArea);
		add(quit);
		add(submit);
		
	submit.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent ev)
		{
			if(flag)
			{
				submitAction=true;
				score=score+1000;
				JOptionPane.showMessageDialog(null,"Correct "+score);
				flag=false;
				curQuesCount=curQuesCount+1;
			//	nextQuestion();
			}
			else
			{
				//curQuesCount=curQuesCount+1;
			//	nextQuestion();
			JOptionPane.showMessageDialog(null,"Oops.. wrong answer. Game End ");
				submitAction=true;
				new ShowScore(playerName,score);
				dispose();
				
			}
			//question.setText("sdkfbjsdgfs");
			nextQuestion();
		}

	});
	quit.addActionListener(new ActionListener()
	{
		public void actionPerformed(ActionEvent ev)
		{
			JOptionPane.showMessageDialog(null,"You quit the Game.");
			submitAction=true;
				new ShowScore(playerName,score);
				dispose();
				
		}

	});
	lifeline2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			submitAction=true;
			curQuesCount++;
			quesCount++;
			lifeline2.setEnabled(false);
			nextQuestion();
		}
	});
	lifeline1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e)
		{
			String s;
			if ((option2.getName()).equals(correctAnswer))
			{
				s=option3.getName();
			}
			else
			{
				s=option2.getName();
			}
			JOptionPane.showMessageDialog(null,"One of them is correct:- "+"\n"+correctAnswer+"\n"+s);
			lifeline1.setEnabled(false);
		}
	});
			System.out.println("actions added");
			nextQuestion();
	}
	void nextQuestion()
	{
		System.out.println("outside db");
			if(curQuesCount<=quesCount)
			{
				System.out.println("inside db");
				try
				{
					System.out.println("adding questions "+curQuesCount+" "+quesCount);
					rs=BrainBooster.getResultSet("Select * From questions where ques_no="+curQuesCount);	// selection entire row of current question no. to get ques anser and options set
					System.out.println("adding questions "+curQuesCount+" "+quesCount);
					while(rs.next())
					{
						System.out.println("questions");
						question.setText("Question "+curQuesCount+" : "+rs.getString(2));
						System.out.println("questions");
					}
					rs=BrainBooster.getResultSet("Select * From options where ques_no="+curQuesCount);	// selection entire row of current question no. to get ques anser and options set
					while(rs.next())
					{
					System.out.println("opotions");
						optionPane.remove(option1);								//clearing option panel to add new options
						optionPane.remove(option2);
						optionPane.remove(option3);
						optionPane.remove(option4);
						ResultSetMetaData rsmd = rs.getMetaData();
						System.out.println("adding questions "+curQuesCount+" "+quesCount);
						String s=rs.getString(2);						//creatin new option buttons with new option
						option1=new Button(s);																
						option1.setName(s);
						s=rs.getString(3);
						option2=new Button(s);
						option2.setName(s);
						s=rs.getString(4);
						option3=new Button(s);
						option3.setName(s);
						s=rs.getString(5);
						option4=new Button(s);
						option4.setName(s);										//creatin new option buttons with new option
						System.out.println(option1.getName());
						System.out.println(option2.getName());
						System.out.println(option3.getName());
						System.out.println(option4.getName());
						optionPane.add(option1);								// adding new options in option panel
						optionPane.add(option2);
						optionPane.add(option3);
						optionPane.add(option4);
						optionPane.validate();
						optionPane.repaint();
						System.out.println("opotions");
					}		
					rs=BrainBooster.getResultSet("Select * From correct where ques_no="+curQuesCount);	// selection entire row of current question no. to get ques anser and options set					
					while(rs.next())
					{
					System.out.println("correct");
						correctAnswer=rs.getString("correct");
						System.out.println("correct");
					}
					
				}
				catch(Exception e)
				{
					System.out.println("DAtabase Failure");
				}
				
				
	/* every option button have a unique action to performe . whenever any option is clicked the button name is compared to correct answer and disable
	all the option buttons in order to prevent further attempts. if the answer matched the boolean variable flag is set to true else set to false.
	once one made his attempt he have to press submit button.
	Submit button checks the flag status. if the flag is true, next question is called and reward is added to account.But if the flag is false the game is over 
	and you get redirected to result page *//////////////////////////////
				option1.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(option1.getName().equals(correctAnswer))
						{
							flag=true;
						}
						else
						{
							flag=false;
						}
						option1.setEnabled(false);
						option2.setEnabled(false);
						option3.setEnabled(false);
						option4.setEnabled(false);
					}
				});
				option2.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(option2.getName().equals(correctAnswer))
						{
							flag=true;
						}
						else
						{
							flag=false;
						}
						option1.setEnabled(false);
						option2.setEnabled(false);
						option3.setEnabled(false);
						option4.setEnabled(false);
					}
				});
				option3.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(option3.getName().equals(correctAnswer))
							{
								flag=true;
							}
						else
							{
								flag=false;
							}
						option1.setEnabled(false);
						option2.setEnabled(false);
						option3.setEnabled(false);
						option4.setEnabled(false);
					}
				});
				option4.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(option4.getName().equals(correctAnswer))
						{
							flag=true;
						}
						else
						{
							flag=false;
						}
						option1.setEnabled(false);
						option2.setEnabled(false);
						option3.setEnabled(false);
						option4.setEnabled(false);
					}
				});
			}
			else
			{
				JOptionPane.showMessageDialog(null,"you attempted all questions.");
				submitAction=true;
				new ShowScore(playerName,score);
				dispose();
			}
			new Thread(){
		public void run()
		{
			for(int i=20;i>0;i--)
			{
				try{	
				if(submitAction==true)
					{
					submitAction=false;
					interupt=true;
					break;
					}
				else
					{
					timer.setText(" Time Left: 00:"+i);
					sleep(1000);
					}
				}
				catch(Exception e)
				{}
			}
			if(interupt==false)
				{
				curQuesCount++;
				//interupt=false;
				nextQuestion();	
				}
				else
				{
					interupt=false;
				}
			
			
		}
	}.start();
	}
	Thread t2=new Thread(){
		public void run()
		{
			try
			{
			t.stop();
			}catch(Exception ex)
			{}
			for(int i=2;i>0;i--)
			{
				try{	
				timer.setText(" Time Left: 00:"+i);
				sleep(1000);
				}
				catch(Exception e)
				{}
			}
			curQuesCount++;
			t.start();
			nextQuestion();
			
		}
	};
	Thread t=new Thread(){
		public void run()
		{
			for(int i=20;i>0;i--)
			{
				try{	
				if(submitAction==true)
					{
					submitAction=false;
					break;
					}
				else
					{
					timer.setText(" Time Left: 00:"+i);
					sleep(1000);
					}
				}
				catch(Exception e)
				{}
			}
			curQuesCount++;
			nextQuestion();
			
		}
	};
}
class ShowScore extends Frame
{
	ShowScore(String name,int score)
	{
		setSize(600,400);
		setVisible(true);
		Button pAgain=new Button("Play Again");
		Button exit=new Button("Exit");
		Panel p=new Panel();
		Panel all=new Panel();
		p.setBackground(Color.GREEN);
		p.setPreferredSize(new Dimension(100,50));
		Label a=new Label("Score board");
		a.setFont(new Font("Comic Sans Ms",Font.PLAIN,30));
		p.add(a);
		Panel p2=new Panel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.Y_AXIS));
		//p2.setLayout(new GridLayout(3,1,100,10));
		p2.setBackground(Color.YELLOW);
		Panel np=new Panel();
		Label n=new Label("Player Name:                     "+name);
		Panel qp=new Panel();
		Label q=new Label("Questions attempted:             "+score/1000);
		Panel sp=new Panel();
		np.setPreferredSize(new Dimension(10,30));
		qp.setPreferredSize(new Dimension(10,10));
		sp.setPreferredSize(new Dimension(10,100));
		Label s=new Label("Final Score:                       "+score);
		np.add(n);
	//	add(Box.createRigidArea(new Dimension(50,10)));
		qp.add(q);
	//	add(Box.createRigidArea(new Dimension(0,10)));
		sp.add(s);
	//	p2.setPreferredSize(new Dimension(100,50));
	    p2.add(Box.createRigidArea(new Dimension(0,70)));
		p2.add(np);
		//p2.add(Box.createRigidArea(new Dimension(0,20)));
		p2.add(qp);
		p2.add(Box.createRigidArea(new Dimension(0,20)));
		p2.add(sp);
		pAgain.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ev)
			{
				new BrainBooster();
				dispose();
				
			}

		});	
		exit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ev)
			{
				System.exit(0);				
			}

		});	
		
		Panel bottom=new Panel();
		bottom.add(pAgain);
		bottom.add(exit);
		add(p,BorderLayout.NORTH);
		
		add(p2,BorderLayout.CENTER);
	
		add(bottom,BorderLayout.SOUTH);
		
		
		
	}
}
