package com.indexing.inverted;

import com.indexing.Document;
import com.indexing.KeyWord;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import com.indexing.stopwords.StopWords;
import com.indexing.stopwords.StopWordsFile;

public class Registry {
	public static final String sStopWords = "stop.words.file";
	public static final String sDataStorage = "data.storage";
	public static final String sIndexStorage = "index.storage";

	public enum Match {
		Or, And, Proximity
	}

	// TODO abstract/isolate storage. oh yeah and persist it too
	private static HashMap<String, ArrayList<Document>> index = new HashMap<String, ArrayList<Document>>();
	private static HashMap<String, ArrayList<String>> index1 = new HashMap<String, ArrayList<String>>();

	public Loader loader;
	private Properties conf;

	public Registry()
	{
		
	}
	public Registry(Properties conf) {
		this.conf = conf;
		setup();
	}


public HashMap<String, ArrayList<String>> getindex() {
		
	
		return index1;
	}

	public void register(File[] f) {
		index1 = new HashMap<String, ArrayList<String>>();
		if (f == null)
			return;

		// prevent double registration
 
		List<Document> docs = loader.load(f);
		for (Document d : docs) {
			for (KeyWord kw : d.keywords()) {
				ArrayList<String> list1 = index1.get(kw.word);
				if (list1 == null) {
					list1 = new ArrayList<String>();
					index1.put(kw.word, list1);
				}
				String details = d.getDetails(kw);
				list1.add(details);
			}
		}
	}

	
	private void setup() {
		try {
			File idir = new File(conf.getProperty(sIndexStorage));

			File swf = new File(conf.getProperty(sStopWords));
			StopWords swords = new StopWordsFile(swf);
			loader = new Loader(swords);
		} catch (Exception e) {
			throw new RuntimeException("Failed to setup registry.", e);
		}
	}
}
