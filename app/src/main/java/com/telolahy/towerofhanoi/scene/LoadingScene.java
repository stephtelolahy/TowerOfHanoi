package com.telolahy.towerofhanoi.scene;

import com.telolahy.towerofhanoi.R;

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
        String text = mActivity.getResources().getString(R.string.loading);
        attachChild(new Text(400, 240, mResourcesManager.font, text, mVertexBufferObjectManager));
    }

    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {

    }
}
