import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {
	public static int width = 300;
	public static int height = width * 9 / 16;
	public static int scale = 3;
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	private Keyboard keyboard;
	public static Player player;

	public Game() {
		Dimension dimension = new Dimension(width * scale, height * scale);
		setPreferredSize(dimension);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setPreferredSize(dimension);
		frame.setTitle("title");
		frame.add(this);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		player = new Player();
		keyboard = new Keyboard();
		addKeyListener(keyboard);
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		final double  ns = 1000000000.0 / 60.0;
		double delta = 0;
		int ticks = 0;
		while (running) {
			long thisTime = System.nanoTime();
			delta += (thisTime - lastTime) / ns;
			lastTime = thisTime;
			while( delta >= 1) {
				tick();
				ticks++;
				if (ticks%60 == 0) {
				}
				delta--;
			}
			render();
		}
	}

	private void tick() {
		keyboard.update();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		g.setColor(Color.RED);
		g.drawRect(player.x, player.y, player.width, player.height);
		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game().start();
	}
}
