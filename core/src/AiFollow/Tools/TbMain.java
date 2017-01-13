package AiFollow.Tools;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class TbMain extends TextButton {

    String sNam;

    public TbMain(String sName, TextButtonStyle tbsMain) {
        super(sName, tbsMain);
        this.sNam = sName;
        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent evt, float x, float y) {
                String sScreen = "Scr" + sNam;
            }
        });
    }
}
