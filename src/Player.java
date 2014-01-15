
public class Player {

	public int x;
	public int y;
	public int width;
	public int height;

	public Player() {
		width = 16 * Game.scale;
		height = 16 * Game.scale;
		x = ((Game.width * Game.scale) / 2) - (width / 2);
		y = ((Game.height * Game.scale) / 2) - (height / 2);
	}
}
