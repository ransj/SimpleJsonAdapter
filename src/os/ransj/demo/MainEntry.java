package os.ransj.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import os.ransj.adapter.R;
import os.ransj.adapter.SimpleJsonAdapter;

/**
 * Created by ransj on 14/12/5.
 */
public class MainEntry extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_entry);
        findViewById(R.id.simple).setOnClickListener(this);
        findViewById(R.id.advance).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.simple:
                Intent sample = new Intent(this, SimpleJsonAdapterDemo.class);
                startActivity(sample);
                break;
            case R.id.advance:
                Intent advance = new Intent(this, AdvanceJsonAdapterDemo.class);
                startActivity(advance);
                break;
        }
    }
}
