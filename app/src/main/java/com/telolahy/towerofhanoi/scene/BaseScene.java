package com.telolahy.towerofhanoi.scene;

import com.telolahy.towerofhanoi.GameActivity;
import com.telolahy.towerofhanoi.manager.ResourcesManager;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public abstract class BaseScene extends Scene {

    protected ResourcesManager mResourcesManager;
    protected VertexBufferObjectManager mVertexBufferObjectManager;
    protected BoundCamera mCamera;
    protected GameActivity mActivity;

    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------

    public BaseScene() {

        mResourcesManager = ResourcesManager.getInstance();
        mVertexBufferObjectManager = ResourcesManager.getInstance().vertexBufferObjectManager;
        mCamera = ResourcesManager.getInstance().camera;
        mActivity = ResourcesManager.getInstance().activity;

        createScene();
    }

    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------

    public abstract void createScene();

    public abstract void disposeScene();

    public abstract void onBackKeyPressed();
}
