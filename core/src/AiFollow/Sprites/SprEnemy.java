/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AiFollow.Sprites;

import AiFollow.Screens.ScrMain;
import AiFollow.Tools.GameEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class SprEnemy extends Sprite {

    TextureAtlas atlas;
    Animation animation;
    Vector2 vecLocation;
    float fMaxVelocity = 15, fAcceleration = 100 / ScrMain.ppm, fTimer = 0;
    boolean isFlip;
    Body bMain;
    GameEngine GE;

    public SprEnemy(World wTemp, Vector2 vecStartingLocation) {
        GE = new GameEngine();
        atlas = new TextureAtlas("Enemy/Enemy.pack");
        animation = new Animation(12f, atlas.getRegions());
        vecLocation = vecStartingLocation;
        setSize(20 / ScrMain.ppm, 20 / ScrMain.ppm);
        bMain = wTemp.createBody(GE.createBodyDef(wTemp, vecLocation));
        bMain.createFixture(GE.createFixtureDef(getWidth(), getHeight(), bMain.getLocalCenter(), this));
        bMain.setUserData(this);
    }

    @Override
    public void draw(Batch bMain, float fDelta) {
        update(fDelta);
        super.draw(bMain);
    }

    public void update(float fDelta) {
        if(Math.abs(bMain.getLinearVelocity().x) < fMaxVelocity) {
            bMain.applyForce(new Vector2(fAcceleration, 0), bMain.getLocalCenter(), true);
        }
        if(bMain.getLinearVelocity().x == 0) {
            fAcceleration *= -1;
            isFlip = !isFlip;
            bMain.applyLinearImpulse(new Vector2(fAcceleration, 0), bMain.getLocalCenter(), true);
        }
        setPosition(bMain.getPosition().x - getWidth() / 2f, bMain.getPosition().y - getHeight() / 2f);
        setRegion(getFrame(animation));
    }
    
    public TextureRegion getFrame(Animation aniTemp) {
        TextureRegion texOut;
        fTimer++;
        texOut = aniTemp.getKeyFrame(fTimer, true);
        if (isFlip && !texOut.isFlipX()) {
            texOut.flip(true, false);
        } else if (!isFlip && texOut.isFlipX()) {
            texOut.flip(true, false);
        }
        return texOut;
    }
}
