import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

/**
 * Class: Panning
 * @author Jason Hathaway
 * @version 1.0
 * Course: CSE 201 Spring 2025
 * Written: April 4, 2025
 *
 * Purpose: This class contains logic and functionality that sets up
 * the locations for the panning minigame.
 */
public class Panning extends JPanel {
    private static final int NUM_SPOTS = 5;
    private final Rectangle[] panningSpots;
    private final Random random;
    private String resultMessage = "Click a Spot to Pan for Gold!";
    private final Font rusticFont = new Font("Papyrus",Font.BOLD, 30);
    
    /**
     * Paints the MiningMinigame interface, including background color,
     * the gold nugget, and the result message.
     *
     * @param g the Graphics object used for drawing
     */
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
    
    /**
     * Generates random rectangular panning spots where the user can click to start the mining minigame.
     * The spots are spaced evenly horizontally and have slight vertical randomness.
     */
    private void generatePanningSpots() {
        for (int i = 0; i < NUM_SPOTS; i++) {
            int x = 100 + i * 100;
            int y = 200 + random.nextInt(50);
            panningSpots[i] = new Rectangle(x, y, 50, 50);
        }
    }
    
    /**
     * Checks if the user's click corresponds to a valid panning spot.
     * If so, it launches the mining minigame. Otherwise, displays a retry message.
     *
     * @param clickPoint the point where the user clicked
     */
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
    
    /**
     * Opens a new window that contains the mining minigame.
     * Uses SwingUtilities to ensure GUI creation runs on the Event Dispatch Thread.
     */
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
    
    /**
     * Paints the Panning minigame interface, including the land, river,
     * panning spots, and any result message.
     *
     * @param g the Graphics object used for drawing
     */
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
    
    /**
     * Main method for building GUI frame.
     */
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

/**
* Class: MiningMinigame
* @author Jason Hathaway
* @version 1.0
* Course: CSE 201 Spring 2025
* Written: April 4, 2025
*
* Purpose: This class constructs the panel where you search for 
* gold once a location is picked.
*/
class MiningMinigame extends JPanel {
    private final String[] outcomes = {"Gold", "Fool’s Gold", "Nothing"};
    private final Random random = new Random();
    private String resultMessage = "Click to pan the dirt!";
    private final Font rusticFont = new Font("Papyrus", Font.BOLD, 20);
    private boolean gameOver = false;
    private String result = "";

    /**
     * Constructs the MiningMinigame panel where players can click once
     * to randomly receive a result such as gold, fool’s gold, or nothing.
     */
    public MiningMinigame() {
        setPreferredSize(new Dimension(400, 300));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!gameOver) {
                    result = outcomes[random.nextInt(outcomes.length)];
                    resultMessage = "You found " + result + "!";
                    gameOver = true;
                    repaint();
                }
            }
        });
    }
    
    /**
     * Paints the MiningMinigame interface, including background color,
     * gold nugget representation, and result message.
     *
     * @param g the Graphics object used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Background
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Draw the pan (a large dark circle behind the nugget)
        int panDiameter = 200;
        int panX = (getWidth() - panDiameter) / 2;
        int panY = (getHeight() - panDiameter) / 2;
        g.setColor(new Color(40, 40, 40)); // dark gray/black
        g.fillOval(panX, panY, panDiameter, panDiameter);

        // Determine nugget color
        Color nuggetColor = new Color(139, 69, 19); // default before click
        if (gameOver) {
            switch (result) {
                case "Gold":
                    nuggetColor = Color.ORANGE;
                    break;
                case "Fool’s Gold":
                    nuggetColor = Color.YELLOW;
                    break;
                case "Nothing":
                    nuggetColor = null; // no nugget
                    break;
            }
        }

        // Draw nugget if applicable (centered within the pan)
        if (nuggetColor != null) {
            g.setColor(nuggetColor);
            int nuggetDiameter = 100;
            int nuggetX = (getWidth() - nuggetDiameter) / 2;
            int nuggetY = (getHeight() - nuggetDiameter) / 2;
            g.fillOval(nuggetX, nuggetY, nuggetDiameter, nuggetDiameter);
        }

        // Draw result message
        g.setFont(rusticFont);
        g.setColor(Color.WHITE);
        g.drawString(resultMessage, 20, 30);
    }
}

