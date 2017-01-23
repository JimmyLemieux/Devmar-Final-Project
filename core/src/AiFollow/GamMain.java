package AiFollow;

import AiFollow.Screens.ScrGameOver;
import AiFollow.Screens.ScrMain;
import com.badlogic.gdx.Game;

public class GamMain extends Game {

    private ScrMain scrMain;
    private ScrGameOver scrGameOver;

    @Override
    public void create() {
        scrMain = new ScrMain();
        setScreen(scrMain);
    }

    @Override
    public void resize(int Width, int Height) {
        super.resize(Width, Height);
    }

    @Override
    public void render() {
        super.render();
        if (this.getScreen() instanceof ScrMain) {
            if (scrMain.nLives == 0) {
                this.getScreen().dispose();
                scrGameOver = new ScrGameOver();
                setScreen(scrGameOver);
            }
        } else if (this.getScreen() instanceof ScrGameOver) {
            if (scrGameOver.tbRestart.isPressed()) {
                this.getScreen().dispose();
                scrMain = new ScrMain();
                setScreen(scrMain);
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
