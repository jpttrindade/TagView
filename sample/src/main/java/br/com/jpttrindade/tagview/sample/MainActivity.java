package br.com.jpttrindade.tagview.sample;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import br.com.jpttrindade.tagview.OnTagClickListener;
import br.com.jpttrindade.tagview.Tag;
import br.com.jpttrindade.tagview.TagView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TagView tagView = (TagView) findViewById(R.id.tagview);
        final EditText et_text = (EditText) findViewById(R.id.et_text);
        final Button bt_remove_all = (Button) findViewById(R.id.bt_remove_all);


        bt_remove_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagView.removeAll();
            }
        });

        final Button bt_add_tag = (Button) findViewById(R.id.bt_add_tag);

        bt_add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag_text = et_text.getText().toString();
                if (tag_text != null && !tag_text.isEmpty()) {
                    Tag tag = new Tag(tag_text, Color.parseColor("#e53935"), R.drawable.ic_close_circle_white_18dp, false);
                    tagView.addTag(tag);
                }
            }
        });

        tagView.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position, int clickType) {
                switch (clickType) {
                    case TagView.ONCLICK_EDIT:
                        Log.d("DEBUG", "OnClickEdit");
                        break;
                    case TagView.ONCLICK_REMOVE:
                        Log.d("DEBUG", "OnClickRemove");
                        break;
                    case TagView.ONCLICK_DEFAULT:
                        Log.d("DEBUG", "OnClickDefault");
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
