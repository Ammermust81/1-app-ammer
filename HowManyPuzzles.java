/*    */ package crosswordexpress;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.awt.LayoutManager;
/*    */ import javax.swing.BorderFactory;
/*    */ import javax.swing.JCheckBox;
/*    */ import javax.swing.JDialog;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JTextField;
/*    */ 
/*    */ class HowManyPuzzles
/*    */   extends JPanel {
/*    */   JLabel jlHowMany;
/*    */   JLabel jlStartPuz;
/*    */   
/*    */   HowManyPuzzles(JDialog jd, int x, int y, int howMany, int startPuz, boolean vary) {
/* 18 */     JPanel jpHM = new JPanel();
/* 19 */     jpHM.setLayout((LayoutManager)null);
/* 20 */     jpHM.setSize(240, 104);
/* 21 */     jpHM.setLocation(x, y);
/* 22 */     jpHM.setOpaque(true);
/* 23 */     jpHM.setBorder(BorderFactory.createEtchedBorder());
/* 24 */     jd.add(jpHM);
/*    */     
/* 26 */     JLabel jl = new JLabel("Multi-Puzzle control");
/* 27 */     jl.setForeground(Def.COLOR_LABEL);
/* 28 */     jl.setSize(235, 20);
/* 29 */     jl.setLocation(5, 3);
/* 30 */     jl.setHorizontalAlignment(0);
/* 31 */     jpHM.add(jl);
/*    */     
/* 33 */     JLabel jlHowMany = new JLabel("How many puzzles:");
/* 34 */     jlHowMany.setForeground(Def.COLOR_LABEL);
/* 35 */     jlHowMany.setSize(140, 20);
/* 36 */     jlHowMany.setLocation(5, 28);
/* 37 */     jlHowMany.setHorizontalAlignment(4);
/* 38 */     jpHM.add(jlHowMany);
/*    */     
/* 40 */     this.jtfHowMany = new JTextField("" + howMany, 15);
/* 41 */     this.jtfHowMany.setSize(80, 20);
/* 42 */     this.jtfHowMany.setLocation(150, 28);
/* 43 */     this.jtfHowMany.selectAll();
/* 44 */     this.jtfHowMany.setHorizontalAlignment(2);
/* 45 */     jpHM.add(this.jtfHowMany);
/* 46 */     this.jtfHowMany.setFont(new Font("SansSerif", 1, 13));
/*    */     
/* 48 */     JLabel jlStartPuz = new JLabel("First puzzle:");
/* 49 */     jlStartPuz.setForeground(Def.COLOR_LABEL);
/* 50 */     jlStartPuz.setSize(140, 20);
/* 51 */     jlStartPuz.setLocation(5, 55);
/* 52 */     jlStartPuz.setHorizontalAlignment(4);
/* 53 */     jpHM.add(jlStartPuz);
/*    */     
/* 55 */     this.jtfStartPuz = new JTextField("" + startPuz, 15);
/* 56 */     this.jtfStartPuz.setSize(80, 20);
/* 57 */     this.jtfStartPuz.setLocation(150, 55);
/* 58 */     this.jtfStartPuz.selectAll();
/* 59 */     this.jtfStartPuz.setHorizontalAlignment(2);
/* 60 */     jpHM.add(this.jtfStartPuz);
/* 61 */     this.jtfStartPuz.setFont(new Font("SansSerif", 1, 13));
/*    */     
/* 63 */     this.jcbVaryDiff = new JCheckBox("Vary Difficulty on 7 day cycle", vary);
/* 64 */     this.jcbVaryDiff.setForeground(Def.COLOR_LABEL);
/* 65 */     this.jcbVaryDiff.setOpaque(false);
/* 66 */     this.jcbVaryDiff.setSize(225, 20);
/* 67 */     this.jcbVaryDiff.setLocation(10, 80);
/* 68 */     jpHM.add(this.jcbVaryDiff);
/*    */   }
/*    */   
/*    */   JTextField jtfHowMany;
/*    */   JTextField jtfStartPuz;
/*    */   JCheckBox jcbVaryDiff;
/*    */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\HowManyPuzzles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */