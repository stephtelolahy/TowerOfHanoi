package com.telolahy.towerofhanoi.object;

import com.telolahy.towerofhanoi.Constants;
import com.telolahy.towerofhanoi.R;
import com.telolahy.towerofhanoi.manager.GameManager;
import com.telolahy.towerofhanoi.manager.ResourcesManager;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

/**
 * Created by stephanohuguestelolahy on 11/8/14.
 */
public class LevelCompleteWindow extends Sprite {


    public interface LevelCompleteWindowListener {

        void levelCompleteWindowNextButtonClicked();

        void levelCompleteWindowReplayButtonClicked();
    }

    private TiledSprite mStar1;
    private TiledSprite mStar2;
    private TiledSprite mStar3;
    private LevelCompleteWindowListener mListener;

    public enum StarsCount {
        ONE,
        TWO,
        THREE
    }

    public LevelCompleteWindow(VertexBufferObjectManager pSpriteVertexBufferObject, Scene scene, LevelCompleteWindowListener listener) {

        super(400, 240, ResourcesManager.getInstance().gameWindowRegion, pSpriteVertexBufferObject);
        mListener = listener;
        attachStars(pSpriteVertexBufferObject, scene);
    }

    private void attachStars(VertexBufferObjectManager pSpriteVertexBufferObject, Scene scene) {

        ResourcesManager resourcesManager = ResourcesManager.getInstance();

        String text = resourcesManager.activity.getResources().getString(R.string.level_completed);
        boolean isOnLastLevel = GameManager.getInstance().isOnLastLevel();
        if (isOnLastLevel) {
            text = resourcesManager.activity.getResources().getString(R.string.last_level_completed);
        }

        attachChild(new Text(Constants.CAMERA_WIDTH / 2, Constants.CAMERA_HEIGHT - 100, resourcesManager.mainFont.font, text, resourcesManager.vertexBufferObjectManager));

        mStar1 = new TiledSprite(275, 260, resourcesManager.gameCompleteStarsRegion, pSpriteVertexBufferObject);
        mStar2 = new TiledSprite(400, 260, resourcesManager.gameCompleteStarsRegion, pSpriteVertexBufferObject);
        mStar3 = new TiledSprite(525, 260, resourcesManager.gameCompleteStarsRegion, pSpriteVertexBufferObject);
        attachChild(mStar1);
        attachChild(mStar2);
        attachChild(mStar3);

        Sprite retryButton = new Sprite(260, 120, resourcesManager.gameCompleteRetryRegion, pSpriteVertexBufferObject) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                mListener.levelCompleteWindowReplayButtonClicked();
                return true;
            }
        };
        attachChild(retryButton);
        scene.registerTouchArea(retryButton);


        if (isOnLastLevel) {

            retryButton.setPosition(400, retryButton.getY());
            return;
        }

        Sprite nextButton = new Sprite(540, 120, resourcesManager.gameCompleteNextRegion, pSpriteVertexBufferObject) {
            @Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
                mListener.levelCompleteWindowNextButtonClicked();
                return true;
            }
        };
        attachChild(nextButton);
        scene.registerTouchArea(nextButton);
    }

    /**
     * Change star`s tile index, depends on stars count.
     *
     * @param starsCount
     */
    public void display(StarsCount starsCount, Scene scene, Camera camera) {
        // Change stars tile index, based on stars count (1-3)
        switch (starsCount) {
            case ONE:
                mStar1.setCurrentTileIndex(0);
                mStar2.setCurrentTileIndex(1);
                mStar3.setCurrentTileIndex(1);
                break;
            case TWO:
                mStar1.setCurrentTileIndex(0);
                mStar2.setCurrentTileIndex(0);
                mStar3.setCurrentTileIndex(1);
                break;
            case THREE:
                mStar1.setCurrentTileIndex(0);
                mStar2.setCurrentTileIndex(0);
                mStar3.setCurrentTileIndex(0);
                break;
        }


        // Hide HUD
        camera.getHUD().setVisible(false);

        // Disable camera chase entity
        camera.setChaseEntity(null);

        // Attach our level complete panel in the middle of camera
        setPosition(camera.getCenterX(), camera.getCenterY());
        scene.attachChild(this);
    }
}
