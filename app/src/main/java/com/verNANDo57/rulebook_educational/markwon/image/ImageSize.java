/*
 * Author: noties <https://noties.io/>
 * date: 2021/03/15
 *
 * Modified by: VerNANDo57 <silvenation@gmail.com>
 * date: 2022/01/24 6:01PM GMT+7
 */

package com.verNANDo57.rulebook_educational.markwon.image;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @since 1.0.1
 */
@SuppressWarnings("WeakerAccess")
public class ImageSize {

    public static class Dimension {

        public final float value;
        public final String unit;

        public Dimension(float value, @Nullable String unit) {
            this.value = value;
            this.unit = unit;
        }

        @NonNull
        @Override
        public String toString() {
            return "Dimension{" +
                    "value=" + value +
                    ", unit='" + unit + '\'' +
                    '}';
        }
    }

    public final Dimension width;
    public final Dimension height;

    public ImageSize(@Nullable Dimension width, @Nullable Dimension height) {
        this.width = width;
        this.height = height;
    }

    @NonNull
    @Override
    public String toString() {
        return "ImageSize{" +
                "width=" + width +
                ", height=" + height +
                '}';
    }
}
