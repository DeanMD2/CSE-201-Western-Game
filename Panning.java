import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class Panning extends JPanel {
    private static final int NUM_SPOTS = 5;
    private final Rectangle[] panningSpots;
    private final Random random;
    private String resultMessage = "Click a Spot to Travel to a Location:";
    private final Font rusticFont = new Font("Trattatello", Font.BOLD, 30);
    
    public Panning() {
        this.panningSpots = new Rectangle[NUM_SPOTS];
        this.random = new Random();
        setPreferredSize(new Dimension(600, 400));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                checkPanningSpot(e.getPoint());
            }
        });
        generatePanningSpots();
    }
    
    private void generatePanningSpots() {
        for (int i = 0; i < NUM_SPOTS; i++) {
            int x = 100 + i * 100;
            int y = 200 + random.nextInt(50);
            panningSpots[i] = new Rectangle(x, y, 50, 50);
        }
    }
    
    private void checkPanningSpot(Point clickPoint) {
        for (Rectangle spot : panningSpots) {
            if (spot.contains(clickPoint)) {
                openMiningMinigame();
                return;
            }
        }
        resultMessage = "Try again! Click on a panning spot.";
        repaint();
    }
    
    private void openMiningMinigame() {
        SwingUtilities.invokeLater(() -> {
            JFrame gameFrame = new JFrame("Mining Minigame");
            gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            gameFrame.add(new MiningMinigame());
            gameFrame.pack();
            gameFrame.setLocationRelativeTo(null);
            gameFrame.setVisible(true);
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw green background for land
        g.setColor(new Color(34, 139, 34));
        g.fillRect(0, 0, getWidth(), getHeight());
        
        // Draw river
        g.setColor(new Color(0, 100, 255));
        g.fillRect(0, 150, getWidth(), 150);
        
        // Draw spots
        g.setColor(Color.GRAY);
        for (Rectangle spot : panningSpots) {
            g.fillRect(spot.x, spot.y, spot.width, spot.height);
        }
        
        g.setFont(rusticFont);
        g.setColor(Color.WHITE);
        g.drawString(resultMessage, 20, 40);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Panning Minigame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new Panning());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

class MiningMinigame extends JPanel {
    private final String[] outcomes = {"Gold", "Fool’s Gold", "Nothing"};
    private final Random random = new Random();
    private String resultMessage = "Click to grab for gold!";
    private final Font rusticFont = new Font("Trattatello", Font.BOLD, 20);
    private boolean gameOver = false;
    
    public MiningMinigame() {
        setPreferredSize(new Dimension(400, 300));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!gameOver) {
                    resultMessage = "You found " + outcomes[random.nextInt(outcomes.length)] + "!";
                    gameOver = true;
                    repaint();
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.setColor(Color.BLUE); // background in mining window
        g.fillRect(getX(), getY(), getWidth(), getHeight());
        
        g.setColor(Color.ORANGE);
        g.fillOval(150, 100, 100, 100); // gold nugget
        
        g.setFont(rusticFont); // game message prompt/result
        g.setColor(Color.WHITE);
        g.drawString(resultMessage, 122, 30);
    }
}
