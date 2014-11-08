package com.telolahy.towerofhanoi.object;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Stack;

/**
 * Created by stephanohuguestelolahy on 11/2/14.
 */
public class Ring extends Sprite {

    private int mWeight;
    private Stack mStack; //this represents the stack that this ring belongs to
    private Sprite mTower;

    public Ring(int weight, float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.mWeight = weight;
    }

    public int getWeight() {
        return mWeight;
    }

    public Stack getStack() {
        return mStack;
    }

    public void setStack(Stack mStack) {
        this.mStack = mStack;
    }

    public Sprite getTower() {
        return mTower;
    }

    public void setTower(Sprite mTower) {
        this.mTower = mTower;
    }
}
