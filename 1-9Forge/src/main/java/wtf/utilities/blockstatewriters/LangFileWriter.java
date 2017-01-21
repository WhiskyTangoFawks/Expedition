package wtf.utilities.blockstatewriters;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

public class LangFileWriter {

	
	static TreeMap<String, String> langFile = new TreeMap<String, String>();
	static ArrayList<String> addToLandFile = new ArrayList<String>();
	static String path = "resourcepacks"+File.separatorChar+"WTFExpedition"+File.separatorChar+"assets"+File.separatorChar+"wtfcore"+File.separatorChar+"lang";
	
	public static void loadLangFile(){
		
		new File(path).mkdir();
		
		
		
		try {

			Charset charset = Charset.defaultCharset();        
			
			Path filePath = new File(path+File.separatorChar+"en_US.lang").toPath();
			List<String> stringList = Files.readAllLines(filePath, charset);
			
			
			for (String string : stringList){
				String name = string.split("=")[0];
				name = name.substring(5,  name.length()-5);
				langFile.put(name, string);
			}
			
			
			

		}
		catch (IOException e) {
			e.printStackTrace();
		}
		


	}
	
	public static void addName(String regName, String localName){
		if (!langFile.containsKey(regName)){
			langFile.put(regName, "tile."+regName+".name="+localName);
		}
	}
	
	public static void finishLangFile(){
		
		BufferedWriter writer;
		try {
			writer = new BufferedWriter(new FileWriter(path+File.separatorChar+"en_US.lang"));

			for (Entry<String, String> entry : langFile.entrySet()){
				writer.write(entry.getValue());
				writer.newLine();
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
}
