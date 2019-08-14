package com.julive.library.navigation;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.julive.library.navigation.dragview.DraggableFlagView;


public class TabIndicatorItemView extends LinearLayout {
    private TextView indicatorTextView;
    private DraggableFlagView remindView;
    private boolean isUseCircleUnReadView = true;

    public void setUseCircleUnReadView(boolean useCircleUnReadView) {
        isUseCircleUnReadView = useCircleUnReadView;
    }

    public TabIndicatorItemView(Context context) {
        super(context);
        initView(null);
    }

    public TabIndicatorItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    /**
     * Constructor for dynamic add TabIndicatorItemView.
     *
     * @param context  the context.
     * @param text     the text that tab displays.
     * @param drawable drawable id.
     */
    public TabIndicatorItemView(Context context, String text, Drawable drawable) {
        super(context);
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.tab_indicator_item, this);
        ImageView iv_tab_indicator = (ImageView) findViewById(R.id.iv_tab_indicator);
        indicatorTextView = (TextView) findViewById(R.id.tv_tab_indicator);
        remindView = (DraggableFlagView) findViewById(R.id.dfv_remind);
        iv_tab_indicator.setImageDrawable(drawable);
        indicatorTextView.setText(text);
    }

    private void initView(AttributeSet attrs) {
        setOrientation(VERTICAL);
        inflate(getContext(), R.layout.tab_indicator_item, this);
        ImageView iv_tab_indicator = (ImageView) findViewById(R.id.iv_tab_indicator);
        indicatorTextView = (TextView) findViewById(R.id.tv_tab_indicator);
        remindView = (DraggableFlagView) findViewById(R.id.dfv_remind);
        if (attrs != null) {
            TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.TabIndicatorItemView);
            Drawable drawable = attributes.getDrawable(R.styleable.TabIndicatorItemView_tab_indicator_image_src);
            iv_tab_indicator.setImageDrawable(drawable);
            CharSequence text = attributes.getText(R.styleable.TabIndicatorItemView_tab_indicator_text_string);
            indicatorTextView.setText(text);
            attributes.recycle();
        }
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (selected) {
//            indicatorTextView.setTextColor(getResources().getColor(R.color.color_primary));
        } else {
//            indicatorTextView.setTextColor(getResources().getColor(R.color.color_tab_indicator_text_normal));
        }
    }

    public void setRemindVisible(final boolean visible) {
        remindView.setVisibility(visible ? VISIBLE : GONE);

    }

    public void setRemindType(RemindType remindType) {
        int size;
        int dx = 0;
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) remindView.getLayoutParams();
        if (remindType.equals(RemindType.REMIND_NORMAL)) {
            remindView.setDraggable(false);
            size = (int) getResources().getDimension(R.dimen.dimen_size_9);
            params.width = size;
            params.height = size;
            params.setMargins(params.leftMargin, params.topMargin + size / 2, params.rightMargin, params.bottomMargin);
            remindView.setLayoutParams(params);
        } else if (remindType.equals(RemindType.REMIND_TEXT_DIS_DRAGGABLE)) {
            remindView.setDraggable(false);
            size = (int) getResources().getDimension(R.dimen.dimen_size_18);
            dx = getResources().getDimensionPixelSize(R.dimen.dimen_size_4);
            params.width = size;
            params.height = size;
            remindView.setLayoutParams(params);
        } else {
            remindView.setDraggable(true);
            size = (int) getResources().getDimension(R.dimen.dimen_size_18);
            dx = getResources().getDimensionPixelSize(R.dimen.dimen_size_4);
            if (!isUseCircleUnReadView) {
                params.width = (int) getResources().getDimension(R.dimen.dimen_size_24);
            } else {
                params.width = (int) getResources().getDimension(R.dimen.dimen_size_18);
            }
            params.height = size;
        }
        params.setMargins(size / 2 - dx, (int) getResources().getDimension(R.dimen.dimen_size_2), 0, 0);
        remindView.setLayoutParams(params);
    }

    public void setDraggable(boolean draggable) {
        remindView.setDraggable(draggable);
    }

    public void setRemindText(String text) {
        remindView.setText(text);
    }

    public enum RemindType {
        /**
         * 仅包含提醒
         */
        REMIND_NORMAL,
        /**
         * 文字提醒
         */
        REMIND_TEXT,
        /**
         * 文字提醒，不能拖拽
         */
        REMIND_TEXT_DIS_DRAGGABLE
    }

    public void setRemindDragListener(@NonNull final RemindDragListener dragListener) {
        remindView.setDragListener(new DraggableFlagView.OnDragListener() {
            @Override
            public void onDragOut() {
                dragListener.onDragOut();
            }
        });
    }

    public interface RemindDragListener {
        void onDragOut();
    }
}
