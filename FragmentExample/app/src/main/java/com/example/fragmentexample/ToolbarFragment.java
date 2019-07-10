package com.example.fragmentexample;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

public class ToolbarFragment extends Fragment implements SeekBar.OnSeekBarChangeListener {
    private static int seekvalue = 10;
    private static EditText editText;



    ToolbarListener activityCallback;

    // Tao interface ben trong class ... la quaaaaa''''
    public interface ToolbarListener{
        public void onButtonClick(int fontsize,String text);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            activityCallback = (ToolbarListener)context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement ToolbarListener");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.toolbar_fragment, container,false);

        editText = (EditText) view.findViewById(R.id.editText1);
        final SeekBar seekBar = (SeekBar)view.findViewById(R.id.seekBar1);
        // Khong hieu ???
        seekBar.setOnSeekBarChangeListener(this);

        final Button button = (Button)view.findViewById(R.id.button1);
        button.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        buttonClicked(v);
                    }
                }
        );
        return view;
    }

    private void buttonClicked(View v) {
        activityCallback.onButtonClick(seekvalue,editText.getText().toString());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        seekvalue = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
