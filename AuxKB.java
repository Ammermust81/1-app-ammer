/*    */ package crosswordexpress;
/*    */ import java.awt.Color;
/*    */ import java.awt.Font;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.RenderingHints;
/*    */ import java.awt.event.MouseEvent;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ class AuxKB extends JPanel {
/* 14 */   static String[] alphabets = new String[] { "Latin", "Latin Extended A", "Latin Extended B", "Greek & Coptic", "Cyrillic", "Armenian/Hebrew", "Arabic" };
/*    */   static int base;
/*    */   
/*    */   AuxKB(int x, int y, JDialog jd) {
/* 18 */     setOpaque(true);
/* 19 */     setLocation(x, y);
/* 20 */     setSize(560, 560);
/* 21 */     jd.add(this);
/*    */   }
/*    */   
/*    */   public void paintComponent(Graphics g) {
/* 25 */     int cell = 35, rect = cell * 16;
/*    */     
/* 27 */     Graphics2D g2 = (Graphics2D)g;
/* 28 */     super.paintComponent(g2);
/*    */     
/* 30 */     RenderingHints rh = g2.getRenderingHints();
/* 31 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 32 */     g2.setRenderingHints(rh);
/*    */     
/* 34 */     g2.setColor(new Color(16777198));
/* 35 */     g2.fillRect(0, 0, rect, rect);
/* 36 */     g2.setColor(new Color(0));
/* 37 */     g2.drawRect(0, 0, rect - 1, rect - 1);
/*    */     
/* 39 */     g.setFont(new Font("", 0, 26));
/* 40 */     for (int j = 0; j < 16; j++) {
/* 41 */       for (int i = 0; i < 16; i++) {
/* 42 */         g2.setColor(new Color(0));
/* 43 */         g2.drawString(Character.toString((char)(base * 256 + i + 16 * j)), 4 + i * cell, j * cell + cell - 6);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   static void getCharacter(MouseEvent e, JTextArea jta, JTextField jtf) {
/* 48 */     int cell = 35;
/*    */ 
/*    */     
/* 51 */     int x = e.getX() / cell;
/* 52 */     int y = e.getY() / cell;
/* 53 */     if (x > 16 || y > 16)
/*    */       return; 
/* 55 */     String target = "" + (char)(base * 256 + y * 16 + x);
/* 56 */     if (jta != null)
/* 57 */       jta.replaceSelection(target); 
/* 58 */     if (jtf != null)
/* 59 */       jtf.replaceSelection(target); 
/*    */   }
/*    */   
/*    */   static String getAlphabetName() {
/* 63 */     return "<html><font color=006644 size=3><font size=4>Character Chart : " + ((base < 7) ? alphabets[base] : (
/*    */       
/* 65 */       String.format("%04X", new Object[] { Integer.valueOf(base * 256) }) + " - " + String.format("%04X", new Object[] { Integer.valueOf(base * 256 + 255) })));
/*    */   }
/*    */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\AuxKB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */