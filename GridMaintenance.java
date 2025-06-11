/*      */ package crosswordexpress;
/*      */ import java.awt.Graphics2D;
/*      */ import java.awt.event.ActionEvent;
/*      */ import java.io.File;
/*      */ import javax.swing.JButton;
/*      */ import javax.swing.JDialog;
/*      */ import javax.swing.JFileChooser;
/*      */ import javax.swing.JLabel;
/*      */ import javax.swing.JMenuItem;
/*      */ import javax.swing.JTextField;
/*      */ 
/*      */ public class GridMaintenance extends JPanel implements ActionListener {
/*      */   static JFrame jfGridMtce;
/*      */   static JMenuBar menuBar;
/*      */   static JMenuBar editMenuBar;
/*   16 */   JButton[] jbCell = new JButton[15]; static JMenu menu; static JMenu submenu; static JMenuItem menuItem; static JPanel pp;
/*      */   boolean updated = false;
/*      */   static int panelW;
/*      */   static int panelH;
/*   20 */   String gridMtce = "<div>The <b>Grid Maintenance</b> screen provides a set of functions which allow you to create new puzzle Grids (and Templates) and to edit existing ones. Grids of course are the familiar patterns of black and white square cells on which standard crossword puzzles are built. Templates are similar, but do not contain any pattern cells, and are usually constructed in some shape other than rectangular. They are used in conjunction with <b>Free-form</b> puzzles and <b>Word-search</b> puzzles as well as several others.<br/><br/></div><span class='m'>Menu Functions</span><ul><li/><span class='s'>File Menu</span><ul><li/><span>Select a Grid</span><br/>If your intention is to make some changes to one of your existing grids, then you will need to use this option to select the grid in question. The grids which are available for selection are listed in a File Chooser dialog. A single mouse click on any grid in the list will result in that grid being displayed in the Preview panel. Confirm your selection by clicking the <b>Select Grid</b> button.<p/><li/><span>Select a Template</span><br/>This option behaves in the same way as the <b>Select a Grid</b> option, but allows you to select a Template instead.<p/><li/><span>Save</span><br/>When you have completed all of the changes you wish to make to your new (or modified) Grid (or Template), you will use this option to save your work. It will be saved as a Grid if it contains one or more pattern cells. If it contains only active cells and null cells, it will instead be saved as a Template. If you forget to save your work, you will receive a reminder message when you attempt to exit from the Grid Maintenance function.<p/><li/><span>SaveAs</span><br/>Use this option when you have made changes to an existing Grid or Template and want to keep both the new and the old versions. It provides a small dialog in which you can enter a name for the new version. In all other respects, this function is identical to the <b>Save Grid</b> function.<p/><li/><span>Quit Grid Maintenance</span><br/>Use this option to return to the Crossword Express opening screen.</ul><li/><span class='s'>Tasks Menu</span><ul><li/><span>Create a new Grid</span><br/>This option presents you with a dialog in which you can specify the name, size and symmetry of your new grid. As always, the dialog contains a Help button which will provide you with all necessary information.<p/><li/><span>Delete this Grid</span><br/>Use this option to remove Grids or Templates which you no longer require. Naturally you will receive a warning message before the grid is actually deleted.<p/><li/><span>Reflections and Rotations</span><br/>Having designed a grid which you particularly like, you can create variations of it by the processes of Reflection and Rotation. The following five options are available, and are considered to be self explanatory.<ul><li/><span>Left to Right</span><br/><li/><span>Top to Bottom</span><br/><li/><span>Top-left to Bottom-right</span><br/><li/><span>Bottom-left to Top-right</span><br/><li/><span>Rotate 90 degrees</span><br/></ul>If you make use of these functions, it is your responsibility to save the modified grid. If you overlook doing this you will receive a reminder before you attempt to use any other grid maintenance functions.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Grid Statistics</span><br/>This leads you to an information only dialog describing the current state of your grid design. It contains word length totals and counts of the 15 possible cell types.</ul><li/><span class='s'>View Menu</span><ul><li/><span>Grid Maintenance Help</span><br/>This option leads you to the Help which you are currently reading.</ul></ul></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   81 */   String createGrid = "<div>The <b>Create a new Grid</b> button presents you with a dialog which requests the details of the new grid. Four items of information are required:-</div><ul><li/><b>Grid Name :</b> Enter a distinctive name for the new grid or template.<p/><li/><b>Squares across :</b> Enter a number between <b>3</b> and <b>50</b>.<p/><li/><b>Squares down :</b> Enter a number between <b>3</span> and <b>50</b>.<p/><li/><b>Symmetry :</b> Grids can be built using any one of nine varieties of symmetry. These are shown graphically in the dialog, and may be selected by means of radio buttons.</ul><div>Click on the <b>OK</b> button when you are satisfied with what you have entered, or click the <b>Cancel</b> button if you decide not to proceed with the creation process. If you choose to proceed, a blank grid will appear, and you can begin the design process.</div></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   96 */   static String editGrid = "<div>Editing of the grid is assisted by a colored cursor cell. Any editing commands you give are applied to the cursor cell and to its reflections as specified by the selected symmetry. Firstly, you must select the cell you wish to edit. Do this by pointing to it with the mouse and clicking the mouse button once, or you can use the cursor control keys if you prefer. Secondly, you will convert this cell to any one of following 15 different cell types.</div><span class='m'>Cell Types</span><ul><li/><b>Active Cell:</b> As in any crossword system there is of course the active cell into which a letter of the solution will be entered.<p/><li/><b>Pattern Cell:</b> Also there is the standard black pattern cell.<p/><li/><b>Null Cell:</b> This is similar to the black pattern cell, but has the added feature that it will not be printed when the puzzle is sent to the printer, or solved on the computer.<p/><li/><b>Bar Cells:</b> There is a floor bar, a wall bar and a corner bar. These are similar to the standard pattern cell in that they  terminate a word, but they also allow a letter to be inserted into the cell.<p/><li/><b>Tunnel Cells:</b> These are pattern cells which do not contain a letter, but they also do not terminate a word. The word continues on the other side of the tunnel. There are horizontal and vertical tunnels which do not change the direction of a word, and there are four right angle turn cells which change the direction of a word by 90 degrees.<p/><li/><b>Direction Cells:</b> There are three of these, and they are similar to the normal pattern cells, but they also contain arrow symbols indicating a direction. One has a left pointing arrow, one has an upward pointing arrow, and the third contains both left pointing and upward pointing arrows. When a puzzle is being built by Crossword Express, the words which are pointed to by these arrows will be inserted into the puzzle in the <b>Reverse</b> direction. You can use this function in any way you wish, but mainly it is intended for puzzles which are destined to be printed in the <b>Arrow-Word</b> format.</ul><div>There are two ways in which you can set the cell type:-</div><ul><li/>Repeated clicking of the mouse button will cycle you through the available cell types. Certain of the cell types may be skipped if they create an illegal situation. See Error Conditions below for a description of how these errors occur, and how they are handled.<p/><li/>Mouse click one of the Cell Selection buttons on the left hand side of the screen. If you click a selection which is illegal for the cell in question, your request will not be honoured.</ul><span class='m'>Error Conditions</span><div>There are two very important rules which apply to the placement of cell types in a grid:-</div><ul><li/>The cell adjacent to the bar in a bar cell must be either an active cell or another bar cell. ie. It must be a cell into which a letter can be entered.<p/><li/>The <b>mouth</b> of a tunnel cell must open onto a cell which contains a letter, or it can open onto the <b>mouth</b> of another tunnel cell.</ul><div>If you make a request which breaks either of these rules, it will be rejected by Crossword Express.<p/>Two other illegal situations should also be understood.</div><ul><li/>By using tunnel cells, it is possible to create a word in which both ends of the word can only be interpreted as word beginnings or as word endings. Such a situation has no meaning in a crossword puzzle, and Crossword Express can make no use of such a grid to build a puzzle.<p/><li/>It is also possible to build a loop such that the word has no beginning and no end.</ul><div>These conditions are not detected until you make an attempt to save the grid, at which time you will be presented with this help screen which alerts you to the problem.</div></body>";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   GridMaintenance(JFrame jfCWE) {
/*  148 */     Def.puzzleMode = 2;
/*  149 */     Def.dispNullCells = Boolean.valueOf(true); Def.dispCursor = Boolean.valueOf(true);
/*      */     
/*  151 */     jfGridMtce = new JFrame("");
/*  152 */     jfGridMtce.setSize(Op.getInt(Op.MSC.GridW.ordinal(), Op.msc), Op.getInt(Op.MSC.GridH.ordinal(), Op.msc));
/*  153 */     int frameX = (jfCWE.getX() + jfGridMtce.getWidth() > Methods.scrW) ? (Methods.scrW - jfGridMtce.getWidth() - 10) : jfCWE.getX();
/*  154 */     jfGridMtce.setLocation(frameX, jfCWE.getY());
/*  155 */     jfGridMtce.getContentPane().setBackground(Def.COLOR_FRAMEBG);
/*  156 */     jfGridMtce.setLayout((LayoutManager)null);
/*  157 */     jfGridMtce.setDefaultCloseOperation(0);
/*  158 */     jfGridMtce
/*  159 */       .addComponentListener(new ComponentAdapter() {
/*      */           public void componentResized(ComponentEvent ce) {
/*  161 */             int oldw = Op.getInt(Op.MSC.GridW.ordinal(), Op.msc);
/*  162 */             int oldh = Op.getInt(Op.MSC.GridH.ordinal(), Op.msc);
/*  163 */             Methods.frameResize(GridMaintenance.jfGridMtce, oldw, oldh, 870, 733);
/*  164 */             Op.setInt(Op.MSC.GridW.ordinal(), GridMaintenance.jfGridMtce.getWidth(), Op.msc);
/*  165 */             Op.setInt(Op.MSC.GridH.ordinal(), GridMaintenance.jfGridMtce.getHeight(), Op.msc);
/*  166 */             GridMaintenance.restoreFrame();
/*      */           }
/*      */         });
/*      */     
/*  170 */     jfGridMtce
/*  171 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  173 */             GridMaintenance.this.saveChanges();
/*  174 */             Op.saveOptions("miscellaneous.opt", Op.msc);
/*  175 */             CrosswordExpress.jfCWE.setVisible(true);
/*  176 */             GridMaintenance.jfGridMtce.dispose();
/*  177 */             Methods.closeHelp();
/*  178 */             Def.puzzleMode = 1;
/*      */           }
/*      */         });
/*      */     
/*  182 */     Methods.closeHelp();
/*      */ 
/*      */     
/*  185 */     menuBar = new JMenuBar();
/*  186 */     menuBar.setBackground(Def.COLOR_MENUBAR);
/*  187 */     jfGridMtce.setJMenuBar(menuBar);
/*      */     
/*  189 */     menu = new JMenu("File");
/*  190 */     menuBar.add(menu);
/*  191 */     menuItem = new JMenuItem("Select a Grid");
/*  192 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(71, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  193 */     menu.add(menuItem);
/*  194 */     menuItem
/*  195 */       .addActionListener(ae -> {
/*      */           String oldName = Op.msc[Op.MSC.GridName.ordinal()];
/*      */           
/*      */           saveChanges();
/*      */           
/*      */           Def.dispCursor = Boolean.valueOf(false);
/*      */           JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/grids");
/*      */           chooser.setFileFilter(new FileNameExtensionFilter("Grid", new String[] { "grid" }));
/*      */           File fl = new File(Op.msc[Op.MSC.GridName.ordinal()]);
/*      */           chooser.setSelectedFile(fl);
/*      */           chooser.setAccessory(new Preview(chooser));
/*      */           if (chooser.showDialog(jfGridMtce, "Select Grid") == 0) {
/*      */             Op.msc[Op.MSC.GridName.ordinal()] = chooser.getSelectedFile().getName();
/*      */             fl = new File("grids/" + Op.msc[Op.MSC.GridName.ordinal()]);
/*      */             if (!fl.exists()) {
/*      */               JOptionPane.showMessageDialog(jfGridMtce, "There is no file by that name!");
/*      */               Op.msc[Op.MSC.GridName.ordinal()] = oldName;
/*      */             } 
/*      */           } 
/*      */           if (Op.msc[Op.MSC.GridName.ordinal()].length() > 0) {
/*      */             Grid.loadGrid(Op.msc[Op.MSC.GridName.ordinal()]);
/*      */           } else {
/*      */             Grid.clearGrid();
/*      */           } 
/*      */           Grid.setSizesAndOffsets(0, 0, panelW, panelH);
/*      */           Def.dispCursor = Boolean.valueOf(true);
/*      */           restoreFrame();
/*      */         });
/*  223 */     menuItem = new JMenuItem("Select a Template");
/*  224 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(84, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  225 */     menu.add(menuItem);
/*  226 */     menuItem
/*  227 */       .addActionListener(ae -> {
/*      */           String oldName = Op.msc[Op.MSC.TemplateName.ordinal()];
/*      */           
/*      */           saveChanges();
/*      */           
/*      */           Def.dispCursor = Boolean.valueOf(false);
/*      */           JFileChooser chooser = new JFileChooser(System.getProperty("user.dir") + "/grids");
/*      */           chooser.setFileFilter(new FileNameExtensionFilter("Template", new String[] { "template" }));
/*      */           File fl = new File(Op.msc[Op.MSC.TemplateName.ordinal()]);
/*      */           chooser.setSelectedFile(fl);
/*      */           chooser.setAccessory(new Preview(chooser));
/*      */           if (chooser.showDialog(jfGridMtce, "Select Template") == 0) {
/*      */             Op.msc[Op.MSC.TemplateName.ordinal()] = chooser.getSelectedFile().getName();
/*      */             fl = new File("grids/" + Op.msc[Op.MSC.TemplateName.ordinal()]);
/*      */             if (!fl.exists()) {
/*      */               JOptionPane.showMessageDialog(jfGridMtce, "There is no file by that name!");
/*      */               Op.msc[Op.MSC.TemplateName.ordinal()] = oldName;
/*      */             } 
/*      */           } 
/*      */           if (Op.msc[Op.MSC.TemplateName.ordinal()].length() > 0) {
/*      */             Grid.loadGrid(Op.msc[Op.MSC.TemplateName.ordinal()]);
/*      */           } else {
/*      */             Grid.clearGrid();
/*      */           } 
/*      */           Grid.setSizesAndOffsets(0, 0, panelW, panelH);
/*      */           Def.dispCursor = Boolean.valueOf(true);
/*      */           restoreFrame();
/*      */         });
/*  255 */     menuItem = new JMenuItem("Save");
/*  256 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(83, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  257 */     menu.add(menuItem);
/*  258 */     menuItem
/*  259 */       .addActionListener(ae -> {
/*      */           saveGrid(jfGridMtce, Op.msc[Op.MSC.GridName.ordinal()]);
/*      */           
/*      */           this.updated = false;
/*      */           
/*      */           jfGridMtce.requestFocus();
/*      */         });
/*  266 */     menuItem = new JMenuItem("SaveAs");
/*  267 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(65, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  268 */     menu.add(menuItem);
/*  269 */     menuItem
/*  270 */       .addActionListener(ae -> {
/*      */           saveAsGridDialog();
/*      */           
/*      */           jfGridMtce.requestFocus();
/*      */           
/*      */           restoreFrame();
/*      */           this.updated = false;
/*      */         });
/*  278 */     menuItem = new JMenuItem("Quit Grid Maintenance", 81);
/*  279 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(81, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  280 */     menu.add(menuItem);
/*  281 */     menuItem
/*  282 */       .addActionListener(ae -> {
/*      */           saveChanges();
/*      */           
/*      */           Op.saveOptions("miscellaneous.opt", Op.msc);
/*      */           
/*      */           CrosswordExpress.jfCWE.setVisible(true);
/*      */           
/*      */           jfGridMtce.dispose();
/*      */           Methods.closeHelp();
/*      */           Def.puzzleMode = 1;
/*      */         });
/*  293 */     menu = new JMenu("Tasks");
/*  294 */     menuBar.add(menu);
/*  295 */     menuItem = new JMenuItem("Create a new Grid");
/*  296 */     menu.add(menuItem);
/*  297 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(67, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  298 */     menuItem
/*  299 */       .addActionListener(ae -> {
/*      */           saveChanges();
/*      */           
/*      */           newGridDialog();
/*      */           
/*      */           Grid.setSizesAndOffsets(0, 0, panelW, panelH);
/*      */           restoreFrame();
/*      */         });
/*  307 */     menuItem = new JMenuItem("Delete this Grid");
/*  308 */     menu.add(menuItem);
/*  309 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(68, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  310 */     menuItem
/*  311 */       .addActionListener(ae -> {
/*      */           int resp = JOptionPane.showConfirmDialog(jfGridMtce, "<html>Delete the Grid <font color=444488 size=3>" + Op.msc[Op.MSC.GridName.ordinal()] + "</font>?", "Delete a Grid", 0);
/*      */           
/*      */           if (resp == 0) {
/*      */             File fl = new File("grids/" + Op.msc[Op.MSC.GridName.ordinal()]);
/*      */             
/*      */             fl.delete();
/*      */             
/*      */             fl = new File("grids");
/*      */             
/*      */             String[] s = fl.list();
/*      */             for (String item : s) {
/*      */               if (item.lastIndexOf(".grid") != -1 && item.charAt(0) != '.') {
/*      */                 Grid.loadGrid(item);
/*      */                 Op.msc[Op.MSC.GridName.ordinal()] = item;
/*      */                 Grid.setSizesAndOffsets(0, 0, panelW, panelH);
/*      */                 break;
/*      */               } 
/*      */             } 
/*      */           } 
/*      */           restoreFrame();
/*      */         });
/*  333 */     menu.addSeparator();
/*  334 */     submenu = new JMenu("Reflections and Rotations");
/*      */     
/*  336 */     menuItem = new JMenuItem("Left to Right");
/*  337 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(76, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  338 */     submenu.add(menuItem);
/*  339 */     menuItem
/*  340 */       .addActionListener(ae -> rotationAndReflection(0));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  345 */     menuItem = new JMenuItem("Top to Bottom");
/*  346 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(79, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  347 */     submenu.add(menuItem);
/*  348 */     menuItem
/*  349 */       .addActionListener(ae -> rotationAndReflection(1));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  354 */     menuItem = new JMenuItem("Top-Left to Bottom-Right");
/*  355 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(80, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  356 */     submenu.add(menuItem);
/*  357 */     menuItem
/*  358 */       .addActionListener(ae -> rotationAndReflection(2));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  363 */     menuItem = new JMenuItem("Bottom-Left to Top-Right");
/*  364 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(66, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  365 */     submenu.add(menuItem);
/*  366 */     menuItem
/*  367 */       .addActionListener(ae -> rotationAndReflection(3));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  372 */     menuItem = new JMenuItem("Rotate 90 degrees");
/*  373 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(82, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  374 */     submenu.add(menuItem);
/*  375 */     menuItem
/*  376 */       .addActionListener(ae -> {
/*      */           rotationAndReflection(0);
/*      */           
/*      */           rotationAndReflection(2);
/*      */         });
/*      */     
/*  382 */     menu.add(submenu);
/*      */ 
/*      */     
/*  385 */     menu = new JMenu("View");
/*  386 */     menuBar.add(menu);
/*  387 */     menuItem = new JMenuItem("View Grid Statistics");
/*  388 */     menu.add(menuItem);
/*  389 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(86, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  390 */     menuItem
/*  391 */       .addActionListener(ae -> {
/*      */           gridStatisticsDialog();
/*      */           
/*      */           jfGridMtce.requestFocus();
/*      */           
/*      */           restoreFrame();
/*      */         });
/*      */     
/*  399 */     menu = new JMenu("Help");
/*  400 */     menuBar.add(menu);
/*  401 */     menuItem = new JMenuItem("Grid Maintenance Help");
/*  402 */     menu.add(menuItem);
/*  403 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  404 */     menuItem
/*  405 */       .addActionListener(ae -> {
/*      */           Methods.closeHelp();
/*      */           
/*      */           Methods.cweHelp(jfGridMtce, null, "Grid Maintenance", this.gridMtce);
/*      */         });
/*      */     
/*  411 */     menuItem = new JMenuItem("Editing a Grid");
/*  412 */     menu.add(menuItem);
/*  413 */     menuItem.setAccelerator(KeyStroke.getKeyStroke(72, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
/*  414 */     menuItem
/*  415 */       .addActionListener(ae -> {
/*      */           Methods.closeHelp();
/*      */ 
/*      */           
/*      */           Methods.cweHelp(jfGridMtce, null, "Editing a Grid", editGrid);
/*      */         });
/*      */     
/*  422 */     JLabel jl = new JLabel("<html><font color=006644 size=3>Cell Type Selection");
/*  423 */     jl.setSize(168, 16);
/*  424 */     jl.setLocation(10, 10);
/*  425 */     jl.setHorizontalAlignment(0);
/*  426 */     jfGridMtce.add(jl);
/*      */     
/*  428 */     for (int j = 0; j < 15; j++) {
/*  429 */       ImageIcon myIcon = new ImageIcon("graphics/cellmode" + j + ".png");
/*  430 */       this.jbCell[j] = new JButton(myIcon);
/*  431 */       this.jbCell[j].setSize(44, 44);
/*  432 */       this.jbCell[j].setLocation(10 + 56 * j % 3, 30 + 51 * j / 3);
/*  433 */       this.jbCell[j].setActionCommand("" + j);
/*  434 */       this.jbCell[j].addActionListener(this);
/*  435 */       jfGridMtce.add(this.jbCell[j]);
/*      */     } 
/*      */     
/*  438 */     Grid.clearGrid();
/*  439 */     Op.msc[Op.MSC.GridName.ordinal()] = Grid.loadGrid(Op.msc[Op.MSC.GridName.ordinal()]);
/*      */     
/*  441 */     pp = new GridMaintenancePP(180, 0);
/*  442 */     jfGridMtce.add(pp);
/*      */     
/*  444 */     pp
/*  445 */       .addMouseListener(new MouseAdapter() {
/*      */           public void mousePressed(MouseEvent e) {
/*  447 */             GridMaintenance.this.updateGrid(e);
/*  448 */             GridMaintenance.pp.repaint();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  453 */     pp
/*  454 */       .addMouseMotionListener(new MouseAdapter() {
/*      */           public void mouseMoved(MouseEvent e) {
/*  456 */             if (Def.isMac) {
/*  457 */               GridMaintenance.jfGridMtce.setResizable((GridMaintenance.jfGridMtce.getWidth() - e.getX() < 195 && GridMaintenance.jfGridMtce
/*  458 */                   .getHeight() - e.getY() < 58));
/*      */             }
/*      */           }
/*      */         });
/*      */     
/*  463 */     jfGridMtce
/*  464 */       .addKeyListener(new KeyAdapter() {
/*      */           public void keyPressed(KeyEvent e) {
/*  466 */             GridMaintenance.this.handleKeyPressed(e);
/*  467 */             GridMaintenance.pp.repaint();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  472 */     restoreFrame();
/*      */   }
/*      */   
/*      */   public void actionPerformed(ActionEvent e) {
/*  476 */     int oldX = Grid.xCur, oldY = Grid.yCur;
/*      */     
/*  478 */     for (int i = 0; i < 15; i++) {
/*  479 */       if (e.getActionCommand().equals("" + i)) {
/*  480 */         int previous = Grid.mode[Grid.xCur][Grid.yCur];
/*  481 */         Grid.mode[Grid.xCur][Grid.yCur] = i;
/*  482 */         if (validCellType(Grid.xCur, Grid.yCur)) {
/*  483 */           if (Grid.symmetry != 0) {
/*  484 */             symmetryUpdate(Grid.xCur, Grid.yCur, previous, Grid.mode[Grid.xCur][Grid.yCur]);
/*      */           }
/*      */         } else {
/*  487 */           Grid.mode[Grid.xCur][Grid.yCur] = previous;
/*      */           return;
/*      */         } 
/*  490 */         this.updated = true;
/*  491 */         NodeList.buildNodeList();
/*  492 */         Grid.xCur = oldX; Grid.yCur = oldY;
/*  493 */         pp.repaint();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   static void restoreFrame() {
/*  498 */     jfGridMtce.setVisible(true);
/*  499 */     Insets insets = jfGridMtce.getInsets();
/*  500 */     panelW = jfGridMtce.getWidth() - insets.left + insets.right + 180;
/*  501 */     panelH = jfGridMtce.getHeight() - insets.top + insets.bottom + menuBar.getHeight();
/*  502 */     pp.setSize(panelW, panelH);
/*  503 */     setSizesAndOffsets(0, 0, panelW, panelH, 20);
/*  504 */     jfGridMtce.requestFocusInWindow();
/*  505 */     jfGridMtce.setTitle("Grid Maintenance : " + Op.msc[Op.MSC.GridName.ordinal()]);
/*  506 */     pp.repaint();
/*      */   }
/*      */   
/*      */   static void setSizesAndOffsets(int x, int y, int width, int height, int inset) {
/*  510 */     int i = (width - inset) / Grid.xSz;
/*  511 */     int j = (height - inset) / Grid.ySz;
/*  512 */     Grid.xCell = Grid.yCell = (i < j) ? i : j;
/*  513 */     Grid.xOrg = 10;
/*  514 */     Grid.yOrg = 10;
/*      */   }
/*      */ 
/*      */   
/*      */   static void saveGrid(JFrame jf, String gridName) {
/*  519 */     boolean template = true;
/*      */     
/*  521 */     NodeList.buildNodeList();
/*      */     
/*      */     int j;
/*  524 */     for (j = 0; j < Grid.ySz; j++) {
/*  525 */       for (int i = 0; i < Grid.xSz; i++) {
/*  526 */         if (Grid.control[i][j] != 0) {
/*  527 */           final JDialog jdlgErrorMsg = new JDialog(jf, "Grid Error", true);
/*  528 */           jdlgErrorMsg.setSize(250, 150);
/*  529 */           jdlgErrorMsg.setResizable(false);
/*  530 */           jdlgErrorMsg.setLayout((LayoutManager)null);
/*  531 */           jdlgErrorMsg.setLocation(jf.getX(), jf.getY());
/*      */           
/*  533 */           JLabel jl = new JLabel("<html>Your grid contains design errors.<br>Click Help for more information.");
/*  534 */           jl.setForeground(Def.COLOR_LABEL);
/*  535 */           jl.setSize(240, 50);
/*  536 */           jl.setLocation(10, 0);
/*  537 */           jl.setHorizontalAlignment(2);
/*  538 */           jdlgErrorMsg.add(jl);
/*      */           
/*  540 */           Action doOK = new AbstractAction("OK") {
/*      */               public void actionPerformed(ActionEvent e) {
/*  542 */                 jdlgErrorMsg.dispose();
/*  543 */                 Methods.closeHelp();
/*      */               }
/*      */             };
/*  546 */           JButton jbOK = Methods.newButton("doOK", doOK, 79, 10, 50, 70, 26);
/*  547 */           jdlgErrorMsg.add(jbOK);
/*      */           
/*  549 */           Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */               public void actionPerformed(ActionEvent e) {
/*  551 */                 Methods.closeHelp();
/*  552 */                 Methods.cweHelp(null, jdlgErrorMsg, "Editing a Grid", GridMaintenance.editGrid);
/*      */               }
/*      */             };
/*  555 */           JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 90, 50, 150, 60);
/*  556 */           jdlgErrorMsg.add(jbHelp);
/*      */           
/*  558 */           Methods.setDialogSize(jdlgErrorMsg, 250, 120);
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*  563 */     for (j = 0; j < Grid.ySz; j++) {
/*  564 */       for (int i = 0; i < Grid.xSz; i++)
/*  565 */       { if (Grid.mode[i][j] != 0 && Grid.mode[i][j] != 2)
/*  566 */           template = false;  } 
/*  567 */     }  gridName = gridName.substring(0, gridName.lastIndexOf('.')) + (template ? ".template" : ".grid");
/*  568 */     if (template) { Op.msc[Op.MSC.TemplateName.ordinal()] = gridName; }
/*  569 */     else { Op.msc[Op.MSC.GridName.ordinal()] = gridName; }
/*      */ 
/*      */     
/*      */     try {
/*  573 */       DataOutputStream dataOut = new DataOutputStream(new FileOutputStream("grids/" + gridName));
/*  574 */       dataOut.writeInt(Grid.xSz);
/*  575 */       dataOut.writeInt(Grid.ySz);
/*  576 */       dataOut.writeInt(Grid.symmetry);
/*  577 */       for (j = 0; j < Grid.ySz; j++) {
/*  578 */         for (int i = 0; i < Grid.xSz; i++)
/*  579 */           dataOut.writeInt(Grid.mode[i][j]); 
/*  580 */       }  dataOut.close();
/*      */     }
/*  582 */     catch (IOException exc) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void drawGridMtce(Graphics2D g2) {
/*  589 */     int nL = (int)Math.ceil((Grid.xCell / 60.0F)), wL = (int)Math.ceil((Grid.xCell / 10.0F));
/*  590 */     Stroke normalStroke = new BasicStroke(nL, 1, 0);
/*  591 */     Stroke wideStroke = new BasicStroke(wL, 0, 0);
/*  592 */     g2.setStroke(normalStroke);
/*      */     
/*  594 */     RenderingHints rh = g2.getRenderingHints();
/*  595 */     rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
/*  596 */     g2.setRenderingHints(rh);
/*      */     
/*      */     int j;
/*  599 */     for (j = 0; j < Grid.ySz; j++) {
/*  600 */       for (int k = 0; k < Grid.xSz; k++) {
/*  601 */         if (Grid.mode[k][j] != 2) {
/*      */           
/*  603 */           g2.setColor((Grid.sol[k][j] != 0 && Grid.sol[k][j] != Grid.letter[k][j] && Def.dispErrors.booleanValue()) ? Def.COLOR_ERROR : new Color(Grid.color[k][j]));
/*  604 */           g2.fillRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */         } 
/*      */       } 
/*  607 */     }  Grid.drawPatternCells(g2, Def.COLOR_BLACK, Def.COLOR_WHITE, true);
/*      */ 
/*      */     
/*  610 */     g2.setColor(Def.COLOR_BLACK);
/*  611 */     for (j = 0; j < Grid.ySz; j++) {
/*  612 */       for (int k = 0; k < Grid.xSz; k++) {
/*  613 */         g2.drawRect(Grid.xOrg + k * Grid.xCell, Grid.yOrg + j * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */       }
/*      */     } 
/*  616 */     g2.setStroke(wideStroke);
/*  617 */     Grid.drawBars(g2);
/*      */     
/*  619 */     Grid.drawOutline(g2, false);
/*      */ 
/*      */     
/*  622 */     if (Def.dispCursor.booleanValue()) {
/*  623 */       g2.setColor(Def.COLOR_RED);
/*  624 */       g2.setStroke(wideStroke);
/*  625 */       g2.drawRect(Grid.xOrg + Grid.xCur * Grid.xCell, Grid.yOrg + Grid.yCur * Grid.yCell, Grid.xCell, Grid.yCell);
/*      */     } 
/*      */ 
/*      */     
/*  629 */     g2.setFont(new Font("SansSerif", 0, Grid.yCell / 3));
/*  630 */     g2.setColor(Def.COLOR_BLACK);
/*  631 */     FontMetrics fm = g2.getFontMetrics();
/*  632 */     for (int i = 0; NodeList.nodeList[i] != null; i++) {
/*  633 */       g2.drawString("" + (NodeList.nodeList[i]).id, Grid.xOrg + (NodeList.nodeList[i]).x * Grid.xCell + ((Grid.xCell / 20 > 1) ? (Grid.xCell / 20) : 1), Grid.yOrg + (NodeList.nodeList[i]).y * Grid.yCell + fm
/*  634 */           .getAscent());
/*      */     }
/*  636 */     g2.setStroke(new BasicStroke(1.0F));
/*      */   }
/*      */   
/*      */   private void saveChanges() {
/*  640 */     if (!this.updated)
/*  641 */       return;  if (JOptionPane.showConfirmDialog(jfGridMtce, "<html>Changes have been made to this grid.<br>Do you want to save them first?", "Warning!", 0) == 0)
/*  642 */       saveGrid(jfGridMtce, Op.msc[Op.MSC.GridName.ordinal()]); 
/*  643 */     this.updated = false;
/*      */   }
/*      */   
/*      */   private void rotationAndReflection(int mode) {
/*  647 */     int i = 1;
/*      */     
/*  649 */     if (Grid.xSz != Grid.ySz) i = 0;  int y;
/*  650 */     for (y = 0; y < Grid.ySz; y++) {
/*  651 */       for (int x = 0; x < Grid.xSz; x++)
/*  652 */       { if (Grid.mode[x][y] > 2)
/*  653 */           i = 0;  } 
/*  654 */     }  if (i == 0) {
/*  655 */       JOptionPane.showMessageDialog(jfGridMtce, "<html>Rotations and Reflections can only be applied to<br>square grids which do not use bars or tunnels.");
/*      */       
/*      */       return;
/*      */     } 
/*  659 */     switch (mode) {
/*      */       case 0:
/*  661 */         for (y = 0; y < Grid.ySz; y++) {
/*  662 */           for (int x = 0; x < Grid.xSz / 2; x++) {
/*  663 */             i = Grid.mode[x][y];
/*  664 */             Grid.mode[x][y] = Grid.mode[Grid.xSz - 1 - x][y];
/*  665 */             Grid.mode[Grid.xSz - 1 - x][y] = i;
/*      */           } 
/*      */         }  break;
/*      */       case 1:
/*  669 */         for (y = 0; y < Grid.ySz / 2; y++) {
/*  670 */           for (int x = 0; x < Grid.xSz; x++) {
/*  671 */             i = Grid.mode[x][y];
/*  672 */             Grid.mode[x][y] = Grid.mode[x][Grid.ySz - 1 - y];
/*  673 */             Grid.mode[x][Grid.ySz - 1 - y] = i;
/*      */           } 
/*      */         }  break;
/*      */       case 2:
/*  677 */         for (y = 0; y < Grid.ySz - 1; y++) {
/*  678 */           for (int x = 0; x < Grid.xSz - 1 - y; x++) {
/*  679 */             i = Grid.mode[x][y];
/*  680 */             Grid.mode[x][y] = Grid.mode[Grid.xSz - 1 - y][Grid.ySz - 1 - x];
/*  681 */             Grid.mode[Grid.xSz - 1 - y][Grid.ySz - 1 - x] = i;
/*      */           } 
/*      */         }  break;
/*      */       case 3:
/*  685 */         for (y = 1; y < Grid.ySz - 1; y++) {
/*  686 */           for (int x = 0; x < y; x++) {
/*  687 */             i = Grid.mode[x][y];
/*  688 */             Grid.mode[x][y] = Grid.mode[y][x];
/*  689 */             Grid.mode[y][x] = i;
/*      */           } 
/*      */         }  break;
/*      */       case 4:
/*  693 */         for (y = 1; y < Grid.ySz - 1; y++) {
/*  694 */           for (int x = 0; x < y; x++) {
/*  695 */             i = Grid.mode[x][y];
/*  696 */             Grid.mode[x][y] = Grid.mode[y][x];
/*  697 */             Grid.mode[y][x] = i;
/*      */           } 
/*      */         }  break;
/*      */     } 
/*  701 */     NodeList.buildNodeList();
/*  702 */     restoreFrame();
/*  703 */     this.updated = true;
/*      */   }
/*      */   
/*      */   private void newGridDialog() {
/*  707 */     final JRadioButton[] jrbSym = new JRadioButton[10];
/*      */     
/*  709 */     final JDialog jdlgNewGrid = new JDialog(jfGridMtce, "Create a new Grid", true);
/*  710 */     jdlgNewGrid.setSize(400, 465);
/*  711 */     jdlgNewGrid.setResizable(false);
/*  712 */     jdlgNewGrid.setLayout((LayoutManager)null);
/*  713 */     jdlgNewGrid.setLocation(jfGridMtce.getX(), jfGridMtce.getY());
/*      */     
/*  715 */     jdlgNewGrid
/*  716 */       .addWindowListener(new WindowAdapter() {
/*      */           public void windowClosing(WindowEvent we) {
/*  718 */             Methods.closeHelp();
/*      */           }
/*      */         });
/*      */     
/*  722 */     Methods.closeHelp();
/*      */     
/*  724 */     JLabel jlName = new JLabel("Grid Name:");
/*  725 */     jlName.setForeground(Def.COLOR_LABEL);
/*  726 */     jlName.setSize(100, 20);
/*  727 */     jlName.setLocation(15, 5);
/*  728 */     jlName.setHorizontalAlignment(4);
/*  729 */     jdlgNewGrid.add(jlName);
/*      */     
/*  731 */     final JTextField jtfGridName = new JTextField("untitled", 15);
/*  732 */     jtfGridName.setSize(100, 20);
/*  733 */     jtfGridName.setLocation(125, 5);
/*  734 */     jtfGridName.selectAll();
/*  735 */     jtfGridName.setHorizontalAlignment(2);
/*  736 */     jdlgNewGrid.add(jtfGridName);
/*      */     
/*  738 */     jtfGridName.setFont(new Font("SansSerif", 1, 13));
/*      */     
/*  740 */     JLabel jlAcross = new JLabel("Cells Across:");
/*  741 */     jlAcross.setForeground(Def.COLOR_LABEL);
/*  742 */     jlAcross.setSize(100, 20);
/*  743 */     jlAcross.setLocation(15, 30);
/*  744 */     jlAcross.setHorizontalAlignment(4);
/*  745 */     jdlgNewGrid.add(jlAcross);
/*      */     
/*  747 */     final JTextField jtfCellsAcross = new JTextField("13", 4);
/*  748 */     jtfCellsAcross.setSize(50, 20);
/*  749 */     jtfCellsAcross.setLocation(125, 30);
/*  750 */     jtfCellsAcross.selectAll();
/*  751 */     jtfCellsAcross.setHorizontalAlignment(2);
/*  752 */     jdlgNewGrid.add(jtfCellsAcross);
/*      */     
/*  754 */     jtfCellsAcross.setFont(new Font("SansSerif", 1, 13));
/*      */     
/*  756 */     JLabel jlDown = new JLabel("Cells Down:");
/*  757 */     jlDown.setForeground(Def.COLOR_LABEL);
/*  758 */     jlDown.setSize(100, 20);
/*  759 */     jlDown.setLocation(15, 55);
/*  760 */     jlDown.setHorizontalAlignment(4);
/*  761 */     jdlgNewGrid.add(jlDown);
/*      */     
/*  763 */     final JTextField jtfCellsDown = new JTextField("13", 4);
/*  764 */     jtfCellsDown.setSize(50, 20);
/*  765 */     jtfCellsDown.setLocation(125, 55);
/*  766 */     jtfCellsDown.selectAll();
/*  767 */     jtfCellsDown.setHorizontalAlignment(2);
/*  768 */     jdlgNewGrid.add(jtfCellsDown);
/*      */     
/*  770 */     jtfCellsDown.setFont(new Font("SansSerif", 1, 13));
/*      */     
/*  772 */     JLabel jlSym = new JLabel("Grid Symmetry Selection:");
/*  773 */     jlSym.setForeground(Def.COLOR_LABEL);
/*  774 */     jlSym.setSize(200, 20);
/*  775 */     jlSym.setLocation(15, 80);
/*  776 */     jlSym.setHorizontalAlignment(2);
/*  777 */     jdlgNewGrid.add(jlSym);
/*      */     
/*  779 */     ButtonGroup bg = new ButtonGroup();
/*  780 */     for (int i = 0; i < 9; i++) {
/*  781 */       ImageIcon theIcon = new ImageIcon("graphics/sym" + i + ".png");
/*  782 */       JLabel jl1 = new JLabel(theIcon);
/*  783 */       jl1.setSize(80, 80);
/*  784 */       jl1.setLocation(40 + 115 * i % 3, 100 + 80 * i / 3);
/*  785 */       jdlgNewGrid.add(jl1);
/*  786 */       jrbSym[i] = new JRadioButton("" + i);
/*  787 */       jrbSym[i].setOpaque(false);
/*  788 */       jrbSym[i].setForeground(Def.COLOR_LABEL);
/*  789 */       jrbSym[i].setSize(40, 20);
/*  790 */       jrbSym[i].setLocation(10 + 115 * i % 3, 105 + 80 * i / 3);
/*  791 */       jrbSym[i].setHorizontalAlignment(2);
/*  792 */       jdlgNewGrid.add(jrbSym[i]);
/*  793 */       if (bg != null) bg.add(jrbSym[i]); 
/*      */     } 
/*  795 */     jrbSym[Grid.symmetry].setSelected(true);
/*      */     
/*  797 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  799 */           for (int symmetry = 0; symmetry < 9; symmetry++) {
/*  800 */             if (jrbSym[symmetry].isSelected()) {
/*  801 */               String s = jtfGridName.getText() + ".grid";
/*  802 */               File fl = new File("grids/" + s);
/*  803 */               if (fl.exists()) {
/*  804 */                 int response = JOptionPane.showConfirmDialog(jdlgNewGrid, "<html>The grid <font color=AA0000 size=4>" + s + "</font> already exists.<br>Continue anyway?", "Warning!", 0);
/*      */ 
/*      */                 
/*  807 */                 if (response == 1)
/*      */                   break; 
/*      */               } 
/*  810 */               Op.msc[Op.MSC.GridName.ordinal()] = s;
/*  811 */               int x1 = Integer.parseInt(jtfCellsAcross.getText());
/*  812 */               int y1 = Integer.parseInt(jtfCellsDown.getText());
/*  813 */               Grid.symmetry = symmetry;
/*  814 */               Grid.xSz = x1;
/*  815 */               Grid.ySz = y1;
/*  816 */               for (int j = 0; j < Grid.ySz; j++) {
/*  817 */                 for (int i = 0; i < Grid.xSz; i++)
/*  818 */                   Grid.mode[i][j] = 0; 
/*  819 */               }  NodeList.buildNodeList();
/*      */               break;
/*      */             } 
/*      */           } 
/*  823 */           jdlgNewGrid.setVisible(false);
/*  824 */           Methods.closeHelp();
/*      */         }
/*      */       };
/*  827 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 43, 360, 100, 26);
/*  828 */     jdlgNewGrid.add(jbOK);
/*      */     
/*  830 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  832 */           jdlgNewGrid.dispose();
/*  833 */           Methods.closeHelp();
/*  834 */           Grid.loadGrid(Op.msc[Op.MSC.GridName.ordinal()]);
/*      */         }
/*      */       };
/*  837 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 43, 395, 100, 26);
/*  838 */     jdlgNewGrid.add(jbCancel);
/*      */     
/*  840 */     Action doHelp = new AbstractAction("<html><font size=6 color=BB0000 face=Serif>Help ", new ImageIcon("graphics/help.png")) {
/*      */         public void actionPerformed(ActionEvent e) {
/*  842 */           Methods.cweHelp(null, jdlgNewGrid, "Grid Creation", GridMaintenance.this.createGrid);
/*      */         }
/*      */       };
/*  845 */     JButton jbHelp = Methods.newButton("doHelp", doHelp, 72, 153, 360, 165, 61);
/*  846 */     jdlgNewGrid.add(jbHelp);
/*      */     
/*  848 */     jdlgNewGrid.getRootPane().setDefaultButton(jbOK);
/*  849 */     Methods.setDialogSize(jdlgNewGrid, 360, 431);
/*      */   }
/*      */   
/*      */   private void saveAsGridDialog() {
/*  853 */     final JDialog jdlgSaveAs = new JDialog(jfGridMtce, "Save As", true);
/*  854 */     jdlgSaveAs.setSize(235, 100);
/*  855 */     jdlgSaveAs.setResizable(false);
/*  856 */     jdlgSaveAs.setLayout((LayoutManager)null);
/*  857 */     jdlgSaveAs.setLocation(jfGridMtce.getX(), jfGridMtce.getY());
/*      */     
/*  859 */     JLabel jlGridName = new JLabel("Grid Name:");
/*  860 */     jlGridName.setForeground(Def.COLOR_LABEL);
/*  861 */     jlGridName.setSize(80, 20);
/*  862 */     jlGridName.setLocation(15, 5);
/*  863 */     jlGridName.setHorizontalAlignment(4);
/*  864 */     jdlgSaveAs.add(jlGridName);
/*      */     
/*  866 */     final JTextField jtfGridName = new JTextField("untitled", 15);
/*  867 */     jtfGridName.setSize(115, 22);
/*  868 */     jtfGridName.setLocation(105, 5);
/*  869 */     jtfGridName.selectAll();
/*  870 */     jtfGridName.setHorizontalAlignment(2);
/*  871 */     jdlgSaveAs.add(jtfGridName);
/*  872 */     jtfGridName.setFont(new Font("SansSerif", 1, 13));
/*      */     
/*  874 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  876 */           String s = jtfGridName.getText() + ".grid";
/*  877 */           File fl = new File("grids/" + s);
/*  878 */           int response = 0;
/*  879 */           if (fl.exists()) {
/*  880 */             response = JOptionPane.showConfirmDialog(jdlgSaveAs, "<html>The grid <font color=AA0000 size=4>" + s + "</font> already exists.<br>Continue anyway?", "Warning!", 0);
/*      */           }
/*      */           
/*  883 */           if (response == 0) {
/*  884 */             GridMaintenance.saveGrid(GridMaintenance.jfGridMtce, Op.msc[Op.MSC.GridName.ordinal()] = s);
/*  885 */             GridMaintenance.this.updated = false;
/*      */           } 
/*  887 */           jdlgSaveAs.dispose();
/*      */         }
/*      */       };
/*  890 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 10, 35, 100, 26);
/*  891 */     jdlgSaveAs.add(jbOK);
/*      */     
/*  893 */     Action doCancel = new AbstractAction("Cancel") {
/*      */         public void actionPerformed(ActionEvent e) {
/*  895 */           jdlgSaveAs.dispose();
/*      */         }
/*      */       };
/*  898 */     JButton jbCancel = Methods.newButton("doCancel", doCancel, 67, 120, 35, 100, 26);
/*  899 */     jdlgSaveAs.add(jbCancel);
/*      */     
/*  901 */     jdlgSaveAs.getRootPane().setDefaultButton(jbOK);
/*  902 */     Methods.setDialogSize(jdlgSaveAs, 230, 71);
/*      */   }
/*      */   
/*      */   private void gridStatisticsDialog() {
/*  906 */     int[] cellCount = new int[15];
/*  907 */     int[] acr = new int[23], dwn = new int[23];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  912 */     final JDialog jdlgGridStatistics = new JDialog(jfGridMtce, "Grid Statistics", true);
/*  913 */     jdlgGridStatistics.setSize(425, 510);
/*  914 */     jdlgGridStatistics.setResizable(false);
/*  915 */     jdlgGridStatistics.setLayout((LayoutManager)null);
/*  916 */     jdlgGridStatistics.setLocation(jfGridMtce.getX(), jfGridMtce.getY());
/*      */ 
/*      */     
/*  919 */     JLabel jl = new JLabel("<html><font size=5>WORD COUNTS"); jl.setForeground(Def.COLOR_LABEL); jl.setSize(200, 20); jl.setLocation(15, 5); jl.setHorizontalAlignment(2); jdlgGridStatistics.add(jl);
/*  920 */     jl = new JLabel("<html><font size=4>Length"); jl.setForeground(Def.COLOR_LABEL); jl.setSize(50, 20); jl.setLocation(15, 25); jl.setHorizontalAlignment(2); jdlgGridStatistics.add(jl);
/*  921 */     jl = new JLabel("<html><font size=4>Across"); jl.setForeground(Def.COLOR_LABEL); jl.setSize(50, 20); jl.setLocation(67, 25); jl.setHorizontalAlignment(2); jdlgGridStatistics.add(jl);
/*  922 */     jl = new JLabel("<html><font size=4>Down"); jl.setForeground(Def.COLOR_LABEL); jl.setSize(50, 20); jl.setLocation(119, 25); jl.setHorizontalAlignment(2); jdlgGridStatistics.add(jl);
/*  923 */     jl = new JLabel("<html><font size=5>CELL COUNTS"); jl.setForeground(Def.COLOR_LABEL); jl.setSize(200, 20); jl.setLocation(228, 5); jl.setHorizontalAlignment(2); jdlgGridStatistics.add(jl);
/*  924 */     jl = new JLabel("<html><font size=4>Cells Across:&nbsp;" + Grid.xSz); jl.setForeground(Def.COLOR_LABEL); jl.setSize(200, 20); jl.setLocation(220, 260); jl.setHorizontalAlignment(2); jdlgGridStatistics.add(jl);
/*  925 */     jl = new JLabel("<html><font size=4>Cells Down:&nbsp;&nbsp;" + Grid.ySz); jl.setForeground(Def.COLOR_LABEL); jl.setSize(200, 20); jl.setLocation(220, 280); jl.setHorizontalAlignment(2); jdlgGridStatistics.add(jl);
/*      */     int cells, links, k;
/*  927 */     for (cells = 0, links = 0, k = 0; k < Grid.ySz; k++) {
/*  928 */       for (int n = 0; n < Grid.xSz; n++) {
/*  929 */         int sig = NodeList.cellSignature(n, k);
/*  930 */         if ((sig & 0x10) == 16) {
/*  931 */           cells++;
/*  932 */           if ((sig & 0x5) != 5 && (sig & 0xA) != 10)
/*      */           {
/*  934 */             links++; } 
/*      */         } 
/*      */       } 
/*      */     } 
/*  938 */     JLabel jl3 = new JLabel("<html><font size=4>Linked Letters: &nbsp; " + (100 * links / cells) + "%");
/*  939 */     jl3.setForeground(Def.COLOR_LABEL);
/*  940 */     jl3.setSize(300, 20);
/*  941 */     jl3.setLocation(220, 300);
/*  942 */     jl3.setHorizontalAlignment(2);
/*  943 */     jdlgGridStatistics.add(jl3);
/*      */     
/*  945 */     jl3 = new JLabel("<html><font size=4>Symmetry:");
/*  946 */     jl3.setForeground(Def.COLOR_LABEL);
/*  947 */     jl3.setSize(200, 20);
/*  948 */     jl3.setLocation(220, 350);
/*  949 */     jl3.setHorizontalAlignment(2);
/*  950 */     jdlgGridStatistics.add(jl3);
/*      */     
/*  952 */     ImageIcon theIcon = new ImageIcon("graphics/sym" + Grid.symmetry + ".png");
/*  953 */     JLabel jl1 = new JLabel(theIcon);
/*  954 */     jl1.setSize(80, 80);
/*  955 */     jl1.setLocation(300, 320);
/*  956 */     jdlgGridStatistics.add(jl1);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  961 */     int xMem = Grid.xCur, yMem = Grid.yCur;
/*  962 */     NodeList.buildNodeList();
/*  963 */     Grid.xCur = xMem; Grid.yCur = yMem; int m;
/*  964 */     for (m = 0; NodeList.nodeList[m] != null; m++) {
/*  965 */       int thisLength = (NodeList.nodeList[m]).length;
/*  966 */       if (thisLength > 21) thisLength = 21; 
/*  967 */       if ((NodeList.nodeList[m]).direction == 0) {
/*  968 */         acr[thisLength] = acr[thisLength] + 1;
/*  969 */         acr[22] = acr[22] + 1;
/*      */       } else {
/*      */         
/*  972 */         dwn[thisLength] = dwn[thisLength] + 1;
/*  973 */         dwn[22] = dwn[22] + 1;
/*      */       } 
/*      */     } 
/*  976 */     for (m = 2; m < 23; m++) {
/*  977 */       JLabel jl2 = new JLabel((m < 21) ? ("" + m) : ((m == 21) ? "20+" : "Total"));
/*  978 */       jl2.setSize(45, 15);
/*  979 */       jl2.setLocation(7, 5 + m * 20);
/*  980 */       jl2.setHorizontalAlignment(4);
/*  981 */       jdlgGridStatistics.add(jl2);
/*      */       
/*  983 */       jl2 = new JLabel("" + acr[m]);
/*  984 */       jl2.setSize(35, 15);
/*  985 */       jl2.setLocation(71, 5 + m * 20);
/*  986 */       jl2.setHorizontalAlignment(4);
/*  987 */       jdlgGridStatistics.add(jl2);
/*      */       
/*  989 */       jl2 = new JLabel("" + dwn[m]);
/*  990 */       jl2.setSize(35, 15);
/*  991 */       jl2.setLocation(115, 5 + m * 20);
/*  992 */       jl2.setHorizontalAlignment(4);
/*  993 */       jdlgGridStatistics.add(jl2);
/*      */     } 
/*      */ 
/*      */     
/*  997 */     for (int j = 0; j < Grid.ySz; j++) {
/*  998 */       for (int n = 0; n < Grid.ySz; n++)
/*  999 */         cellCount[Grid.mode[n][j]] = cellCount[Grid.mode[n][j]] + 1; 
/* 1000 */     }  for (int i = 0; i < 15; i++) {
/* 1001 */       ImageIcon theIcon1 = new ImageIcon("graphics/cellmode" + i + ".png");
/* 1002 */       JLabel jl2 = new JLabel(theIcon1);
/* 1003 */       jl2.setSize(40, 40);
/* 1004 */       jl2.setLocation(70 * i % 3 + 200, 30 + 44 * i / 3);
/* 1005 */       jdlgGridStatistics.add(jl2);
/* 1006 */       jl = new JLabel("" + cellCount[i]);
/* 1007 */       jl.setForeground(Def.COLOR_LABEL);
/* 1008 */       jl.setSize(26, 40);
/* 1009 */       jl.setLocation(70 * i % 3 + 240, 30 + 44 * i / 3);
/* 1010 */       jl.setHorizontalAlignment(2);
/* 1011 */       jdlgGridStatistics.add(jl);
/*      */     } 
/*      */     
/* 1014 */     Action doOK = new AbstractAction("OK") {
/*      */         public void actionPerformed(ActionEvent e) {
/* 1016 */           jdlgGridStatistics.dispose();
/*      */         }
/*      */       };
/* 1019 */     JButton jbOK = Methods.newButton("doOK", doOK, 79, 240, 430, 120, 26);
/* 1020 */     jdlgGridStatistics.add(jbOK);
/*      */     
/* 1022 */     jdlgGridStatistics.getRootPane().setDefaultButton(jbOK);
/* 1023 */     Methods.setDialogSize(jdlgGridStatistics, 400, 470);
/*      */   }
/*      */ 
/*      */   
/*      */   static boolean validCellType(int x, int y) {
/* 1028 */     int[] neighbourMask = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768 };
/*      */     
/* 1030 */     int[][] typeMask = { { 0, 0, 0, 0 }, { 1456, 21312, 27264, 3176 }, { 1456, 21312, 27264, 3176 }, { 0, 32710, 0, 0 }, { 0, 0, 32710, 0 }, { 0, 32710, 32710, 0 }, { 1456, 31878, 27264, 29614 }, { 31350, 21312, 30022, 3176 }, { 1456, 21312, 30022, 29614 }, { 31350, 21312, 27264, 29614 }, { 1456, 31878, 30022, 3176 }, { 31350, 31878, 27264, 3176 }, { 1456, 21312, 27264, 32750 }, { 32758, 21312, 27264, 3176 }, { 32758, 21312, 27264, 32750 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1048 */     if (Grid.symmetry != 0 && Grid.mode[x][y] > 11 && Grid.mode[x][y] < 15) {
/* 1049 */       return false;
/*      */     }
/*      */     
/* 1052 */     int neighbour = (y == 0) ? 2 : neighbourMask[Grid.mode[x][y - 1]];
/* 1053 */     if ((typeMask[Grid.mode[x][y]][0] & neighbour) > 0) {
/* 1054 */       return false;
/*      */     }
/*      */     
/* 1057 */     neighbour = (x + 1 == Grid.xSz) ? 2 : neighbourMask[Grid.mode[x + 1][y]];
/* 1058 */     if ((typeMask[Grid.mode[x][y]][1] & neighbour) > 0) {
/* 1059 */       return false;
/*      */     }
/*      */     
/* 1062 */     neighbour = (y + 1 == Grid.ySz) ? 2 : neighbourMask[Grid.mode[x][y + 1]];
/* 1063 */     if ((typeMask[Grid.mode[x][y]][2] & neighbour) > 0) {
/* 1064 */       return false;
/*      */     }
/*      */     
/* 1067 */     neighbour = (x == 0) ? 2 : neighbourMask[Grid.mode[x - 1][y]];
/* 1068 */     if ((typeMask[Grid.mode[x][y]][3] & neighbour) > 0) {
/* 1069 */       return false;
/*      */     }
/*      */     
/* 1072 */     switch (Grid.symmetry) {
/*      */       case 3:
/*      */       case 5:
/* 1075 */         if (Grid.xSz % 2 == 1 && Grid.xSz / 2 == x && (
/* 1076 */           neighbourMask[Grid.mode[x][y]] & 0xFF28) != 0)
/* 1077 */           return false; 
/* 1078 */         if (Grid.symmetry == 3)
/*      */           break; 
/*      */       case 4:
/* 1081 */         if (Grid.ySz % 2 == 1 && Grid.ySz / 2 == y && (
/* 1082 */           neighbourMask[Grid.mode[x][y]] & 0xFF30) != 0)
/* 1083 */           return false; 
/*      */         break;
/*      */       case 6:
/*      */       case 8:
/* 1087 */         if (x == y && (
/* 1088 */           neighbourMask[Grid.mode[x][y]] & 0xF9D8) != 0)
/* 1089 */           return false; 
/* 1090 */         if (Grid.symmetry == 6)
/*      */           break; 
/*      */       case 7:
/* 1093 */         if (x + y + 1 == Grid.xSz && (
/* 1094 */           neighbourMask[Grid.mode[x][y]] & 0xF6F8) != 0) {
/* 1095 */           return false;
/*      */         }
/*      */         break;
/*      */     } 
/* 1099 */     return true;
/*      */   }
/*      */   
/*      */   static void updateCell(int x, int y, int newMask, int oldMask) {
/* 1103 */     switch (oldMask) { case 49:
/* 1104 */         Grid.mode[x][y] = (Grid.mode[x][y] == 5) ? 4 : 0; break;
/* 1105 */       case 65: Grid.mode[x][y] = (Grid.mode[x][y] == 5) ? 3 : 0; break;
/* 1106 */       case 81: Grid.mode[x][y] = 0; break;
/*      */       case 48: case 80:
/* 1108 */         Grid.mode[x - 1][y] = (Grid.mode[x - 1][y] == 5) ? 4 : 0;
/* 1109 */         if (oldMask == 48)
/* 1110 */           break; case 64: Grid.mode[x][y - 1] = (Grid.mode[x][y - 1] == 5) ? 3 : 0; break;
/* 1111 */       case 83: Grid.mode[x][y - 1] = (Grid.mode[x][y - 1] == 5) ? 3 : 0;
/* 1112 */         Grid.mode[x][y] = (Grid.mode[x][y] == 5) ? 4 : 0; break;
/* 1113 */       case 84: Grid.mode[x - 1][y] = (Grid.mode[x - 1][y] == 5) ? 4 : 0;
/* 1114 */         Grid.mode[x][y] = (Grid.mode[x][y] == 5) ? 3 : 0; break; }
/*      */     
/* 1116 */     if (newMask < 17)
/* 1117 */       Grid.mode[x][y] = newMask; 
/* 1118 */     switch (newMask) { case 49:
/* 1119 */         Grid.mode[x][y] = (Grid.mode[x][y] == 4) ? 5 : 3; break;
/* 1120 */       case 65: Grid.mode[x][y] = (Grid.mode[x][y] == 3) ? 5 : 4; break;
/* 1121 */       case 81: Grid.mode[x][y] = 5; break;
/*      */       case 48: case 80:
/* 1123 */         Grid.mode[x - 1][y] = (Grid.mode[x - 1][y] == 4) ? 5 : 3;
/* 1124 */         if (Grid.mode[x][y] < 3 || Grid.mode[x][y] > 5) Grid.mode[x][y] = 0; 
/* 1125 */         if (newMask == 48)
/* 1126 */           break; case 64: Grid.mode[x][y - 1] = (Grid.mode[x][y - 1] == 3) ? 5 : 4;
/* 1127 */       case 32: if (Grid.mode[x][y] < 3 || Grid.mode[x][y] > 5)
/* 1128 */           Grid.mode[x][y] = 0; 
/*      */         break;
/*      */       case 83:
/* 1131 */         Grid.mode[x][y - 1] = (Grid.mode[x][y - 1] == 3) ? 5 : 4;
/* 1132 */         Grid.mode[x][y] = (Grid.mode[x][y] == 4) ? 5 : 3; break;
/* 1133 */       case 84: Grid.mode[x - 1][y] = (Grid.mode[x - 1][y] == 4) ? 5 : 3;
/* 1134 */         Grid.mode[x][y] = (Grid.mode[x][y] == 3) ? 5 : 4;
/*      */         break; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean symmetryUpdate(int x, int y, int previous, int current) {
/* 1196 */     int[][] mask = { { 32, 32, 32, 32, 32, 32, 32, 32, 32 }, { 1, 1, 1, 1, 1, 1, 1, 1, 1 }, { 2, 2, 2, 2, 2, 2, 2, 2, 2 }, { 65, 48, 64, 48, 48, 49, 65, 48, 64 }, { 48, 64, 49, 65, 64, 64, 49, 64, 48 }, { 84, 80, 83, 84, 80, 83, 81, 80, 80 }, { 7, 6, 7, 6, 6, 6, 7, 6, 7 }, { 6, 7, 6, 7, 7, 7, 6, 7, 6 }, { 9, 11, 10, 10, 11, 9, 11, 11, 8 }, { 11, 10, 8, 11, 10, 8, 9, 10, 10 }, { 8, 9, 11, 8, 9, 11, 10, 9, 9 }, { 10, 8, 9, 9, 8, 10, 8, 8, 11 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0, 0 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1214 */     if (x == Grid.xSz - 1 - x && y == Grid.ySz - 1 - y) {
/* 1215 */       if (current > 2)
/* 1216 */         Grid.mode[x][y] = previous; 
/* 1217 */       return true;
/*      */     } 
/*      */     
/* 1220 */     switch (Grid.symmetry) {
/*      */       case 3:
/*      */       case 5:
/* 1223 */         if (y == Grid.xSz - 1 - y && current >= 3 && current != 6 && current != 7) {
/* 1224 */           Grid.mode[x][y] = previous;
/* 1225 */           return true;
/*      */         } 
/* 1227 */         if (Grid.symmetry == 3)
/*      */           break; 
/* 1229 */       case 4: if (x == Grid.xSz - 1 - x && current >= 3 && current != 6 && current != 7) {
/* 1230 */           Grid.mode[x][y] = previous;
/* 1231 */           return true;
/*      */         } 
/*      */         break;
/*      */       case 6:
/*      */       case 8:
/* 1236 */         if (x == y && current >= 3 && current != 6 && current != 7) {
/* 1237 */           Grid.mode[x][y] = previous;
/* 1238 */           return true;
/*      */         } 
/* 1240 */         if (Grid.symmetry == 8)
/*      */           break; 
/* 1242 */       case 7: if (x == Grid.xSz - 1 - y && current >= 3 && current != 6 && current != 7) {
/* 1243 */           Grid.mode[x][y] = previous;
/* 1244 */           return true;
/*      */         } 
/*      */         break;
/*      */     } 
/*      */     
/* 1249 */     switch (Grid.symmetry) {
/*      */ 
/*      */       
/*      */       case 1:
/* 1253 */         if (Grid.letter[Grid.xSz - 1 - y][x] != 0) return false; 
/* 1254 */         if (Grid.letter[y][Grid.ySz - 1 - x] != 0) return false; 
/*      */       case 2:
/* 1256 */         if (Grid.letter[Grid.xSz - 1 - x][Grid.ySz - 1 - y] != 0) return false; 
/*      */         break;
/*      */       case 5:
/* 1259 */         if (Grid.letter[Grid.xSz - 1 - x][Grid.ySz - 1 - y] != 0) return false; 
/*      */       case 3:
/* 1261 */         if (Grid.letter[x][Grid.ySz - 1 - y] != 0) return false; 
/* 1262 */         if (Grid.symmetry == 3)
/*      */           break; 
/* 1264 */       case 4: if (Grid.letter[Grid.xSz - 1 - x][y] != 0) return false; 
/*      */         break;
/*      */       case 8:
/* 1267 */         if (Grid.letter[Grid.xSz - 1 - x][Grid.ySz - 1 - y] != 0) return false; 
/*      */       case 6:
/* 1269 */         if (Grid.letter[y][x] != 0) return false; 
/* 1270 */         if (Grid.symmetry == 6)
/*      */           break; 
/* 1272 */       case 7: if (Grid.letter[Grid.xSz - 1 - x][Grid.ySz - 1 - y] != 0) return false;
/*      */         
/*      */         break;
/*      */     } 
/* 1276 */     switch (Grid.symmetry) {
/*      */ 
/*      */       
/*      */       case 1:
/* 1280 */         updateCell(Grid.xSz - 1 - y, x, mask[current][0], mask[previous][0]);
/* 1281 */         updateCell(y, Grid.ySz - 1 - x, mask[current][2], mask[previous][2]);
/*      */       case 2:
/* 1283 */         updateCell(Grid.xSz - 1 - x, Grid.ySz - 1 - y, mask[current][1], mask[previous][1]);
/*      */         break;
/*      */       case 3:
/* 1286 */         updateCell(x, Grid.ySz - 1 - y, mask[current][5], mask[previous][5]);
/*      */         break;
/*      */       case 5:
/* 1289 */         if (y != Grid.ySz - 1 - y) {
/* 1290 */           updateCell(x, Grid.ySz - 1 - y, mask[current][5], mask[previous][5]);
/* 1291 */           if (Grid.symmetry == 3)
/* 1292 */             break;  if (x != Grid.xSz - 1 - x)
/* 1293 */             updateCell(Grid.xSz - 1 - x, Grid.ySz - 1 - y, mask[current][4], mask[previous][4]); 
/*      */         } 
/* 1295 */         if (Grid.symmetry == 3)
/*      */           break; 
/* 1297 */       case 4: if (x != Grid.xSz - 1 - x)
/* 1298 */           updateCell(Grid.xSz - 1 - x, y, mask[current][3], mask[previous][3]); 
/*      */         break;
/*      */       case 6:
/*      */       case 8:
/* 1302 */         if (x != y) {
/* 1303 */           updateCell(y, x, mask[current][6], mask[previous][6]);
/* 1304 */           if (Grid.symmetry == 6)
/* 1305 */             break;  if (y != Grid.xSz - 1 - x)
/* 1306 */             updateCell(Grid.xSz - 1 - x, Grid.ySz - 1 - y, mask[current][7], mask[previous][7]); 
/*      */         } 
/* 1308 */         if (Grid.symmetry == 6)
/*      */           break; 
/* 1310 */       case 7: if (x != Grid.xSz - 1 - y)
/* 1311 */           updateCell(Grid.xSz - 1 - y, Grid.ySz - 1 - x, mask[current][8], mask[previous][8]); 
/*      */         break;
/*      */     } 
/* 1314 */     return true;
/*      */   }
/*      */   
/*      */   void updateGrid(MouseEvent e) {
/* 1318 */     int oldX = Grid.xCur, oldY = Grid.yCur, x = e.getX(), y = e.getY();
/*      */     
/* 1320 */     if (x < Grid.xOrg || y < Grid.yOrg)
/*      */       return; 
/* 1322 */     x = (x - Grid.xOrg) / Grid.xCell;
/* 1323 */     y = (y - Grid.yOrg) / Grid.yCell;
/* 1324 */     if (x >= Grid.xSz || y >= Grid.ySz)
/*      */       return; 
/* 1326 */     if (x == Grid.xCur && y == Grid.yCur) {
/* 1327 */       int previous = Grid.mode[x][y];
/*      */       while (true) {
/* 1329 */         Grid.mode[x][y] = Grid.mode[x][y] + 1; if (Grid.mode[x][y] + 1 == 15)
/* 1330 */           Grid.mode[x][y] = 0; 
/* 1331 */         if (validCellType(x, y)) {
/* 1332 */           if (Grid.symmetry != 0) {
/* 1333 */             symmetryUpdate(x, y, previous, Grid.mode[x][y]);
/*      */           }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1340 */           this.updated = true;
/* 1341 */           NodeList.buildNodeList();
/* 1342 */           Grid.xCur = oldX; Grid.yCur = oldY; return;
/*      */         } 
/*      */       } 
/*      */     }  Grid.xCur = x;
/* 1346 */     Grid.yCur = y; } void handleKeyPressed(KeyEvent e) { switch (e.getKeyCode()) { case 38:
/* 1347 */         if (Grid.yCur > 0) Grid.yCur--;  break;
/* 1348 */       case 40: if (Grid.yCur < Grid.ySz - 1) Grid.yCur++;  break;
/* 1349 */       case 37: if (Grid.xCur > 0) Grid.xCur--;  break;
/* 1350 */       case 39: if (Grid.xCur < Grid.xSz - 1) Grid.xCur++;  break;
/* 1351 */       case 36: Grid.xCur = 0; break;
/* 1352 */       case 35: Grid.xCur = Grid.xSz - 1; break;
/* 1353 */       case 33: Grid.yCur = 0; break;
/* 1354 */       case 34: Grid.yCur = Grid.ySz - 1;
/*      */         break; }
/*      */      }
/*      */ 
/*      */ }


/* Location:              C:\Users\mustapha\Desktop\Crossword-Express.jar!\crosswordexpress\GridMaintenance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */