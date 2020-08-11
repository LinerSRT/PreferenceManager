
<img src="https://github.com/LinerSRT/PreferenceManager/raw/master/images/logo.png" height="100">

# Preference Manager
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/27abcb9584c64e289cafa8fb7a3d0f20)](https://www.codacy.com/manual/LinerSRT/PreferenceManager?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=LinerSRT/PreferenceManager&amp;utm_campaign=Badge_Grade)
[![API](https://img.shields.io/badge/API-11%2B-blue.svg?style=flat)](https://android-arsenal.com/api?level=11)

This library allows you to store data in Android SharedPreferences
Supported types:
- **String**
- **Integer**
- **Boolean**
- **Float**
- **Long**
- **Byte Array**
- **Custom Objects**
- **List of custom objects**
- **String set**
- **Hash map**

Usage
-----
For using Preference Manager you need init them first in your Application.class
This should do only 1 time. Here is a example how to do it:

```java
       [MyApplication.java]
       public class MyApplication extends Application {
           @Override
           public void onCreate() {
               super.onCreate();
               PreferenceManager.init(this);
           }
       }

```
Declare you Application class in the AndroidManifest.xml
```xml
         <manifest
             ...
             >
            <application
               ...
               android:label="@string/app_name"
               android:name=".MyApplication"
               >
               ...
             </application>
             ...
           </manifest>

```

#### For regular types
```java
//For saving value 
//value can be string, int, bool, float, long, byte[].
   PreferenceManager.put("my_key", value);
   
//To get value from preferences
// Note, value used as default value and type detection
   PreferenceManager.get("my_key", value);
```
#### For objects and list types
```java
       class MyObject{
              public String name;
			  ....
              }
              
       MyObject myObject = new MyObject();

//For saving object 
   PreferenceManager.putObject("my_key", myObject);
   
//To get object from preferences
   PreferenceManager.getObject("my_key", MyObject.class);
   
//For saving list of objects 
   List<MyObject> myObjectList =  new ArrayList<>();
   myObjectList.add(new MyObject("my object"));
   
   PreferenceManager.putList("my_key", myObjectList);
   
//To get list of objects from preferences
   PreferenceManager.getList("my_key", MyObject.class);
```
#### For hash maps
```java
HashMap<String, Boolean> myBooleanHashMap = new HashMap<>();
HashMap<String, String> myStringHashMap = new HashMap<>();
for (int i = 0; i < 10; i++) {
            myBooleanHashMap.put("myBooleanHashMap" + i, (i % 2) == 0);
            myStringHashMap.put("myStringHashMap" + i, "My string " + i);
}

//For saving hash map 
    PreferenceManager.putMap("myBooleanHashMap", myBooleanHashMap);
    PreferenceManager.putMap("myStringHashMap", myStringHashMap);
   
//To get hash map  from preferences
// Note, value used as default value and type detection
    myBooleanHashMap = (HashMap<String, Boolean>) PreferenceManager.getMap("myBooleanHashMap", false);
    myStringHashMap = (HashMap<String, String>) PreferenceManager.getMap("myStringHashMap", "");
```
#### For string set
```java
    private Set<String> stringSet = new HashSet<>();
    stringSet.add("My set1");
    stringSet.add("My set2");
    stringSet.add("My set3");
//For saving value 
    PreferenceManager.putSet("stringSet", stringSet);
   
//To get value from preferences
   Set<String> stringSet = PreferenceManager.getSet("stringSet");
```

#### For more help see demo project



## License
Licensed under the Apache License, Version 2.0
