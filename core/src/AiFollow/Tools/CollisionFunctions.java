package AiFollow.Tools;

import AiFollow.Sprites.SprPlayer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionFunctions implements ContactListener{
    String sPlayer = "SprPlayer";
    String sDeath = "Death";
    String sEnemy = "SprEnemy";
    String sEnd = "End";
    @Override
    public void beginContact(Contact cnTemp) {
        Fixture fixA = cnTemp.getFixtureA();
        Fixture fixB = cnTemp.getFixtureB();
        if(fixA.getBody().getUserData() == null) return;
        if(fixB.getBody().getUserData() == null) return;
        System.out.println(fixB.getBody().getUserData().toString());
        if(fixA.getBody().getUserData().toString().contains(sPlayer)) {
            System.out.println("d");
            if(fixB.getBody().getUserData().toString().contains(sDeath) || fixB.getBody().getUserData().toString().contains(sEnemy)) {
                SprPlayer sppTemp = (SprPlayer) fixA.getBody().getUserData();
                System.out.println("die1");
                sppTemp.die();
            } else if(fixB.getBody().getUserData().toString().contains(sEnd)) {
                SprPlayer sppTemp = (SprPlayer) fixA.getBody().getUserData();
                System.out.println("win");
                sppTemp.getScreen().isWin = true;
            }
        } else if (fixB.getBody().getUserData().toString().contains(sPlayer)) {
            System.out.println("0");
            if(fixA.getBody().getUserData().toString().contains(sDeath) || fixA.getBody().getUserData().toString().contains(sEnemy)) {
                System.out.println("die");
                SprPlayer sppTemp = (SprPlayer) fixA.getBody().getUserData();
                sppTemp.die();
            }
        }
    }

    @Override
    public void endContact(Contact cntct) {
    }

    @Override
    public void preSolve(Contact cntct, Manifold mnfld) {
    }

    @Override
    public void postSolve(Contact cntct, ContactImpulse ci) {
    }
    
}
