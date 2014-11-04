package com.telolahy.towerofhanoi.scene;

import com.telolahy.towerofhanoi.manager.ResourcesManager;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public abstract class BaseScene extends Scene {

    protected ResourcesManager mResourcesManager;
    protected VertexBufferObjectManager mVertexBufferObjectManager;

    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------

    public BaseScene() {
        mResourcesManager = ResourcesManager.getInstance();
        mVertexBufferObjectManager = ResourcesManager.getInstance().vertexBufferObjectManager;
        createScene();
    }

    //---------------------------------------------
    // ABSTRACTION
    //---------------------------------------------

    public abstract void createScene();

    public abstract void onBackKeyPressed();

//    public abstract SceneType getSceneType();

    public abstract void disposeScene();
}
