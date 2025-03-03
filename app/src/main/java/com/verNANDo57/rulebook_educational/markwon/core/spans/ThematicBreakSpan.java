/*
 * Author: noties <https://noties.io/>
 * date: 2021/03/15
 *
 * Modified by: VerNANDo57 <silvenation@gmail.com>
 * date: 2022/01/24 6:01PM GMT+7
 */

package com.verNANDo57.rulebook_educational.markwon.core.spans;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.style.LeadingMarginSpan;

import androidx.annotation.NonNull;

import com.verNANDo57.rulebook_educational.markwon.core.MarkwonTheme;

public class ThematicBreakSpan implements LeadingMarginSpan {

    private final MarkwonTheme theme;
    private final Rect rect = ObjectsPool.rect();
    private final Paint paint = ObjectsPool.paint();

    public ThematicBreakSpan(@NonNull MarkwonTheme theme) {
        this.theme = theme;
    }

    @Override
    public int getLeadingMargin(boolean first) {
        return 0;
    }

    @Override
    public void drawLeadingMargin(Canvas c, Paint p, int x, int dir, int top, int baseline, int bottom, CharSequence text, int start, int end, boolean first, Layout layout) {

        final int middle = top + ((bottom - top) / 2);

        paint.set(p);
        theme.applyThematicBreakStyle(paint);

        final int height = (int) (paint.getStrokeWidth() + .5F);
        final int halfHeight = (int) (height / 2.F + .5F);

        final int left;
        final int right;
        if (dir > 0) {
            left = x;
            right = c.getWidth();
        } else {
            left = x - c.getWidth();
            right = x;
        }

        rect.set(left, middle - halfHeight, right, middle + halfHeight);
        c.drawRect(rect, paint);
    }
}
