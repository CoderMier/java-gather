package com.qdm.proxy.jdk;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Random;

public class SnakeGame extends JPanel implements ActionListener {
    private int width;
    private int height;
    private int[][] board;
    private int[][] currentPiece;
    private int pieceX, pieceY;
    private int[][][] pieces;
    private Random random;
    private int score;
    private Timer timer;
    private final int DELAY = 400;
    private final int BLOCK_SIZE = 30;
    private final int BOARD_WIDTH = 10;
    private final int BOARD_HEIGHT = 20;
    private boolean inGame;
    private int speedLevel; // 添加速度级别变量
    private int[][] nextPiece; // 新增变量来存储下一个方块

    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        this.currentPiece = new int[4][2];
        this.random = new Random();
        this.score = 0;
        this.speedLevel = 1; // 初始化速度级别

        this.pieces = new int[][][]{
            { {0, 0}, {1, 0}, {2, 0}, {3, 0} }, // I
            { {0, 0}, {1, 0}, {1, 1}, {2, 1} }, // Z
            { {0, 1}, {1, 1}, {1, 0}, {2, 0} }, // S
            { {0, 0}, {1, 0}, {2, 0}, {2, 1} }, // J
            { {0, 1}, {1, 1}, {2, 1}, {2, 0} }, // L
            { {0, 0}, {1, 0}, {1, 1}, {2, 1} }, // T
            { {0, 0}, {0, 1}, {1, 0}, {1, 1} }  // O
        };

