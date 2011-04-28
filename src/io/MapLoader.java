package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gameobjects.map.Layer;
import gameobjects.map.Map;

public class MapLoader {
	
	public static Map loadMap( File file ) throws IOException {
		
		if( file.isFile() == false ) {
			throw new IOException("Given file is not file.");
		}
		
		FileReader reader = new FileReader(file);			
		BufferedReader bufferedReader = new BufferedReader(reader);
		
		List<String> lines = new ArrayList<String>();
		
		String line = null;
		
		while( (line = bufferedReader.readLine()) != null ) {
			
			lines.add(line);
		}
		
		Map map = new Map();
		
		map.setName(lines.get(0));
		map.setCreator(lines.get(1));
				
		lines.indexOf("LAYER_GROUND");
		
		lines.indexOf("LAYER_SPECIAL");
		
		lines.indexOf("LAYER_COLLISION");
		//Layer collisionLayer = new Layer(width, height);
		
		lines.indexOf("LAYER_OBJECTS");
		
		return map;
	}
	
	
}
