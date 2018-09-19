package com.cody.app.business.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cody.app.R;

/**
 * primary menu
 *
 */
public class EaseChatPrimaryMenu extends EaseChatPrimaryMenuBase implements OnClickListener {
    private EditText editText;
    private View buttonSetModeKeyboard;
    private RelativeLayout edittext_layout;
    private View buttonSetModeVoice;
    private View buttonSend;
    private View buttonPressToSpeak;
    private ImageView quickMsgNormal;
    private ImageView quickMsgChecked;
    private ImageView buttonMore;
//    private boolean ctrlPress = false;

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public EaseChatPrimaryMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EaseChatPrimaryMenu(Context context) {
        super(context);
        init(context);
    }

    private void init(final Context context) {
        LayoutInflater.from(context).inflate(R.layout.ease_widget_chat_primary_menu, this);
        editText = findViewById(R.id.et_sendmessage);
        buttonSetModeKeyboard = findViewById(R.id.btn_set_mode_keyboard);
        edittext_layout = findViewById(R.id.edittext_layout);
        buttonSetModeVoice = findViewById(R.id.btn_set_mode_voice);
        buttonSend = findViewById(R.id.btn_send);
        buttonPressToSpeak = findViewById(R.id.btn_press_to_speak);
        quickMsgNormal = findViewById(R.id.iv_face_normal);
        quickMsgChecked = findViewById(R.id.iv_face_checked);
        RelativeLayout faceLayout = findViewById(R.id.rl_quickMsg);
        buttonMore = findViewById(R.id.btn_more);
//        edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_normal);
        
        buttonSend.setOnClickListener(this);
        buttonSetModeKeyboard.setOnClickListener(this);
        buttonSetModeVoice.setOnClickListener(this);
        buttonMore.setOnClickListener(this);
        faceLayout.setOnClickListener(this);
        editText.setOnClickListener(this);

        // listen the text change
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    buttonMore.setVisibility(GONE);
                    buttonSend.setVisibility(VISIBLE);
                } else {
                    buttonMore.setVisibility(VISIBLE);
                    buttonSend.setVisibility(GONE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editText.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });

//        editText.setOnKeyListener(new OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                EMLog.d("key", "keyCode:" + keyCode + " action:" + event.getAction());
//
//                // test on Mac virtual machine: ctrl map to KEYCODE_UNKNOWN
//                if (keyCode == KeyEvent.KEYCODE_UNKNOWN) {
//                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
//                        ctrlPress = true;
//                    } else if (event.getAction() == KeyEvent.ACTION_UP) {
//                        ctrlPress = false;
//                    }
//                }
//                return false;
//            }
//        });

//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                EMLog.d("key", "keyCode:" + event.getKeyCode() + " action" + event.getAction() + " ctrl:" + ctrlPress);
//                if (actionId == EditorInfo.IME_ACTION_SEND ||
//                        (event.getKeyCode() == KeyEvent.KEYCODE_ENTER &&
//                         event.getAction() == KeyEvent.ACTION_DOWN &&
//                                ctrlPress)) {
//                    String s = editText.getText().toString();
//                    editText.setText("");
//                    listener.onSendBtnClicked(s);
//                    return true;
//                }
//                else{
//                    return false;
//                }
//            }
//        });

        
        buttonPressToSpeak.setOnTouchListener(new OnTouchListener() {
            
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return listener != null && listener.onPressToSpeakBtnTouch(v, event);
            }
        });
    }
    
    /**
     * set recorder view when speak icon is touched
     * @param voiceRecorderView
     */
    public void setPressToSpeakRecorderView(EaseVoiceRecorderView voiceRecorderView){
        EaseVoiceRecorderView voiceRecorderView1 = voiceRecorderView;
    }

    /**
     * on clicked event
     * @param view
     */
    @Override
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.btn_send) {
            if(listener != null){
                String s = editText.getText().toString();
                editText.setText("");
                listener.onSendBtnClicked(s);
            }
        } else if (id == R.id.btn_set_mode_voice) {
            setModeVoice();
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleVoiceBtnClicked();
        } else if (id == R.id.btn_set_mode_keyboard) {
            setModeKeyboard();
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleVoiceBtnClicked();
        } else if (id == R.id.btn_more) {
            buttonSetModeVoice.setVisibility(VISIBLE);
            buttonSetModeKeyboard.setVisibility(GONE);
            edittext_layout.setVisibility(VISIBLE);
            buttonPressToSpeak.setVisibility(GONE);
            showNormalFaceImage();
            if(listener != null)
                listener.onToggleExtendClicked();
        } else if (id == R.id.et_sendmessage) {
//            edittext_layout.setBackgroundResource(R.drawable.ease_input_bar_bg_active);
            quickMsgNormal.setVisibility(VISIBLE);
            quickMsgChecked.setVisibility(INVISIBLE);
            if(listener != null)
                listener.onEditTextClicked();
        } else if (id == R.id.rl_quickMsg) {
//            toggleQuickMsg();
            if(listener != null){
                listener.onToggleEmojiconClicked();
            }
        }
    }
    
    
    /**
     * show voice icon when speak bar is touched
     * 
     */
    protected void setModeVoice() {
        hideKeyboard();
        edittext_layout.setVisibility(GONE);
        buttonSetModeVoice.setVisibility(GONE);
        buttonSetModeKeyboard.setVisibility(VISIBLE);
        buttonSend.setVisibility(GONE);
        buttonMore.setVisibility(VISIBLE);
        buttonPressToSpeak.setVisibility(VISIBLE);
        quickMsgNormal.setVisibility(VISIBLE);
        quickMsgChecked.setVisibility(INVISIBLE);

    }

    /**
     * show keyboard
     */
    protected void setModeKeyboard() {
        edittext_layout.setVisibility(VISIBLE);
        buttonSetModeKeyboard.setVisibility(GONE);
        buttonSetModeVoice.setVisibility(VISIBLE);
        // mEditTextContent.setVisibility(View.VISIBLE);
        editText.requestFocus();
        // buttonSend.setVisibility(View.VISIBLE);
        buttonPressToSpeak.setVisibility(GONE);
        if (TextUtils.isEmpty(editText.getText())) {
            buttonMore.setVisibility(VISIBLE);
            buttonSend.setVisibility(GONE);
        } else {
            buttonMore.setVisibility(GONE);
            buttonSend.setVisibility(VISIBLE);
        }

    }
    
    
    protected void toggleQuickMsg(){
        if(quickMsgNormal.getVisibility() == VISIBLE){
            showSelectedFaceImage();
        }else{
            showNormalFaceImage();
        }
    }
    
    private void showNormalFaceImage(){
        quickMsgNormal.setVisibility(VISIBLE);
        quickMsgChecked.setVisibility(INVISIBLE);
    }
    
    private void showSelectedFaceImage(){
        quickMsgNormal.setVisibility(INVISIBLE);
        quickMsgChecked.setVisibility(VISIBLE);
    }
    

    @Override
    public void onExtendMenuContainerHide() {
        showNormalFaceImage();
    }

    @Override
    public void onTextInsert(CharSequence text) {
       int start = editText.getSelectionStart();
       Editable editable = editText.getEditableText();
       editable.insert(start, text);
       setModeKeyboard();
    }

    @Override
    public EditText getEditText() {
        return editText;
    }

}
