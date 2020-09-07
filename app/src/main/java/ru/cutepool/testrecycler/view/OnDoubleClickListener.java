package ru.cutepool.testrecycler.view;

import android.view.View;

public abstract class OnDoubleClickListener implements View.OnClickListener {

    private static final long DOUBLE_CLICK_TIME_DELTA = 200;//milliseconds

    private long lastClickTime = 0;
    private boolean tap = true;

    @Override
    public void onClick(View v) {
        long clickTime = System.currentTimeMillis();
        if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
            onDoubleClick(v);
            tap = false;
        } else {
            tap = true;
        }

        v.postDelayed(() -> {
            if (tap) {
                onSingleClick(v);
            }
        }, DOUBLE_CLICK_TIME_DELTA);

        lastClickTime = clickTime;
    }

    public abstract void onDoubleClick(View v);

    public abstract void onSingleClick(View v);
}