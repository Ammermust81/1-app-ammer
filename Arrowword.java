/*     */ package crosswordexpress;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.event.ActionEvent;
/*     */ import javax.swing.AbstractAction;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JRadioButton;
/*     */ 
/*     */ public class Arrowword {
/*  14 */   static String arrowwordOptions = "<div>An <b>Arrow-word</b> puzzle is simply a crossword puzzle in which the clues are printed inside the body of the puzzle in the spaces normally occupied by the pattern cells. You can attempt to create such an output for any crossword puzzle, but you won't always be successful. Some puzzles don't have enough pattern cells to accommodate all of the clues, and the clues may no be able to be printed if they are too long. Error messages are provided if you encounter either of these conditions.</div><p/><div>Before printing your <b>Arrow-word</b> puzzle, you can set some options which will change the appearance of the printed output...</div><ul><li/>A set of six <b>Color Selection</b> buttons allow you to change the colors of the following components of the Arrow-word puzzle.<ul><li/><b>Puzzle Cells.</b> These are the cells in which a solver will enter the proposed solution letters.<p/><li/><b>Clue Cells.</b> These are the cells into which the program will insert the text of the clues.<p/><li/><b>Pattern Cells.</b> Those pattern cells which are not required for clues will be printed in a color selected here.<p/><li/><b>Arrows.</b> Each clue has an attached arrow which points to the location where the solution is to be entered.<p/><li/><b>Lines.</b> The lines are those which define the individual cells of the puzzle.<p/><li/><b>Text.</b> Finally, the color of the clue text may be set by this control.<p/></ul><li/>The arrows mentioned above may be drawn in any one of six <b>Arrow Styles</b> selected by means of a set of radio buttons.<p/><li/>The appearance of the clue text may be further varied by means of a set of four <b>Clue Alignment</b> radio buttons. The options are <b>Align Left, Align Center, Align Right,</b> and <b>Justify.</b><p/><li/>The width of the lines used to draw the puzzle can be varied using the <b>Line Width</b> control. Line widths are expressed as a percentage of the width of a puzzle cell, and may be selected from the combo box within the range 1% to 10%.<p/><li/>Space is at a premium when printing the clues into a puzzle cell, and sometimes you may like to have more space available. This can be achieved using the <b>Clue Area Overlap</b> control to specify the extent to which a clue cell is permitted to expand into the area occupied by adjacent non-clue cells. You can select a number from 0 up to 20, which will be interpreted as the percentage of a puzzle cell width by which a clue cell is permitted to expand.<p/><li/>By default, Arrow-word clues are printed using normal font. The size of the font actually used is selected by the program as the largest font which will allow all of the clues to be fitted into the limited space available to them. The controlling factor in this selection process will be the length of the longest word in any of the clues, and this will often mean that quite a small font will be used. There is nothing that can be done about this, but if you want to add extra emphasis to the clues, you can request that they be printed using a Bold font by selecting the <b>Print clues using Bold characters</b> check-box.</ul></body>";
/*     */ 
/*     */ 
/*     */   
/*     */   static int actualCellsWithMax;
/*     */ 
/*     */   
/*     */   static int cellX;
/*     */ 
/*     */   
/*     */   static int cellY;
/*     */ 
/*     */   
/*     */   static boolean top;
/*     */ 
/*     */   
/*     */   static boolean bot;
/*     */ 
/*     */   
/*     */   static boolean rgh;
/*     */ 
/*     */   
/*     */   static boolean lft;
/*     */ 
/*     */   
/*     */   static int xCell;
/*     */ 
/*     */   
/*     */   static int yCell;
/*     */ 
/*     */   
/*     */   static int xOrg;
/*     */ 
/*     */   
/*     */   static int yOrg;
/*     */ 
/*     */   
/*     */   static char TorB;
/*     */ 
/*     */ 
/*     */   
/*     */   static void arrowwordPrintOptions() {
/*  56 */     final JDialog jdlgPrintOptions = new JDialog(Print.jfPrint, "Arrowword Print Options", true);
/*  57 */     jdlgPrintOptions.setSize(325, 465);
/*  58 */     jdlgPrintOptions.setResizable(false);
/*  59 */     jdlgPrintOptions.setLayout((LayoutManager)null);
/*  60 */     jdlgPrintOptions.setLocation(Print.jfPrint.getX(), Print.jfPrint.getY());
/*     */     
/*  62 */     jdlgPrintOptions
/*  63 */       .addWindowListener(new WindowAdapter() {
/*     */           public void windowClosing(WindowEvent we) {
/*  65 */             Methods.closeHelp();
/*     */           }
/*     */         });
/*     */     
/*  69 */     Methods.closeHelp();
/*     */ 
/*     */ 
/*     */     
/*  73 */     final int[] localColor = { Op.getColorInt(Op.CW.AwClue.ordinal(), Op.cw), Op.getColorInt(Op.CW.AwCell.ordinal(), Op.cw), Op.getColorInt(Op.CW.AwPattern.ordinal(), Op.cw), Op.getColorInt(Op.CW.AwArrow.ordinal(), Op.cw), Op.getColorInt(Op.CW.AwLine.ordinal(), Op.cw), Op.getColorInt(Op.CW.AwText.ordinal(), Op.cw) };
/*  74 */     final JRadioButton[] jrbArrow = new JRadioButton[6];
/*  75 */     final JRadioButton[] jrbAlign = new JRadioButton[4];
/*     */     
/*  77 */     JPanel jpColors = new JPanel();
/*  78 */     jpColors.setLayout((LayoutManager)null);
/*  79 */     jpColors.setSize(155, 249);
/*  80 */     jpColors.setLocation(10, 10);
/*  81 */     jpColors.setOpaque(true);
/*  82 */     jpColors.setBorder(BorderFactory.createEtchedBorder());
/*  83 */     jdlgPrintOptions.add(jpColors);
/*     */     
/*  85 */     JLabel jl = new JLabel("Color Selection");
/*  86 */     jl.setForeground(Def.COLOR_LABEL);
/*  87 */     jl.setSize(155, 20);
/*  88 */     jl.setLocation(0, 2);
/*  89 */     jl.setHorizontalAlignment(0);
/*  90 */     jpColors.add(jl);
/*     */     
/*  92 */     Action doPuzCells = new AbstractAction("Puzzle Cells") {
/*     */         public void actionPerformed(ActionEvent e) {
/*  94 */           Color color = JColorChooser.showDialog(jdlgPrintOptions, "Choose the Arrowword puzzle cell color", new Color(Op.getColorInt(Op.CW.AwCell.ordinal(), Op.cw)));
/*  95 */           if (color != null)
/*  96 */             Op.setColorInt(Op.CW.AwCell.ordinal(), color.getRGB() & 0xFFFFFF, Op.cw); 
/*  97 */           Print.pp.repaint();
/*     */         }
/*     */       };
/* 100 */     JButton jbCellColor = Methods.newButton("doPuzCells", doPuzCells, 90, 10, 25, 135, 26);
/* 101 */     jbCellColor.setToolTipText("Select a color for the background of the puzzle cells.");
/* 102 */     jpColors.add(jbCellColor);
/*     */     
/* 104 */     Action doCluCells = new AbstractAction("Clue Cells") {
/*     */         public void actionPerformed(ActionEvent e) {
/* 106 */           Color color = JColorChooser.showDialog(jdlgPrintOptions, "Choose the Arrowword clue cell color", new Color(Op.getColorInt(Op.CW.AwClue.ordinal(), Op.cw)));
/* 107 */           if (color != null)
/* 108 */             Op.setColorInt(Op.CW.AwClue.ordinal(), color.getRGB() & 0xFFFFFF, Op.cw); 
/* 109 */           Print.pp.repaint();
/*     */         }
/*     */       };
/* 112 */     JButton jbClueColor = Methods.newButton("doCluCells", doCluCells, 76, 10, 62, 135, 26);
/* 113 */     jbClueColor.setToolTipText("Select a color for the background of the clue cells.");
/* 114 */     jpColors.add(jbClueColor);
/*     */     
/* 116 */     Action doPatCells = new AbstractAction("Pattern Cells") {
/*     */         public void actionPerformed(ActionEvent e) {
/* 118 */           Color color = JColorChooser.showDialog(jdlgPrintOptions, "Choose the Arrowword Pattern Cell Color", new Color(Op.getColorInt(Op.CW.AwPattern.ordinal(), Op.cw)));
/* 119 */           if (color != null)
/* 120 */             Op.setColorInt(Op.CW.AwPattern.ordinal(), color.getRGB() & 0xFFFFFF, Op.cw); 
/* 121 */           Print.pp.repaint();
/*     */         }
/*     */       };
/* 124 */     JButton jbPatternColor = Methods.newButton("doPatCells", doPatCells, 80, 10, 99, 135, 26);
/* 125 */     jbPatternColor.setToolTipText("Select a color for the Pattern cells.");
/* 126 */     jpColors.add(jbPatternColor);
/*     */     
/* 128 */     Action doArrows = new AbstractAction("Arrows") {
/*     */         public void actionPerformed(ActionEvent e) {
/* 130 */           Color color = JColorChooser.showDialog(jdlgPrintOptions, "Choose the Arrow color", new Color(Op.getColorInt(Op.CW.AwArrow.ordinal(), Op.cw)));
/* 131 */           if (color != null)
/* 132 */             Op.setColorInt(Op.CW.AwArrow.ordinal(), color.getRGB() & 0xFFFFFF, Op.cw); 
/* 133 */           Print.pp.repaint();
/*     */         }
/*     */       };
/* 136 */     JButton jbArrowColor = Methods.newButton("doArrows", doArrows, 65, 10, 136, 135, 26);
/* 137 */     jbArrowColor.setToolTipText("Select a color for the arrows.");
/* 138 */     jpColors.add(jbArrowColor);
/*     */     
/* 140 */     Action doLines = new AbstractAction("Lines") {
/*     */         public void actionPerformed(ActionEvent e) {
/* 142 */           Color color = JColorChooser.showDialog(jdlgPrintOptions, "Choose the Arrowword Line Color", new Color(Op.getColorInt(Op.CW.AwLine.ordinal(), Op.cw)));
/* 143 */           if (color != null)
/* 144 */             Op.setColorInt(Op.CW.AwLine.ordinal(), color.getRGB() & 0xFFFFFF, Op.cw); 
/* 145 */           Print.pp.repaint();
/*     */         }
/*     */       };
/* 148 */     JButton jbLineColor = Methods.newButton("doLines", doLines, 73, 10, 173, 135, 26);
/* 149 */     jbLineColor.setToolTipText("Select a color for the Puzzle lines.");
/* 150 */     jpColors.add(jbLineColor);
/*     */     
/* 152 */     Action doText = new AbstractAction("Clue Text") {
/*     */         public void actionPerformed(ActionEvent e) {
/* 154 */           Color color = JColorChooser.showDialog(jdlgPrintOptions, "Choose the clue text color", new Color(Op.getColorInt(Op.CW.AwText.ordinal(), Op.cw)));
/* 155 */           if (color != null)
/* 156 */             Op.setColorInt(Op.CW.AwText.ordinal(), color.getRGB() & 0xFFFFFF, Op.cw); 
/* 157 */           Print.pp.repaint();
/*     */         }
/*     */       };
/* 160 */     JButton jbTextColor = Methods.newButton("doText", doText, 84, 10, 210, 135, 26);
/* 161 */     jbTextColor.setToolTipText("Select a color for the clue text.");
/* 162 */     jpColors.add(jbTextColor);
/*     */     
/* 164 */     JPanel jpArrow = new JPanel();
/* 165 */     jpArrow.setLayout((LayoutManager)null);
/* 166 */     jpArrow.setSize(135, 133);
/* 167 */     jpArrow.setLocation(175, 10);
/* 168 */     jpArrow.setOpaque(true);
/* 169 */     jpArrow.setBorder(BorderFactory.createEtchedBorder());
/* 170 */     jdlgPrintOptions.add(jpArrow);
/*     */     
/* 172 */     JLabel jlArrow = new JLabel("Arrow Style");
/* 173 */     jlArrow.setForeground(Def.COLOR_LABEL);
/* 174 */     jlArrow.setSize(135, 20);
/* 175 */     jlArrow.setLocation(0, 2);
/* 176 */     jlArrow.setHorizontalAlignment(0);
/* 177 */     jpArrow.add(jlArrow);
/* 178 */     ButtonGroup bg1 = new ButtonGroup();
/*     */     
/* 180 */     for (int i = 0; i < 6; i++) {
/* 181 */       ImageIcon theIcon = new ImageIcon("graphics/aw" + i + ".png");
/* 182 */       JLabel jli = new JLabel(theIcon);
/* 183 */       jli.setSize(26, 26);
/* 184 */       jli.setLocation(33 + 70 * i % 2, 25 + 35 * i / 2);
/* 185 */       jpArrow.add(jli);
/*     */       
/* 187 */       jrbArrow[i] = new JRadioButton("" + i);
/* 188 */       jrbArrow[i].setOpaque(false);
/* 189 */       jrbArrow[i].setForeground(Def.COLOR_LABEL);
/* 190 */       jrbArrow[i].setSize(40, 20);
/* 191 */       jrbArrow[i].setLocation(70 * i % 2, 25 + 35 * i / 2);
/* 192 */       jrbArrow[i].setHorizontalAlignment(2);
/* 193 */       jpArrow.add(jrbArrow[i]);
/* 194 */       if (bg1 != null) bg1.add(jrbArrow[i]); 
/*     */     } 
/* 196 */     jrbArrow[Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)].setSelected(true);
/*     */     
/* 198 */     JPanel jpAlign = new JPanel();
/* 199 */     jpAlign.setLayout((LayoutManager)null);
/* 200 */     jpAlign.setSize(135, 108);
/* 201 */     jpAlign.setLocation(175, 151);
/* 202 */     jpAlign.setOpaque(true);
/* 203 */     jpAlign.setBorder(BorderFactory.createEtchedBorder());
/* 204 */     jdlgPrintOptions.add(jpAlign);
/*     */     
/* 206 */     jl = new JLabel("Clue Alignment");
/* 207 */     jl.setForeground(Def.COLOR_LABEL);
/* 208 */     jl.setSize(135, 20);
/* 209 */     jl.setLocation(0, 2);
/* 210 */     jl.setHorizontalAlignment(0);
/* 211 */     jpAlign.add(jl);
/* 212 */     String[] alignText = { "6 Align Left", "7 Align center", "8 Align Right", "9 Justify" };
/* 213 */     ButtonGroup bg2 = new ButtonGroup();
/* 214 */     for (int j = 0; j < 4; j++) {
/* 215 */       jrbAlign[j] = new JRadioButton();
/* 216 */       jrbAlign[j].setText(alignText[j]);
/* 217 */       jrbAlign[j].setForeground(Def.COLOR_LABEL);
/* 218 */       jrbAlign[j].setOpaque(false);
/* 219 */       jrbAlign[j].setSize(120, 20);
/* 220 */       jrbAlign[j].setLocation(10, 23 + 20 * j);
/* 221 */       jpAlign.add(jrbAlign[j]);
/* 222 */       if (bg2 != null) bg2.add(jrbAlign[j]); 
/*     */     } 
/* 224 */     jrbAlign[Op.getInt(Op.CW.AwAlign.ordinal(), Op.cw)].setSelected(true);
/*     */     
/* 226 */     JLabel jlWidth = new JLabel("Line Width");
/* 227 */     jlWidth.setForeground(Def.COLOR_LABEL);
/* 228 */     jlWidth.setSize(200, 20);
/* 229 */     jlWidth.setLocation(100, 265);
/* 230 */     jlWidth.setHorizontalAlignment(2);
/* 231 */     jdlgPrintOptions.add(jlWidth);
/*     */     
/* 233 */     final JComboBox<Integer> jcbbWidth = new JComboBox<>();
/* 234 */     for (int k = 1; k <= 10; k++)
/* 235 */       jcbbWidth.addItem(Integer.valueOf(k)); 
/* 236 */     jcbbWidth.setSize(50, 23);
/* 237 */     jcbbWidth.setLocation(40, 265);
/* 238 */     jdlgPrintOptions.add(jcbbWidth);
/* 239 */     jcbbWidth.setBackground(Def.COLOR_BUTTONBG);
/* 240 */     jcbbWidth.setSelectedIndex(Op.getInt(Op.CW.AwWidth.ordinal(), Op.cw) - 1);
/*     */     
/* 242 */     JLabel jlOverlap = new JLabel("Clue Area Overlap");
/* 243 */     jlOverlap.setForeground(Def.COLOR_LABEL);
/* 244 */     jlOverlap.setSize(200, 20);
/* 245 */     jlOverlap.setLocation(100, 298);
/* 246 */     jlOverlap.setHorizontalAlignment(2);
/* 247 */     jdlgPrintOptions.add(jlOverlap);
/*     */     
/* 249 */     final JComboBox<Integer> jcbbOverlap = new JComboBox<>();
/* 250 */     for (int m = 0; m <= 21; m += 2)
/* 251 */       jcbbOverlap.addItem(Integer.valueOf(m)); 
/* 252 */     jcbbOverlap.setSize(50, 23);
/* 253 */     jcbbOverlap.setLocation(40, 298);
/* 254 */     jdlgPrintOptions.add(jcbbOverlap);
/* 255 */     jcbbOverlap.setBackground(Def.COLOR_BUTTONBG);
/* 256 */     jcbbOverlap.setSelectedIndex(Op.getInt(Op.CW.AwOverlap.ordinal(), Op.cw) / 2);
/*     */     
/* 258 */     final JCheckBox jcbBold = new JCheckBox("Print clues using Bold characters", Op.getBool(Op.CW.AwBold.ordinal(), Op.cw).booleanValue());
/* 259 */     jcbBold.setForeground(Def.COLOR_LABEL);
/* 260 */     jcbBold.setOpaque(false);
/* 261 */     jcbBold.setSize(290, 20);
/* 262 */     jcbBold.setLocation(40, 328);
/* 263 */     jdlgPrintOptions.add(jcbBold);
/*     */     
/* 265 */     Action doOK = new AbstractAction("OK") {
/*     */         public void actionPerformed(ActionEvent e) {
/*     */           int x;
/* 268 */           for (x = 0; x < 6; x++) {
/* 269 */             if (jrbArrow[x].isSelected()) {
/* 270 */               Op.setInt(Op.CW.AwStyle.ordinal(), x, Op.cw);
/*     */               break;
/*     */             } 
/*     */           } 
/* 274 */           for (x = 0; x < 4; x++) {
/* 275 */             if (jrbAlign[x].isSelected()) {
/* 276 */               Op.setInt(Op.CW.AwAlign.ordinal(), x, Op.cw); break;
/*     */             } 
/*     */           } 
/* 279 */           Op.setBool(Op.CW.AwBold.ordinal(), Boolean.valueOf(jcbBold.isSelected()), Op.cw);
/* 280 */           Op.setInt(Op.CW.AwWidth.ordinal(), jcbbWidth.getSelectedIndex() + 1, Op.cw);
/* 281 */           Op.setInt(Op.CW.AwOverlap.ordinal(), jcbbOverlap.getSelectedIndex() * 2, Op.cw);
/* 282 */           Methods.clickedOK = true;
/* 283 */           jdlgPrintOptions.dispose();
/* 284 */           Methods.closeHelp();
/*     */         }
/*     */       };
/* 287 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 10, 363, 120, 26);
/* 288 */     jdlgPrintOptions.add(jbOK);
/*     */     
/* 290 */     Action doCancel = new AbstractAction("Cancel") {
/*     */         public void actionPerformed(ActionEvent e) {
/* 292 */           Op.setColorInt(Op.CW.AwClue.ordinal(), localColor[0], Op.cw);
/* 293 */           Op.setColorInt(Op.CW.AwCell.ordinal(), localColor[1], Op.cw);
/* 294 */           Op.setColorInt(Op.CW.AwPattern.ordinal(), localColor[2], Op.cw);
/* 295 */           Op.setColorInt(Op.CW.AwArrow.ordinal(), localColor[3], Op.cw);
/* 296 */           Op.setColorInt(Op.CW.AwLine.ordinal(), localColor[4], Op.cw);
/* 297 */           Op.setColorInt(Op.CW.AwText.ordinal(), localColor[5], Op.cw);
/* 298 */           Methods.clickedOK = false;
/* 299 */           jdlgPrintOptions.dispose();
/* 300 */           Methods.closeHelp();
/*     */         }
/*     */       };
/* 303 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 10, 398, 120, 26);
/* 304 */     jdlgPrintOptions.add(jbCancel);
/*     */     
/* 306 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*     */         public void actionPerformed(ActionEvent e) {
/* 308 */           Methods.cweHelp(null, jdlgPrintOptions, "Arrow-word Print Options", Arrowword.arrowwordOptions);
/*     */         }
/*     */       };
/* 311 */     JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 140, 363, 170, 61);
/* 312 */     jdlgPrintOptions.add(jbHelp);
/*     */     
/* 314 */     jdlgPrintOptions.getRootPane().setDefaultButton(jbOK);
/* 315 */     Methods.setDialogSize(jdlgPrintOptions, 320, 435);
/*     */   }
/*     */   
/*     */   static boolean clueAllocation(Node node, int maxCluesPerCell, int cellsWithMax) {
/* 319 */     if (node.select == 0) {
/* 320 */       node.select = 1;
/*     */     } else {
/* 322 */       getXandY(node);
/* 323 */       if (TorB == 'T') Grid.vert[cellX][cellY] = Grid.vert[cellX][cellY] & 0xFE; 
/* 324 */       if (TorB == 'B') Grid.vert[cellX][cellY] = Grid.vert[cellX][cellY] & 0xFD; 
/* 325 */       if (Grid.horz[cellX][cellY] == maxCluesPerCell)
/* 326 */         actualCellsWithMax--; 
/* 327 */       Grid.horz[cellX][cellY] = Grid.horz[cellX][cellY] - 1;
/* 328 */       node.select *= 2;
/* 329 */       if (node.select == 64) {
/* 330 */         node.select = 0;
/* 331 */         return false;
/*     */       } 
/*     */     } 
/*     */     
/*     */     while (true) {
/* 336 */       while ((node.select & node.status) == 0) {
/* 337 */         node.select *= 2;
/* 338 */         if (node.select == 64) {
/* 339 */           node.select = 0;
/* 340 */           return false;
/*     */         } 
/*     */       } 
/*     */       
/* 344 */       getXandY(node);
/* 345 */       if ((TorB != 'T' || (Grid.vert[cellX][cellY] & 0x1) <= 0) && (TorB != 'B' || (Grid.vert[cellX][cellY] & 0x2) <= 0) && (Grid.horz[cellX][cellY] < maxCluesPerCell - 1 || (Grid.horz[cellX][cellY] == maxCluesPerCell - 1 && actualCellsWithMax < cellsWithMax))) {
/*     */ 
/*     */         
/* 348 */         if (TorB == 'T') Grid.vert[cellX][cellY] = Grid.vert[cellX][cellY] | 0x1; 
/* 349 */         if (TorB == 'B') Grid.vert[cellX][cellY] = Grid.vert[cellX][cellY] | 0x2; 
/* 350 */         Grid.horz[cellX][cellY] = Grid.horz[cellX][cellY] + 1; if (Grid.horz[cellX][cellY] + 1 == maxCluesPerCell)
/* 351 */           actualCellsWithMax++; 
/* 352 */         return true;
/*     */       } 
/*     */       
/* 355 */       node.select *= 2;
/* 356 */       if (node.select == 64) {
/* 357 */         node.select = 0;
/* 358 */         return false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void getXandY(Node node) {
/* 368 */     if (node.direction == 0) {
/* 369 */       int xs = node.x, ye = node.y, ys = ye, xe = node.x + node.length - 1;
/* 370 */       switch (node.select) { case 1:
/* 371 */           cellX = xs - 1; cellY = ys; TorB = 'L'; break;
/* 372 */         case 2: cellX = xs; cellY = ys + 1; TorB = 'T'; break;
/* 373 */         case 4: cellX = xs; cellY = ys - 1; TorB = 'B'; break;
/* 374 */         case 8: cellX = xe + 1; cellY = ye; TorB = 'L'; break;
/* 375 */         case 16: cellX = xe; cellY = ye - 1; TorB = 'B'; break;
/* 376 */         case 32: cellX = xe; cellY = ye + 1; TorB = 'T';
/*     */           break; }
/*     */     
/*     */     } else {
/* 380 */       int xe = node.x, xs = xe, ys = node.y, ye = node.y + node.length - 1;
/* 381 */       switch (node.select) { case 1:
/* 382 */           cellX = xs; cellY = ys - 1; TorB = 'B'; break;
/* 383 */         case 2: cellX = xs - 1; cellY = ys; TorB = 'D'; break;
/* 384 */         case 4: cellX = xs + 1; cellY = ys; TorB = 'D'; break;
/* 385 */         case 8: cellX = xe; cellY = ye + 1; TorB = 'T'; break;
/* 386 */         case 16: cellX = xe + 1; cellY = ye; TorB = 'S'; break;
/* 387 */         case 32: cellX = xe - 1; cellY = ye; TorB = 'S';
/*     */           break; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   static void swapNodes(Node n1, Node n2) {
/* 394 */     Node swap = n1;
/* 395 */     n1 = n2;
/* 396 */     n2 = swap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void shuffleNodes(int mode) {
/* 403 */     for (int j = 0; j < NodeList.nodeListLength - 1; j++) {
/* 404 */       Node n = NodeList.nodeList[j];
/* 405 */       for (int i = j + 1; i < NodeList.nodeListLength; i++) {
/* 406 */         Node s = NodeList.nodeList[i];
/* 407 */         switch (mode) { case 0:
/* 408 */             if (s.id < n.id) swapNodes(n, s);  break;
/* 409 */           case 1: if (s.direction < n.direction || (s.direction == n.direction && s.id < n.id)) swapNodes(n, s);  break;
/* 410 */           case 2: if (s.direction < n.direction || (s.direction == n.direction && s.id > n.id)) swapNodes(n, s);  break;
/* 411 */           case 3: if (s.direction > n.direction || (s.direction == n.direction && s.id < n.id)) swapNodes(n, s);  break;
/* 412 */           case 4: if (s.direction > n.direction || (s.direction == n.direction && s.id > n.id)) swapNodes(n, s);  break;
/* 413 */           case 5: if (s.count < n.count) swapNodes(n, s); 
/*     */             break; }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */   static void drawArrowHead(Graphics2D g2, int x, int y, int xCell, int yCell, char style) {
/* 420 */     int[] xPoints = new int[3], yPoints = new int[3];
/* 421 */     int x14 = xCell / 14, y14 = yCell / 14, x6 = xCell / 7, y6 = yCell / 7;
/*     */     
/* 423 */     if (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) > 3) {
/* 424 */       x6 = xCell / 5; y6 = yCell / 5;
/*     */     } 
/* 426 */     switch (style) {
/*     */       case 'R':
/* 428 */         xPoints[0] = x; yPoints[0] = y - y14;
/* 429 */         xPoints[1] = x; yPoints[1] = y + y14;
/* 430 */         xPoints[2] = x + x6; yPoints[2] = y;
/*     */         break;
/*     */       case 'L':
/* 433 */         xPoints[0] = x; yPoints[0] = y - y14;
/* 434 */         xPoints[1] = x; yPoints[1] = y + y14;
/* 435 */         xPoints[2] = x - x6; yPoints[2] = y;
/*     */         break;
/*     */       case 'D':
/* 438 */         xPoints[0] = x - x14; yPoints[0] = y;
/* 439 */         xPoints[1] = x + x14; yPoints[1] = y;
/* 440 */         xPoints[2] = x; yPoints[2] = y + y6;
/*     */         break;
/*     */       case 'U':
/* 443 */         xPoints[0] = x - x14; yPoints[0] = y;
/* 444 */         xPoints[1] = x + x14; yPoints[1] = y;
/* 445 */         xPoints[2] = x; yPoints[2] = y - y6;
/*     */         break;
/*     */       case 'r':
/* 448 */         g2.drawLine(x, y - y14, x, y + y14);
/* 449 */         g2.drawLine(x, y + y14, x + x6, y);
/* 450 */         g2.drawLine(x + x6, y, x, y - y14);
/*     */         break;
/*     */       case 'l':
/* 453 */         g2.drawLine(x, y - y14, x, y + y14);
/* 454 */         g2.drawLine(x, y + y14, x - x6, y);
/* 455 */         g2.drawLine(x - x6, y, x, y - y14);
/*     */         break;
/*     */       case 'd':
/* 458 */         g2.drawLine(x - x14, y, x + x14, y);
/* 459 */         g2.drawLine(x + x14, y, x, y + y6);
/* 460 */         g2.drawLine(x, y + y6, x - x14, y);
/*     */         break;
/*     */       case 'u':
/* 463 */         g2.drawLine(x - x14, y, x + x14, y);
/* 464 */         g2.drawLine(x + x14, y, x, y - y6);
/* 465 */         g2.drawLine(x, y - y6, x - x14, y);
/*     */         break;
/*     */     } 
/* 468 */     if (style < 'a') {
/* 469 */       g2.fillPolygon(xPoints, yPoints, 3);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawArrow(Graphics2D g2, int x, int y, int xCell, int yCell, int mode) {
/* 478 */     int xpoints[], ypoints[], x7 = (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) > 3) ? (xCell / 14) : (xCell / 7);
/* 479 */     int y7 = (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) > 3) ? (yCell / 14) : (yCell / 7);
/* 480 */     int x10 = xCell / 16;
/* 481 */     int y10 = yCell / 16;
/* 482 */     int x14 = xCell / 14;
/* 483 */     int y14 = yCell / 14;
/* 484 */     int xadjust = (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) > 3) ? x10 : 0;
/* 485 */     int yadjust = (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) > 3) ? y10 : 0;
/*     */     
/* 487 */     switch (mode) {
/*     */       case 2561:
/* 489 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0: case 1: case 2:
/*     */           case 3:
/* 491 */             g2.drawLine(x, y, x + x10, y); break; }
/*     */         
/* 493 */         drawArrowHead(g2, x + x10 - xadjust, y, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 114 : 82);
/*     */         break;
/*     */       case 2562:
/* 496 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0:
/*     */           case 1:
/* 498 */             g2.drawLine(x, y, x, y - 3 * y14); break;
/*     */           case 2:
/*     */           case 3:
/* 501 */             xpoints = IntStream.of(new int[] { x - x10, x - x10, x }).toArray();
/* 502 */             ypoints = IntStream.of(new int[] { y, y - y7, y - y7 }).toArray();
/* 503 */             g2.drawPolyline(xpoints, ypoints, 3); break; }
/*     */         
/* 505 */         drawArrowHead(g2, x - xadjust, y - y7, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 114 : 82);
/*     */         break;
/*     */       case 2564:
/* 508 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0:
/*     */           case 1:
/* 510 */             g2.drawLine(x, y, x, y + 3 * y14); break;
/*     */           case 2:
/*     */           case 3:
/* 513 */             xpoints = IntStream.of(new int[] { x - x10, x - x10, x }).toArray();
/* 514 */             ypoints = IntStream.of(new int[] { y, y + y7, y + y7 }).toArray();
/* 515 */             g2.drawPolyline(xpoints, ypoints, 3); break; }
/*     */         
/* 517 */         drawArrowHead(g2, x - xadjust, y + y7, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 114 : 82);
/*     */         break;
/*     */       case 2568:
/* 520 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0: case 1: case 2:
/*     */           case 3:
/* 522 */             g2.drawLine(x, y, x - x10, y); break; }
/*     */         
/* 524 */         drawArrowHead(g2, x - x10 + xadjust, y, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 108 : 76);
/*     */         break;
/*     */       case 2576:
/* 527 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0:
/*     */           case 1:
/* 529 */             g2.drawLine(x, y, x, y + 3 * y14); break;
/*     */           case 2:
/*     */           case 3:
/* 532 */             xpoints = IntStream.of(new int[] { x + x10, x + x10, x }).toArray();
/* 533 */             ypoints = IntStream.of(new int[] { y, y + y7, y + y7 }).toArray();
/* 534 */             g2.drawPolyline(xpoints, ypoints, 3); break; }
/*     */         
/* 536 */         drawArrowHead(g2, x + xadjust, y + y7, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 108 : 76);
/*     */         break;
/*     */       case 2592:
/* 539 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0:
/*     */           case 1:
/* 541 */             g2.drawLine(x, y, x, y - 3 * y14); break;
/*     */           case 2:
/*     */           case 3:
/* 544 */             xpoints = IntStream.of(new int[] { x + x10, x + x10, x }).toArray();
/* 545 */             ypoints = IntStream.of(new int[] { y, y - y7, y - y7 }).toArray();
/* 546 */             g2.drawPolyline(xpoints, ypoints, 3); break; }
/*     */         
/* 548 */         drawArrowHead(g2, x + xadjust, y - y7, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 108 : 76);
/*     */         break;
/*     */       case 3329:
/* 551 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0: case 1: case 2:
/*     */           case 3:
/* 553 */             g2.drawLine(x, y, x, y + y10); break; }
/*     */         
/* 555 */         drawArrowHead(g2, x, y + y10 - yadjust, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 100 : 68);
/*     */         break;
/*     */       case 3330:
/* 558 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0:
/*     */           case 1:
/* 560 */             g2.drawLine(x, y, x + 3 * x14, y); break;
/*     */           case 2:
/*     */           case 3:
/* 563 */             xpoints = IntStream.of(new int[] { x, x + x7, x + x7 }).toArray();
/* 564 */             ypoints = IntStream.of(new int[] { y - y10, y - y10, y }).toArray();
/* 565 */             g2.drawPolyline(xpoints, ypoints, 3); break; }
/*     */         
/* 567 */         drawArrowHead(g2, x + x7, y - yadjust, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 100 : 68);
/*     */         break;
/*     */       case 3332:
/* 570 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0:
/*     */           case 1:
/* 572 */             g2.drawLine(x, y, x - 3 * x14, y); break;
/*     */           case 2:
/*     */           case 3:
/* 575 */             xpoints = IntStream.of(new int[] { x, x - x7, x - x7 }).toArray();
/* 576 */             ypoints = IntStream.of(new int[] { y - y10, y - y10, y }).toArray();
/* 577 */             g2.drawPolyline(xpoints, ypoints, 3); break; }
/*     */         
/* 579 */         drawArrowHead(g2, x - x7, y - yadjust, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 100 : 68);
/*     */         break;
/*     */       case 3336:
/* 582 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0: case 1: case 2:
/*     */           case 3:
/* 584 */             g2.drawLine(x, y, x, y - y10); break; }
/*     */         
/* 586 */         drawArrowHead(g2, x, y - y10 + yadjust, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 117 : 85);
/*     */         break;
/*     */       case 3344:
/* 589 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0:
/*     */           case 1:
/* 591 */             g2.drawLine(x, y, x - 3 * x14, y); break;
/*     */           case 2:
/*     */           case 3:
/* 594 */             xpoints = IntStream.of(new int[] { x, x - x7, x - x7 }).toArray();
/* 595 */             ypoints = IntStream.of(new int[] { y + y10, y + y10, y }).toArray();
/* 596 */             g2.drawPolyline(xpoints, ypoints, 3); break; }
/*     */         
/* 598 */         drawArrowHead(g2, x - x7, y + yadjust, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 117 : 85);
/*     */         break;
/*     */       case 3360:
/* 601 */         switch (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw)) { case 0:
/*     */           case 1:
/* 603 */             g2.drawLine(x, y, x + 3 * x14, y); break;
/*     */           case 2:
/*     */           case 3:
/* 606 */             xpoints = IntStream.of(new int[] { x, x + x7, x + x7 }).toArray();
/* 607 */             ypoints = IntStream.of(new int[] { y + y10, y + y10, y }).toArray();
/* 608 */             g2.drawPolyline(xpoints, ypoints, 3); break; }
/*     */         
/* 610 */         drawArrowHead(g2, x + x7, y + yadjust, xCell, yCell, (Op.getInt(Op.CW.AwStyle.ordinal(), Op.cw) % 2 == 1) ? 117 : 85);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static int arrowwordPrint(Graphics2D g2, int l, int t, int w, int h) {
/* 617 */     int shuffleMode = 0, count = 0, fontSize = 200, retVal = 0;
/* 618 */     Node[] cNode = new Node[3];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 624 */     RenderingHints rh = g2.getRenderingHints();
/* 625 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/* 626 */     rh.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
/* 627 */     rh.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
/* 628 */     g2.setRenderingHints(rh);
/*     */     
/* 630 */     Methods.clearGrid(Grid.sig);
/* 631 */     Methods.clearGrid(Grid.horz);
/* 632 */     Methods.clearGrid(Grid.vert); int j;
/* 633 */     for (j = 0; j < NodeList.nodeListLength; j++) {
/* 634 */       Node node = NodeList.nodeList[j];
/* 635 */       node.status = node.count = 0;
/* 636 */       if (node.direction == 0) {
/* 637 */         int xs = node.x;
/* 638 */         int xe = node.x + node.length - 1;
/* 639 */         int ye = node.y, ys = ye;
/* 640 */         if (xe < Grid.xSz - 1 && (Grid.mode[xe + 1][ye] == 12 || Grid.mode[xe + 1][ye] == 14)) {
/*     */           
/* 642 */           node.status |= 0x8;
/* 643 */           node.count++;
/* 644 */           Grid.sig[xe + 1][ye] = Grid.sig[xe + 1][ye] + 1;
/*     */         } else {
/*     */           
/* 647 */           if (xs > 0) {
/* 648 */             int z = Grid.mode[xs - 1][ys];
/* 649 */             if (z == 1 || z > 11) {
/* 650 */               node.status |= 0x1;
/* 651 */               node.count++;
/* 652 */               Grid.sig[xs - 1][ys] = Grid.sig[xs - 1][ys] + 1;
/*     */             } 
/*     */           } 
/* 655 */           if (ys > 0) {
/* 656 */             int z = Grid.mode[xs][ys - 1];
/* 657 */             if (z == 1 || z > 11) {
/* 658 */               node.status |= 0x4;
/* 659 */               node.count++;
/* 660 */               Grid.sig[xs][ys - 1] = Grid.sig[xs][ys - 1] + 1;
/*     */             } 
/*     */           } 
/* 663 */           if (ys < Grid.ySz - 1) {
/* 664 */             int z = Grid.mode[xs][ys + 1];
/* 665 */             if (z == 1 || z == 12) {
/* 666 */               node.status |= 0x2;
/* 667 */               node.count++;
/* 668 */               Grid.sig[xs][ys + 1] = Grid.sig[xs][ys + 1] + 1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         
/* 674 */         int xe = node.x, xs = xe, ys = node.y, ye = node.y + node.length - 1;
/* 675 */         if (ye < Grid.ySz - 1 && (Grid.mode[xe][ye + 1] == 13 || Grid.mode[xe][ye + 1] == 14)) {
/* 676 */           node.status |= 0x8; node.count++; Grid.sig[xe][ye + 1] = Grid.sig[xe][ye + 1] + 1;
/*     */         } else {
/* 678 */           if (ys > 0) { int z = Grid.mode[xs][ys - 1]; if (z == 1 || z > 11) { node.status |= 0x1; node.count++; Grid.sig[xs][ys - 1] = Grid.sig[xs][ys - 1] + 1; }  }
/* 679 */            if (xs > 0) { int z = Grid.mode[xs - 1][ys]; if (z == 1 || z > 11) { node.status |= 0x2; node.count++; Grid.sig[xs - 1][ys] = Grid.sig[xs - 1][ys] + 1; }  }
/* 680 */            if (xs < Grid.xSz - 1) { int z = Grid.mode[xs + 1][ys]; if (z == 1 || z == 13) { node.status |= 0x4; node.count++; Grid.sig[xs + 1][ys] = Grid.sig[xs + 1][ys] + 1; }
/*     */              }
/*     */         
/*     */         } 
/*     */       } 
/* 685 */     }  int nodeCount = NodeList.nodeListLength; int cellCount;
/* 686 */     for (cellCount = j = 0; j < Grid.ySz; j++) {
/* 687 */       for (int k = 0; k < Grid.xSz; k++)
/* 688 */       { if (Grid.sig[k][j] > 0)
/* 689 */           cellCount++;  } 
/* 690 */     }  if (cellCount == 0) return 1; 
/* 691 */     int maxCluesPerCell = nodeCount / cellCount;
/* 692 */     int cellsWithMax = cellCount; int i;
/* 693 */     if ((i = nodeCount - maxCluesPerCell * cellCount) > 0) {
/* 694 */       maxCluesPerCell++;
/* 695 */       cellsWithMax = i;
/*     */     } 
/*     */     
/* 698 */     for (j = 0; j < NodeList.nodeListLength; j++) {
/* 699 */       Node node = NodeList.nodeList[j];
/* 700 */       for (node.select = 1; node.select < 64; node.select *= 2) {
/* 701 */         if ((node.select & node.status) > 0) {
/* 702 */           getXandY(node);
/* 703 */           if (Grid.sig[cellX][cellY] == 1)
/* 704 */             node.status = node.select; 
/*     */         } 
/*     */       } 
/* 707 */       node.select = 0;
/*     */     } 
/*     */     
/* 710 */     for (actualCellsWithMax = 0, j = 0; j < NodeList.nodeListLength; ) {
/* 711 */       Node node = NodeList.nodeList[j];
/* 712 */       j += clueAllocation(node, maxCluesPerCell, cellsWithMax) ? 1 : -1;
/* 713 */       if (++count == 100000 || j < 0) {
/* 714 */         count = 0; actualCellsWithMax = 0;
/* 715 */         Methods.clearGrid(Grid.horz);
/* 716 */         Methods.clearGrid(Grid.vert);
/* 717 */         for (i = 0; i < NodeList.nodeListLength; ) { (NodeList.nodeList[i]).select = 0; i++; }
/* 718 */          j = 0;
/* 719 */         shuffleNodes(shuffleMode % 6);
/* 720 */         if (++shuffleMode % 6 == 0 && 
/* 721 */           ++cellsWithMax > cellCount) {
/* 722 */           if (maxCluesPerCell == 2) {
/* 723 */             cellsWithMax = 1;
/* 724 */             maxCluesPerCell = 3; continue;
/*     */           } 
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 730 */     if (count == 0 || j == 0) {
/* 731 */       retVal = 1;
/*     */     }
/* 733 */     xCell = w / Grid.xSz;
/* 734 */     yCell = h / Grid.ySz;
/* 735 */     xOrg = l + (w - xCell * Grid.xSz) / 2;
/* 736 */     yOrg = t + (h - yCell * Grid.ySz) / 2;
/* 737 */     int lineWidth = xCell * Op.getInt(Op.CW.AwWidth.ordinal(), Op.cw) / 100;
/* 738 */     if (lineWidth == 0) lineWidth = 1;
/*     */ 
/*     */     
/* 741 */     g2.setStroke(new BasicStroke(lineWidth, 1, 1));
/* 742 */     for (j = 0; j < Grid.ySz; j++) {
/* 743 */       for (i = 0; i < Grid.xSz; i++) {
/* 744 */         if (Grid.mode[i][j] != 2) {
/* 745 */           int xt = xOrg + i * xCell, yt = yOrg + j * yCell;
/* 746 */           if (Grid.mode[i][j] == 1 && Grid.horz[i][j] == 0) {
/* 747 */             g2.setColor(new Color(Op.getColorInt(Op.CW.AwPattern.ordinal(), Op.cw)));
/* 748 */           } else if (Grid.mode[i][j] == 0) {
/* 749 */             g2.setColor(new Color((Grid.color[i][j] == 16777215) ? Op.getColorInt(Op.CW.AwCell.ordinal(), Op.cw) : Grid.color[i][j]));
/*     */           } else {
/* 751 */             g2.setColor(new Color(Op.getColorInt(Op.CW.AwClue.ordinal(), Op.cw)));
/* 752 */           }  g2.fillRect(xt, yt, xCell, yCell);
/* 753 */           g2.setColor(new Color(Op.getColorInt(Op.CW.AwLine.ordinal(), Op.cw)));
/* 754 */           g2.drawRect(xt, yt, xCell, yCell);
/*     */         } 
/*     */       } 
/* 757 */     }  int xadj = xCell * Op.getInt(Op.CW.AwOverlap.ordinal(), Op.cw) / 100;
/* 758 */     int yadj = yCell * Op.getInt(Op.CW.AwOverlap.ordinal(), Op.cw) / 100; boolean print;
/* 759 */     for (print = false;; print = true) {
/* 760 */       for (j = 0; j < Grid.ySz; j++) {
/* 761 */         for (i = 0; i < Grid.xSz; i++) {
/* 762 */           if (Grid.mode[i][j] == 1 && Grid.horz[i][j] != 0) {
/* 763 */             int p = Op.getInt(Op.CW.AwOverlap.ordinal(), Op.cw);
/* 764 */             int cl = xOrg + i * xCell - xadj;
/* 765 */             int ct = yOrg + j * yCell - yadj;
/* 766 */             int cw = xCell + xadj * 2;
/* 767 */             int ch = yCell + yadj * 2;
/*     */             
/* 769 */             if (i == 0 || Grid.mode[i - 1][j] == 1) {
/* 770 */               cl += xadj;
/* 771 */               cw -= xadj;
/*     */             } 
/*     */             
/* 774 */             if (j == 0 || Grid.mode[i][j - 1] == 1) {
/* 775 */               ct += yadj;
/* 776 */               ch -= yadj;
/*     */             } 
/*     */             
/* 779 */             if (i == Grid.xSz - 1 || Grid.mode[i + 1][j] == 1) {
/* 780 */               cw -= xadj;
/*     */             }
/* 782 */             if (j == Grid.ySz - 1 || Grid.mode[i][j + 1] == 1) {
/* 783 */               ch -= yadj;
/*     */             }
/*     */             
/* 786 */             if (Grid.horz[i][j] > 0)
/* 787 */             { for (int y = 0, x = y; y < NodeList.nodeListLength; y++) {
/* 788 */                 Node node = NodeList.nodeList[y];
/* 789 */                 getXandY(node);
/* 790 */                 if (i == cellX && j == cellY) {
/* 791 */                   node.wordIndex = TorB;
/* 792 */                   cNode[x++] = node;
/*     */                 } 
/*     */               } 
/*     */               
/* 796 */               if (Grid.horz[i][j] > 1) {
/* 797 */                 if ((cNode[0]).wordIndex < (cNode[1]).wordIndex)
/* 798 */                   swapNodes(cNode[0], cNode[1]); 
/* 799 */                 if (Grid.horz[i][j] == 3) {
/* 800 */                   if ((cNode[0]).wordIndex < (cNode[2]).wordIndex)
/* 801 */                     swapNodes(cNode[0], cNode[2]); 
/* 802 */                   if ((cNode[1]).wordIndex < (cNode[2]).wordIndex) {
/* 803 */                     swapNodes(cNode[1], cNode[2]);
/*     */                   }
/*     */                 } 
/*     */               } 
/* 807 */               if (print) {
/* 808 */                 int rectCorner = xCell / 8;
/* 809 */                 g2.setStroke(new BasicStroke(lineWidth, 0, 0));
/* 810 */                 g2.setColor(new Color(Op.getColorInt(Op.CW.AwClue.ordinal(), Op.cw)));
/* 811 */                 g2.fillRoundRect(cl, ct, cw, ch, rectCorner, rectCorner);
/* 812 */                 g2.setColor(new Color(Op.getColorInt(Op.CW.AwLine.ordinal(), Op.cw)));
/* 813 */                 g2.drawRoundRect(cl, ct, cw, ch, rectCorner, rectCorner);
/*     */                 
/* 815 */                 g2.setColor(new Color(Op.getColorInt(Op.CW.AwLine.ordinal(), Op.cw)));
/* 816 */                 if (Grid.horz[i][j] == 2)
/* 817 */                   g2.drawLine(cl, ct + ch / 2, cl + cw, ct + ch / 2); 
/* 818 */                 g2.setColor(new Color(Op.getColorInt(Op.CW.AwArrow.ordinal(), Op.cw)));
/* 819 */                 for (int k = 0; k < Grid.horz[i][j]; k++) {
/* 820 */                   int yside, xright = cl + cw;
/* 821 */                   switch (Grid.horz[i][j]) { case 1:
/* 822 */                       yside = ct + ch / 2; break;
/* 823 */                     default: if (k == 1) {
/* 824 */                         yside = ct + ch * 3 / 4; break;
/*     */                       } 
/* 826 */                       yside = ct + ch * 3 / 7;
/*     */                       break; }
/*     */                   
/* 829 */                   int xtb = cl + cw / 2;
/* 830 */                   int ybot = ct + ch;
/*     */                   
/* 832 */                   if ((cNode[k]).direction == 0) {
/* 833 */                     switch ((cNode[k]).select) { case 1:
/* 834 */                         drawArrow(g2, xright, yside, cw, ch, 2561); break;
/* 835 */                       case 2: drawArrow(g2, xtb, ct, cw, ch, 2562); break;
/* 836 */                       case 4: drawArrow(g2, xtb, ybot, cw, ch, 2564); break;
/* 837 */                       case 8: drawArrow(g2, cl, yside, cw, ch, 2568); break; }
/*     */                   
/*     */                   } else {
/* 840 */                     switch ((cNode[k]).select) { case 1:
/* 841 */                         drawArrow(g2, xtb, ybot, cw, ch, 3329); break;
/* 842 */                       case 2: drawArrow(g2, xright, yside, cw, ch, 3330); break;
/* 843 */                       case 4: drawArrow(g2, cl, yside, cw, ch, 3332); break;
/* 844 */                       case 8: drawArrow(g2, xtb, ct, cw, ch, 3336); break; }
/*     */                   
/*     */                   } 
/*     */                 } 
/*     */               } 
/* 849 */               int[] alignment = { 0, 2, 1, 3 };
/*     */               
/* 851 */               g2.setColor(new Color(Op.getColorInt(Op.CW.AwArrow.ordinal(), Op.cw)));
/* 852 */               for (int v = 0; v < Grid.horz[i][j]; v++) {
/* 853 */                 int vStart, vHeight; switch (Grid.horz[i][j]) { case 1:
/* 854 */                     vStart = 0; vHeight = ch; break;
/* 855 */                   default: vStart = v * ch / 2;
/* 856 */                     vHeight = ch / 2;
/*     */                     break; }
/*     */                 
/* 859 */                 String clue = (cNode[v]).clue;
/* 860 */                 fontSize = Methods.renderText(g2, cl + cw / 30, ct + vStart, cw - cw / 16, vHeight, "SansSerif", 0, clue, alignment[
/* 861 */                       Op.getInt(Op.CW.AwAlign.ordinal(), Op.cw)], 5, print, Op.getColorInt(Op.CW.AwText.ordinal(), Op.cw), fontSize);
/*     */               } 
/* 863 */               if (fontSize < 1)
/* 864 */                 return 2;  } 
/*     */           } 
/*     */         } 
/* 867 */       }  if (print)
/*     */         break; 
/* 869 */     }  return retVal;
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\Arrowword.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */