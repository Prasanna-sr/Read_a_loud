package com.indexing.inverted;

import com.indexing.Document;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.indexing.stopwords.StopWords;

public class Loader {
	private StopWords ignore;
	private List<File> list = new ArrayList<File>();

	public Loader() throws Exception {
		
	}
	
	public Loader(StopWords stopWords) throws Exception {
		ignore = stopWords;
	}

	public List<Document> load(File[] file) {
		list=new ArrayList<File>();

		for(File f : file)
		{
			if(f!=null)
			{
				if (f.isFile())
					list.add(f);
				else {
					discoverFiles(f);
				}
			}
		}
		System.out.println("--->list of files : " + list);
		
		return gatherData();
	}

	public List<File> files() {
		return list;
	}

	private List<Document> gatherData() {
		// TODO this should be ran in parallel
		ArrayList<Document> r = new ArrayList<Document>(list.size());
		for (File f : list) {
			BufferedReader rdr = null;
			Document d = new Document(f);
			
			try {
				rdr = new BufferedReader(new FileReader(f));
				String raw = rdr.readLine();
				int relPosition = 0;
				long numWords = 0;
				while (raw != null) {
					String[] parts = raw.trim().split(
							"[\\s,\\.:;\\-#~\\(\\)\\?\\!\\&\\*\\\"\\/\\'\\`]");

					numWords += parts.length;

					for (String p : parts) {
						if (!ignore.contains(p))
							d.addKeyword(p, relPosition);

						// location (word position) in document allows use to
						// calculate strength by relative location and frequency
						relPosition++;
					}

					raw = rdr.readLine();
				}
				
				d.setNumWords(numWords);
				r.add(d);
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (rdr != null)
						rdr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return r;
	}

	private List<Document> gatherData1() {
		// TODO this should be ran in parallel
		int count=0;
		ArrayList<Document> r = new ArrayList<Document>(list.size());
		for (File f : list) {
			BufferedReader rdr = null;
			Document d = new Document(f);
			
			try {
				rdr = new BufferedReader(new FileReader(f));
				String raw = rdr.readLine();
				int relPosition = 0;
				long numWords = 0;
				while (raw != null) {
					String[] parts = raw.trim().split(
							"[\\s,\\.:;\\-#~\\(\\)\\?\\!\\&\\*\\\"\\/\\'\\`]");

					numWords += parts.length;

					for (String p : parts) {
						if (!ignore.contains(p))
							d.addKeyword(p, relPosition);

						// location (word position) in document allows use to
						// calculate strength by relative location and frequency
						relPosition++;
					}

					raw = rdr.readLine();
				}
				
				d.setNumWords(numWords);
				r.add(d);
				count++;
				if(count==3)
					break;
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				try {
					if (rdr != null)
						rdr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return r;
	}

	
	/**
	 * depth search
	 * 
	 * @param dir
	 */
	private void discoverFiles(File dir) {
		if (dir == null || dir.isFile())
			return;

		File[] dirs = dir.listFiles(new FileFilter() {

			public boolean accept(File f) {
				if (f.isFile())
					list.add(f);
				else if (f.getName().startsWith("."))
					; // ignore
				else if (f.isDirectory()) {
					discoverFiles(f);
				}

				return false;
			}
		});
	}
	
	}
