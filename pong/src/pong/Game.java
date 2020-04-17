package pong;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable, KeyListener {

    public static int WIDTH = 180;
    public static int HEIGHT = 140;
    public static int SCALE = 2;

    public static Player player;
    public static Enemy enemy;
    public static Ball ball;

    public BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    public Game () {
        this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
        this.addKeyListener(this);
        player = new Player(100, HEIGHT-5); // O -5 indica o fim da janela, na parte inferior. Se for igual a 0, não será visível.
        enemy = new Enemy(100, 0);
        ball = new Ball(100, HEIGHT/2 - 1);
    }

    public static void main(String[] args) {
        initFrame();

    }
    public void tick() {
        player.tick();
        enemy.tick();
        ball.tick();
    }
    public void render () {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = layer.getGraphics();

        g.setColor(Color.black);
        g.fillRect(0,0, WIDTH, HEIGHT);

        player.render(g);
        enemy.render(g);
        ball.render(g);

        scoreEnemy(g);
        scorePlayer(g);

        g = bs.getDrawGraphics();
        g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        bs.show();

    }

    public void scoreEnemy(Graphics g) {
        g.setFont (new Font("Arial", Font.PLAIN, 10));
        g.setColor(Color.white);
        g.drawString("Enemy: " + Ball.countEnemy, 0, HEIGHT/2 - 10);


    }
    public void scorePlayer(Graphics g) {
        g.setFont (new Font("Arial", Font.PLAIN, 10));
        g.setColor(Color.white);
        g.drawString("Player: " + Ball.countPlayer, 0, HEIGHT/2 -20);

    }

    public void run() {
        while (true) {
            tick();
            render();
            requestFocus();
            try {
                Thread.sleep(1000 / 60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    public static void initFrame () {
        Game game = new Game();
        JFrame frame = new JFrame("PONG");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        new Thread(game).start();
    }




    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = true;

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            player.right = false;

        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            player.left = false;
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


}
