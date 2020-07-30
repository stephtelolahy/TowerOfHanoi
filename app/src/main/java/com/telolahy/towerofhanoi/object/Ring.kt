package com.telolahy.towerofhanoi.`object`

import org.andengine.entity.sprite.Sprite
import org.andengine.opengl.texture.region.ITextureRegion
import org.andengine.opengl.vbo.VertexBufferObjectManager
import java.util.*

/**
 * Created by stephanohuguestelolahy on 11/2/14.
 */
open class Ring(val weight: Int, pX: Float, pY: Float, pTextureRegion: ITextureRegion?, pVertexBufferObjectManager: VertexBufferObjectManager?) : Sprite(pX, pY, pTextureRegion, pVertexBufferObjectManager) {
  var stack //this represents the stack that this ring belongs to
      : Stack<Any>? = null
  var tower: Sprite? = null

}