package com.aalj34.aa53;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	private EditText editName = null;
	private EditText editPass = null;
	private CheckBox checkBox1 = null;
	SharedPreferences preferences = null;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataInit();
    }
    //ҳ���ʼ��
    public void dataInit(){
    	preferences = this.getSharedPreferences("name.dat",Context.MODE_PRIVATE);
    	editName = (EditText) findViewById(R.id.editText1);
    	editPass = (EditText) findViewById(R.id.editText2);
    	checkBox1 = (CheckBox) findViewById (R.id.checkBox1);
    	
    	
    	
    	editName.setText(preferences.getString("NAME", ""));
    	editPass.setText(preferences.getString("PASS", ""));
    	checkBox1.setChecked(preferences.getBoolean("CHECK", false));
    			
    }
    
    public void onclick(View v){
    	if (editName.getText().toString().equals("")&&editPass.getText().toString().equals("")) {
    		
    		Toast.makeText(MainActivity.this, "�������˺�����", 0).show();
    		
    		
			
		}else{
			String name=editName.getText().toString()+":"+editPass.getText().toString();
			//���ݴ洢
			  
			Editor editor  = preferences.edit();
			editor.putString("NAME", editName.getText().toString());
			editor.putString("PASS", editPass.getText().toString());
			editor.putBoolean("CHECK", checkBox1.isChecked());
			editor.commit();
			
			if(checkBox1.isChecked()){
				
				
				
				Toast.makeText(MainActivity.this, "�˺�: "+editName.getText().toString()+"����: "+editPass.getText().toString()+"   �Ѿ���ס����", 0).show();
    			System.out.println();
    			
    		}else{
    			Toast.makeText(MainActivity.this, "�˺�: "+editName.getText().toString()+"����: "+editPass.getText().toString()+"   û�м�ס����", 0).show();
    			
    		}
		}
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
