package View;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class JLabelView extends View{
    JFrame jFrame;
    JLabel jLabel;
    public JLabelView(JFrame jFrame, String backgroundPath){
        this.jFrame = jFrame;
        ImageIcon imageIcon = new ImageIcon(backgroundPath);
        Image image = imageIcon.getImage();
        this.jLabel = new JLabel(new ImageIcon(image)){
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
        jFrame.setContentPane(jLabel);
    }
    public void addText(String text, Font font, int x, int y, int x_size, int y_size, Color color){
        JLabel textLabel = new JLabel(text);
        textLabel.setFont(font);
        textLabel.setForeground(color);
        textLabel.setBounds(x,y, x_size, y_size);
        jLabel.add(textLabel);
    }
    public void addButton(String pathToGraphics, int x, int y, ActionListener actionListener){
        ImageIcon imageIcon = new ImageIcon(pathToGraphics);
        JButton jButton = new JButton(imageIcon);
        jButton.setBorderPainted(false);
        jButton.setContentAreaFilled(false);
        jButton.setBounds(x,y,imageIcon.getIconWidth(), imageIcon.getIconHeight());
        jButton.addActionListener(actionListener);

        jButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        jLabel.add(jButton);
    }
    public void addTextLabel(int x, int y,String text,  DocumentListener documentListener){
        JTextArea jTextArea = new JTextArea(1,10);
        jTextArea.setBounds(x,y,90,15);
        jTextArea.getDocument().addDocumentListener(documentListener);
        jTextArea.setText(text);
        jLabel.add(jTextArea);
    }
    public void setVisible(boolean visibility){
        jFrame.setVisible(true);
    }
}
