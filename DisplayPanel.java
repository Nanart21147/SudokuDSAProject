import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public class DisplayPanel extends JPanel implements ActionListener 
{
    private static final long serialVersionUID = 1L;
    private int DisplayWidth = 557; 
    private int DisplayHeight = 580; 
    private int ButtonsWidth = 200; 
    private final Color WS = new Color(0xf5, 0xf5, 0xf5);  
    private final Color MB = new Color(0000, 0000, 0000);  
    private final Color P = new Color(255,0, 0);  
    private String currentLevel = ""; 

    public DisplayPanel()   
    {       
        addMouseListener(new MouseAdapter() 
        {
            public void mousePressed(MouseEvent e) 
            {           	
                selectNumber(e.getX(),e.getY());      	
            }
        });
        this.setLayout(new BorderLayout());
        
        JPanel pb = new JPanel();   
        pb.setPreferredSize(new Dimension(ButtonsWidth,DisplayHeight));
        pb.setBackground(WS);
        
        FlowLayout FL = new FlowLayout();
        FL.setVgap(55);
        FL.setHgap(100);  
        pb.setLayout(FL);
        SButton SS = new SButton(" Solved Sudoku ", "SS");
        SS.addActionListener(this);
        pb.add(SS);
        SButton GBS = new SButton(" Undo ", "GBS");
        GBS.addActionListener(this);
        pb.add(GBS);  
        SButton ES = new SButton(" Easy ", "ES");
        ES.addActionListener(this);
        pb.add(ES);
        SButton MS = new SButton(" Medium ", "MS");
        MS.addActionListener(this);
        pb.add(MS);
        SButton HS = new SButton(" Hard ", "HS");
        HS.addActionListener(this);
        pb.add(HS);
        SButton CS = new SButton(" Custom Sudoku", "CS");
        CS.addActionListener(this);
        pb.add(CS);
        SButton restartButton = new SButton(" Restart ", "RST");
        restartButton.addActionListener(this);
        pb.add(restartButton);
        
        this.add(pb,BorderLayout.WEST);        
    }
       
    private void selectNumber(int x, int y)   
    {
    	int NumberPosition[] = {3,63,124,187,248,309,372,433,494}; 
    	final byte pSNumberY = 19;  
    	if( x < ButtonsWidth + NumberPosition[0])
    		return; 
    	x -= ButtonsWidth - NumberPosition[0];  
    	
    	byte count;
    	byte Xposition = 0; 
    	for(count = 0; count < 9; count++) 
    	{
    		if(x > NumberPosition[count])
    			Xposition = count;  
    	}
    	
    	byte Yposition = 0; 
    	for(count = 0; count < 9; count++) 
    	{
    		if(y > NumberPosition[count])
    			Yposition = count;  
    	}
    	byte position = (byte) (Xposition + Yposition*9); 
    	
    	byte Xnumber = 0; 
    	x -=  NumberPosition[Xposition];  
    	for(count = 0; count < 3; count++) 
    	{
    		if(x >  pSNumberY*count)
    			Xnumber = count;  
    	}
    	
    	byte Ynumber = 0; 
    	y -=  NumberPosition[Yposition];  
    	for(count = 0; count < 3; count++) 
    	{
    		if(y >  pSNumberY*count)
    			Ynumber = count;  
    	}
    	byte number = (byte) (Xnumber + Ynumber*3); 
    	
    	MySudoku.step = Smethods.select(MySudoku.sudoku, number, position, MySudoku.step);
        repaint(ButtonsWidth,0, DisplayWidth,DisplayHeight);  
                 
    }
        
    
    public Dimension getPreferredSize()  
    {
        return new Dimension(DisplayWidth + ButtonsWidth,DisplayHeight);
    }
    
     protected void paintComponent(Graphics g)  
    {
    	final byte Foot = 24; 
     	final byte NumberX = 11;  
     	final byte NumberY = 54;  
     	final byte blanksize = 59;  
     	final byte pNumberX = 4;  
     	final byte pNumberY = 18;  
     	final byte pSNumberX = 20;  
     	final byte pSNumberY = 19;  
     	final int FootMessageX = 96;  
     	final int FootMessageY = 574;  
     	final int FootNumberX = 211;  
     	final int FootNumberY = 574;  
     	
     	int BigLines[] = {0, 184, 369, 554, 577};  
     	int SmallLines[] = {62, 123, 247, 308, 432, 493};  
     	int NumberPosition[] = {3,63,124,187,248,309,372,433,494}; 
     	Font fontSelected = new Font("SansSerif", Font.ROMAN_BASELINE, 70);  
        Font fontFoot = new Font("SansSerif", Font.ROMAN_BASELINE, 20);  
        Font fontPencil = new Font("SansSerif", Font.ROMAN_BASELINE, 20); 
     	
        super.paintComponent(g);     
        g.setColor(MB);
        g.setFont(fontPencil);
        
        
      
        byte count;  
        for(count = 0; count < 5; count++)
        g.fillRect(0, BigLines[count], DisplayWidth + ButtonsWidth, 3);
        for(count = 0; count < 6; count++)
        g.drawLine(0, SmallLines[count], DisplayWidth + ButtonsWidth, SmallLines[count]);
      
        g.fillRect(BigLines[0] + ButtonsWidth , 0, 3, DisplayHeight);
        g.fillRect(BigLines[1] + ButtonsWidth , 0, 3, DisplayHeight - Foot);
        g.fillRect(BigLines[2] + ButtonsWidth , 0, 3, DisplayHeight - Foot);
        g.fillRect(BigLines[3] + ButtonsWidth , 0, 3, DisplayHeight);
        for(count = 0; count < 6; count++)
        g.drawLine(SmallLines[count] + ButtonsWidth, 0, SmallLines[count] + ButtonsWidth, DisplayHeight -Foot);
        
        g.setFont(fontFoot);
        g.drawString(" Step No: ", FootMessageX + ButtonsWidth, FootMessageY);
        g.drawString(String.valueOf(MySudoku.step), FootNumberX + ButtonsWidth, FootNumberY);
        byte numbercount;
        for(numbercount = 0; numbercount < 81; numbercount++)
        {
        g.setColor(MB);  
        byte zeros = 0; 
        byte outercount;  
        for(outercount = 0; outercount < 3; outercount++)
        {
        for(count = 0; count < 3; count++)
        {
        byte pencilnumber = MySudoku.sudoku[count + outercount*3 + numbercount*9][ MySudoku.step];
        if(pencilnumber > 0)    
        {
        if(pencilnumber < 10)
        {
        g.setFont(fontPencil);
        g.drawString(String.valueOf(pencilnumber ), NumberPosition[numbercount%9] + (count*pSNumberX) + pNumberX + ButtonsWidth, NumberPosition[numbercount/9] + outercount*pSNumberY + pNumberY);
        } 
        else
        {
        g.setFont(fontSelected);
        g.drawString(String.valueOf(pencilnumber - 10), NumberPosition[numbercount%9] + ButtonsWidth + NumberX, NumberPosition[numbercount/9] + NumberY);
        } 
        }
        else
        	zeros += 1; 
        }
        }
        if(zeros == 9)
        {
        	g.setColor(P);  
        	g.fillRect(NumberPosition[numbercount%9] + ButtonsWidth, NumberPosition[numbercount/9], blanksize, blanksize);
        }
        }
    } 

	
	public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.equals("ES")) {
            currentLevel = "Easy";
            Smethods.trysudoku(MySudoku.sudoku, (byte) 0);
            MySudoku.step = 45; // Easy level 
        } else if (command.equals("MS")) {
            currentLevel = "Medium";
            Smethods.trysudoku(MySudoku.sudoku, (byte) 0);
            MySudoku.step = 35; // Medium level 
        } else if (command.equals("HS")) {
            currentLevel = "Hard";
            Smethods.trysudoku(MySudoku.sudoku, (byte) 0);
            MySudoku.step = 25; // Hard level 
        } else if (command.equals("CS")) {
            currentLevel = "Custom";
            MySudoku.step = 0; // Custom level
        } else if (command.equals("RST")) {
            // Restart the current level
            if (currentLevel.equals("Easy")) {
                Smethods.trysudoku(MySudoku.sudoku, (byte) 0);
                MySudoku.step = 45;
            } else if (currentLevel.equals("Medium")) {
                Smethods.trysudoku(MySudoku.sudoku, (byte) 0);
                MySudoku.step = 35;
            } else if (currentLevel.equals("Hard")) {
                Smethods.trysudoku(MySudoku.sudoku, (byte) 0);
                MySudoku.step = 25;
            } else if (currentLevel.equals("Custom")) {
                MySudoku.step = 0; 
            }
        } else if (command.equals("SS")) {
            Smethods.trysudoku(MySudoku.sudoku, MySudoku.step); // Solve 
        } else if (command.equals("GBS")) {
            if (MySudoku.step > 0) {
                MySudoku.step -= 1; // Undo 
            }
        }
        
        repaint(ButtonsWidth, 0, DisplayWidth, DisplayHeight); 
    }
}
 
 


