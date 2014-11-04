package com.telolahy.towerofhanoi.scene;

import com.telolahy.towerofhanoi.manager.ResourcesManager;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
public abstract class BaseScene extends Scene {

    //    protected Engine engine;
//    protected Activity activity;
    protected ResourcesManager mResourcesManager;
    protected VertexBufferObjectManager mVertexBufferObjectManager;
//    protected BoundCamera camera;

    //---------------------------------------------
    // CONSTRUCTOR
    //---------------------------------------------

    public BaseScene() {
        mResourcesManager = ResourcesManager.getInstance();
        mVertexBufferObjectManager = ResourcesManager.getInstance().vertexBufferObjectManager;
//        this.engine = resourcesManager.engine;
//        this.activity = resourcesManager.activity;
//        this.camera = resourcesManager.camera;
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
