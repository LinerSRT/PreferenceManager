package com.liner.preferencemanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.google.gson.Gson;



import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked | unused")
@SuppressLint("StaticFieldLeak")
public class PreferenceManager {
    private static PreferenceManager preferenceManager;
    private SharedPreferences sharedPreferences;

    /**
     * Init in Application class
     *
     * @param context Application context
     */
    public static void init(Context context) {
        preferenceManager = new PreferenceManager();
        if (preferenceManager.sharedPreferences == null)
            preferenceManager.sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    /**
     * Save value to shared preferences
     *
     * @param key   key
     * @param value value
     */
    public static void put(String key, Object value) {
        checkInitialization();
        SharedPreferences.Editor editor = preferenceManager.sharedPreferences.edit();
        if (value instanceof String) {
            editor.putString(key, (String) value);
        } else if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        } else if (value instanceof byte[]) {
            editor.putString(key, Base64.encodeToString((byte[]) value, Base64.DEFAULT));
        }
        editor.apply();
    }

    /**
     * Get saved value from shared preferences
     *
     * @param key      key
     * @param defValue default value (if key not exists)
     * @return saved value
     */
    public static <T> T get(String key, Object defValue) {
        checkInitialization();
        Object result = defValue;
        if (defValue instanceof String) {
            result = preferenceManager.sharedPreferences.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            result = preferenceManager.sharedPreferences.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Boolean) {
            result = preferenceManager.sharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Float) {
            result = preferenceManager.sharedPreferences.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Long) {
            result = preferenceManager.sharedPreferences.getLong(key, (Long) defValue);
        } else if (defValue instanceof byte[]) {
            result = Base64.decode(preferenceManager.sharedPreferences.getString(key, ""),  Base64.DEFAULT);
        }
        return (T) result;
    }

    /**
     * Save object to shared preferences
     *
     * @param key    saving key
     * @param object saving object
     * @return not used
     */
    public static <T> T putObject(String key, final T object) {
        checkInitialization();
        put(key, new Gson().toJson(object));
        return null;
    }

    /**
     * Get object from shared preferences
     *
     * @param key    saving key
     * @param object saved object type
     * @return saved object
     */
    public static <T> T getObject(String key, Class<T> object) {
        checkInitialization();
        return new Gson().fromJson((String) get(key, ""), object);
    }

    /**
     * Save list of objects to shared preferences
     *
     * @param key  saving key
     * @param list saving list
     * @return not used
     */
    public static <T> T putList(String key, final T list) {
        checkInitialization();
        return putObject(key, list);
    }

    /**
     * Get list of objects from shared preferences
     *
     * @param key   saving key
     * @param clazz class of object
     * @return saved list of objects
     */
    public static <T> List<T> getList(String key, Class<T> clazz) {
        checkInitialization();
        List<T> list = new Gson().fromJson((String) get(key, ""), new ListTypeToken<>(clazz));
        if (list == null)
            list = new ArrayList<>();
        return list;
    }

    /**
     * Save string set to shared preferences
     *
     * @param key saving key
     * @param set set
     */
    public static void putSet(String key, Set<String> set) {
        checkInitialization();
        SharedPreferences.Editor editor = preferenceManager.sharedPreferences.edit();
        editor.putStringSet(key, set);
        editor.apply();
    }

    /**
     * Get string set from shared preferences
     *
     * @param key saving key
     * @return saved set
     */
    public static Set<String> getSet(String key) {
        checkInitialization();
        return preferenceManager.sharedPreferences.getStringSet(key, null);

    }

    /**
     * Save hash map to shared preferences
     *
     * @param key saving key
     * @param map hash map
     */
    public static void putMap(String key, HashMap<?, ?> map){
        checkInitialization();
        for(Map.Entry<?, ?> entry:map.entrySet()){
            put("map_"+key+"_"+entry.getKey(), entry.getValue());
        }
    }

    /**
     * Get hash map from shared preferences
     *
     * @param key saving key
     * @return saved hash map
     */
    public static <T> HashMap<?, ?> getMap(String key, T type){
        checkInitialization();
        HashMap<String, Object> hashMap = new HashMap<>();
        for(String preferenceKey:preferenceManager.sharedPreferences.getAll().keySet()){
            if(preferenceKey.contains(key))
                hashMap.put(preferenceKey.replace("map_"+key+"_", ""), get(preferenceKey, type));
        }
        return hashMap;
    }

    /**
     * Clear all
     */
    public static void clearAll() {
        checkInitialization();
        SharedPreferences.Editor editor = preferenceManager.sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    /**
     * Clear specific key
     *
     * @param key key
     */
    public static void clear(String key) {
        checkInitialization();
        SharedPreferences.Editor editor = preferenceManager.sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    /**
     * Clear saved hash map
     * @param key key
     */
    public static void clearMap(String key) {
        checkInitialization();
        for (String preferenceKey : preferenceManager.sharedPreferences.getAll().keySet()) {
            if (preferenceKey.replace("map_"+key+"_", "").equalsIgnoreCase(key)) {
                clear(preferenceKey);
            }
        }
    }

    private static void checkInitialization(){
        if(preferenceManager == null)
            throw new RuntimeException("Warning! Did you init manager in your Application class?");
    }

    public static class ListTypeToken<T> implements ParameterizedType {
        private Class<?> clazz;

        public ListTypeToken(Class<T> wrapper) {
            this.clazz = wrapper;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}