package br.com.jpttrindade.tagview.sample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.jpttrindade.tagview.widget.Tag;
import br.com.jpttrindade.tagview.widget.TagView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TagView tagView = (TagView) findViewById(R.id.tagview);
        final EditText et_text = (EditText) findViewById(R.id.et_text);
        final Button bt_add_tag = (Button) findViewById(R.id.bt_add_tag);

        bt_add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag_text = et_text.getText().toString();
                if (tag_text != null && !tag_text.isEmpty()) {
                    Tag tag = new Tag(tag_text, Color.GRAY, 0, false);
                    tagView.addTag(tag);
                }
            }
        });

        tagView.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position, int clickType) {
                tagView.removeTag(tag);
            }
        });

        //String text, int color, int imgID ,boolean editable
        Tag tag1 = new Tag("Tag_1", Color.GRAY, 0, false);
//        Tag tag2 = new Tag("Tag_2", Color.GRAY, 0, false);
//        Tag tag3 = new Tag("Tag_3", Color.GRAY, 0, false);
//        Tag tag4 = new Tag("Tag_4", Color.GRAY, 0, false);


        tagView.addTag(tag1);
//        tagView.addTag(tag2);
//        tagView.addTag(tag3);
//        tagView.addTag(tag4);

    }
}
