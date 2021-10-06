package com.interrupt.dungeoneer.editor.handles;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.*;
import com.interrupt.dungeoneer.editor.Editor;
import com.interrupt.dungeoneer.editor.ui.Handles;

public class ArrowHandle extends Handle {
    public final Vector3 position = new Vector3();
    private final Quaternion rotation = new Quaternion();

    private final Color color = Color.WHITE.cpy();
    private final Color highlightColor = Color.WHITE.cpy();

    public ArrowHandle(Vector3 position, Vector3 eulerAngles) {
        super();
        position.set(position);
        rotation.setEulerAngles(eulerAngles.y, eulerAngles.x, eulerAngles.z);
    }

    public ArrowHandle(Vector3 position, Quaternion rotation) {
        super();
        this.position.set(position);
        this.rotation.set(rotation);
    }

    public ArrowHandle(float x, float y, float z, float yaw, float pitch, float roll) {
        super();
        position.set(x, y, z);
        rotation.setEulerAngles(yaw, pitch, roll);
    }

    private Vector3 axis = new Vector3();
    @Override
    public void draw() {
        super.draw();
        Camera camera = Editor.app.camera;

        float distance = Vector3.dst(
            position.x,
            position.y,
            position.z,
            camera.position.x,
            camera.position.z,
            camera.position.y
        );

        float pixelScale = 1f / Gdx.graphics.getWidth();

        axis.set(camera.position)
            .sub(position)
            .nor();

        Handles.setColor(getDrawColor());
        Handles.drawCube(position, 0.25f);
        //Handles.drawCube(position, 25f * pixelScale * distance);
        Handles.setColor(Color.WHITE);
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public Color getColor() {
        return this.color;
    }

    public void setHighlightColor(Color color) {
        this.highlightColor.set(color);
    }

    public Color getHighlightColor() {
        return this.highlightColor;
    }

    private Color getDrawColor() {
        if (getSelected() || getHovered()) return highlightColor;

        return color;
    }

    private final Vector3 intersection = new Vector3();
    private final Plane plane = new Plane();
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!getSelected()) return false;

        Camera camera = Editor.app.camera;
        plane.set(
            position.x,
            position.z,
            position.y,
            camera.direction.x,
            camera.direction.y,
            camera.direction.z
        );
        Intersector.intersectRayPlane(
            camera.getPickRay(
                screenX,
                screenY
            ),
            plane,
            intersection
        );

        position.set(intersection.x, intersection.z, intersection.y);
        change();

        return false;
    }
}
