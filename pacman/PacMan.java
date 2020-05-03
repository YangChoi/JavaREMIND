package pacman;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PacMan extends Frame implements Runnable {
	private Image img, foodImg;
	private int sel = 2; // �̹��� sel
	private int x = 225, y = 225; // �̹��� ��ǥ
	private int mx, my; // ������
	private int[] foodX, foodY; // ���� ��ǥ 
	
	// �Ѹ� ���� : 5���� �Ѹ���. 
	int[] numX = new int[5];
	int[] numY = new int[5];
	public PacMan() {
		img = Toolkit.getDefaultToolkit().getImage("D:/Again/Review/src/pacman/pacman.png");
		foodImg = Toolkit.getDefaultToolkit().getImage("D:/Again/Review/src/pacman/foodImg.png");
		
		// ���� �̹��� ��ǥ�� : ���������� �Ѹ���. 
		foodX = new int[5];
		foodY = new int[5];
		for(int i = 0; i < foodX.length; i++) {
			foodX[i] = (int)(Math.random()*461)+20; // frame�� �ɸ��� �ʵ��� ���� 
			foodY[i] = (int)(Math.random()*461)+20; 
		}
		setBounds(700, 200, 500, 500);
		setVisible(true);
		setResizable(false);
		setTitle("�Ѹ�");

		// ������
		Thread t = new Thread(this);
		t.start();

		// Ű���� �̺�Ʈ
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				} else if (e.getKeyCode() == KeyEvent.VK_UP) {
					sel = 4;
					mx = 0;
					my -= 10;
				} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					sel = 6;
					mx = 0;
					my += 10;
				} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					sel = 2;
					mx = 10;
					my = 0;
				} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					sel = 0;
					mx -= 10;
					my = 0;
				}
				repaint();
			}
		});
		
		// â�ݱ�
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	} // pacMan constructor

	// �Ѹ� �׸���
	@Override
	public void paint(Graphics g) {
		// �Ѹ� ���� 
		for(int i = 0; i < foodX.length; i++) {
			g.drawImage(foodImg, foodX[i], foodY[i], this);
		}
		
		// �Ѹ�
		g.drawImage(img, x, y, x+50, y+50, // ȭ����ġ
					sel*50, 0, sel*50+50, 50, // �̹��� ��ġ 
					this);
	}

	// ������
	@Override
	public void run() {
		while (true) {
			if (sel % 2 == 0) {
				sel++;
			} else {
				sel--;
			}
			
			x += mx;
			y += my;
			// ������ �����̰� 
			
			if(x > 460) { // ������ ������ ���� 
				sel = 0; // �������� ���� 
				mx -= 10;
				my = 0;
			}else if(x < 15) { // ���� ������ ���� 
				sel = 2; // ���������� ���� 
				mx += 10;
				my = 0;
			}else if(y > 460) { // ���� ������ ����
				sel = 4; // �Ʒ��� ���� 
				mx = 0;
				my -= 10;
				
			}else if(y < 40) { // �Ʒ��� ������ ����
				sel = 6;// ���� ����.
				mx = 0;
				my += 10;
			}
			
			repaint();
			// ���� �����δ�

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// ���� �Դ� ����
			for(int i = 0; i < foodX.length; i++) {
				if(x+25 >= foodX[i]-5 &&  y+24 >= foodY[i]-5 && x+25 <= foodX[i] + 20 && y+25 <= foodY[i] + 20) {
					foodX[i] = foodY[i] = -10; // ���� ������ ������ ������ ������. 
				}
			}

		} // while

	}

	public static void main(String[] args) {
		PacMan pm = new PacMan();

	}

}
