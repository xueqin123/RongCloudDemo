package com.example.qinxue.lean;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import io.rong.common.FileUtils;

/**
 * Created by qinxue on 2017/12/1.
 */

public class DownLoadTestActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "DownLoadTestActivity";
    private Button button;
    private LinearLayout linearLayout;
    private Uri fileUri1 = Uri.parse("http://b.hiphotos.baidu.com/image/pic/item/aec379310a55b3193297a0ec48a98226cffc1738.jpg");
    private Uri fileUri2 = Uri.parse("http://h.hiphotos.baidu.com/image/pic/item/aec379310a55b3193c60aeec48a98226cefc1789.jpg");
    private Uri fileUri3 = Uri.parse("http://h.hiphotos.baidu.com/image/pic/item/060828381f30e92477f1e8a947086e061c95f7d5.jpg");
    private Uri fileUri4 = Uri.parse("http://a.hiphotos.baidu.com/image/pic/item/d6ca7bcb0a46f21f46612acbfd246b600d33aed5.jpg");
    private ArrayList<UriHolder> holders = new ArrayList();
    private ProgressBar mProgressBar;
    private int progress;
    private int failed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.down_load_test_activity);
        button = (Button) findViewById(R.id.download_start);
        button.setOnClickListener(this);
        linearLayout = (LinearLayout) findViewById(R.id.container);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        holders.add(new UriHolder(fileUri1, String.valueOf(1)));
        holders.add(new UriHolder(fileUri2, String.valueOf(2)));
        holders.add(new UriHolder(fileUri3, String.valueOf(3)));
        holders.add(new UriHolder(fileUri4, String.valueOf(4)));

    }

    private class UriHolder {

        UriHolder(Uri uri, String name) {
            this.uri = uri;
            this.name = name;
        }


        public Uri getUri() {
            return uri;
        }

        public void setUri(Uri uri) {
            this.uri = uri;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private Uri uri;
        private String name;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.download_start:
                progress = 0;
                mProgressBar.setMax(holders.size());
                mProgressBar.setProgress(0);
                Log.i(TAG, "downLoad start");
                for (UriHolder holder : holders) {
                    new DownLoadAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, holder); //并行
                    new DownLoadAsyncTask(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, holder); //串行

                }

                break;
            default:
                break;
        }
    }


    private class DownLoadAsyncTask extends AsyncTask<UriHolder, Integer, Boolean> {
        private Context context;
        private String savePath;
        private File dir;

        public DownLoadAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            File path = Environment.getExternalStorageDirectory();
            String defaultPath = "downlodtest";
            dir = new File(path, defaultPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
        }

        @Override
        protected Boolean doInBackground(UriHolder... holders) {
            Log.i(TAG, "doInBackground()");
            //download from internet
            URL url;
            HttpURLConnection connection = null;
            Bitmap bitmap = null;
            File resultfile = null;
            try {
                url = new URL(holders[0].getUri().toString());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(6000); //超时设置
                connection.setDoInput(true);
                connection.setUseCaches(false); //设置不使用缓存
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();
                resultfile = FileUtils.convertBitmap2File(bitmap, dir.getPath() + File.separator, holders[0].getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (resultfile != null && resultfile.exists()) {
                MediaScannerConnection.scanFile(context, new String[]{dir.getPath() + File.separator + holders[0].getName()}, null, null);
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (aBoolean) {
                progress++;
            } else {
                failed++;
            }
            mProgressBar.setProgress(progress);

            if (progress + failed == holders.size()) {
                mProgressBar.setProgress(0);
                Toast.makeText(DownLoadTestActivity.this, "success: " + progress + "failed: " + failed, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
