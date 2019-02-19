/**
 * Programlama Laboratuvarı II - Proje II
 *
 * @author BerkayEzdemir 150202041
 * @version 1.0.0
 * @since 07-04-2017
 */
package main.java;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Quad Tree ile ilgili işlemlerin yapıldığı Tuval sınıfı
 */
public class Canvas extends JFrame {

    private static final Canvas sgCanvas = new Canvas();

    private static Node root;
    public static int name = 0;
    public static int factorX, factorY;
    public static int[][] points = new int[4][2];
    public static int circleR;
    int x, y;
    public static int clickedX, clickedY;
    private static LinkedList<Line> lines = new LinkedList<>();
    private static LinkedList<Node> nodes = new LinkedList<>();

    public static boolean isMoved;
    public static boolean isClicked;

    public Canvas() {
        initComponents();
    }

    public void setIsMoved(boolean isMoved) {
        Canvas.isMoved = isMoved;
    }

    public void setIsClicked(boolean isClicked) {
        Canvas.isClicked = isClicked;
    }

    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

    /**
     * Pointer Quad Tree'yi tanımlamak için oluşturulan class
     */
    private class Node {

        private final int x;
        private final int y;
        private final int x1;
        private final int x2;
        private final int y1;
        private final int y2;

        public int rank;

        Node SE, SW, NE, NW;

