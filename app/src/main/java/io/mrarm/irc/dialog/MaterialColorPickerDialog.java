package io.mrarm.irc.dialog;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;

import io.mrarm.irc.R;
import io.mrarm.irc.view.MaterialColorPicker;

public class MaterialColorPickerDialog {

    private Context mContext;

    public MaterialColorPickerDialog(Context ctx) {
        mContext = ctx;
    }

    public void show() {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.dialog_material_color_picker, null);
        MaterialColorPicker picker = view.findViewById(R.id.picker);
        View backButton = view.findViewById(R.id.back);
        View title = view.findViewById(R.id.title);
        backButton.setOnClickListener((View v) -> {
            picker.closeColor();
        });
        picker.setBackButtonVisibilityCallback((boolean visible) -> {
            if (visible) {
                backButton.setVisibility(View.VISIBLE);
                backButton.setAlpha(0.f);
                backButton.animate().setStartDelay(250L).setDuration(500L).alpha(1.f).setListener(null);
                title.animate().setDuration(500L).x(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48.f, mContext.getResources().getDisplayMetrics()));
            } else {
                if (backButton.getVisibility() == View.VISIBLE) {
                    backButton.animate().setStartDelay(0L).setDuration(500L).alpha(0.f).setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            backButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    title.animate().setDuration(500L).x(0.f);
                }
            }
        });
        AlertDialog dialog = new AlertDialog.Builder(mContext)
                .setView(view)
                .show();

    }

}
