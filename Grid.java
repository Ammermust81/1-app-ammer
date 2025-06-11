/*     */ package crosswordexpress;
/*     */ import java.awt.Color;
/*     */ import java.awt.Graphics2D;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ 
/*     */ public class Grid extends JPanel {
/*   8 */   static int gridMax = 50;
/*   9 */   static int[][] mode = new int[gridMax][gridMax];
/*  10 */   static int[][] color = new int[gridMax][gridMax];
/*  11 */   static int[][] curColor = new int[gridMax][gridMax];
/*  12 */   static int[][] letter = new int[gridMax][gridMax];
/*  13 */   static int[][] puz = new int[gridMax][gridMax];
/*  14 */   static int[][] grid = new int[gridMax][gridMax];
/*  15 */   static int[][] sol = new int[gridMax][gridMax];
/*  16 */   static int[][] answer = new int[gridMax][gridMax];
/*  17 */   static int[][] puzzle = new int[gridMax][gridMax];
/*  18 */   static int[][] horz = new int[gridMax][gridMax];
/*  19 */   static int[][] vert = new int[gridMax][gridMax];
/*  20 */   static int[][] iSol = new int[gridMax][gridMax];
/*  21 */   static int[][] copy = new int[gridMax][gridMax];
/*  22 */   static int[][] work = new int[gridMax][gridMax];
/*  23 */   static int[][] build = new int[gridMax][gridMax];
/*  24 */   static int[][] scratch = new int[gridMax][gridMax];
/*  25 */   static int[][] status = new int[gridMax][gridMax];
/*  26 */   static int[][] sig = new int[gridMax][gridMax];
/*  27 */   static byte[][] control = new byte[gridMax][gridMax];
/*  28 */   static byte[][][] xstatus = new byte[gridMax][gridMax][20];
/*  29 */   static char[][] ld = new char[gridMax][gridMax];
/*     */   
/*  31 */   static final int[] bit = new int[] { 1, 2, 4, 8, 16, 32, 64, 128, 256 }; static final int across = 0; static final int down = 1; static int xSz; static int ySz; static int xCell; static int yCell; static int xOrg; static int yOrg; static int symmetry; static int xCur; static int yCur; static int dirCur; static int nCur; static int xNew; static int yNew; static int dirNew;
/*  32 */   static final int[] invbit = new int[] { 510, 509, 507, 503, 495, 479, 447, 383, 255 }; static int nNew; static int xSzW; static int ySzW; static int xCellW;
/*     */   static int yCellW;
/*     */   static int xOrgW;
/*     */   static int yOrgW;
/*     */   static int xCurW;
/*     */   static int yCurW;
/*     */   static int dirCurW;
/*     */   static int nodeCurW;
/*     */   static int xNewW;
/*     */   static int yNewW;
/*     */   static int dirNewW;
/*     */   static int nodeNewW;
/*     */   static boolean inW;
/*  45 */   static Boolean findHint = Boolean.valueOf(false); static Boolean errorExists = Boolean.valueOf(false);
/*     */   static int hintIndexb;
/*  47 */   static int[] hintXb = new int[16]; static int[] hintYb = new int[16];
/*     */   static int hintIndexg;
/*  49 */   static int[] hintXg = new int[16]; static int[] hintYg = new int[16];
/*     */   static int hintIndexr;
/*  51 */   static int[] hintXr = new int[16]; static int[] hintYr = new int[16];
/*  52 */   static String[] hintWord = new String[20];
/*     */   static int hintWrdCnt;
/*     */   static String hintTitle;
/*     */   
/*     */   static void clearGrid() {
/*  57 */     nCur = xCur = yCur = 0;
/*  58 */     for (int j = 0; j < gridMax; j++) {
/*  59 */       for (int i = 0; i < gridMax; i++) {
/*  60 */         control[i][j] = 0; work[i][j] = 0; puzzle[i][j] = 0; answer[i][j] = 0; vert[i][j] = 0; horz[i][j] = 0; iSol[i][j] = 0; mode[i][j] = 0; status[i][j] = 0; scratch[i][j] = 0; copy[i][j] = 0; sol[i][j] = 0; puz[i][j] = 0; grid[i][j] = 0; letter[i][j] = 0;
/*     */ 
/*     */         
/*  63 */         curColor[i][j] = 16777215; color[i][j] = 16777215;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   static int RorL(int x) {
/*  68 */     return (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) ? (xSz - 1 - x) : x;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawPatternCells(Graphics2D g2, Color pattern, Color background, boolean dispNullCells) {
/*  74 */     for (int j = 0; j < ySz; j++) {
/*  75 */       for (int i = 0; i < xSz; i++) {
/*  76 */         int dX, dY, x13, x23, y13, y23, xCoords[], yCoords[], drawI = (Def.puzzleMode == 2) ? i : RorL(i);
/*  77 */         switch (mode[i][j]) {
/*     */           case 0:
/*     */           case 3:
/*     */           case 4:
/*     */           case 5:
/*     */             break;
/*     */           case 2:
/*  84 */             if (dispNullCells) {
/*  85 */               g2.setColor(Def.COLOR_GRAY);
/*  86 */               g2.fillRect(xOrg + drawI * xCell, yOrg + j * yCell, xCell, yCell);
/*     */             } 
/*     */             break;
/*     */           default:
/*  90 */             g2.setColor(pattern);
/*  91 */             g2.fillRect(xOrg + drawI * xCell, yOrg + j * yCell, xCell, yCell);
/*  92 */             g2.setColor(background);
/*  93 */             x13 = xCell / 3; x23 = x13 * 2;
/*  94 */             y13 = yCell / 3; y23 = y13 * 2;
/*  95 */             switch (mode[i][j]) {
/*     */               case 6:
/*  97 */                 g2.fillRect(xOrg + drawI * xCell, yOrg + j * yCell + y13, xCell, y13);
/*     */                 break;
/*     */               case 7:
/* 100 */                 g2.fillRect(xOrg + drawI * xCell + x13, yOrg + j * yCell, x13, yCell);
/*     */                 break;
/*     */               case 8:
/* 103 */                 if (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) {
/* 104 */                   int k = xOrg + (drawI + 1) * xCell, m = yOrg + (j + 1) * yCell;
/* 105 */                   g2.fillRect(k - x23, m - y23, x23, y13);
/* 106 */                   g2.fillRect(k - x23, m - y23, x13, y23);
/*     */                   break;
/*     */                 } 
/* 109 */                 dX = xOrg + drawI * xCell; dY = yOrg + (j + 1) * yCell;
/* 110 */                 g2.fillRect(dX, dY - y23, x23, y13);
/* 111 */                 g2.fillRect(dX + x13, dY - y23, x13, y23);
/*     */                 break;
/*     */               
/*     */               case 9:
/* 115 */                 if (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) {
/*     */                   
/* 117 */                   dX = xOrg + (drawI + 1) * xCell; dY = yOrg + j * yCell;
/* 118 */                   g2.fillRect(dX - x23, dY, x13, y23);
/* 119 */                   g2.fillRect(dX - x23, dY + y13, x23, y13);
/*     */                   break;
/*     */                 } 
/* 122 */                 dX = xOrg + drawI * xCell; dY = yOrg + j * yCell;
/* 123 */                 g2.fillRect(dX, dY + y13, x23, y13);
/* 124 */                 g2.fillRect(dX + x13, dY, x13, y23);
/*     */                 break;
/*     */               
/*     */               case 10:
/* 128 */                 if (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) {
/* 129 */                   dX = xOrg + drawI * xCell; dY = yOrg + (j + 1) * yCell;
/* 130 */                   g2.fillRect(dX, dY - y23, x23, y13);
/* 131 */                   g2.fillRect(dX + x13, dY - y23, x13, y23);
/*     */                   break;
/*     */                 } 
/* 134 */                 dX = xOrg + (drawI + 1) * xCell; dY = yOrg + (j + 1) * yCell;
/* 135 */                 g2.fillRect(dX - x23, dY - y23, x23, y13);
/* 136 */                 g2.fillRect(dX - x23, dY - y23, x13, y23);
/*     */                 break;
/*     */               
/*     */               case 11:
/* 140 */                 if (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) {
/* 141 */                   dX = xOrg + drawI * xCell; dY = yOrg + j * yCell;
/* 142 */                   g2.fillRect(dX, dY + y13, x23, y13);
/* 143 */                   g2.fillRect(dX + x13, dY, x13, y23);
/*     */                   break;
/*     */                 } 
/* 146 */                 dX = xOrg + (drawI + 1) * xCell; dY = yOrg + j * yCell;
/* 147 */                 g2.fillRect(dX - x23, dY, x13, y23);
/* 148 */                 g2.fillRect(dX - x23, dY + y13, x23, y13);
/*     */                 break;
/*     */               
/*     */               case 12:
/*     */               case 14:
/* 153 */                 g2.setColor(new Color(Op.getColorInt(Op.CW.CwReverseArrow.ordinal(), Op.cw)));
/* 154 */                 xCoords = new int[] { xOrg + drawI * xCell + 1, xOrg + drawI * xCell + xCell / 3, xOrg + drawI * xCell + xCell / 3 };
/* 155 */                 yCoords = new int[] { yOrg + j * yCell + yCell / 2, yOrg + j * yCell + yCell / 3, yOrg + j * yCell + 2 * yCell / 3 };
/* 156 */                 g2.fillPolygon(xCoords, yCoords, 3);
/*     */                 
/* 158 */                 if (mode[i][j] == 12)
/*     */                   break; 
/* 160 */               case 13: g2.setColor(new Color(Op.getColorInt(Op.CW.CwReverseArrow.ordinal(), Op.cw)));
/* 161 */                 xCoords = new int[] { xOrg + drawI * xCell + xCell / 2, xOrg + drawI * xCell + xCell / 3, xOrg + drawI * xCell + 2 * xCell / 3 };
/* 162 */                 yCoords = new int[] { yOrg + j * yCell + 1, yOrg + j * yCell + yCell / 3, yOrg + j * yCell + yCell / 3 };
/* 163 */                 g2.fillPolygon(xCoords, yCoords, 3);
/*     */                 break;
/*     */             } 
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static void drawBars(Graphics2D g2) {
/* 174 */     g2.setColor(Def.dispWithColor.booleanValue() ? new Color(Op.getColorInt(Op.CW.CwPatternC.ordinal(), Op.cw)) : Def.COLOR_BLACK);
/* 175 */     for (int j = 0; j < ySz; j++) {
/* 176 */       for (int i = 0; i < xSz; i++) {
/* 177 */         int drawI = RorL(i);
/* 178 */         int cellX = xOrg + drawI * xCell;
/* 179 */         int floor = yOrg + (j + 1) * yCell;
/* 180 */         int wall = (DictionaryMtce.dicHeader[DictionaryMtce.R2L_WORD] == 1) ? cellX : (cellX + xCell);
/* 181 */         switch (mode[i][j]) {
/*     */           case 3:
/*     */           case 5:
/* 184 */             g2.drawLine(wall, floor - yCell, wall, floor);
/* 185 */             if (mode[i][j] == 3)
/*     */               break; 
/*     */           case 4:
/* 188 */             g2.drawLine(cellX, floor, cellX + xCell, floor);
/*     */             break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static void drawOutline(Graphics2D g2, boolean respectNull) {
/* 198 */     int nL = (int)Math.ceil((xCell / 60.0F));
/* 199 */     Stroke normalStroke = new BasicStroke(nL, 2, 0);
/* 200 */     Stroke wideStroke = new BasicStroke((2 * nL), 0, 0);
/*     */     
/* 202 */     g2.setStroke(wideStroke);
/* 203 */     g2.setColor(Def.COLOR_BLACK);
/* 204 */     for (int j = 0; j < ySz; j++) {
/* 205 */       for (int i = 0; i < xSz; i++)
/* 206 */       { if (respectNull)
/* 207 */         { if (mode[i][j] != 2) {
/* 208 */             if (i == 0 || mode[i - 1][j] == 2)
/* 209 */               g2.drawLine(xOrg + i * xCell, yOrg + j * yCell - 1, xOrg + i * xCell, yOrg + (j + 1) * yCell + 1); 
/* 210 */             if (i == xSz - 1 || mode[i + 1][j] == 2)
/* 211 */               g2.drawLine(xOrg + (i + 1) * xCell, yOrg + j * yCell - 1, xOrg + (i + 1) * xCell, yOrg + (j + 1) * yCell + 1); 
/* 212 */             if (j == 0 || mode[i][j - 1] == 2)
/* 213 */               g2.drawLine(xOrg + i * xCell - 1, yOrg + j * yCell, xOrg + (i + 1) * xCell + 1, yOrg + j * yCell); 
/* 214 */             if (j == ySz - 1 || mode[i][j + 1] == 2) {
/* 215 */               g2.drawLine(xOrg + i * xCell - 1, yOrg + (j + 1) * yCell, xOrg + (i + 1) * xCell + 1, yOrg + (j + 1) * yCell);
/*     */             }
/*     */           }  }
/*     */         else
/* 219 */         { g2.drawRect(xOrg - 1, yOrg - 1, xCell * xSz + 2, yCell * ySz + 2); }  } 
/* 220 */     }  g2.setStroke(normalStroke);
/*     */   }
/*     */   
/*     */   static void drawGrid(Graphics2D g2) {
/* 224 */     GridMaintenance.drawGridMtce(g2);
/* 225 */     Def.dispErrors = Boolean.valueOf(false);
/* 226 */     g2.setStroke(new BasicStroke(1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static String loadGrid(String gridName) {
/*     */     String ext;
/* 233 */     if (gridName.length() > 0) {
/* 234 */       ext = gridName.substring(gridName.lastIndexOf('.'));
/*     */     } else {
/* 236 */       return "";
/*     */     } 
/* 238 */     clearGrid();
/*     */ 
/*     */     
/* 241 */     File fl = new File("grids/" + gridName);
/* 242 */     if (!fl.exists() || gridName.length() == 0) {
/* 243 */       fl = new File("grids/");
/* 244 */       String[] s = fl.list(); int i;
/* 245 */       for (i = 0; i < s.length && (
/* 246 */         s[i].lastIndexOf(ext) == -1 || s[i].charAt(0) == '.'); i++);
/*     */       
/* 248 */       gridName = s[i];
/* 249 */       Methods.theFileName = gridName;
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 254 */       DataInputStream dataIn = new DataInputStream(new FileInputStream("grids/" + gridName));
/* 255 */       xSz = dataIn.readInt();
/* 256 */       ySz = dataIn.readInt();
/* 257 */       symmetry = dataIn.readInt();
/* 258 */       for (int j = 0; j < ySz; j++) {
/* 259 */         for (int i = 0; i < xSz; i++)
/* 260 */           mode[i][j] = dataIn.readInt(); 
/* 261 */       }  dataIn.close();
/*     */     }
/* 263 */     catch (IOException exc) {}
/*     */     
/* 265 */     if (Def.puzzleMode != 20)
/* 266 */       NodeList.buildNodeList(); 
/* 267 */     return gridName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static void saveGrid(String fileName) {
/*     */     try {
/* 275 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("grids/" + fileName));
/* 276 */       dataOut.writeInt(xSz);
/* 277 */       dataOut.writeInt(ySz);
/* 278 */       dataOut.writeInt(0);
/* 279 */       for (int j = 0; j < ySz; j++) {
/* 280 */         for (int i = 0; i < xSz; i++)
/* 281 */           dataOut.writeInt(mode[i][j]); 
/* 282 */       }  dataOut.close();
/*     */     }
/* 284 */     catch (IOException exc) {}
/*     */   }
/*     */   
/*     */   static void setSizesAndOffsets(int x, int y, int width, int height) {
/* 288 */     int i = width / xSz;
/* 289 */     int j = height / ySz;
/* 290 */     xCell = yCell = (i < j) ? i : j;
/* 291 */     xOrg = x + (width - xCell * xSz) / 2;
/* 292 */     yOrg = y + (height - yCell * ySz) / 2;
/*     */   }
/*     */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\Grid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */