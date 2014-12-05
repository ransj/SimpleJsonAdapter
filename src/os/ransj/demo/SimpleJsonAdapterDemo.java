package os.ransj.demo;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import os.ransj.adapter.R;
import os.ransj.adapter.SimpleJsonAdapter;

/**
 * Created by ransj on 14/12/4.
 */
public class SimpleJsonAdapterDemo extends ListActivity {
    private static final String EXTRA_PATH_ROOT = "k_path_root";

    private static final String KEY_JSON_NAME = "k_json_name";
    private static final String KEY_JSON_TYPE = "k_json_type";
    private ListAdapter mAdapter;
    private String mPathRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPathRoot = getIntent().getStringExtra(EXTRA_PATH_ROOT);
        if (TextUtils.isEmpty(mPathRoot)) {
            mPathRoot = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        mAdapter = new SimpleJsonAdapter(this, getFilesInPath(mPathRoot), R.layout.item_files, new String[]{KEY_JSON_NAME, KEY_JSON_TYPE}, new int[]{R.id.item_files_name, R.id.item_files_type});
        setListAdapter(mAdapter);
        setTitle(mPathRoot);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        JSONObject json = (JSONObject) mAdapter.getItem(position);
        if(getString(R.string.file_type_directory).equals(json.optString(KEY_JSON_TYPE))){
            jump2NewFile(new File(mPathRoot, json.optString(KEY_JSON_NAME)));
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPathRoot = intent.getStringExtra(EXTRA_PATH_ROOT);
        mAdapter = new SimpleJsonAdapter(this, getFilesInPath(mPathRoot), R.layout.item_files, new String[]{KEY_JSON_NAME, KEY_JSON_TYPE}, new int[]{R.id.item_files_name, R.id.item_files_type});
        setListAdapter(mAdapter);
        setTitle(mPathRoot);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            File file = new File(mPathRoot);
            File parent = file.getParentFile();
            if (parent != null && parent.exists()) {
                jump2NewFile(new File(mPathRoot).getParentFile());
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void jump2NewFile(File file) {
        if (file.canRead()) {
            Intent data = new Intent(this, SimpleJsonAdapterDemo.class);
            data.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            data.putExtra(EXTRA_PATH_ROOT, file.getAbsolutePath());
            startActivity(data);
        }
    }

    private List<JSONObject> getFilesInPath(String path) {
        List<JSONObject> result = new ArrayList<JSONObject>();
        File root = new File(path);
        if (root.exists() && root.isDirectory()) {
            File[] list = root.listFiles();
            for (File temp : list) {
                JSONObject json = new JSONObject();
                try {
                    json.put(KEY_JSON_NAME, temp.getName());
                    json.put(KEY_JSON_TYPE, getString(temp.isDirectory() ? R.string.file_type_directory : R.string.file_type_file));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                result.add(json);
            }
        }
        return result;
    }

}
