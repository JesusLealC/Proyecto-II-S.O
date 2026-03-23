/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author jleal
 */
package gui;

import filesystem.Block;
import filesystem.SimulatedDisk;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class DiskVisualizer extends JPanel {
    private SimulatedDisk disk;
    private final int columns = 10;
    private final int cellSize = 26;
    private final int margin = 4;

    public DiskVisualizer(SimulatedDisk disk) {
        this.disk = disk;
        setBackground(Color.WHITE);
        updatePreferredSize();
    }

    public void setDisk(SimulatedDisk disk) {
        this.disk = disk;
        updatePreferredSize();
        repaint();
    }

    private void updatePreferredSize() {
        int totalBlocks = disk != null ? disk.getTotalCapacity() : 100;
        int rows = (int) Math.ceil(totalBlocks / (double) columns);
        int width = columns * (cellSize + margin) + margin;
        int height = rows * (cellSize + margin) + margin;
        setPreferredSize(new Dimension(width, height));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (disk == null) {
            return;
        }

        Block[] blocks = disk.getBlocks();

        for (int i = 0; i < blocks.length; i++) {
            int row = i / columns;
            int col = i % columns;
            int x = margin + col * (cellSize + margin);
            int y = margin + row * (cellSize + margin);

            g.setColor(blocks[i].getBlockColor());
            g.fillRect(x, y, cellSize, cellSize);

            if (i == disk.getHeadPosition()) {
                g.setColor(Color.RED);
            } else {
                g.setColor(Color.DARK_GRAY);
            }

            g.drawRect(x, y, cellSize, cellSize);
            g.drawString(String.valueOf(i), x + 6, y + 16);
        }
    }
}