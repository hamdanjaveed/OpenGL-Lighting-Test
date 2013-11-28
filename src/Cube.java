import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Cube {

	private final Vector3f position;
	private Plane[] faces;

	public Cube(Vector3f position, float sideLength) {
		this.position = position;
		faces = new Plane[6];
		// front
		faces[0] = new Plane(position, new Vector3f(0, 0, 0), new Vector3f(sideLength, 0, 0), new Vector3f(sideLength, sideLength, 0), new Vector3f(0, sideLength, 0));
		// right
		faces[1] = new Plane(position, new Vector3f(sideLength, 0, 0), new Vector3f(sideLength, 0, -sideLength), new Vector3f(sideLength, sideLength, -sideLength), new Vector3f(sideLength, sideLength, 0));
		// back
		faces[2] = new Plane(position, new Vector3f(sideLength, 0, -sideLength), new Vector3f(0, 0, -sideLength), new Vector3f(0, sideLength, -sideLength), new Vector3f(sideLength, sideLength, -sideLength));
		// left
		faces[3] = new Plane(position, new Vector3f(0, 0, -sideLength), new Vector3f(0, 0, 0), new Vector3f(0, sideLength, 0), new Vector3f(0, sideLength, -sideLength));
		// top
		faces[4] = new Plane(position, new Vector3f(0, sideLength, 0), new Vector3f(sideLength, sideLength, 0), new Vector3f(sideLength, sideLength, -sideLength), new Vector3f(0, sideLength, -sideLength));
		// bottom
		faces[5] = new Plane(position, new Vector3f(0, 0, 0), new Vector3f(0, 0, -sideLength), new Vector3f(sideLength, 0, -sideLength), new Vector3f(sideLength, 0, 0));
	}

	public void draw() {
		glPushMatrix(); {
			glColor3f(1.0f, 1.0f, 1.0f);
			glTranslatef(position.x, position.y, position.z);
			for (Plane face : faces) {
				face.draw();
			}
		}glPopMatrix();
	}

}
