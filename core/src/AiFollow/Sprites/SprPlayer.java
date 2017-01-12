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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;


public final class SprPlayer extends Sprite {
    
    TextureAtlas atlas;
    Animation animation;
    GameEngine GE;
    boolean bFlip, bCanJump;
    public Vector2 vecLocation, vecStartingLocation = new Vector2(200 / ScrMain.ppm, 40 / ScrMain.ppm);
    public Body bMain;
    public boolean isDead;
    
    public SprPlayer(World wTemp){
        bCanJump = true;
        isDead = false;
        atlas = new TextureAtlas("run/megapack.pack");
        animation = new Animation(10f,atlas.getRegions());
        vecLocation = vecStartingLocation;
        setSize(30 / ScrMain.ppm, 30 / ScrMain.ppm);
        GE = new GameEngine();
        bMain = wTemp.createBody(GE.createBodyDef(wTemp, vecLocation));
        bMain.setUserData(this);
        bMain.createFixture(GE.createFixtureDef(getWidth(), getHeight(), bMain.getLocalCenter()));
        bMain.setLinearDamping(1);
        
    }
    
    public void update(){
        vecLocation = bMain.getPosition();
        setPosition(vecLocation.x - getWidth() / 2, vecLocation.y  - getHeight() / 2);
        if(bCanJump) {
            if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
                bMain.applyLinearImpulse(new Vector2(0, 20), bMain.getLocalCenter(), true);
                bCanJump = false;
            }
        } else {
            if(bMain.getLinearVelocity().y == 0) {
                bCanJump = true;
            }
        }
        setRegion(animation.getKeyFrame(Gdx.graphics.getDeltaTime(), true));
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
}
