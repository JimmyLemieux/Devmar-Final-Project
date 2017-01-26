/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package AiFollow.Screens;

import AiFollow.Screens.DataStore;
import AiFollow.Screens.jsonSave;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 *
 * @author chesj2479
 */
public class jsonSave {

    DataStore data = new DataStore();
    Json json = new Json();
    FileHandle file = new FileHandle("myjson.json");
    String sUser;

    public void SavetoJson(String sUsername) {
        data.sInput = sUsername;
        json.toJson(data, file);
    }
}
