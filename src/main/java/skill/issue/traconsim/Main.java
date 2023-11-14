package skill.issue.traconsim;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import skill.issue.dim2d.Superimposition;
import skill.issue.dim2d.text.TextRenderer;
import skill.issue.dim2d.text.VRCFont;
import skill.issue.traconsim.sim.fsd.FSDPipelineSupplier;
import skill.issue.traconsim.sim.fsd.impl.TestFSDImpl;
import skill.issue.traconsim.sim.objects.Owner;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
    public static final Owner POSITION = Owner.APP;
    private static long win;
    public static void init() throws Exception {
        System.out.println("Initiating GLFW and GL");
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) throw new IllegalStateException("Unable to initialize GLFW");
        win = glfwCreateWindow(800,600, "theaviationbee's TRACON Simulator", 0, 0);
        glfwMakeContextCurrent(win);
        glfwSwapInterval(1);
        glfwShowWindow(win);

        System.out.println("Initiating Superimposition rendering engine");
        Superimposition.init(100);
        System.out.println("Generating bitmaps for text rendering");
        TextRenderer.init();
        FSDPipelineSupplier.initializeFSD(new TestFSDImpl());
    }
    public static void loop() {
        GL.createCapabilities();
        glClearColor(0,0,0,1);
        while (!glfwWindowShouldClose(win)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            Renderer.doRenderTick();

            glfwSwapBuffers(win);
            glfwPollEvents();
        }
    }
    public static void shutdown() {
        System.out.println("Shutting down GLFW and GL");
        glfwTerminate();
        glfwSetErrorCallback(null);
        VRCFont.cleanupBitmaps();
        TextRenderer.cleanupAlias();
        FSDPipelineSupplier.terminateFSD();
    }
    public static void main(String[] args) throws Exception {
        init();
        loop();
        shutdown();
    }
}
