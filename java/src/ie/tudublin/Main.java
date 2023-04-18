package ie.tudublin;

public class Main
{

	public static void audio1()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Audio1());
    }

	public static void particleScene()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Particle());
    }

	public static void tetris()
	{
		String[] a = {"MAIN"};
        processing.core.PApplet.runSketch( a, new Tetris());
    }
	
	public static void main(String[] args)
	{
		tetris();
		// audio1();
		// particleScene();
	}
	
}