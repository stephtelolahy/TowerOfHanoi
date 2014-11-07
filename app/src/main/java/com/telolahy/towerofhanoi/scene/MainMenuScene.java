package com.telolahy.towerofhanoi.scene;

import android.app.AlertDialog;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.widget.TextView;

import com.telolahy.towerofhanoi.R;
import com.telolahy.towerofhanoi.manager.SceneManager;

import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;

/**
 * Created by stephanohuguestelolahy on 11/5/14.
 */
public class MainMenuScene extends BaseScene {

    private MenuScene mMenuChildScene;

    private static final int MENU_PLAY = 0;
    private static final int MENU_HELP = 1;

    private Sprite mBackground;

    @Override
    public void createScene() {

        createBackground();
        createMenuChildScene();
    }

    private void createBackground() {

        Sprite backgroundSprite = new Sprite(400, 240, mResourcesManager.menuBackgroundTextureRegion, mVertexBufferObjectManager);
        attachChild(backgroundSprite);
    }

    private void createMenuChildScene() {

        mMenuChildScene = new MenuScene(mCamera);
        mMenuChildScene.setPosition(-140, -200);

        IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, mResourcesManager.menuPlayTextureRegion, mVertexBufferObjectManager), 1.2f, 1);
        IMenuItem helpMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_HELP, mResourcesManager.menuHelpTextureRegion, mVertexBufferObjectManager), 1.2f, 1);
        mMenuChildScene.addMenuItem(playMenuItem);
        mMenuChildScene.addMenuItem(helpMenuItem);

        mMenuChildScene.buildAnimations();
        mMenuChildScene.setBackgroundEnabled(false);

        helpMenuItem.setPosition(helpMenuItem.getX() + 260, playMenuItem.getY());

        mMenuChildScene.setOnMenuItemClickListener(new MenuScene.IOnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem, float pMenuItemLocalX, float pMenuItemLocalY) {

                switch (pMenuItem.getID()) {
                    case MENU_PLAY:
                        SceneManager.getInstance().loadGameScene();
                        return true;
                    case MENU_HELP:
                        displayHelpDialog();
                        return true;
                    default:
                        return false;
                }
            }
        });

        setChildScene(mMenuChildScene);
    }


    @Override
    public void onBackKeyPressed() {

    }

    @Override
    public void disposeScene() {

    }

    private void displayHelpDialog() {

        mActivity.runOnUiThread(new Runnable() {
            public void run() {

                final TextView textView = new TextView(mActivity);

                int padding = mActivity.getResources().getDimensionPixelSize(R.dimen.dialog_padding);
                textView.setPadding(padding, padding, padding, padding);
                textView.setTextAppearance(mActivity, R.style.dialog_text);

                String text = mActivity.getResources().getString(R.string.app_help);
                final SpannableString message = new SpannableString(text);
                Linkify.addLinks(message, Linkify.WEB_URLS);
                textView.setText(message);
                textView.setMovementMethod(LinkMovementMethod.getInstance());

                String title = mActivity.getResources().getString(R.string.app_name);
                String positiveText = mActivity.getResources().getString(R.string.ok);
                new AlertDialog.Builder(mActivity)
                        .setTitle(title)
                        .setCancelable(true)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .setPositiveButton(positiveText, null)
                        .setView(textView)
                        .create()
                        .show();
            }
        });
    }
}