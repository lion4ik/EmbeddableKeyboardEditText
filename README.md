# EmbeddableKeyboardEditText
EmbeddableKeyboardEditText widget allows you to use any view as keyboad instead of system keyboard.

Add dependency to your build.gradle file
```groovy
compile 'com.github.lion4ik:embeddablekeyboardedittext:1.0.0'
```
## How to use
You should extend KeyboardFrame class and ovveride getLayoutResource() which returns layoutId of your keyboard and ovveride getBackspaceResId() which returns id of your backspace button. 

```java
 public class CalculatorKeyboard extends KeyboardFrame {
    public CalculatorKeyboard(Context context) {
        super(context);
    }

    public CalculatorKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CalculatorKeyboard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public CalculatorKeyboard(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public @LayoutRes int getLayoutResource() {
        return R.layout.calculator_layout;
    }

    @Override
    public int getBackspaceResId() {
        return R.id.imbtnBackspace;
    }
}
```

Then place EmbeddableKeyboardEditText and your which extends KeyboardFrame on layout 

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:paddingBottom="@dimen/activity_vertical_margin"
    android:paddingLeft="@dimen/activity_horizontal_margin"
    android:paddingRight="@dimen/activity_horizontal_margin"
    android:paddingTop="@dimen/activity_vertical_margin"
    >

    <LinearLayout android:layout_width="match_parent"
        android:id="@+id/container" android:layout_alignParentTop="true"
        android:orientation="vertical"
        android:layout_height="wrap_content"
        >
        <com.github.lion4ik.EmbeddableKeyboardEditText
            android:id="@+id/target"
            app:availableSymbols="0123456789RESVCL"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <Button
            android:id="@+id/btnHideKeyboard"
            android:layout_gravity="center"
            android:text="@string/btn_title"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

    </LinearLayout>

    <com.keyboard.test.CalculatorKeyboard
        android:id="@+id/keyboard"
        android:layout_below="@+id/container"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        >

    </com.keyboard.test.CalculatorKeyboard>

</RelativeLayout>
```

After that you should connect your keyboard with EmbeddableKeyboardEditText. You need just setInputConnection on keyboard and register this keyboard in EmbeddableKeyboardEditText.
You can do it in onCreate of your Activity:

```java
public class KeyboardWidgetTutorialActivity extends Activity {

	private EmbeddableKeyboardEditText mTargetView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mTargetView = (EmbeddableKeyboardEditText) findViewById(R.id.target);
        Button btnShowHideKeyboard = (Button) findViewById(R.id.btnHideKeyboard);
        final KeyboardFrame keyboardFrame = (KeyboardFrame) findViewById(R.id.keyboard);
        keyboardFrame.setInputConnection(mTargetView.getInputConnection());
        mTargetView.registerKeyboard(keyboardFrame);
        btnShowHideKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keyboardFrame.isKeyboardShown()){
                    keyboardFrame.hideKeyboard();
                } else {
                    keyboardFrame.showKeyboard();
                }
            }
        });
	}
}
```

### License
MIT
