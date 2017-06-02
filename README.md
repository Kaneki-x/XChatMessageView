# XChatMessageView

[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://api.bintray.com/packages/echohaha/maven/XChatMessageView/images/download.svg) ](https://bintray.com/echohaha/maven/XChatMessageView/_latestVersion)

**XChatMessageView** - An Android library to help you quickly build chat list view.

## Sample
<img src="snapshot/snapshot.gif" alt="sample" title="sample" width="300" height="450" />

## Usage

**For a working implementation of this project see the `sample/` folder.**

### Dependency

Include the library as local library project or add the dependency in your build.gradle.

```groovy
dependencies {
    compile 'me.kankei.xchatmessageview:library:0.0.2'
}
```

### Layout

Set XChatMessageView in xml is as same as simple ViewGroup.
     
```xml
	<me.kaneki.xchatmessageview.XChatMessageView
        android:id="@+id/xcmv_home"
        android:background="#f5f5f5"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

```

### Java

#### 1.init
You can set adapter and some attributes after `findViewById`

```java
	xChatMessageView.setMessageAdapter(sampleAdapter);
	xChatMessageView.setIsNeedFooterLoadMore(false);
	xChatMessageView.setIsNeedHeaderLoadMore(true);
	xChatMessageView.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onHeaderLoadMore() {
                                    }

                @Override
                public void onFooterLoadMore() {
                }
            });

```

## Customization

todo
 

## Change Log

### 0.0.2（2017-05-24）
- fix the crash of recycler view during layout
- add some new interface about refresh item

### 0.0.1（2017-05-21）
- library first build


## Community

Looking for contributors, feel free to fork !

Tell me if you're using my library in your application, I'll share it in this README.

## Contact Me

I work as Android Development Engineer at Meili-inc Group.

If you have any questions or want to make friends with me, please feel free to contact me : [chenjianbo2222#gmail.com](mailto:chenjianbo2222@gmail.com)


## License

    Copyright 2016 Kaneki

	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
