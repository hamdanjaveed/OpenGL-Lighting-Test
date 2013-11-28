import org.lwjgl.util.vector.Vector3f;

import static org.lwjgl.opengl.GL11.*;

public class Plane {

	private Vector3f bottomLeft, bottomRight, topRight, topLeft;
	private Vector3f position; // position refers to bottom left
	private Vector3f normal;

	public Plane(Vector3f position, Vector3f bottomLeft, Vector3f bottomRight, Vector3f topLeft, Vector3f topRight) {
		this.position = position;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
		this.topLeft = topLeft;
		this.topRight = topRight;
		normal = Vector3f.cross(bottomRight, topLeft, null);
		if (normal.length() != 0)
			normal.normalise();
	}

	public void draw() {
		glPushMatrix(); {
			glTranslatef(position.x, position.y, position.z);
			glColor3f(0.5f, 0.5f, 0.5f);
			glBegin(GL_QUADS); {
				glNormal3f(normal.x, normal.y, normal.z);
				glVertex3f(bottomLeft.x, bottomLeft.y, bottomLeft.z);
				glVertex3f(bottomRight.x, bottomRight.y, bottomRight.z);
				glVertex3f(topLeft.x, topLeft.y, topLeft.z);
				glVertex3f(topRight.x, topRight.y, topRight.z);
			} glEnd();
			float x = (bottomLeft.x + bottomRight.x) / 2.0f;
			float y = (bottomLeft.y + topLeft.y) / 2.0f;
			float z = (bottomLeft.z + bottomRight.z) / 2.0f;
			Vector3f start = new Vector3f(x, y, z);
			glColor3f(1, 0, 0);
			glBegin(GL_LINES); {
				glVertex3f(start.x, start.y, start.z);
				glVertex3f(normal.x, normal.y, normal.z);
			} glEnd();
		} glPopMatrix();
	}

}
