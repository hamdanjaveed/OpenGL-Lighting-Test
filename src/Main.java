import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.vector.Vector3f;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;

public class Main {

	private long lastFrameSystemTime;
	private int framesPerSecond;
	private long timeOfLastFramePerSecondUpdate;

	private Camera camera;

	private Cube cube = new Cube(new Vector3f(0, 0, 0), 1);

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		// create display
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			Display.destroy();
			System.exit(1);
		}

		initGL();

		lastFrameSystemTime = getSystemTimeInMilliseconds();
		timeOfLastFramePerSecondUpdate = getSystemTimeInMilliseconds();

		while(!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			int delta = getDelta();
			update(delta);
			renderGL();
			Display.update();
			Display.sync(60);
		}

		Display.destroy();
		System.exit(0);
	}

	private void renderGL() {
		// clear the screen and depth buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		//renderTriangle();
		renderCube();

		glLoadIdentity();
		camera.lookThrough();
	}

	private void renderCube() {
		cube.draw();
	}

	private void initGL() {
		// init open gl (import static org.lwjgl.opengl.GL11.*;)
		// (import static org.lwjgl.util.glu.GLU.gluPerspective;)
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(75.0f, 800.0f / 600.0f, 0.001f, 1000);

		glMatrixMode(GL_MODELVIEW);
		// to 'turn on' depth
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

		glEnable(GL_LIGHTING);
		glEnable(GL_LIGHT0);
		glLightModel(GL_LIGHT_MODEL_AMBIENT, asFloatBuffer(0.05f, 0.05f, 0.05f, 1f));
		glEnable(GL_COLOR_MATERIAL);
		glColorMaterial(GL_FRONT_AND_BACK, GL_DIFFUSE);
		glLight(GL_LIGHT0, GL_POSITION, asFloatBuffer(0, 0, 0, 1));

		camera = new Camera(0, 0, 0);

		Mouse.setGrabbed(true);
	}

	private void update(int delta) {
		camera.yawBy(Mouse.getDX() * delta * Camera.YAW_AND_PITCH_SPEED);
		camera.pitchBy(-Mouse.getDY() * delta * Camera.YAW_AND_PITCH_SPEED);

		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			camera.walkForward(Camera.MOVE_SPEED * delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			camera.walkBackwards(Camera.MOVE_SPEED * delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			camera.strafeLeft(Camera.MOVE_SPEED * delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			camera.strafeRight(Camera.MOVE_SPEED * delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			camera.flyUp(Camera.MOVE_SPEED * delta);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			camera.flyDown(Camera.MOVE_SPEED * delta);
		}
	}

	private long getSystemTimeInMilliseconds() {
		// return the system time in ms
		return System.nanoTime() / 1000000;
	}

	private int getDelta() {
		long time = getSystemTimeInMilliseconds();
		int delta = (int) (time - lastFrameSystemTime);
		lastFrameSystemTime = time;
		return delta;
	}

	private void updateFramesPerSecond() {
		// if a second has passed
		if (getSystemTimeInMilliseconds() - timeOfLastFramePerSecondUpdate > 1000) {
			Display.setTitle("FPS: " + framesPerSecond);
			framesPerSecond = 0;
			timeOfLastFramePerSecondUpdate += 1000;
		}
		// increment framesPerSecond every frame
		framesPerSecond++;
	}

	private FloatBuffer asFloatBuffer(float x, float y, float z, float w) {
		float[] data = new float[] {
			x, y, z, w
		};
		FloatBuffer floatBuffer = BufferUtils.createFloatBuffer(data.length);
		floatBuffer.put(data);
		floatBuffer.flip();
		return floatBuffer;
	}

}
