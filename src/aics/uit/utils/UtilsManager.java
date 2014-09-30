package aics.uit.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import aics.uit.CacheVariant;
import aics.uit.item.ItemWord;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

public class UtilsManager {
	
	public static UtilsManager lockabulary;

	public static UtilsManager instance() {
		if (lockabulary == null)
			lockabulary = new UtilsManager();
		return lockabulary;
	}
	
	
	
	public void WriteObject(ArrayList<ItemWord> itemWord,String strFileName){
		ObjectOutputStream oos = null;
		FileOutputStream fout;
		try {
			 fout = new FileOutputStream(strFileName);
			 oos = new ObjectOutputStream(fout);
			oos.writeObject(itemWord);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(oos != null){
				try {
					oos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void ReadObject(String strFileName){
		ObjectInputStream obInputStream = null;
		FileInputStream streamIn;
		
		try {
			streamIn = new FileInputStream(strFileName);
			obInputStream = new ObjectInputStream(streamIn);
			ArrayList<ItemWord> itemWordTemp  = (ArrayList<ItemWord>) obInputStream.readObject();
			CacheVariant.arrayListWord = itemWordTemp;
				Log.i("", "Read object finish");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
	}
	
	public File addFordel(File fileRoot, String fordelName) {
		File fileBase = new File(fileRoot, fordelName);
		if (!fileBase.isDirectory()) {
			fileBase.mkdirs();
		}
		return fileBase;
	}

	public File getCacheFolder(Context context) {
		File cacheDir = null;
		// File pathCacheDir = context.getCacheDir();
		// cacheDir = addFordel(pathCacheDir, "Apollo");
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			cacheDir = new File(Environment.getExternalStorageDirectory(),
					"Lockabulary");
			if (!cacheDir.isDirectory()) {
				cacheDir.mkdirs();
			}
		}

		if (!cacheDir.isDirectory()) {
			cacheDir = context.getCacheDir(); // get system cache folder
		}

		return cacheDir;
	}

	public String getFilename(String fileName) {
		return fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
	}
	
	public static String readData(Resources res, int id) {
		String result = "";
		String data;
		InputStream in = res.openRawResource(id);
		InputStreamReader inreader = new InputStreamReader(in);
		BufferedReader bufreader = new BufferedReader(inreader);
		StringBuilder builder = new StringBuilder();
		if (in != null) {
			try {
				while ((data = bufreader.readLine()) != null) {
					builder.append(data);
					builder.append("\n");
				}
				in.close();
				// editdata.setText(builder.toString());
				result = builder.toString();
			} catch (IOException ex) {
				Log.e("ERROR", ex.getMessage());
			}
		}
		return result;
	}
	
	public String readCache(Context context, String stringPath) {
		String strUserID = "";
		try {
			File pathCacheDir = context.getCacheDir();
			File newCacheFile = new File(pathCacheDir, stringPath);
			Scanner sc = new Scanner(newCacheFile);
			strUserID = "";
			try {
				while (sc.hasNext()) {
					
//					CacheVariant.USER_ID = sc.nextLine();
//					CacheVariant.USER_NAME = sc.nextLine();
//					CacheVariant.USER_URL_PIC = sc.nextLine();
//					strUserID = CacheVariant.USER_ID;
				}
			} catch (Exception ex) {
				strUserID = "";
			}
			sc.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			strUserID = "";
		}
		return strUserID;
	}
}
