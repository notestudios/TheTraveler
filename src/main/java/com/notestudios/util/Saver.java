package com.notestudios.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.retrozinndev.jsonutils.JSON;

public class Saver {
    
    JSON json;
    File jsonFile;

    public Saver(File outputFile) {
        this.jsonFile = outputFile;
        json = new JSON(outputFile);
    }

    public void saveMap(Map<String, Object> data) {
        json.toMap().clear();
        json.toMap().putAll(data);
        json.write();
    }

    public Map<String, Object> loadMap() {
        Map<String, Object> jsonData = new HashMap<String, Object>();
        json.read();
        jsonData.putAll(json.toMap());
        return jsonData;
    }
}
