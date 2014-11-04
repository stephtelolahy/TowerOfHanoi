package com.telolahy.towerofhanoi;

import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Stack;

/**
 * Created by stephanohuguestelolahy on 11/2/14.
 */
class Ring extends Sprite {
    private int mWeight;
    private Stack mStack; //this represents the stack that this ring belongs to
    private Sprite mTower;

    public Ring(int weight, float pX, float pY, ITextureRegion pTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTextureRegion, pVertexBufferObjectManager);
        this.mWeight = weight;
    }

    public int getmWeight() {
        return mWeight;
    }

    public Stack getmStack() {
        return mStack;
    }

    public void setmStack(Stack mStack) {
        this.mStack = mStack;
    }

    public Sprite getmTower() {
        return mTower;
    }

    public void setmTower(Sprite mTower) {
        this.mTower = mTower;
    }
}