        this.nextPiece = new int[4][2]; // 初始化下一个方块
        initGame();
    }

    private void initGame() {
        addKeyListener(new TAdapter());
        setBackground(Color.decode("#282c34")); // 修改背景颜色
        setFocusable(true);
        setPreferredSize(new Dimension(BOARD_WIDTH * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE));
        loadLevel();
        timer = new Timer(DELAY, this);
        timer.start();
        inGame = true;
    }

    private void loadLevel() {
        loadNextPiece(); // 确保在游戏开始前加载下一个方块
        newPiece();
    }

    private void loadNextPiece() {
        int num = random.nextInt(7);
        nextPiece = pieces[num];
    }

    private void newPiece() {
        currentPiece = nextPiece;
        pieceX = BOARD_WIDTH / 2;
        pieceY = 0;
        loadNextPiece(); // 更新下一个方块
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        if (inGame) {
            // 绘制边界线
            g.setColor(Color.WHITE);
            g.drawRect(0, 0, BOARD_WIDTH * BLOCK_SIZE, BOARD_HEIGHT * BLOCK_SIZE);

            for (int y = 0; y < BOARD_HEIGHT; y++) {
                for (int x = 0; x < BOARD_WIDTH; x++) {
                    if (board[y][x] != 0) {
                        drawBlock(g, x, y, board[y][x]);
                    }
                }
            }

            for (int i = 0; i < 4; i++) {
                int x = currentPiece[i][0] + pieceX;
                int y = currentPiece[i][1] + pieceY;
                drawBlock(g, x, y, 1);
            }

            Toolkit.getDefaultToolkit().sync();

            // 绘制分数和速度
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18)); // 修改字体和大小
            g.drawString("Score: " + score, 10, 30); // 调整位置
            g.drawString("Speed: " + speedLevel, 10, 60); // 调整位置

            // 绘制下一个方块
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            g.drawString("Next Piece:", BOARD_WIDTH * BLOCK_SIZE + 20, 30); // 显示标题
            for (int i = 0; i < 4; i++) {
                int x = nextPiece[i][0]; // 调整x坐标以确保方块显示在游戏区域之外
                int y = nextPiece[i][1]; // 调整y坐标以确保方块显示在游戏区域之外
                drawBlock(g, x + 5, y + 2, 1, true); // 新增参数来区分下一个方块的绘制
            }

        } else {
            gameOver(g);
        }
    }

    private void drawBlock(Graphics g, int x, int y, int color) {
        Color colors[] = {new Color(0, 0, 0), new Color(255, 87, 51),
                          new Color(76, 175, 80), new Color(33, 150, 243), 
                          new Color(255, 235, 59), new Color(233, 30, 99), 
                          new Color(0, 188, 212), new Color(255, 152, 0)};

        Color colorVal = colors[color];
        g.setColor(colorVal);
        g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        g.setColor(colorVal.brighter());
        g.drawLine(x * BLOCK_SIZE, y * BLOCK_SIZE, x * BLOCK_SIZE, y * BLOCK_SIZE + BLOCK_SIZE - 1);
        g.drawLine(x * BLOCK_SIZE, y * BLOCK_SIZE, x * BLOCK_SIZE + BLOCK_SIZE - 1, y * BLOCK_SIZE);
        g.setColor(colorVal.darker());
        g.drawLine(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + BLOCK_SIZE - 1, 
             x * BLOCK_SIZE + BLOCK_SIZE - 1, y * BLOCK_SIZE + BLOCK_SIZE - 1);
        g.drawLine(x * BLOCK_SIZE + BLOCK_SIZE - 1, y * BLOCK_SIZE + 1, 
             x * BLOCK_SIZE + BLOCK_SIZE - 1, y * BLOCK_SIZE + BLOCK_SIZE - 1);
    }

    private void drawBlock(Graphics g, int x, int y, int color, boolean isNextPiece) {
        Color colors[] = {new Color(0, 0, 0), new Color(255, 87, 51),
                      new Color(76, 175, 80), new Color(33, 150, 243), 
                      new Color(255, 235, 59), new Color(233, 30, 99), 
                      new Color(0, 188, 212), new Color(255, 152, 0)};

        Color colorVal = colors[color];
        g.setColor(colorVal);
        if (isNextPiece) {
            g.fillRect(x * BLOCK_SIZE + BOARD_WIDTH * BLOCK_SIZE + 20, y * BLOCK_SIZE + 40, BLOCK_SIZE, BLOCK_SIZE); // 调整绘制位置
        } else {
            g.fillRect(x * BLOCK_SIZE, y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
        }
        g.setColor(colorVal.brighter());
        if (isNextPiece) {
            g.drawLine(x * BLOCK_SIZE + BOARD_WIDTH * BLOCK_SIZE + 20, y * BLOCK_SIZE + 40, x * BLOCK_SIZE + BOARD_WIDTH * BLOCK_SIZE + 20, y * BLOCK_SIZE + 40 + BLOCK_SIZE - 1);
            g.drawLine(x * BLOCK_SIZE + BOARD_WIDTH * BLOCK_SIZE + 20, y * BLOCK_SIZE + 40, x * BLOCK_SIZE + BOARD_WIDTH * BLOCK_SIZE + 20 + BLOCK_SIZE - 1, y * BLOCK_SIZE + 40);
        } else {
            g.drawLine(x * BLOCK_SIZE, y * BLOCK_SIZE, x * BLOCK_SIZE, y * BLOCK_SIZE + BLOCK_SIZE - 1);
            g.drawLine(x * BLOCK_SIZE, y * BLOCK_SIZE, x * BLOCK_SIZE + BLOCK_SIZE - 1, y * BLOCK_SIZE);
        }
        g.setColor(colorVal.darker());
        if (isNextPiece) {
            g.drawLine(x * BLOCK_SIZE + 1 + BOARD_WIDTH * BLOCK_SIZE + 20, y * BLOCK_SIZE + 40 + BLOCK_SIZE - 1, 
                     x * BLOCK_SIZE + BLOCK_SIZE - 1 + BOARD_WIDTH * BLOCK_SIZE + 20, y * BLOCK_SIZE + 40 + BLOCK_SIZE - 1);
            g.drawLine(x * BLOCK_SIZE + BLOCK_SIZE - 1 + BOARD_WIDTH * BLOCK_SIZE + 20, y * BLOCK_SIZE + 1 + 40, 
                     x * BLOCK_SIZE + BLOCK_SIZE - 1 + BOARD_WIDTH * BLOCK_SIZE + 20, y * BLOCK_SIZE + 40 + BLOCK_SIZE - 1);
        } else {
            g.drawLine(x * BLOCK_SIZE + 1, y * BLOCK_SIZE + BLOCK_SIZE - 1, 
                     x * BLOCK_SIZE + BLOCK_SIZE - 1, y * BLOCK_SIZE + BLOCK_SIZE - 1);
            g.drawLine(x * BLOCK_SIZE + BLOCK_SIZE - 1, y * BLOCK_SIZE + 1, 
                     x * BLOCK_SIZE + BLOCK_SIZE - 1, y * BLOCK_SIZE + BLOCK_SIZE - 1);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            moveDown();
        } else {
            // 检查是否点击了重新开始游戏的按钮
            if (e.getSource() instanceof JButton) {
                JButton button = (JButton) e.getSource();
                if (button.getText().equals("Restart")) {
                    restartGame();
                }
            }
        }

        repaint();
    }

    private void moveDown() {
        if (!tryMove(currentPiece, pieceX, pieceY + 1)) {
            placePiece();
            removeFullLines();
            if (!inGame) {
                timer.stop();
            }
        }
    }

    private void placePiece() {
        for (int i = 0; i < 4; i++) {
            int x = currentPiece[i][0] + pieceX;
            int y = currentPiece[i][1] + pieceY;
            board[y][x] = 1;
        }

        newPiece();
        // 检查游戏是否结束
        if (!tryMove(currentPiece, pieceX, pieceY)) {
            inGame = false;
        }
    }

    private void removeFullLines() {
        int numFullLines = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean lineIsFull = true;

            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == 0) {
                    lineIsFull = false;
                    break;
                }
            }

            if (lineIsFull) {
                numFullLines++;
                for (int k = i; k > 0; k--) {
                    System.arraycopy(board[k - 1], 0, board[k], 0, BOARD_WIDTH);
                }
                Arrays.fill(board[0], 0);
            }
        }

        if (numFullLines > 0) {
            score += numFullLines * 10;
            speedLevel = 1 + score / 100; // 根据分数调整速度级别
            timer.setDelay(DELAY / speedLevel); // 调整定时器速度
        }
    }

    private boolean tryMove(int[][] newPiece, int newX, int newY) {
        for (int i = 0; i < 4; i++) {
            int x = newPiece[i][0] + newX;
            int y = newPiece[i][1] + newY;

            if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) {
                return false;
            }
            if (y < 0) {
                continue;
            }
            if (board[y][x] != 0) {
                return false;
            }
        }

        currentPiece = newPiece;
        pieceX = newX;
        pieceY = newY;
        return true;
    }

    private void rotateLeft() {
        int[][] rotatedPiece = new int[4][2];
        for (int i = 0; i < 4; i++) {
            rotatedPiece[i][0] = -currentPiece[i][1];
            rotatedPiece[i][1] = currentPiece[i][0];
        }
        tryMove(rotatedPiece, pieceX, pieceY);
    }

    private void rotateRight() {
        int[][] rotatedPiece = new int[4][2];
        for (int i = 0; i < 4; i++) {
            rotatedPiece[i][0] = currentPiece[i][1];
            rotatedPiece[i][1] = -currentPiece[i][0];
        }
        tryMove(rotatedPiece, pieceX, pieceY);
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_P) {
                pause();
            }

            if (!inGame) {
                return;
            }

            if (key == KeyEvent.VK_LEFT) {
                tryMove(currentPiece, pieceX - 1, pieceY);
            }

            if (key == KeyEvent.VK_RIGHT) {
                tryMove(currentPiece, pieceX + 1, pieceY);
            }

            if (key == KeyEvent.VK_DOWN) {
                moveDown();
            }

            if (key == KeyEvent.VK_UP) {
                rotateRight();
            }

            if (key == KeyEvent.VK_SPACE) {
                rotateLeft();
            }

            // 新增重新开始游戏的按键事件处理
            if (key == KeyEvent.VK_R) {
                restartGame();
            }
        }
    }

    // 新增重新开始游戏的方法
    private void restartGame() {
        score = 0;
        speedLevel = 1;
        inGame = true;
        board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        loadLevel();
        timer.start();
    }

    private void pause() {
        inGame = !inGame;
        if (inGame) {
            timer.start();
        } else {
            timer.stop();
        }
    }

    private void gameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Arial", Font.BOLD, 18); // 修改字体和大小
        FontMetrics metr = getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (width * BLOCK_SIZE - metr.stringWidth(msg)) / 2, height * BLOCK_SIZE / 2);
        // 显示分数
        String scoreMsg = "Score: " + score;
        g.drawString(scoreMsg, (width * BLOCK_SIZE - metr.stringWidth(scoreMsg)) / 2, height * BLOCK_SIZE / 2 + 30); // 调整位置
        // 显示速度
        String speedMsg = "Speed: " + speedLevel;
        g.drawString(speedMsg, (width * BLOCK_SIZE - metr.stringWidth(speedMsg)) / 2, height * BLOCK_SIZE / 2 + 60); // 调整位置

        // 添加游戏结束弹框
        int option = JOptionPane.showOptionDialog(this, "Game Over\nScore: " + score + "\nSpeed: " + speedLevel, "Game Over",
                JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE, null,
                new Object[]{"Restart", "Exit"}, "Restart");
        if (option == JOptionPane.YES_OPTION) {
            restartGame();
        } else {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tetris Game");
        SnakeGame game = new SnakeGame(30, 30, new int[][]{{1, 1}, {1, 0}});
        frame.add(game);
        frame.pack();
        frame.setLocationRelativeTo(null); // 使窗口居中显示
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}