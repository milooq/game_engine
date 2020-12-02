package com.company.physics;

import com.company.Vec2;

public abstract class Body {
    protected Vec2 position;
    protected Vec2 velocity;

    public abstract void update();
}
