import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Main extends JPanel implements ActionListener, ChangeListener, KeyListener {
  private static final long serialVersionUID = -6611601176764357208L;
  
  JFrame parentFrame = null;
  
  int[] preferences = new int[] { 0, 0, 0, 1, 1 };
  
  JLabel title = new JLabel("Catch Rate Calculator");
  
  JRadioButton genIButton = new JRadioButton("Gen I");
  
  JRadioButton genIIButton = new JRadioButton("Gen II");
  
  JRadioButton genIIIButton = new JRadioButton("Gen III");
  
  JRadioButton genIVButton = new JRadioButton("Gen IV");
  
  JRadioButton genVButton = new JRadioButton("Gen V");
  
  ButtonGroup genGroup = new ButtonGroup();
  
  JLabel pokeLabel = new JLabel("Pokemon:");
  
  JComboBox<String> pokeBox = new JComboBox<>(Constants.names);
  
  JTextField pokeField = new JTextField(10);
  
  JLabel ballLabel = new JLabel("Ball:");
  
  JComboBox<String> ballBox = new JComboBox<>(Constants.balls);
  
  ButtonGroup statusGroup = new ButtonGroup();
  
  JRadioButton[] statusButtons;
  
  JLabel healthLabel = new JLabel("Health %:");
  
  JSlider healthSlider = new JSlider(0, 0, 100, 100);
  
  JLabel rateLabel = new JLabel("Catch rate:");
  
  JTextArea output = new JTextArea("      ");
  
  JLabel[] statLabels;
  
  JTextArea[] statNums;
  
  JLabel expLabel1 = new JLabel("1st:");
  
  JTextArea expText1 = new JTextArea("     ");
  
  JLabel expLabel2 = new JLabel("2nd:");
  
  JTextArea expText2 = new JTextArea("     ");
  
  JLabel expLabel3 = new JLabel("3rd:");
  
  JTextArea expText3 = new JTextArea("     ");
  
  JButton statsButton = new JButton("Tell me more");
  
  JLabel levelLabel = new JLabel("Level:");
  
  JTextField levelField = new JTextField(3);
  
  TreeNode root;
  
  TreeNode cur;
  
  String last = "";
  
  int[] get_stats(int gen, int poke) {
    int[] stats;
    if (poke <= 151 && gen == 1) {
      stats = new int[6];
      int[] x = Constants.gen1stats[poke];
      stats[0] = x[0];
      stats[1] = x[1];
      stats[2] = x[2];
      stats[4] = x[4];
      stats[3] = x[4];
      stats[5] = x[3];
    } else {
      stats = Constants.stats[poke];
    } 
    return stats;
  }
  
  void set_stats(int[] x) {
    for (int i = 0; i < 6; i++)
      this.statNums[i].setText("" + x[i]); 
  }
  
  void update() {
    try {
      Formula calculator;
      int poke = this.pokeBox.getSelectedIndex();
      if (poke == 0)
        throw new Exception(); 
      int level = 0;
      try {
        level = Integer.parseInt(this.levelField.getText());
      } catch (Exception exception) {}
      int gen = -1;
      if (this.genIButton.isSelected()) {
        calculator = new GenICalculator();
        gen = 1;
      } else if (this.genIIButton.isSelected()) {
        calculator = new GenIICalculator();
        gen = 2;
      } else if (this.genIIIButton.isSelected()) {
        calculator = new GenIIICalculator();
        gen = 3;
      } else if (this.genIVButton.isSelected()) {
        calculator = new GenIIICalculator();
        gen = 4;
      } else if (this.genVButton.isSelected()) {
        calculator = new GenVCalculator();
        gen = 5;
      } else {
        throw new Exception();
      } 
      set_stats(get_stats(gen, poke));
      int ball = this.ballBox.getSelectedIndex();
      int status = -1;
      for (int i = 0; i < Constants.status.length; i++) {
        if (this.statusButtons[i].isSelected()) {
          status = i;
          break;
        } 
      } 
      if (status == -1)
        return; 
      int health = this.healthSlider.getValue();
      if (health <= 0)
        health = 1; 
      double hp = health / 100.0D;
      double p = 0.0D;
      if (level <= 0) {
        for (int l = 2; l <= 6; l++)
          p += calculator.calculate(poke, hp, status, ball, l); 
        p /= 5.0D;
      } else {
        p = calculator.calculate(poke, hp, status, ball, level);
      } 
      this.output.setText((new DecimalFormat("##.000")).format(100.0D * p));
      int first = 0, second = 0, third = 0;
      int ntrials = 20000;
      int[][] enemies = { { 300, 285, 226 }, { 286, 251, 237 }, { 357, 349, 368 }, { 332, 324, 321 }, { 318, 295, 285 }, { 366, 329, 314 }, { 341, 301, 264 }, { 326, 292, 282 }, { 270, 282, 251 }, { 267, 254, 259 } };
      Random r = new Random();
      for (int j = 0; j < ntrials; j++) {
        int[] scores = new int[5];
        int k;
        for (k = 0; k < 5; k++) {
          int m = -1;
          boolean isDuplicate = true;
          while (isDuplicate) {
            isDuplicate = false;
            m = r.nextInt(enemies.length);
            for (int k1 = 0; k1 < k; k1++) {
              if (scores[k1] == m) {
                isDuplicate = true;
                break;
              } 
            } 
            scores[k] = m;
          } 
        } 
        for (k = 0; k < 5; k++) {
          int m = scores[k];
          scores[k] = enemies[m][r.nextInt((enemies[m]).length)] + r.nextInt(8);
        } 
        Arrays.sort(scores);
        int atkDV = r.nextInt(16);
        int defDV = r.nextInt(16);
        int speDV = r.nextInt(16);
        int spcDV = r.nextInt(16);
        int hpDV = (atkDV & 0x1) << 3 | (defDV & 0x1) << 2 | (speDV & 0x1) << 1 | (spcDV & 0x1) << 0;
        int maxHP = (Constants.stats[poke][0] + hpDV + 50) * level / 50 + 10;
        int curHP = (int)Math.round(hp * maxHP);
        if (curHP <= 0)
          curHP = 1; 
        int atk = (Constants.stats[poke][1] + atkDV) * level / 50 + 5;
        int def = (Constants.stats[poke][2] + atkDV) * level / 50 + 5;
        int spA = (Constants.stats[poke][3] + atkDV) * level / 50 + 5;
        int spD = (Constants.stats[poke][4] + atkDV) * level / 50 + 5;
        int spe = (Constants.stats[poke][5] + atkDV) * level / 50 + 5;
        int score = 0;
        score += 4 * maxHP;
        score += atk + def + spA + spD + spe;
        if (defDV % 2 == 1)
          score += 16; 
        if (atkDV % 2 == 1)
          score += 8; 
        if (spcDV % 2 == 1)
          score += 4; 
        if (speDV % 2 == 1)
          score++; 
        score += curHP / 8;
        if (score >= scores[scores.length - 1]) {
          first++;
        } else if (score >= scores[scores.length - 2]) {
          second++;
        } else if (score >= scores[scores.length - 3]) {
          third++;
        }
      }
      this.expText1.setText((new DecimalFormat("##.00")).format((100.0F * first / ntrials)));
      this.expText2.setText((new DecimalFormat("##.00")).format((100.0F * second / ntrials)));
      this.expText3.setText((new DecimalFormat("##.00")).format((100.0F * third / ntrials)));
    } catch (Exception e) {
      this.output.setText("      ");
      for (int i = 0; i < 6; i++)
        this.statNums[i].setText("   "); 
    } 
  }
  
  static final String[][] game_names = new String[][] { { "Red/Blue", "Yellow" }, { "Gold/Silver", "Crystal" }, { "R/S/E", "FR/LG" }, { "D/P", "HG/SS" }, { "B/W", "B2/W2" } };
  
  int last_gen;
  
  class StatsPanel extends JPanel implements ActionListener {
    int poke;
    
    int gen;
    
    Main parentPanel;
    
    JFrame parentFrame;
    
    JRadioButton[] gameButtons;
    
    JPanel levelPanel = null;
    
    JPanel movesPanel = null;
    
    StatsPanel(int poke, int gen, Main parentPanel, JFrame parentFrame) {
      this.poke = poke;
      this.gen = gen;
      this.parentPanel = parentPanel;
      this.parentFrame = parentFrame;
      this.gameButtons = new JRadioButton[2];
    }
    
    public boolean fillPanels(int poke, int gen, int gameIndex) {
      int[][] moves = MovesetConstants.get(poke, gen, gameIndex);
      if (moves == null)
        return false; 
      for (int i = 0; i < moves.length; i++) {
        int[] move = moves[i];
        JLabel level = new JLabel("");
        level.setText((move[0] == 1) ? "Start" : ""+move[0]);
        JPanel a = new JPanel();
        a.add(level);
        this.levelPanel.add(a);
        JPanel b = new JPanel();
        JLabel movename = new JLabel(Constants.move_names[move[1]]);
        b.add(movename);
        this.movesPanel.add(b);
      } 
      return true;
    }
    
    public void actionPerformed(ActionEvent e) {
      int k;
      if (e.getSource() == this.gameButtons[0]) {
        k = 0;
      } else if (e.getSource() == this.gameButtons[1]) {
        k = 1;
      } else {
        return;
      } 
      this.parentPanel.preferences[this.gen - 1] = k;
      this.levelPanel.removeAll();
      this.movesPanel.removeAll();
      fillPanels(this.poke, this.gen, k);
      this.parentFrame.pack();
    }
  }
  
  public JPanel createStatsPanel(int poke, int gen, Main parentPanel, JFrame parentFrame) {
    StatsPanel statsPanel = new StatsPanel(poke, gen, parentPanel, parentFrame);
    statsPanel.setLayout(new BoxLayout(statsPanel, 1));
    JLabel title = new JLabel(String.valueOf(Constants.names[poke]) + " (Generation " + gen + ")");
    JPanel a = new JPanel();
    a.add(title);
    statsPanel.add(a);
    a = new JPanel();
    a.setLayout(new BoxLayout(a, 0));
    JPanel b = new JPanel();
    JPanel c = new JPanel();
    ButtonGroup bgroup = new ButtonGroup();
    JRadioButton[] gameButtons = statsPanel.gameButtons;
    gameButtons[0] = new JRadioButton(game_names[gen - 1][0]);
    gameButtons[1] = new JRadioButton(game_names[gen - 1][1]);
    bgroup.add(gameButtons[0]);
    bgroup.add(gameButtons[1]);
    gameButtons[this.preferences[gen - 1]].setSelected(true);
    gameButtons[0].addActionListener(statsPanel);
    gameButtons[1].addActionListener(statsPanel);
    b.add(gameButtons[0]);
    c.add(gameButtons[1]);
    a.add(b);
    a.add(c);
    statsPanel.add(a);
    JPanel movesPanel = new JPanel();
    movesPanel.setLayout(new BoxLayout(movesPanel, 0));
    statsPanel.add(movesPanel);
    JPanel levelPanel = new JPanel();
    JPanel movenamePanel = new JPanel();
    levelPanel.setLayout(new BoxLayout(levelPanel, 1));
    movenamePanel.setLayout(new BoxLayout(movenamePanel, 1));
    movesPanel.add(levelPanel);
    movesPanel.add(movenamePanel);
    statsPanel.levelPanel = levelPanel;
    statsPanel.movesPanel = movenamePanel;
    boolean x = statsPanel.fillPanels(poke, gen, this.preferences[gen - 1]);
    if (x)
      return statsPanel; 
    return null;
  }
  
  class StatsThread implements Runnable {
    int poke;
    
    int gen;
    
    JFrame parent;
    
    Main parentPanel;
    
    public StatsThread(int poke, int gen, JFrame parent, Main parentPanel) {
      this.poke = poke;
      this.gen = gen;
      this.parent = parent;
      this.parentPanel = parentPanel;
    }
    
    public void run() {
      JFrame frame = new JFrame();
      frame.setTitle(Constants.names[this.poke]);
      frame.setResizable(false);
      JPanel panel = Main.this.createStatsPanel(this.poke, this.gen, this.parentPanel, frame);
      if (panel == null)
        return; 
      frame.add(panel);
      frame.pack();
      frame.setMinimumSize(new Dimension(180, 0));
      Point loc = Main.this.parentFrame.getLocation();
      Dimension parentSize = Main.this.parentFrame.getSize();
      Dimension size = frame.getSize();
      int windowX = Math.max(0, (parentSize.width - size.width) / 2);
      int windowY = Math.max(0, (parentSize.height - size.height) / 2);
      frame.setLocation(loc.x + windowX, loc.y + windowY);
      frame.setVisible(true);
    }
  }
  
  public void makeStatsWindow() {
    int poke = this.pokeBox.getSelectedIndex();
    if (poke == 0)
      return; 
    int gen = -1;
    if (this.genIButton.isSelected()) {
      gen = 1;
    } else if (this.genIIButton.isSelected()) {
      gen = 2;
    } else if (this.genIIIButton.isSelected()) {
      gen = 3;
    } else if (this.genIVButton.isSelected()) {
      gen = 4;
    } else if (this.genVButton.isSelected()) {
      gen = 5;
    } else {
      return;
    } 
    SwingUtilities.invokeLater(new StatsThread(poke, gen, this.parentFrame, this));
  }
  
  class LevelListener implements KeyListener, FocusListener {
    public void keyPressed(KeyEvent arg0) {}
    
    public void keyReleased(KeyEvent arg0) {
      String s = Main.this.levelField.getText();
      for (int i = 0; i < s.length(); i++) {
        char c = s.charAt(i);
        if (i >= 3 || c < '0' || c > '9') {
          Main.this.levelField.setText(s.substring(0, i));
          break;
        } 
      } 
      Main.this.update();
    }
    
    public void keyTyped(KeyEvent arg0) {}
    
    public void focusGained(FocusEvent e) {}
    
    public void focusLost(FocusEvent e) {
      String s = Main.this.levelField.getText();
      try {
        Integer.parseInt(s);
      } catch (Exception ex) {
        Main.this.levelField.setText("");
      } 
    }
  }
  
  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
          public void run() {
            try {
              UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception exception) {}
            JFrame parentFrame = new JFrame("Catch Rate Calculator");
            parentFrame.setResizable(false);
            JPanel panel = new Main(parentFrame);
            parentFrame.add(panel);
            parentFrame.setDefaultCloseOperation(3);
            parentFrame.pack();
            int windowX = 10;
            int windowY = 10;
            parentFrame.setLocation(windowX, windowY);
            parentFrame.setVisible(true);
          }
        });
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.statsButton) {
      makeStatsWindow();
    } else {
      update();
    } 
  }
  
  public void stateChanged(ChangeEvent arg0) {
    update();
  }
  
  public void keyPressed(KeyEvent e) {}
  
  public Main(JFrame parentFrame) {
    this.last_gen = -1;
    this.parentFrame = parentFrame;
    MovesetConstants.init();
    setBorder(BorderFactory.createLineBorder(Color.black));
    setLayout(new BoxLayout(this, 1));
    JPanel x = new JPanel();
    x.add(this.title);
    add(x);
    JPanel genPanel = new JPanel();
    genPanel.setLayout(new BoxLayout(genPanel, 0));
    genPanel.add(this.genIButton);
    genPanel.add(this.genIIButton);
    genPanel.add(this.genIIIButton);
    genPanel.add(this.genIVButton);
    genPanel.add(this.genVButton);
    this.genGroup.add(this.genIButton);
    this.genGroup.add(this.genIIButton);
    this.genGroup.add(this.genIIIButton);
    this.genGroup.add(this.genIVButton);
    this.genGroup.add(this.genVButton);
    this.genIButton.setSelected(true);
    this.genIButton.addActionListener(this);
    this.genIIButton.addActionListener(this);
    this.genIIIButton.addActionListener(this);
    this.genIVButton.addActionListener(this);
    this.genVButton.addActionListener(this);
    add(genPanel);
    JPanel y = new JPanel();
    y.setLayout(new BoxLayout(y, 0));
    JPanel y1 = new JPanel();
    y1.add(this.pokeLabel);
    JPanel y2 = new JPanel();
    this.pokeBox.setEditable(false);
    this.pokeBox.addActionListener(this);
    y2.add(this.pokeBox);
    JPanel y3 = new JPanel();
    this.pokeField.addKeyListener(this);
    y3.add(this.pokeField);
    y.add(y2);
    y.add(y3);
    add(y);
    y = new JPanel();
    y.setLayout(new BoxLayout(y, 0));
    y1 = new JPanel();
    y2 = new JPanel();
    y3 = new JPanel();
    this.ballBox.addActionListener(this);
    LevelListener levelListener = new LevelListener();
    this.levelField.addKeyListener(levelListener);
    this.levelField.addFocusListener(levelListener);
    this.statsButton.addActionListener(this);
    y1.add(this.levelLabel);
    y1.add(this.levelField);
    y2.add(this.ballBox);
    y3.add(this.statsButton);
    y.add(y1);
    y.add(y2);
    y.add(y3);
    add(y);
    y = new JPanel();
    y.setLayout(new BoxLayout(y, 0));
    JPanel z = new JPanel();
    z.setLayout(new BoxLayout(z, 0));
    this.statusButtons = new JRadioButton[Constants.status.length];
    int i;
    for (i = 0; i < Constants.status.length; i++) {
      this.statusButtons[i] = new JRadioButton(Constants.status[i]);
      this.statusGroup.add(this.statusButtons[i]);
      this.statusButtons[i].addActionListener(this);
      if (2 * i < Constants.status.length) {
        y.add(this.statusButtons[i]);
      } else {
        z.add(this.statusButtons[i]);
      } 
    } 
    this.statusButtons[0].setSelected(true);
    add(y);
    add(z);
    y = new JPanel();
    y.setLayout(new BoxLayout(y, 0));
    y1 = new JPanel();
    y1.add(this.healthLabel);
    y2 = new JPanel();
    y2.add(this.healthSlider);
    this.healthSlider.addChangeListener(this);
    this.healthSlider.setMajorTickSpacing(10);
    this.healthSlider.setPaintTicks(true);
    this.healthSlider.setPaintLabels(true);
    y.add(y1);
    y.add(y2);
    add(y);
    y = new JPanel();
    y.setLayout(new BoxLayout(y, 0));
    y1 = new JPanel();
    y1.add(this.rateLabel);
    y2 = new JPanel();
    this.output.setEditable(false);
    y2.add(this.output);
    y2.add(new JLabel("%"));
    y.add(y1);
    y.add(y2);
    add(y);
    this.statLabels = new JLabel[6];
    this.statNums = new JTextArea[6];
    for (i = 0; i < 6; i++) {
      this.statLabels[i] = new JLabel(String.valueOf(Constants.stat_names[i]) + ": ");
      this.statNums[i] = new JTextArea("   ");
      this.statNums[i].setEditable(false);
    } 
    y = new JPanel();
    y.setLayout(new BoxLayout(y, 0));
    y1 = new JPanel();
    y2 = new JPanel();
    y3 = new JPanel();
    y1.add(this.statLabels[0]);
    y1.add(this.statNums[0]);
    y2.add(this.statLabels[1]);
    y2.add(this.statNums[1]);
    y3.add(this.statLabels[2]);
    y3.add(this.statNums[2]);
    y.add(y1);
    y.add(y2);
    y.add(y3);
    add(y);
    y = new JPanel();
    y.setLayout(new BoxLayout(y, 0));
    y1 = new JPanel();
    y2 = new JPanel();
    y3 = new JPanel();
    y1.add(this.statLabels[3]);
    y1.add(this.statNums[3]);
    y2.add(this.statLabels[4]);
    y2.add(this.statNums[4]);
    y3.add(this.statLabels[5]);
    y3.add(this.statNums[5]);
    y.add(y1);
    y.add(y2);
    y.add(y3);
    add(y);
    y = new JPanel();
    y.setLayout(new BoxLayout(y, 0));
    y1 = new JPanel();
    y1.add(this.expLabel1);
    y2 = new JPanel();
    y2.add(this.expText1);
    this.expText1.setEditable(false);
    y.add(y1);
    y.add(y2);
    y1 = new JPanel();
    y1.add(this.expLabel2);
    y2 = new JPanel();
    y2.add(this.expText2);
    this.expText2.setEditable(false);
    y.add(y1);
    y.add(y2);
    y1 = new JPanel();
    y1.add(this.expLabel3);
    y2 = new JPanel();
    y2.add(this.expText3);
    this.expText3.setEditable(false);
    y.add(y1);
    y.add(y2);
    add(y);
    this.root = new TreeNode(0);
    this.root.add(0, "", 0);
    for (i = 1; i < Constants.names.length; i++) {
      String s = Constants.names[i].toLowerCase();
      this.root.add(i, s, 0);
    } 
    this.cur = this.root;
  }
  
  public void keyReleased(KeyEvent e) {
    int k, gen = -1;
    if (this.genIButton.isSelected()) {
      gen = 1;
    } else if (this.genIIButton.isSelected()) {
      gen = 2;
    } else if (this.genIIIButton.isSelected()) {
      gen = 3;
    } else if (this.genIVButton.isSelected()) {
      gen = 4;
    } else if (this.genVButton.isSelected()) {
      gen = 5;
    } 
    if (this.last_gen != gen)
      this.root.sort(gen); 
    this.last_gen = gen;
    String text = this.pokeField.getText();
    String s = text.toLowerCase();
    int depth = this.root.queryDepth(s, 0);
    text = text.substring(0, depth);
    if (depth != s.length())
      this.pokeField.setText(text); 
    if (e.getKeyCode() == 10 || e.getKeyCode() == 40) {
      k = this.cur.getNext();
    } else if (e.getKeyCode() == 38) {
      k = this.cur.getPrev();
    } else if (!this.last.equals(s)) {
      this.cur.reset();
      this.cur = this.root.query(s, 0);
      k = this.cur.getVal();
    } else {
      return;
    } 
    this.last = s;
    this.pokeBox.setSelectedItem(Constants.names[k]);
    update();
  }
  
  public void keyTyped(KeyEvent e) {}
}
