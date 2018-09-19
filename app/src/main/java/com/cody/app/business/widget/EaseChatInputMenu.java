package com.cody.app.business.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.cody.app.R;

/**
 * input menu
 * <p>
 * including below component:
 * EaseChatPrimaryMenu: main menu bar, text input, send button
 * EaseChatExtendMenu: grid menu with image, file, location, etc
 * EaseEmojiconMenu: emoji icons
 */
public class EaseChatInputMenu extends LinearLayout {
    FrameLayout primaryMenuContainer;//, emojiconMenuContainer
    protected EaseChatPrimaryMenuBase chatPrimaryMenu;
//    protected EaseEmojiconMenuBase emojiconMenu;
    protected EaseChatExtendMenu chatExtendMenu;
    protected FrameLayout chatExtendMenuContainer;
    protected LayoutInflater layoutInflater;

    private ChatInputMenuListener listener;
    private Context context;
    private boolean inited;

    public EaseChatInputMenu(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs);
    }

    public EaseChatInputMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EaseChatInputMenu(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        layoutInflater.inflate(R.layout.ease_widget_chat_input_menu, this);
        primaryMenuContainer = findViewById(R.id.primary_menu_container);
//        emojiconMenuContainer = (FrameLayout) findViewById(R.id.emojicon_menu_container);
        chatPrimaryMenu = (EaseChatPrimaryMenu) findViewById(R.id.primary_menu);
        chatExtendMenuContainer = findViewById(R.id.extend_menu_container);

        // extend menu
        chatExtendMenu = findViewById(R.id.extend_menu);


    }

    /**
     * init view
     * <p>
     * This method should be called after registerExtendMenuItem(), setCustomEmojiconMenu() and setCustomPrimaryMenu().
     *
     */
    public void init() {
        if (inited) {
            return;
        }
        // primary menu, use default if no customized one
        if (chatPrimaryMenu == null) {
            chatPrimaryMenu = (EaseChatPrimaryMenu) layoutInflater.inflate(R.layout.ease_layout_chat_primary_menu,
                    null);
            primaryMenuContainer.addView(chatPrimaryMenu);
        }

        processChatMenu();
        chatExtendMenu.init();

        inited = true;
    }

    /**
     * set custom primary menu
     *
     * @param customPrimaryMenu
     */
    public void setCustomPrimaryMenu(EaseChatPrimaryMenuBase customPrimaryMenu) {
        this.chatPrimaryMenu = customPrimaryMenu;
    }

    public EaseChatPrimaryMenuBase getPrimaryMenu() {
        return chatPrimaryMenu;
    }

    public EaseChatExtendMenu getExtendMenu() {
        return chatExtendMenu;
    }

    /**
     * register menu item
     *
     * @param name        item name
     * @param drawableRes background of item
     * @param itemId      id
     * @param listener    on click event of item
     */
    public void registerExtendMenuItem(String name, int drawableRes, int itemId,
                                       EaseChatExtendMenu.EaseChatExtendMenuItemClickListener listener) {
        chatExtendMenu.registerMenuItem(name, drawableRes, itemId, listener);
    }

    /**
     * register menu item
     *
     * @param nameRes        resource id of item name
     * @param drawableRes background of item
     * @param itemId      id
     * @param listener    on click event of item
     */
    public void registerExtendMenuItem(int nameRes, int drawableRes, int itemId,
                                       EaseChatExtendMenu.EaseChatExtendMenuItemClickListener listener) {
        chatExtendMenu.registerMenuItem(nameRes, drawableRes, itemId, listener);
    }


    protected void processChatMenu() {
        // send message button
        chatPrimaryMenu.setChatPrimaryMenuListener(new EaseChatPrimaryMenuBase.EaseChatPrimaryMenuListener() {

            @Override
            public void onSendBtnClicked(String content) {
                if (listener != null)
                    listener.onSendMessage(content);
            }

            @Override
            public void onToggleVoiceBtnClicked() {
                hideExtendMenuContainer();
            }

            @Override
            public void onToggleExtendClicked() {
                hideKeyboard();
                toggleMore();
                if (listener != null) {
                    listener.onToggleExtendClicked();
                }
            }

            @Override
            public void onToggleEmojiconClicked() {
                hideExtendMenuContainer();
                if (listener != null) {
                    listener.onQuickMsgClicked();
                }
            }

            @Override
            public void onEditTextClicked() {
                hideExtendMenuContainer();
            }


            @Override
            public boolean onPressToSpeakBtnTouch(View v, MotionEvent event) {
                if (listener != null) {
                    return listener.onPressToSpeakBtnTouch(v, event);
                }
                return false;
            }
        });
    }


    /**
     * insert text
     *
     * @param text
     */
    public void insertText(String text) {
        getPrimaryMenu().onTextInsert(text);
    }

    /**
     * show or hide extend menu
     */
    protected void toggleMore() {
        if (chatExtendMenuContainer.getVisibility() == View.GONE) {
            chatExtendMenuContainer.setVisibility(View.VISIBLE);
            chatExtendMenu.setVisibility(View.VISIBLE);
        } else {
            chatExtendMenuContainer.setVisibility(View.GONE);
            chatExtendMenu.setVisibility(View.GONE);
        }
    }

    /**
     * hide keyboard
     */
    private void hideKeyboard() {
        chatPrimaryMenu.hideKeyboard();
    }

    /**
     * hide extend menu
     */
    public void hideExtendMenuContainer() {
        chatExtendMenu.setVisibility(View.GONE);
        chatExtendMenuContainer.setVisibility(View.GONE);
//        chatPrimaryMenu.onExtendMenuContainerHide();
    }

    /**
     * when back key pressed
     *
     * @return false--extend menu is on, will hide it first
     * true --extend menu is off
     */
    public boolean onBackPressed() {
        if (chatExtendMenuContainer.getVisibility() == View.VISIBLE) {
            hideExtendMenuContainer();
            return false;
        } else {
            return true;
        }

    }


    public void setChatInputMenuListener(ChatInputMenuListener listener) {
        this.listener = listener;
    }

    public interface ChatInputMenuListener {
        /**
         * when send message button pressed
         *
         * @param content message content
         */
        void onSendMessage(String content);

        /**
         * when speak button is touched
         *
         * @param v
         * @param event
         * @return
         */
        boolean onPressToSpeakBtnTouch(View v, MotionEvent event);

        void onQuickMsgClicked();

        void onToggleExtendClicked();
    }

}
