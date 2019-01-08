# XChatMessageView

[![License](https://img.shields.io/badge/license-Apache%202-green.svg)](https://www.apache.org/licenses/LICENSE-2.0)
[![Download](https://api.bintray.com/packages/echohaha/maven/XChatMessageView/images/download.svg) ](https://bintray.com/echohaha/maven/XChatMessageView/_latestVersion)

**XChatMessageView** - An Android library to help you quickly build chat list view.

## Sample
<img src="snapshot/snapshot.gif" alt="sample" title="sample" width="300" height="450" />

## Usage

**For a working implementation of this project see the `sample/` folder.**

### Dependency

Include the library as local library project or add the dependency in your `build.gradle`.

```groovy
dependencies {
    compile 'me.kaneki.xchatmessageview:library:0.0.2'
}
```

### Layout

Set `XChatMessageView` in xml is as same as simple ViewGroup. Subsequent versions will provide custom attribute set.

```xml
<me.kaneki.xchatmessageview.XChatMessageView
	android:id="@+id/xcmv_home"
	android:background="#f5f5f5"
	android:layout_width="match_parent"
	android:layout_height="match_parent" />
```

### Java

#### 1. construct your Adapter and ViewHolder
Your adapter and holder must extends `XMessageAdapter` and `XViewHolder`, such as the sample code.

```java
@XItemLayoutRes({
        R.layout.msg_list_item_to_text,
        R.layout.msg_list_item_from_text,
        R.layout.msg_list_item_to_img,
        R.layout.msg_list_item_from_img
})
public class SampleAdapter extends XMessageAdapter<Message> {

    public SampleAdapter(Context context, ArrayList<Message> mDatas) {
        super(context, mDatas);
    }

    @Override
    public int getItemViewType(Message message) {
        switch (message.getType()) {
            case Message.TYPE_TEXT:
                return message.isFrom() ? 1 : 0;
            case Message.TYPE_IMG:
                return message.isFrom() ? 3 : 2;
            default:
                return -1;
        }
    }

    @Override
    public XViewHolder<Message> getViewHolder(View itemView, int viewType) {
        switch (viewType) {
            case 0:
                return new SendTextViewHolder(itemView);
            case 1:
                return new ReceiveTextViewHolder(itemView);
            case 2:
                return new SendImageViewHolder(itemView);
            case 3:
                return new ReceiveImageViewHolder(itemView);
            default:
                return null;
        }
    }
}
```

**Notice:**
The generic `Message` is design by your self, the method `getItemViewType` return index must associated with the layout res position in `@XItemLayoutRes` annotations, it begin from position zero. You can according to the properties in the message to return the layout res position to distinguish.

```java
public class SampleViewHolder extends XViewHolder<Message> {

    public ReceiveImageViewHolder(View itemView) {
        super(itemView);

        //findViewById by itemView
    }

    @Override
    public void bindView(Message message) {
    	//set view by message
    }
}

```

**Notice:**
You need to make sure that the `itemView` associated with the layout in `@XItemLayoutRes`. Otherwise `findViewById` may get problem.

#### 2.init Attributes
You can set adapter and attributes after `findViewById`.

```java
	SampleAdapter sampleAdapter = new SampleAdapter(context, mDatas);
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
**Notice:**
`setIsNeedFooterLoadMore` and `setIsNeedHeaderLoadMore` should be used before the Message datas change. Otherwise it will take effect after the interface at the next data changed refresh.

#### 3.API

```java

    /**
     * set message adapter, the adapter should extends XMessageAdapter.
     */
    public void setMessageAdapter(XMessageAdapter messageAdapter);

    /**
     * set message load more listener, it calls when the header is visible and only
     * calls once when trigger.
     */
    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener);

    /**
     * get message adapter, it may return null if setMessageAdapter method with null set.
     */
    public XMessageApdater getMessageAdapter();

    /**
     * toggle the load more header, it should be call before the datas change.
     */
    public void setIsNeedHeaderLoadMore(boolean isNeedHeaderLoadMore);

    /**
     * toggle the load more footer, it should be call before the datas change.
     */
    public void setIsNeedFooterLoadMore(boolean isNeedFooterLoadMore);

    /**
     * return the view's position on the XChatMessageView, the param view must come from the XViewHolder.
     */
    public int getMessageItemPosition(View view);

    /**
     * add a new message at the last of the XChatMessageView, the message should as same as the T of the
     * XViewHolder or XMessageAdapter.
     */
    public void addMessageAtLast(T t);

    /**
     * add messages at the last of the XChatMessageView, the message should as same as the T of the
     * XViewHolder or XMessageAdapter.
     */
    public void addMoreMessageAtLast(List<T> tList);

    /**
     * add messages at the first of the XChatMessageView, the message should as same as the T of the
     * XViewHolder or XMessageAdapter.
     */
    public void addMoreMessageAtFirst(List<T> tList);

    /**
     * remove the message on the XChatMessageView.
     */
    public void removeMessage(View view);

    /**
     * remove all the message on the XChatMessageView.
     */
    public void removeAllMessage();

    /**
     * refresh the view in the recycler view
     */
    public void refreshMessage(View view);

    /**
     * refresh all items.
     */
    public void refreshAllMessage();

    /**
     * scroll to the bottom of the XChatMessageView.
     */
    public void scrollToBottom();

    /**
     * save the XChatMessageView current status, you can resume the status whenever you need.
     */
    public void saveCurrentStatus();

    /**
     * resume the XChatMessageView's saved status.
     */
    public void resumeSaveStatus(int changeSize);

    /**
     * set the message item update animator.
     */
    public void setMessageAnimator(RecyclerView.ItemAnimator itemAnimator);
```

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
