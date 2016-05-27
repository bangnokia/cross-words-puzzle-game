import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class CrosswordGUI
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI()
    {
        JFrame f = new JFrame(); // create main frame
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.getContentPane().setLayout(new BorderLayout());

        JPanel container = new JPanel(new FlowLayout()); // create panel container
        final CrosswordPanel panel = new CrosswordPanel();
        container.add(panel); //add panel to frame
        f.getContentPane().add(container, BorderLayout.CENTER);

        JButton generateButton = new JButton("Generate"); //button generate
        generateButton.addActionListener(new ActionListener() //listen action onclick to generate cross word puzzle
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                generate(panel);
            }
        });
        f.getContentPane().add(generateButton, BorderLayout.SOUTH); //add button to center bottom

        f.setSize(800, 800);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    private static Random random = new Random(0);
    private static void generate(CrosswordPanel panel)
    {
        //int w = 4 + random.nextInt(8); //random width lenght
        //int h = 4 + random.nextInt(8); //random height length
        int w = 4;
        int h = 4;
        char crossword[][] = new char[w][h]; //generate array 2 dimension store char
        for (int y=0; y<h; y++)
        {
            for (int x=0; x<w; x++)
            {
                if (random.nextFloat() > 0.2)
                {
                    char c = (char)('A' + random.nextInt(26));
                    crossword[x][y] = c;
                }
            }
        }

        panel.setCrossword(crossword);
    }

}


class CrosswordPanel extends JPanel
{
    private JTextField textFields[][];

    void setCrossword(char array[][])
    {
        removeAll();
        int w = array.length;
        int h = array[0].length;
        setLayout(new GridLayout(w, h));
        textFields = new JTextField[w][h];

        for (int y=0; y<h; y++)
        {
            for (int x=0; x<w; x++)
            {
                char c = array[x][y];
                if (c != 0)
                {
                    textFields[x][y] = new JTextField(String.valueOf(c));
                    textFields[x][y].setFont(textFields[x][y].getFont().deriveFont(20.0f));
                    add(textFields[x][y]);
                }
                else
                {
                    add(new JLabel());
                }
            }
        }
        getParent().validate();
        repaint();
    }

    char[][] getCrossword()
    {
        int w = textFields.length;
        int h = textFields[0].length;
        char crossword[][] = new char[w][h];
        for (int y=0; y<h; y++)
        {
            for (int x=0; x<w; x++)
            {
                if (textFields[x][y] != null)
                {
                    String s = textFields[x][y].getText();
                    if (s.length() > 0)
                    {
                        crossword[x][y] = s.charAt(0);
                    }

                }
            }
        }
        return crossword;
    }

}