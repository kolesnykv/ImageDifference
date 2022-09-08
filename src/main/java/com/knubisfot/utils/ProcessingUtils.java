package com.knubisfot.utils;

import com.knubisfot.Point;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class ProcessingUtils {
    public BufferedImage getDifferenceImage(BufferedImage input1, BufferedImage input2) {
        List<Point> points = new ArrayList<>();
        findDifferentPoints(input1, input2, points);
        List<List<Point>> groups = new ArrayList<>();
        formGroups(points, groups);
        buildRectangles(input2, groups);
        return input2;
    }

    private void findDifferentPoints(BufferedImage input1, BufferedImage input2, List<Point> points) {
        int w1 = input1.getWidth();
        int h1 = input1.getHeight();
        for (int j = 0; j < h1; j++) {
            for (int i = 0; i < w1; i++) {
                //Getting the RGB values of a pixel
                int pixel1 = input1.getRGB(i, j);
                int pixel2 = input2.getRGB(i, j);
                if (pixel1 != pixel2) {
                    points.add(new Point(i, j));
                }
            }
        }
    }

    private void buildRectangles(BufferedImage input2, List<List<Point>> groups) {
        for (List<Point> list : groups) {
            int mostLeft = Integer.MAX_VALUE;
            int mostTop = Integer.MAX_VALUE;
            int mostBottom = 0;
            int mostRight = 0;
            for (Point p : list) {
                mostLeft = Math.min(p.getX(), mostLeft);
                mostTop = Math.min(p.getY(), mostTop);
                mostRight = Math.max(p.getX(), mostRight);
                mostBottom = Math.max(p.getY(), mostBottom);
            }
            highlight(input2, mostLeft, mostTop, mostBottom, mostRight);
        }
    }

    private void formGroups(List<Point> points, List<List<Point>> groups) {
        while (!points.isEmpty()) {
            List<Point> group = new ArrayList<>();
            Point firstpoint = points.get(0);
            for (Point p : points) {
                if (calculateDistance(firstpoint.getX(), firstpoint.getY(), p.getX(), p.getY()) < 10) {
                    group.add(p);
                }
            }
            points.removeAll(group);
            mergeOrAddNewGroup(groups, group);
        }
    }

    private void mergeOrAddNewGroup(List<List<Point>> groups, List<Point> group) {
        boolean flag = true;
        for (List<Point> list : groups) {
            Point fmp = group.get(group.size() / 2);
            for(Point p: list) {
                if (calculateDistance(fmp.getX(), fmp.getY(), p.getX(), p.getY()) < 50) {
                    flag = false;
                }
            }
            if(!flag){
                list.addAll(group);
            }
        }
        if (flag) {
            groups.add(group);
        }
    }

    private double calculateDistance(int x, int y, int x1, int y1) {
        return Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2));
    }

    private void highlight(BufferedImage input2, int mostLeftPoint, int mostTop, int mostBottom, int mostRightPoint) {
        int width = mostRightPoint - mostLeftPoint;
        int height = mostBottom - mostTop;
        Graphics2D g2d = input2.createGraphics();
        g2d.setColor(Color.RED);
        g2d.drawRect(mostLeftPoint - 5, mostTop - 5, width + 10, height + 10);
        g2d.dispose();
    }
}
