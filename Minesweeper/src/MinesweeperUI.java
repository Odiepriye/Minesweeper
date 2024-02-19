import javax.swing.*;
import java.awt.*;

public class MinesweeperUI extends JFrame {
    private Board board;
    private JButton[][] buttons;
    public MinesweeperUI() {
        setTitle("Minesweeper");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board = new Board();
        board.normal(); //pick difficulty.
        JPanel panel = new JPanel(new GridLayout(board.getRow(), board.getCol()));
        buttons = new JButton[board.getRow()][board.getCol()];
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                JButton button = createButton(i, j);
                buttons[i][j] = button;
                panel.add(button);
            }
        }
        add(panel);
        setVisible(true);
    }
    private JButton createButton(final int row, final int col) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(40, 40));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    board.leftClickTile((byte) row, (byte) col);
                    updateButtonState(row, col);
                    updateUI();
                    checkGameState();
                } else if (SwingUtilities.isRightMouseButton(evt)) {
                    if(board.viewFlags() != 0){
                    board.rightClickTile((byte) row, (byte) col);
                    }
                    updateButtonState(row, col);
                }
            }
        });
        return button;
    }
    private void updateButtonState(int row, int col) {
        Tile tile = board.tiles[row][col];
        JButton button = buttons[row][col];
        if (tile.isFlagged()) {
            button.setText("F");
        } else if (tile.isFlipped()) {
            if (tile.isBomb()) {
                checkGameState();
            } else {
                button.setText(Integer.toString(tile.getNumber()));
            }
        } else {
            button.setText("");
        }
    }
    private void updateUI(){
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                updateButtonState(i,j);
            }
        }
    }
    private void checkGameState() {
        if (board.isGameLost()) {
            JOptionPane.showMessageDialog(this, "You Lost!");
            board.restartGame();
            resetButtons();
        } else if (board.isGameWon()) {
            JOptionPane.showMessageDialog(this, "You Won!");
            board.restartGame();
            resetButtons();
        }
    }
    private void resetButtons() {
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }
    public static void main(String[] args) {
        System.out.println();
        SwingUtilities.invokeLater(MinesweeperUI::new);
    }
}
