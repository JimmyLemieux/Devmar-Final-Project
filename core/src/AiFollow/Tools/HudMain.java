package AiFollow.Tools;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
public class HudMain {
    public Stage stgMain;
    public int nTime;
    private Table tblMain;
    private Label lblLives;
    private Label lblScore;
    private Label lblTime;
    private Label lblCTime;
    private Label lblCScore;
    private Label lblCLives;
    private int nScore, nCounter;
    private Viewport Vp;
    
    public HudMain(Batch batch) {
        nTime = 300;
        nScore = 0;
        Vp = new FitViewport(1000,500, new OrthographicCamera());
        stgMain = new Stage(Vp, batch);
        tblMain = new Table();
        tblMain.top();
        tblMain.setFillParent(true);
        lblLives = new Label("Lives", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblScore = new Label("Score", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblTime = new Label("Time", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }
    public void draw(int nLives) {
        nCounter++;
        if(nCounter % 60 == 0) {
            nTime--;
        }
        lblCLives = new Label(String.format("%03d", nLives), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblCTime = new Label(String.format("%03d", nTime), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblCScore = new Label(String.format("%06d", nScore), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        tblMain.reset();
        tblMain.top();
        tblMain.add(lblLives).expandX().padTop(20);
        tblMain.add(lblScore).expandX().padTop(20);
        tblMain.add(lblTime).expandX().padTop(20);
        tblMain.row();
        tblMain.add(lblCLives).expandX();
        tblMain.add(lblCScore).expandX();
        tblMain.add(lblCTime).expandX();
        stgMain.addActor(tblMain);
        stgMain.draw();
    }
}