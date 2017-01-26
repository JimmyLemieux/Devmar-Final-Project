/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AiFollow.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 *
 * @author lemij7026
 */
public class ScrStart extends Game implements Screen {
    
    
    private Stage stgMain;
    private Table tblMain;
    private Label lblMain;
    private Viewport vpMain;
    
    
    


    jsonSave save;
    DataStore data;
  
    Json json;
    JsonReader reader;
    FileHandle file;
    TextField.TextFieldStyle tstyle;
    Skin tbskin;
    TextField text;
    BitmapFont font;
    Table table;
    Texture texCur;
    TextureAtlas atlas;
    TextField.TextFieldListener tfl;
    String sText;
    SpriteBatch batch;
    public Boolean bDraw = true;
    int nTime;
   
   
     @Override
    public void create() {
        vpMain = new FitViewport(Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), new OrthographicCamera());
        stgMain = new Stage(vpMain);
        lblMain = new Label("Enter username and press enter to continue", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        lblMain.setFontScale(3f);
        lblMain.setPosition(Gdx.graphics.getWidth() / 2 - 420, Gdx.graphics.getHeight() / 2 + 200);
        tblMain = new Table();
        tblMain.center();
        tblMain.add(lblMain).center().pad(1);
        stgMain.addActor(tblMain);     
        
        //init
        save = new jsonSave();
        data = new DataStore();
        json = new Json();
        file = new FileHandle("myjson.json");
        reader = new JsonReader();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        
        //The textfield with style
        //http://libgdx.badlogicgames.com/nightlies/docs/api/com/badlogic/gdx/scenes/scene2d/ui/TextField.html

        tbskin = new Skin();
        tstyle = new TextField.TextFieldStyle();
        table = new Table();
        atlas = new TextureAtlas(Gdx.files.internal("textfield.pack"));
        texCur = new Texture(Gdx.files.internal("cursor.png"));
        tbskin.addRegions(atlas);
        tstyle.background = tbskin.getDrawable("tef1");
        tstyle.font = font;
        tstyle.cursor = new TextureRegionDrawable(new TextureRegion(texCur));
        tstyle.fontColor = Color.BLACK;
        text = new TextField(" ", tstyle);
        text.setWidth(319);
        text.setHeight(57);
        text.setX(Gdx.graphics.getWidth() / 2 - 160);
        text.setY(Gdx.graphics.getHeight() / 2 - 29);
        text.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char key) {
            }
        });
        text.setAlignment(Align.center);
        
        //Adding everything to the scene
        stgMain.addActor(text);      
        stgMain.addActor(lblMain);
        Gdx.input.setInputProcessor(stgMain);
     }
     
    @Override
     public void render() {
        super.render();
        //drawing the stage along with textfield
        stgMain.draw();
        tblMain.reset();
          if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            save.SavetoJson(text.getText());
            //Remove the text from the scene, so you dont see it while playing the game.
            text.remove();
            this.setScreen(new ScrMain());
            lblMain.remove();
        }
  
     }

    //When we want the background to clear, call this
    public void clearBack() {
        Gdx.gl.glClearColor(.135f, .206f, .235f, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
    }

    @Override
    public void show() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void render(float f) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
}
