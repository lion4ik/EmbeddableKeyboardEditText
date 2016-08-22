/*
 * Copyright (C) 2011 - Riccardo Ciovati
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
package com.keyboard.test;

import android.app.Activity;
import android.content.Context;
import android.hardware.input.InputManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.github.lion4ik.EmbeddableKeyboardEditText;
import com.github.lion4ik.KeyboardFrame;


public class KeyboardWidgetTutorialActivity extends Activity {

	private EmbeddableKeyboardEditText mTargetView;
    private InputMethodManager inputManager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mTargetView = (EmbeddableKeyboardEditText) findViewById(R.id.target);
        Button btnShowHideKeyboard = (Button) findViewById(R.id.btnHideKeyboard);
        CheckBox checkBoxSystemKeyboard = (CheckBox) findViewById(R.id.cbSystemKeyboard);

        final KeyboardFrame keyboardFrame = (KeyboardFrame) findViewById(R.id.keyboard);
        mTargetView.addFilter(new InputFilter.LengthFilter(14));
        mTargetView.setUseSystemKeyboard(true);
        btnShowHideKeyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(keyboardFrame.isKeyboardShown()){
                    keyboardFrame.hideKeyboard();
                } else {
                    keyboardFrame.showKeyboard();
                }
            }
        });
        checkBoxSystemKeyboard.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTargetView.setUseSystemKeyboard(isChecked);
                if (isChecked) {
                    keyboardFrame.hideKeyboard();
                    inputManager.showSoftInput(mTargetView, 0);
                } else {
                    keyboardFrame.showKeyboard();
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
    }
}