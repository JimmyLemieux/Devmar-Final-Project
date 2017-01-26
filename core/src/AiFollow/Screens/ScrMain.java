package AiFollow.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import AiFollow.Sprites.SprEnemy;
import AiFollow.Sprites.SprPlayer;
import AiFollow.Tools.CollisionFunctions;
import AiFollow.Tools.GameEngine;
import AiFollow.Tools.HudMain;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScrMain implements Screen {

    public static float ppm = 18;
    public boolean isWin = false;
    public int nLives;
    OrthographicCamera ocMain;
    OrthogonalTiledMapRenderer otmrMain;
    TiledMap tmMap1;
    Viewport vpMain;
    Box2DDebugRenderer B2DR;
    World wMain;
    SprPlayer sprPlayer;
    SprEnemy sprEnemies[];
    GameEngine GE;
    HudMain hudMain;
    int nEnd[] = new int[1];
    float fChangeX;
    
    @Override
    public void show() {
        nEnd[0] = 10;
        GE = new GameEngine();
        tmMap1 = new TmxMapLoader().load("Maps/DevMap2.tmx");
        otmrMain = new OrthogonalTiledMapRenderer(tmMap1,(float) 1 / ppm);
        B2DR = new Box2DDebugRenderer();
        ocMain = new OrthographicCamera();
        vpMain = new FitViewport(1000 / ppm, 500 / ppm, ocMain);
        wMain = new World(new Vector2(0, -25f), false);
        wMain.setContactListener(new CollisionFunctions());
        sprPlayer = new SprPlayer(wMain, this);
        GE.loadMapLayer(4, wMain, tmMap1);
        GE.loadMapLayer(5, wMain, tmMap1);
        GE.loadMapLayer(7, wMain, tmMap1);
        GE.loadObstacleLayer(6, wMain, tmMap1);
        hudMain = new HudMain(otmrMain.getBatch());
        nLives = 3;
        Vector2 vecEnemySpawnPoints[] = GE.getEnemySpawnPoints(8, wMain, tmMap1);
        sprEnemies = new SprEnemy[vecEnemySpawnPoints.length];
        for(int i = 0; i < vecEnemySpawnPoints.length; i++) {
            sprEnemies[i] = new SprEnemy(wMain, vecEnemySpawnPoints[i]);
        }
        GE.loadEnd(7, wMain, tmMap1);
        GE.loadEnemyBarriers(9, wMain, tmMap1);
    }

    @Override
    public void resize(int Width, int Height) {
        //GE.createFloor(wMain, Width, Height);
        ocMain.viewportWidth = Width / ppm;
        ocMain.viewportHeight = Height / ppm;
        ocMain.position.x = ocMain.viewportWidth/ 2;
        ocMain.position.y = ocMain.viewportHeight / 2;
        ocMain.update();
    }

    @Override
    public void render(float delta) {
        input();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        wMain.step(1 / 60f, 6, 2);
        //sprEnemy.update(sprPlayer.vecLocation);
        otmrMain.setView(ocMain);
        otmrMain.render();
        otmrMain.getBatch().begin();
        //sprEnemy.draw(otmrMain.getBatch());
        if (sprPlayer.getX() > ocMain.position.x + (170 / ppm)) {
            fChangeX = sprPlayer.getX()-(ocMain.position.x + (170 / ppm)) ;
        } else if (sprPlayer.getX() < ocMain.position.x - (170 / ppm)) {
            fChangeX  = -(ocMain.position.x - (170 / ppm) - sprPlayer.getX());
        } else {
            fChangeX = 0;
        }
        ocMain.translate(fChangeX, 0);
        ocMain.update();
        sprPlayer.draw(otmrMain.getBatch());
        for(SprEnemy sprEnemy: sprEnemies) {
            sprEnemy.draw(otmrMain.getBatch(), Gdx.graphics.getDeltaTime());
        }
        if(sprPlayer.isDead) {
            wMain.destroyBody(sprPlayer.bMain);
            sprPlayer = new SprPlayer(wMain, this);
            ocMain.position.x = sprPlayer.getX();
            nLives--;
        } 
        otmrMain.getBatch().end();
        otmrMain.render(nEnd);
        hudMain.draw(nLives);
        B2DR.render(wMain, ocMain.combined);
    }
    
    public void input() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (sprPlayer.bMain.getLinearVelocity().x > -25) {
                sprPlayer.bMain.applyForce(new Vector2( -20f, 0), sprPlayer.bMain.getLocalCenter(), true);
            }
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if(sprPlayer.bMain.getLinearVelocity().x < 25) {
                sprPlayer.bMain.applyForce(new Vector2( 20f, 0), sprPlayer.bMain.getLocalCenter(), true);
            }
        }
    }

    @Override
    public void pause() {
        this.dispose();
    }

    @Override
    public void resume() {
        this.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        otmrMain.dispose();
    }
}
