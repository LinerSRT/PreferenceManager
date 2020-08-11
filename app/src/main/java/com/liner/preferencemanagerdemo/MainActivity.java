package com.liner.preferencemanagerdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.liner.preferencemanager.PreferenceManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MainActivity extends AppCompatActivity {
    private String saveString;
    private int saveInt;
    private boolean saveBoolean;
    private float saveFloat;
    private long saveLong;
    private byte[] saveBytes;
    private MyObject myObject;
    private List<MyObject> myObjectList;
    private Set<String> stringSet;
    private HashMap<String, Boolean> myBooleanHashMap;
    private HashMap<String, String> myStringHashMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        saveNewData();
        loadData();
    }

    private void loadData() {
        saveString = PreferenceManager.get("saveString", "null");
        saveInt = PreferenceManager.get("saveInt", -1);
        saveBoolean = PreferenceManager.get("saveBoolean", false);
        saveFloat = PreferenceManager.get("saveFloat", 0f);
        saveLong = PreferenceManager.get("saveLong", 0L);
        saveBytes = PreferenceManager.get("saveBytes", new byte[]{});
        myObject = PreferenceManager.getObject("myObject", MyObject.class);
        myObjectList = PreferenceManager.getList("myObjectList", MyObject.class);
        stringSet = PreferenceManager.getSet("stringSet");
        myBooleanHashMap = (HashMap<String, Boolean>) PreferenceManager.getMap("myBooleanHashMap", false);
        myStringHashMap = (HashMap<String, String>) PreferenceManager.getMap("myStringHashMap", "");
        Log.d("MainActivity", "loadData: {\n" +
                "\nSaved string key={saveString}" + " value = [" + saveString + "]" +
                "\nSaved int key={saveInt}" + " value = [" + saveInt + "]" +
                "\nSaved bool key={saveBoolean}" + " value = [" + saveBoolean + "]" +
                "\nSaved float key={saveFloat}" + " value = [" + saveFloat + "]" +
                "\nSaved long key={saveLong}" + " value = [" + saveLong + "]" +
                "\nSaved bytes key={saveBytes}" + " value = [" + Arrays.toString(saveBytes) + "]" +
                "\nSaved object key={myObject}" + " value = [" + myObject.toString() + "]" +
                "\nSaved hash map key={myBooleanHashMap}" + " value = [" + myBooleanHashMap.toString() + "]" +
                "\nSaved hash map key={myStringHashMap}" + " value = [" + myStringHashMap.toString() + "]" +
                "\n}");
    }

    private void saveNewData() {
        saveString = "MyString";
        saveInt = 2;
        saveBoolean = true;
        saveFloat = 1.3f;
        saveLong = 204L;
        saveBytes = new byte[256];
        myObject = new MyObject("newObject");
        myObjectList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            myObjectList.add(new MyObject("newObjectList_" + i));
        }
        stringSet = new HashSet<>();
        stringSet.add("My set1");
        stringSet.add("My set2");
        stringSet.add("My set3");
        myBooleanHashMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            myBooleanHashMap.put("myBooleanHashMap" + i, (i % 2) == 0);
        }
        myStringHashMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            myStringHashMap.put("myStringHashMap" + i, "My string " + i);
        }
        PreferenceManager.put("saveString", saveString);
        PreferenceManager.put("saveInt", saveInt);
        PreferenceManager.put("saveBoolean", saveBoolean);
        PreferenceManager.put("saveFloat", saveFloat);
        PreferenceManager.put("saveLong", saveLong);
        PreferenceManager.put("saveBytes", saveBytes);
        PreferenceManager.putObject("myObject", myObject);
        PreferenceManager.putList("myObjectList", myObjectList);
        PreferenceManager.putSet("stringSet", stringSet);
        PreferenceManager.putMap("myBooleanHashMap", myBooleanHashMap);
        PreferenceManager.putMap("myStringHashMap", myStringHashMap);
    }
}