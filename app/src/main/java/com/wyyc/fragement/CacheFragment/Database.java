package com.wyyc.fragement.CacheFragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wyyc.App;
import com.wyyc.bean.Item;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

/**
 * Created by Administrator on 2017/3/24.
 */
public class Database {


    private static String DATA_FILE_NAME = "data_db";

    private static Database mDatabase;

    Gson mGson = new Gson();

    File mFile = new File(App.getInstance().getFilesDir(), DATA_FILE_NAME);

    private Database() {
    }

    public static Database getInstance() {

        if (mDatabase == null) {

            mDatabase = new Database();
        }

        return mDatabase;
    }

    /**
     * 读取存储
     * @return
     */
    public List<Item> readItems() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Reader reader = new FileReader(mFile);
            return mGson.fromJson(reader,new TypeToken<List<Item>>(){}.getType());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 写入存储
     * @param items
     */
    public void writeItems(List<Item> items) {

        String json = mGson.toJson(items);
        try {
            if (!mFile.exists()) {
                mFile.createNewFile();
            }
            Writer writer = new FileWriter(mFile);
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        mFile.delete();
    }


}
