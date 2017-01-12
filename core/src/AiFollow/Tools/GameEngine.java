/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AiFollow.Tools;

import AiFollow.Screens.ScrMain;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author mogoa7209
 */
public class GameEngine {
    private float ppm = ScrMain.ppm;
    private short CATEGORY_PLAYER = 2;
    private short CATEGORY_MAP = 4;
    private short CATEGORY_DEATHSPIKE = 8;
    public BodyDef createBodyDef(World wTemp, Vector2 vecLocation) {  
        BodyDef bdTemp = new BodyDef();
        bdTemp.position.set(vecLocation);
        bdTemp.type = BodyDef.BodyType.DynamicBody;
        return bdTemp;
    }
    
    public FixtureDef createFixtureDef(float fWidth, float fHeight, Vector2 vecLoc) {
        PolygonShape psTemp = new PolygonShape();
        psTemp.setAsBox(fWidth / 2, fHeight / 2, new Vector2(vecLoc.x / 2, vecLoc.y / 2), 0);
        FixtureDef fdMain = new FixtureDef();
        fdMain.shape = psTemp;
        return fdMain;
    }
    public void createFloor(World wTemp, float fWidth, float fHeight) {
        Body bodFloor;
        BodyDef bdTemp = new BodyDef();
        bdTemp.type = BodyDef.BodyType.StaticBody;
        bdTemp.position.set(0, 0);
        bodFloor = wTemp.createBody(bdTemp);
        PolygonShape psTemp = new PolygonShape();
        psTemp.setAsBox(fWidth, 30 / ppm, bdTemp.position, 0);
        FixtureDef fdMain = new FixtureDef();
        fdMain.shape = psTemp;
        bodFloor.createFixture(fdMain);
    }
    
    public void loadMapLayer(int nLayer, World wTemp, TiledMap tmTemp) {
        for(MapObject mObj: tmTemp.getLayers().get(nLayer).getObjects()) {
            FixtureDef fdTemp = null;
            BodyDef bdTemp = new BodyDef();
            bdTemp.type = BodyDef.BodyType.StaticBody;
            if(mObj instanceof PolylineMapObject) {
                fdTemp = createPolylineFixture((PolylineMapObject) mObj);
            } else if(mObj instanceof RectangleMapObject) {
                fdTemp = createRectangleFixture((RectangleMapObject) mObj);
                bdTemp.position.set(getRectangleCoordinates((RectangleMapObject) mObj));
            } else if(mObj instanceof PolygonMapObject) {
                fdTemp = createPolygonFixture((PolygonMapObject) mObj);
            }
            Body bTemp = wTemp.createBody(bdTemp);
            fdTemp.filter.categoryBits = CATEGORY_MAP;
            bTemp.createFixture(fdTemp);
        }
    }

    private FixtureDef createPolylineFixture(PolylineMapObject pmObj) {
        FixtureDef fdTemp = new FixtureDef();
        float[] fCoordinates = pmObj.getPolyline().getTransformedVertices();
        Vector2[] vecPoints = new Vector2[fCoordinates.length / 2];
        for(int i = 0; i < vecPoints.length; i++) {
            vecPoints[i] = new Vector2();
            vecPoints[i].x = fCoordinates[i * 2] / ppm;
            vecPoints[i].y = fCoordinates[i * 2 + 1] / ppm;
        }
        ChainShape csTemp = new ChainShape();
        csTemp.createChain(vecPoints);
        fdTemp.shape = csTemp;
        fdTemp.friction = 0.5f;
        return fdTemp;
    }

    private FixtureDef createRectangleFixture(RectangleMapObject rmObj) {
        Rectangle rectTemp = rmObj.getRectangle();
        FixtureDef fdTemp = new FixtureDef();
        PolygonShape psTemp = new PolygonShape();
        psTemp.setAsBox(rectTemp.width / 2 / ppm, rectTemp.height / 2 / ppm);
        fdTemp.shape = psTemp;
        return fdTemp;
    }

    private Vector2 getRectangleCoordinates(RectangleMapObject rmObj) {
        Rectangle rectTemp = rmObj.getRectangle();
        Vector2 vecLocation = new Vector2((rectTemp.getX() + rectTemp.width / 2) / ppm, (rectTemp.getY() + rectTemp.height / 2) / ppm);
        return vecLocation;
    }

    private FixtureDef createPolygonFixture(PolygonMapObject pmObj) {
        float[] fVerticies = pmObj.getPolygon().getTransformedVertices();
        Vector2[] vecPoints = new Vector2[fVerticies.length / 2];
        for(int i = 0; i < fVerticies.length / 2; i++) {
            vecPoints[i] = new Vector2();
            vecPoints[i].x = fVerticies[i * 2] / ppm;
            vecPoints[i].y = fVerticies[i * 2 + 1] / ppm;
        }
        PolygonShape psTemp = new PolygonShape();
        psTemp.set(vecPoints);
        FixtureDef fdTemp = new FixtureDef();
        fdTemp.shape = psTemp;
        return fdTemp;
    }
    
    public void loadObstacleLayer(int nLayer, World wTemp, TiledMap tmTemp) {
        for(MapObject mObj: tmTemp.getLayers().get(nLayer).getObjects()) {
            FixtureDef fdTemp = null;
            BodyDef bdTemp = new BodyDef();
            bdTemp.type = BodyDef.BodyType.StaticBody;
            if(mObj instanceof PolylineMapObject) {
                fdTemp = createPolylineFixture((PolylineMapObject) mObj);
            } else if(mObj instanceof RectangleMapObject) {
                fdTemp = createRectangleFixture((RectangleMapObject) mObj);
                bdTemp.position.set(getRectangleCoordinates((RectangleMapObject) mObj));
            } else if(mObj instanceof PolygonMapObject) {
                fdTemp = createPolygonFixture((PolygonMapObject) mObj);
            }
            Body bTemp = wTemp.createBody(bdTemp);
            fdTemp.filter.categoryBits = CATEGORY_DEATHSPIKE;
            bTemp.createFixture(fdTemp);
            bTemp.setUserData("Death");
        }
    }
}
