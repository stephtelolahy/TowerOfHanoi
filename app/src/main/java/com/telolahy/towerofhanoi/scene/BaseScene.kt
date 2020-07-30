package com.telolahy.towerofhanoi.scene

import com.telolahy.towerofhanoi.MainActivity
import com.telolahy.towerofhanoi.manager.ResourcesManager
import org.andengine.engine.camera.BoundCamera
import org.andengine.entity.scene.Scene
import org.andengine.opengl.vbo.VertexBufferObjectManager

/**
 * Created by stephanohuguestelolahy on 11/4/14.
 */
abstract class BaseScene : Scene() {
  @JvmField
  protected var mResourcesManager: ResourcesManager = ResourcesManager.instance

  @JvmField
  protected var mVertexBufferObjectManager: VertexBufferObjectManager = ResourcesManager.instance.vertexBufferObjectManager

  @JvmField
  protected var mCamera: BoundCamera = ResourcesManager.instance.camera

  @JvmField
  protected var mActivity: MainActivity = ResourcesManager.instance.activity

  //---------------------------------------------
  // ABSTRACTION
  //---------------------------------------------
  abstract fun createScene()
  abstract fun disposeScene()
  abstract fun onBackKeyPressed()

  //---------------------------------------------
  // CONSTRUCTOR
  //---------------------------------------------
  init {
    createScene()
  }
}