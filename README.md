## ShelfView ##

**Android custom view to display books on shelf**

<img src="/portrait.png" width="340"> <img src="/landscape.png" width="528">


A fork project of [ShelfView](https://github.com/tdscientist/ShelfView)

#### How to use ####
----

**build.gradle**
```
allprojects {
      repositories {
           maven {
               url 'https://jitpack.io'
           }
      }
}
```


```
dependencies {
        compile 'com.github.joielechong:shelfview:2.1.2'
}
```

**Layout**
```
<com.rilixtech.shelfview.ShelfView
    android:id="@+id/shelfView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />

```

**Activity**
```
import com.rilixtech.shelfview.BookModel;
import com.rilixtech.shelfview.ShelfView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShelfView.BookClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShelfView shelfView = (ShelfView) findViewById(R.id.shelfView);
        shelfView.setOnBookClicked(this);
        List<BookModel> models = new ArrayList<>();

        models.add(BookModel.urlBookModel("http://eurodroid.com/pics/beginning_android_book.jpg", "1", "Beginning Android"));
       
        shelfView.loadData(models);
    }

    @Override
    public void onBookClicked(int position, String bookId, String bookTitle) {	
       // handle click events here
       //Toast.makeText(this, bookTitle, Toast.LENGTH_SHORT).show();
    }
}

```



#### Loading book covers from other sources ####
----

* Internal/External directory in the device
```
model.add(BookModel.fileBookModel("/path/to/android_book_cover.jpg", "1", "Let's Talk About Android"));
shelfView.loadData(model);
``` 



* Assets folder
```
model.add(BookModel.assetBookModel("android.jpg", "1", "Android for Experts"));
shelfView.loadData(model);
```
 


* Drawable folder
```
model.add(BookModel.drawableBookModel("alice", "1", "Alice in Wonderland"));
shelfView.loadData(model);
``` 


#### Permissions ####
----
```
   <uses-permission android:name="android.permission.INTERNET" />
   <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
``` 



#### License ####
----
```
Copyright 2017 Joielechong

Copyright 2017 Adeyinka Adediji

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```



#### Credits ####
---
* [Picasso](https://github.com/square/picasso)

