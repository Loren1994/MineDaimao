package com.example.loren.minesample.base.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loren.minesample.R;


/**
 * Created by victor on 16-8-10
 */
public class FixedFlowLayout extends ViewGroup implements View.OnClickListener {
    private int columns;
    private int horizontalSpacing;
    private int verticalSpacing;
    private boolean isSingleSelected;
    private int eachWidth;
    private int eachHeight;
    private int childGravity;
    private boolean hasSelected;
    private int preClickPos;
    private OnItemClickListener onItemClickListener;

    public FixedFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < getChildCount(); i++) {
            if (v == getChildAt(i)) {
                if (preClickPos == i) {
                    return;
                } else {
                    preClickPos = i;
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(i);
                    }
                }
            }
            getChildAt(i).setSelected(v == getChildAt(i));
        }
    }

    private void init(Context context, AttributeSet attributeSet) {
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.FixedFlowLayout);
        columns = typedArray.getInt(R.styleable.FixedFlowLayout_columns_per_line, 1);
        isSingleSelected = typedArray.getBoolean(R.styleable.FixedFlowLayout_single_selected, false);
        horizontalSpacing = typedArray.getDimensionPixelOffset(R.styleable.FixedFlowLayout_horizontal_spacing, 0);
        verticalSpacing = typedArray.getDimensionPixelOffset(R.styleable.FixedFlowLayout_vertical_spacing, 0);
        childGravity = typedArray.getInt(R.styleable.FixedFlowLayout_child_gravity, Gravity.TOP | Gravity.START);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (getChildCount() == 0)
            return;
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        eachHeight = getChildAt(0).getMeasuredHeight();
        eachWidth = (MeasureSpec.getSize(widthMeasureSpec) - getPaddingStart() - getPaddingEnd() - (columns - 1) * horizontalSpacing) / columns;
        int curColumn = 0, curRow = 1;
        for (int i = 0; i < getChildCount(); i++) {
            if (curColumn == columns) {
                curColumn = 0;
                curRow++;
            }
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            int span = lp.getColumnSpan();
            if (span > columns - curColumn) {
                span = lp.columnSpan = columns - curColumn;
            }
            lp.height = eachHeight;
            lp.width = span * eachWidth + (span - 1) * horizontalSpacing;
            curColumn += span;
            child.setLayoutParams(lp);
        }
        int height = curRow * eachHeight + (curRow - 1) * verticalSpacing + getPaddingTop() + getPaddingBottom();
        int measuredHeightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, measuredHeightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (getChildCount() == 0)
            return;
        int curY = getPaddingTop();
        int curX = getPaddingStart();
        int itemsCountEachLine = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            LayoutParams lp = (LayoutParams) child.getLayoutParams();
            if (isSingleSelected) {
                if (!hasSelected) {
                    child.setSelected(true);
                    hasSelected = true;
                }
                child.setOnClickListener(this);
            }
            if (itemsCountEachLine == columns) {
                itemsCountEachLine = 0;
                curY += (eachHeight + verticalSpacing);
                curX = getPaddingStart();
            }
            if (getChildAt(i) instanceof TextView) {
                ((TextView) child).setGravity(childGravity);
            }
            child.layout(curX, curY, curX + lp.width, curY + eachHeight);
            curX += (lp.width + horizontalSpacing);
            itemsCountEachLine += lp.columnSpan;
        }
    }

    public void setColumns(int columns) {
        this.columns = columns;
        layout(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new LayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new LayoutParams(p);
    }


    public interface OnItemClickListener {
        void onClick(int position);
    }

    public static class LayoutParams extends ViewGroup.LayoutParams {

        private int columnSpan = 1;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            init(c, attrs);
        }

        public LayoutParams(int width, int height) {
            super(width, height);
        }

        public LayoutParams(ViewGroup.LayoutParams source) {
            super(source);
        }

        public int getColumnSpan() {
            return columnSpan;
        }

        public void setColumnSpan(int columnSpan) {
            this.columnSpan = columnSpan;
        }

        private void init(Context c, AttributeSet attrs) {
            TypedArray typedArray = c.obtainStyledAttributes(attrs, R.styleable.FixedFlowLayout_Layout);
            columnSpan = typedArray.getInteger(R.styleable.FixedFlowLayout_Layout_span_column, 1);
            typedArray.recycle();
        }
    }
}