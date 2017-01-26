/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AiFollow.Sprites;

import AiFollow.Screens.ScrMain;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import AiFollow.Tools.GameEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public final class SprPlayer extends Sprite {

    TextureAtlas atWolverine;
    Animation aniCurrent, aniWalk, aniStand, aniFall, aniJump;
    GameEngine GE;
    boolean bFlip, bCanJump;
    public Vector2 vecLocation, vecStartingLocation = new Vector2(170 / ScrMain.ppm, 40 / ScrMain.ppm);
    public Body bMain;
    public boolean isDead, isFlip;
    float fTimer = 0;
    ScrMain scrTemp;

    public SprPlayer(World wTemp, ScrMain scrTemp) {
        this.scrTemp = scrTemp;
        bCanJump = true;
        isDead = false;
        atWolverine = new TextureAtlas("Wolverine/wolverine.pack");
        aniWalk = getAnimation("Walk", 6, atWolverine, 12f);
        aniStand = getAnimation("Stand", 3, atWolverine, 12f);
        aniFall = getAnimation("Falling", 1, atWolverine, 12f);
        aniJump = getAnimation("Jump", 3, atWolverine, 20f);
        vecLocation = vecStartingLocation;
        setSize(25 / ScrMain.ppm, 30 / ScrMain.ppm);
        GE = new GameEngine();
        bMain = wTemp.createBody(GE.createBodyDef(wTemp, vecLocation));
        bMain.setUserData(this);
        bMain.createFixture(GE.createFixtureDef(getWidth(), getHeight(), bMain.getLocalCenter(), this));
        bMain.setLinearDamping(1);

    }

    public void update() {
        if (bMain.getLinearVelocity().y == 0) {
            setSize(30 / ScrMain.ppm, getHeight());
            if (bMain.getLinearVelocity().x != 0) {
                aniCurrent = aniWalk;
                if (bMain.getLinearVelocity().x < 0) {
                    isFlip = true;
                } else if (bMain.getLinearVelocity().x > 0) {
                    isFlip = false;
                }
            } else if (bMain.getLinearVelocity().x == 0) {
                aniCurrent = aniStand;
            }
        } else if (bMain.getLinearVelocity().y < 0) {
            aniCurrent = aniFall;
            setSize(30 / ScrMain.ppm, getHeight());
        } else if (bMain.getLinearVelocity().y > 0) {
            aniCurrent = aniJump;
        }
        vecLocation = bMain.getPosition();
        if (Gdx.input.isKeyJustPressed(Input.Keys.O)) {
            die();
        }
        setPosition(vecLocation.x - getWidth() / 2, vecLocation.y - getHeight() / 2);
        if (bCanJump) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                bMain.applyLinearImpulse(new Vector2(0, 20), bMain.getLocalCenter(), true);
                setSize(getWidth() / 2, getHeight());
                bCanJump = false;
            }
        } else {
            if (bMain.getLinearVelocity().y == 0) {
                bCanJump = true;
            }
        }
        setRegion(getFrame(aniCurrent));
    }

    @Override
    public void draw(Batch sbMain) {
        update();
        super.draw(sbMain);
    }

    public void die() {
        System.out.println("DEAD");
        isDead = true;
    }

    public Animation getAnimation(String sFile, int nLength, TextureAtlas texPack, float fSpeed) {
        Animation aniTemp;
        String FileName;
        Array<TextureRegion> arrFrames = new Array<TextureRegion>();
        for (int i = 0; i < nLength; i++) {
            FileName = sFile + i;
            arrFrames.add(texPack.findRegion(FileName));
        }
        aniTemp = new Animation(fSpeed, arrFrames);
        return aniTemp;
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
    
    public ScrMain getScreen(){
        return scrTemp;
    }
}
