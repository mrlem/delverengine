package com.interrupt.dungeoneer.editor.gizmos;

import com.badlogic.gdx.math.Vector3;
import com.interrupt.dungeoneer.editor.gfx.Draw;
import com.interrupt.dungeoneer.entities.triggers.Trigger;

@GizmoFor(target = Trigger.class)
public class TriggerGizmo extends EntityGizmo {
    public TriggerGizmo(Trigger entity) {
        super(entity);
    }

    @Override
    public void draw() {
        super.draw();

        Trigger trigger = (Trigger) entity;
        Vector3 boundingBoxCenter = new Vector3(trigger.x, trigger.z - 0.5f + (trigger.collision.z / 2), trigger.y);
        Vector3 boundingBoxSize = new Vector3(trigger.collision.x, trigger.collision.z / 2, trigger.collision.y);
        Draw.wireCube(boundingBoxCenter, boundingBoxSize);
    }
}