        public Node(int x, int y, int x1, int y1, int x2, int y2) {
            this.x = x;
            this.y = y;
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    /**
     * Pointer Quad Tree'ye Node ekleme işini yapar
     *
     * @param x Tıklanan noktadaki x
     * @param y Tıklanan noktadaki y
     */
    public void addNode(int x, int y) {
        int rank = 0;

        if (root == null) {
            //System.out.println("First Node!");
            Node newNode = new Node(x, y, 0, 0, 600, 600);
            root = newNode;
            newNode.rank = 0;

            addLine(x, newNode.y1, x, newNode.y2, Color.yellow, Color.black, x, y, name);
            addLine(newNode.x1, y, newNode.x2, y, Color.yellow, Color.black, x, y, name);
            name++;

        } else {
            Node focusedNode = root;

            Node parent;

            while (true) {
                parent = focusedNode;
                rank++;

                if (x < parent.x && y < parent.y) {
                    focusedNode = focusedNode.NW;

                    if (focusedNode == null) {
                        Node newNode = new Node(x, y, parent.x1, parent.y1, parent.x, parent.y);
                        parent.NW = newNode;
                        newNode.rank = rank;

                        addLine(x, newNode.y1, x, newNode.y2, Color.yellow, Color.orange, x, y, name);
                        addLine(newNode.x1, y, newNode.x2, y, Color.yellow, Color.orange, x, y, name);
                        name++;
                        //System.out.println("NW" + " " + newNode.rank);

                        return;
                    }

                } else if (x > parent.x && y < parent.y) {
                    focusedNode = focusedNode.NE;

                    if (focusedNode == null) {
                        Node newNode = new Node(x, y, parent.x, parent.y1, parent.x2, parent.y);
                        parent.NE = newNode;
                        //System.out.println("NE");
                        newNode.rank = rank;

                        addLine(x, newNode.y1, x, newNode.y2, Color.yellow, Color.orange, x, y, name);
                        addLine(newNode.x1, y, newNode.x2, y, Color.yellow, Color.orange, x, y, name);
                        name++;
                        return;
                    }

                } else if (x < parent.x && y > parent.y) {
                    focusedNode = focusedNode.SW;

                    if (focusedNode == null) {
                        Node newNode = new Node(x, y, parent.x1, parent.y, parent.x, parent.y2);
                        parent.SW = newNode;
                        //System.out.println("SW");
                        newNode.rank = rank;

                        addLine(x, newNode.y1, x, newNode.y2, Color.yellow, Color.orange, x, y, name);
                        addLine(newNode.x1, y, newNode.x2, y, Color.yellow, Color.orange, x, y, name);
                        name++;
                        return;
                    }

                } else if (x > parent.x && y > parent.y) {
                    Node newNode = new Node(x, y, parent.x, parent.y, parent.x2, parent.y2);
                    focusedNode = focusedNode.SE;

                    if (focusedNode == null) {
                        parent.SE = newNode;
                        //System.out.println("SE");
                        newNode.rank = rank;

                        addLine(x, newNode.y1, x, newNode.y2, Color.yellow, Color.orange, x, y, name);
                        addLine(newNode.x1, y, newNode.x2, y, Color.yellow, Color.orange, x, y, name);
                        name++;
                        return;
                    }

                } else {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(new JFrame(),
                            "You can't add nodes to same places.",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }
        }
    }

    /**
     * Pointer Quad Tree üzerinde preorder dolaşma yapar
     *
     * @param rootS Preorder kökü
     */
    public void searchNode(Node rootS) {
        Node focusedNode = rootS;
        Graphics g = getGraphics();
        //Random rnd = new Random();
        //Color clr = new Color(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));

        if (rootS != null) {
           // İki nokta arası uzaklık denklemi
            if(Math.sqrt(Math.pow(focusedNode.x - clickedX , 2) + Math.pow(focusedNode.y - clickedY, 2)) <= circleR) {
                //System.out.println("İçerde");
                nodes.add(focusedNode);
                //g.setColor(clr);
                g.setColor(Color.red);
                g.fillOval(focusedNode.x - 3, focusedNode.y - 3, 6, 6);
            }

            searchNode(focusedNode.NW);
            searchNode(focusedNode.NE);
            searchNode(focusedNode.SW);
            searchNode(focusedNode.SE);

        }
    }

    /**
     * Ekrandaki nodeların çizgilerini çizmek için gerekli bilgileri saklayan
     * class
     */
    public static class Line {

        public final int x1;
        public final int x2;
        public final int y1;
        public final int y2;
        public Color colorDot;
        public Color colorLine;
        public final int x;
        public final int y;
        public final int name;

        public Line(int x1, int x2, int y1, int y2, Color colorDot, Color colorLine, int x, int y, int name) {
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
            this.colorDot = colorDot;
            this.colorLine = colorLine;
            this.x = x;
            this.y = y;
            this.name = name;
        }
    }

    /**
     * Nodelar için ekrandaki çizgileri çizen fonksiyon
     *
     * @param x1 Çizilecek çizginin başlangıç x'i
     * @param y1 Çizilecek çizginin başlangıç y'si
     * @param x2 Çizilecek çizginin bitiş x'i
     * @param y2 Çizilecek çizginin bitiş y'si
     * @param colorDot Çizilecek çizgideki nokranın rengi
     * @param colorLine Çizilecek çizginin rengi
     * @param x Node'un belirtildiği noktanın x'i
     * @param y Node'un belirtildiği noktanın y'si
     * @param name Noktanın numarası
     */
    public void addLine(int x1, int x2, int y1, int y2, Color colorDot, Color colorLine, int x, int y, int name) {
        lines.add(new Line(x1, y1, x2, y2, colorDot, colorLine, x, y, name));
    }

    /**
     * Nodeların ekrandaki çizgilerini ve Node'u silen fonksiyon
     */
    public void deleteLine() {
        lines.clear();
        root = null;
        name = 0;

        System.out.println("deleteLine");
        repaint();
    }

    /**
     * Canvas classı için oluşturulan statik nesneyi göndermek için oluşturulan
     * fonksiyon
     *
     * @return Canvas sınıfına ait statik bir nesne geriye döndürür
     */
    public static Canvas getInstance() {
        return sgCanvas;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Canvas");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setFocusCycleRoot(false);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                formMouseMoved(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 512, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        //System.out.println("isClicked : " + isClicked + "isMoved : " + isMoved);
        //System.out.println("CircleR: " + circleR);
        x = evt.getPoint().x;
        y = evt.getPoint().y;

        if (isClicked) {

            System.out.println(x + ", " + y);

            //Color randomColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
            addNode(x, y);
            //drawLines(g);
            repaint();
        } else if (isMoved) {
            Graphics g = getGraphics();

            clickedX = x;
            clickedY = y;
            g.drawOval(x - circleR, y - circleR, circleR * 2, circleR * 2);
            searchNode(root);

            // Daire içerisindeki nodeları xlerine göre sıralıyor
            Collections.sort(nodes, (Node o1, Node o2) -> {
                if (o1.x < o2.x) {
                    return -1;
                }
                if (o1.x > o2.x) {
                    return 1;
                }
                return 0;
            });

            System.out.println("-----------------");
            nodes.stream().forEach((node) -> {
                System.out.println("x: " + node.x + " y: " + node.y);
            });
            System.out.println("Size: " + nodes.size());
            nodes.clear();

        }

    }//GEN-LAST:event_formMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        repaint();
    }//GEN-LAST:event_formWindowActivated

    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        // System.out.println(evt.getWheelRotation());

        if (evt.getWheelRotation() > 0) {
            circleR--;

        } else {
            circleR++;

        }
    }//GEN-LAST:event_formMouseWheelMoved

    private void formMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseMoved
        if (isMoved) {
            Graphics g = getGraphics();

            x = evt.getPoint().x;
            y = evt.getPoint().y;

            g.drawOval(x - circleR, y - circleR, circleR * 2, circleR * 2);
            // Ekran yenilenme hızını düşürmek için kullanıldı
            try {
                Thread.sleep(150);
            } catch (InterruptedException ex) {
                Logger.getLogger(Canvas.class.getName()).log(Level.SEVERE, null, ex);
            }
            repaint();
        }

    }//GEN-LAST:event_formMouseMoved

    /**
     * Ekranda rastgele Nodelar oluşturmak için kullanılan fonksiyon
     *
     * @param value Rastgele oluşturulacak Nodeların sayısı
     */
    public void randomNodes(int value) {
        int randX, randY;

        root = null;
        lines.clear();
        name = 0;

        Random randomGenerator = new Random();

        for (int i = 0; i < value; i++) {
            randX = randomGenerator.nextInt(512);
            randY = randomGenerator.nextInt(512);

            addNode(randX, randY);
        }
        repaint();
    }

    /**
     * Ekrana çizim işlemini yapan kütüphane fonksiyonu
     *
     * @param g Graphics nesnesi
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //System.out.println("paint");
        lines.stream().map((line) -> {
            g.setColor(line.colorLine);
            return line;
        }).map((line) -> {
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
            return line;
        }).map((line) -> {
            g.setColor(line.colorDot);
            return line;
        }).map((line) -> {
            g.fillOval(line.x - 3, line.y - 3, 6, 6);
            return line;
        }).forEach((line) -> {
            g.setColor(Color.black);
            g.drawString(line.name + "", line.x + 5, line.y + 5);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
