import java.sql.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator extends JFrame implements ActionListener {
    JButton btn1 = new JButton("1");
 JButton btn2 = new JButton("2");
 JButton btn3 = new JButton("3");
 JButton btn4 = new JButton("4");
 JButton btn5 = new JButton("5");
 JButton btn6 = new JButton("6");
 JButton btn7 = new JButton("7");
 JButton btn8 = new JButton("8");
 JButton btn9 = new JButton("9");
 JButton btn0 = new JButton("0");
 JButton btnC = new JButton("C");
 JButton btnP = new JButton(".");
 JButton opPlus = new JButton("+");
 JButton opMult = new JButton("x");
 JButton opDivi = new JButton("/");
 JButton opMinu = new JButton("-");
 JButton Equal = new JButton("=");
 JTextField txt = new JTextField();
 JTextField txt2 = new JTextField();
 double fnum,snum,total;
     String op = null;
  

  public Calculator() {
   Container c = getContentPane();
  c.setLayout(null);
  c.setBackground(Color.MAGENTA); 
  btn1.addActionListener(this);
  btn2.addActionListener(this);
  btn3.addActionListener(this);
  btn4.addActionListener(this);
  btn5.addActionListener(this);
  btn6.addActionListener(this);
  btn7.addActionListener(this);
  btn8.addActionListener(this);
  btn9.addActionListener(this);
  btn0.addActionListener(this);
  btnC.addActionListener(this);
  btnP.addActionListener(this);
  opPlus.addActionListener(this);
  opMult.addActionListener(this);
  opDivi.addActionListener(this);
  opMinu.addActionListener(this);
  Equal.addActionListener(this);
  
  txt.setBounds(35,15,360,50);
  txt2.setBounds(0,330,100,20);
  btn7.setBounds(35,85,50,50);
  btn8.setBounds(95,85,50,50);
  btn9.setBounds(155,85,50,50);
  btn4.setBounds(35,145,50,50);
  btn5.setBounds(95,145,50,50);
  btn6.setBounds(155,145,50,50);
  btn1.setBounds(35,205,50,50);
  btn2.setBounds(95,205,50,50);
  btn3.setBounds(155,205,50,50);
  btnP.setBounds(95,265,50,50);
  btn0.setBounds(35,265,50,50);
  btnC.setBounds(340,85,50,50);
  opPlus.setBounds(280,145,50,50);
  opMult.setBounds(340,145,50,50);
  opMinu.setBounds(280,205,50,50);
  opDivi.setBounds(340,205,50,50);
  Equal.setBounds(280,265,110,50);
  txt.setEditable(false);
  
  
  Font font = new Font("Times New Roman",Font.BOLD,20);
  txt.setFont(font);
  txt.setHorizontalAlignment(JTextField.RIGHT);
  
  c.add(txt);
  c.add(btn7);
  c.add(btn1);
  c.add(btn2);
  c.add(btn3);
  c.add(btn4);
  c.add(btn5);
  c.add(btn6);
  c.add(btn8);
  c.add(btn9);
  c.add(btn0);
  c.add(btnC);
  c.add(opPlus);
  c.add(opMult);
  c.add(opDivi);
  c.add(opMinu);
  c.add(Equal);
  c.add(btnP);
  
 
   Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  
  setSize(440,385);
   int w = getSize().width;
   int h = getSize().height;
   int x = (dim.width-w)/2;
   int y = (dim.height-h)/2;
   setLocation(x, y);
  setVisible(true);
  setDefaultCloseOperation(EXIT_ON_CLOSE); 
  
    }
    public void actionPerformed(ActionEvent e){
     String input = txt.getText();
     
     
     
    
     
     if(e.getSource()==btnC){
   txt.setText("");
     } 
     else if(e.getSource()==btn1){
      txt.setText(input + "1");
     } 
      else if(e.getSource()==btn2){
      txt.setText(input + "2");
     }
     else if(e.getSource()==btn3){
      txt.setText(input + "3");
     }
     else if(e.getSource()==btn4){
      txt.setText(input + "4");
     }
     else if(e.getSource()==btn5){
      txt.setText(input + "5");
     }
     else if(e.getSource()==btn6){
      txt.setText(input + "6");
     }
     else if(e.getSource()==btn7){
      txt.setText(input + "7");
     }
     else if(e.getSource()==btn8){
      txt.setText(input + "8");
     }
     else if(e.getSource()==btn9){
      txt.setText(input + "9");
     }
     else if(e.getSource()==btn0){
      txt.setText(input + "0");
     }
     else if(e.getSource()==btnP){
      txt.setText(input + ".");
     }
     else if(e.getSource()==opPlus){
      fnum = Double.parseDouble(txt.getText());
      op = "+";
      
      txt.setText("");
     }
     else if(e.getSource() == opMinu){
      fnum = Double.parseDouble(txt.getText());
      op = "-";
      
      txt.setText("");
     }
     else if(e.getSource() == opMult){
      fnum = Double.parseDouble(txt.getText());
      op = "x";
      
      txt.setText("");
     
     }
     else if(e.getSource() == opDivi){
      fnum = Double.parseDouble(txt.getText());
      op = "/";
      
      txt.setText("");
     }
     else if(e.getSource()==Equal){
      snum = Double.parseDouble(txt.getText());
      if(op.equals("+"))
      {
       total=fnum+snum; }
      else if(op.equals("x")){
       total = fnum*snum;
      }
      else if(op.equals("/")){
       total = fnum/snum;
      }
      else if(op.equals("-"))
      { total = fnum-snum;
      }
      txt.setText(""+total);
  
     }
 
    }

    public static void main(String[] args) {
       Calculator Cal = new Calculator();
    }
}
