package os.ransj.demo;

import android.app.ListActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import os.ransj.adapter.R;
import os.ransj.adapter.SimpleJsonAdapter;

/**
 * Created by ransj on 14/12/5.
 */
public class AdvanceJsonAdapterDemo extends ListActivity {
    private static final String KEY_IMG_URL = "k_img_url";
    private static final String KEY_IMG_TITLE = "k_img_title";
    private RequestQueue mQueue;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Beauties");
        mQueue = Volley.newRequestQueue(this);
        mImageLoader = new ImageLoader(mQueue, new BitmapLruCache());
        SimpleJsonAdapter adapter = new SimpleJsonAdapter(this, getData(), R.layout.item_images, new String[]{KEY_IMG_URL, KEY_IMG_TITLE}, new int[]{R.id.item_images_img, R.id.item_images_title});
        adapter.setViewBinder(new SimpleJsonAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if (view.getId() == R.id.item_images_img) {
                    ImageLoader.ImageListener listener = ImageLoader.getImageListener((ImageView) view,R.drawable.ic_launcher, R.drawable.ic_launcher);
                    mImageLoader.get(textRepresentation, listener);
                } else if (view.getId() == R.id.item_images_title) {
                    ((TextView)view).setText(textRepresentation);
                }
                return true;
            }
        });
        setListAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mQueue.cancelAll(this);
    }

    private JSONArray getData() {
        JSONArray array = new JSONArray();
        try {
            JSONObject one = new JSONObject();
            one.put(KEY_IMG_TITLE, "第十名：班婕妤");
            one.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094233.jpg");
            array.put(one);
            JSONObject two = new JSONObject();
            two.put(KEY_IMG_TITLE, "第九名：上官婉儿");
            two.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094234.jpg");
            array.put(two);
            JSONObject three = new JSONObject();
            three.put(KEY_IMG_TITLE, "第八名：女皇武则天");
            three.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094235.jpg");
            array.put(three);
            JSONObject four = new JSONObject();
            four.put(KEY_IMG_TITLE, "第七名：道韫");
            four.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094236.jpg");
            array.put(four);
            JSONObject five = new JSONObject();
            five.put(KEY_IMG_TITLE, "第六名：卓文君");
            five.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094237.jpg");
            array.put(five);
            JSONObject six = new JSONObject();
            six.put(KEY_IMG_TITLE, "第五名：班昭");
            six.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094238.jpg");
            array.put(six);
            JSONObject seven = new JSONObject();
            seven.put(KEY_IMG_TITLE, "第四名：杨玉环");
            seven.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094239.jpg");
            array.put(seven);
            JSONObject eight = new JSONObject();
            eight.put(KEY_IMG_TITLE, "第三名：昭君");
            eight.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094240.jpg");
            array.put(eight);
            JSONObject nine = new JSONObject();
            nine.put(KEY_IMG_TITLE, "第二名：貂婵");
            nine.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094241.jpg");
            array.put(nine);
            JSONObject ten = new JSONObject();
            ten.put(KEY_IMG_TITLE, "第一名：西施");
            ten.put(KEY_IMG_URL, "http://www.jydoc.com/uploads/jydoc/p03501/200903090037094242.jpg");
            array.put(ten);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return array;
    }

    public class BitmapLruCache implements ImageLoader.ImageCache {

        private LruCache<String, Bitmap> mCache;

        public BitmapLruCache() {
            int maxSize = 10 * 1024 * 1024;
            mCache = new LruCache<String, Bitmap>(maxSize) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
                    return value.getRowBytes() * value.getHeight();
                }
            };
        }

        @Override
        public Bitmap getBitmap(String url) {
            return mCache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            mCache.put(url, bitmap);
        }

    }
}
