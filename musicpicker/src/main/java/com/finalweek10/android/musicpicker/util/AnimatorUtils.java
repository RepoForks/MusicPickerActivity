/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.finalweek10.android.musicpicker.util;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

public class AnimatorUtils {

    public static ValueAnimator getAlphaAnimator(View view, float... values) {
        return ObjectAnimator.ofFloat(view, View.ALPHA, values);
    }

    public static void startDrawableAnimation(ImageView view) {
        final Drawable d = view.getDrawable();
        if (d instanceof Animatable) {
            ((Animatable) d).start();
        }
    }
}