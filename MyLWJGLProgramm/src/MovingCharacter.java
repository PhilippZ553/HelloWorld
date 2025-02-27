import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

public class MovingCharacter {

    private long window;
    private float characterX = 0.0f;
    private float characterY = 0.0f;
    private float characterSpeed = 0.01f;

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(640, 480, "Moving Character", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            glViewport(0, 0, width, height);
        });

        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_UP && action == GLFW_PRESS) {
                characterY += characterSpeed;
            }
            if (key == GLFW_KEY_DOWN && action == GLFW_PRESS) {
                characterY -= characterSpeed;
            }
            if (key == GLFW_KEY_LEFT && action == GLFW_PRESS) {
                characterX -= characterSpeed;
            }
            if (key == GLFW_KEY_RIGHT && action == GLFW_PRESS) {
                characterX += characterSpeed;
            }
        });

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    private void loop() {
        GL.createCapabilities();

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            drawCharacter();

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    private void drawCharacter() {
        glPushMatrix();
        glTranslatef(characterX, characterY, 0.0f);

        glBegin(GL_QUADS);
        glColor3f(1.0f, 0.0f, 0.0f); // Rot
        glVertex2f(-0.1f, 0.1f);
        glVertex2f(0.1f, 0.1f);
        glVertex2f(0.1f, -0.1f);
        glVertex2f(-0.1f, -0.1f);
        glEnd();

        glPopMatrix();
    }

    public static void main(String[] args) {
        new MovingCharacter().run();
    }
}