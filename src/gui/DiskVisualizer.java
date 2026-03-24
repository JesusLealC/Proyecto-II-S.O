/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



package gui;

import filesystem.Block;
import filesystem.SimulatedDisk;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class DiskVisualizer extends JPanel {
    private SimulatedDisk disk;
    private final int columns = 10;
    private final int cellSize = 35; 
    private final int margin = 5;

    public DiskVisualizer(SimulatedDisk disk) {
        this.disk = disk;
        setBackground(new Color(34, 40, 49)); 
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

        if (disk == null || disk.getBlocks() == null) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Block[] blocks = disk.getBlocks();

        for (int i = 0; i < blocks.length; i++) {
            int row = i / columns;
            int col = i % columns;
            int x = margin + col * (cellSize + margin);
            int y = margin + row * (cellSize + margin);

            Color blockColor = blocks[i].getBlockColor();
            g2d.setColor(blockColor != null ? blockColor : new Color(57, 62, 70));
            g2d.fillRoundRect(x, y, cellSize, cellSize, 8, 8);

            if (i == disk.getHeadPosition()) {
                g2d.setColor(Color.RED);
                g2d.setStroke(new BasicStroke(2));
                g2d.drawRoundRect(x, y, cellSize, cellSize, 8, 8);
            }

            g2d.setColor(Color.WHITE);
            String text = String.format("%02d", i);
            FontMetrics fm = g2d.getFontMetrics();
            int textX = x + (cellSize - fm.stringWidth(text)) / 2;
            int textY = y + ((cellSize - fm.getHeight()) / 2) + fm.getAscent();
            g2d.drawString(text, textX, textY);
        }
    }
}