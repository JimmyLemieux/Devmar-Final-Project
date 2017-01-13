package AiFollow.Tools;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class TbSkin extends TextButton.TextButtonStyle {
     private Skin skMain = new Skin();
     private TextureAtlas taMain;
     
     public TbSkin() {
         BitmapFont bmfMain = new BitmapFont();
         skMain.add("default", bmfMain);
         taMain = new TextureAtlas("Misc/Buttons.pack");
         skMain.addRegions(taMain);
         this.up = skMain.getDrawable("ButtonUp");
         this.down = skMain.getDrawable("ButtonDown");
         this.checked = skMain.getDrawable("ButtonUp");
         this.font = bmfMain;
     }
}