package AiFollow.Screens;

import AiFollow.Tools.TbMain;
import AiFollow.Tools.TbSkin;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ScrGameOver implements Screen {
    
    public TbMain tbRestart;
    
    private Stage stgMain;
    private Table tblMain;
    private Label lblMain;
    private Viewport vpMain;
    
    public ScrGameOver() {
        vpMain = new FitViewport(1000, 500, new OrthographicCamera());
        stgMain = new Stage(vpMain);
        tblMain = new Table();
        lblMain = new Label("GameOver", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblMain.setFontScale(3);
        lblMain.setPosition(500 - (lblMain.getWidth()*1.5f), 300 - (lblMain.getHeight() / 2));
        tbRestart = new TbMain("Restart", new TbSkin());
        tbRestart.setBounds(vpMain.getScreenWidth() / 2 - 50, 150 - 75, 100, 50);
    }
    
    @Override
    public void show() {
        stgMain.addActor(lblMain);
        stgMain.addActor(tbRestart);
        Gdx.input.setInputProcessor(stgMain);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stgMain.draw();
    }

    @Override
    public void resize(int i, int i1) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stgMain.dispose();
    }  
}
