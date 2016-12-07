package br.com.jpttrindade.tagview.sample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                    Tag tag = new Tag(tag_text, Color.parseColor("#e53935"));
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

        tagView.setTagSpanSizeLookup(new TagView.TagSpanSizeLookup() {
            @Override
            public int getSpanSize(int textSize, int position, int spans) {

                if (textSize <= 6) {
                    return 1;
                } else if ( textSize > 6 && textSize <= 17) {
                    return 2;
                } else if (textSize > 17 && textSize <= 28) {
                    return 3;
                }
                return 4 ;
            }
        });

        //String text, int color, int imgID ,boolean editable
        Tag tag1 = new Tag("Tag_1", Color.parseColor("#f06292"), R.drawable.ic_close_circle_black_18dp , false);
//        Tag tag2 = new Tag("Tag_2", Color.GRAY, 0, false);
//        Tag tag3 = new Tag("Tag_3", Color.GRAY, 0, false);
//        Tag tag4 = new Tag("Tag_4", Color.GRAY, 0, false);


        tagView.addTag(tag1);
//        tagView.addTag(tag2);
//        tagView.addTag(tag3);
//        tagView.addTag(tag4);

    }
}
