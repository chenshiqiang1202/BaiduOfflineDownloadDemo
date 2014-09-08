/**
 * @description: 通用搜索界面
 * @author chenshiqiang E-mail:csqwyyx@163.com
 * @date 2014年9月7日 下午3:27:28   
 * @version 1.0   
 */
package com.csq.baiduoffline;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MySearchView extends RelativeLayout {

	// ------------------------ Constants ------------------------

	// ------------------------- Fields --------------------------
	
	private ImageView btnSearch;
    private EditText etSearch;

    private SearchListener listener;

	// ----------------------- Constructors ----------------------
    
    public MySearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_search, this, true);

        btnSearch = (ImageView) findViewById(R.id.btnSearch);
        etSearch = (EditText) findViewById(R.id.etSearch);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.search_view);
        String hint = ta.getString(R.styleable.search_view_hint);
        etSearch.setHint(hint);

        boolean showBtn = ta.getBoolean(R.styleable.search_view_show_search_button, true);
        if(showBtn){
            btnSearch.setVisibility(View.VISIBLE);
        }else{
            btnSearch.setVisibility(View.GONE);
            etSearch.setCompoundDrawables(getResources().getDrawable(R.drawable.btn_search_white)
                                    , null
                                    , null
                                    , null);
        }

        btnSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.search(getInputText());
                }
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                if(listener != null){
                    listener.afterTextChanged(s);
                }
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    if(listener != null){
                        listener.search(getInputText());
                    }
                }
                return false;
            }
        });
        
    }

	// -------- Methods for/from SuperClass/Interfaces -----------

	// --------------------- Methods public ----------------------

	// --------------------- Methods private ---------------------

	// --------------------- Getter & Setter ---------------------
    
    public String getInputText(){
        return etSearch.getText().toString();
    }

    public void setEditText(String text){
        etSearch.setText(text);
    }

    public EditText getEtSearch() {
        return etSearch;
    }

    public ImageView getBtnSearch() {
        return btnSearch;
    }

    public void setSearchListener(SearchListener listener) {
        this.listener = listener;
    }

	// --------------- Inner and Anonymous Classes ---------------
    
    public static interface SearchListener{
        public void afterTextChanged(Editable text);
        public void search(String text);
    }
}
