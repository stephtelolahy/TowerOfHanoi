package com.telolahy.towerofhanoi.scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.adt.color.Color;

/**
 * Created by stephanohuguestelolahy on 11/5/14.
 */
public class LoadingScene extends BaseScene {

    @Override
    public void createScene() {

        setBackground(new Background(Color.BLACK));
        attachChild(new Text(400, 240, mResourcesManager.font, "Loading...", mVertexBufferObjectManager));
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {

    }
}
