package com.example.diaocha;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends Activity {
	String sA1 = "";// ��һ���
	String sA2 = "";// �ڶ����
	String sA3 = "";// �������
	String sA4 = "";// �������
	String sA5 = "";// �������
	String sA6_1 = "";// ������1��
	String sA6_2 = "";// ������2��
	String sA6_3 = "";// ������3��
	String sA6_4 = "";// ������4��
	String sA6_5 = "";// ������5��
	// Boolean flag[]=new Boolean[6];//���1-6���Ƿ����ѡ��
	// Boolean fl[]=new Boolean[5];//��ǵ������Ƿ�ѡ����������
	int flag[] = { 0, 0, 0, 0, 0, 0 };

	CheckBox cb_1, cb_2, cb_3, cb_4, cb_5;
	int m, n;// ���λ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ViewInIt();
		Danxuan();
		MyChecked myonchecked = new MyChecked();
		cb_1.setOnCheckedChangeListener(myonchecked);
		cb_2.setOnCheckedChangeListener(myonchecked);
		cb_3.setOnCheckedChangeListener(myonchecked);
		cb_4.setOnCheckedChangeListener(myonchecked);
		cb_5.setOnCheckedChangeListener(myonchecked);
		// PanDuan();
	}

	// ��ѡ���ʼ��
	public void ViewInIt() {
		 cb_1 = (CheckBox) findViewById(R.id.checkBox1);
		 cb_2 = (CheckBox) findViewById(R.id.checkBox2);
		 cb_3 = (CheckBox) findViewById(R.id.checkBox3);
		 cb_4 = (CheckBox) findViewById(R.id.checkBox4);
		 cb_5 = (CheckBox) findViewById(R.id.checkBox5);
	}

	// ��ѡ���ּ����¼�
	public void Danxuan() {
		// �ֱ��ʼ����ѡ1-5��ĵ�ѡ���
		RadioGroup rg_1 = (RadioGroup) findViewById(R.id.radioGroup1);
		RadioGroup rg_2 = (RadioGroup) findViewById(R.id.radioGroup2);
		RadioGroup rg_3 = (RadioGroup) findViewById(R.id.radioGroup3);
		RadioGroup rg_4 = (RadioGroup) findViewById(R.id.radioGroup4);
		RadioGroup rg_5 = (RadioGroup) findViewById(R.id.radioGroup5);
		// ��ѡ״̬�ı������
		rg_1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				// TODO Auto-generated method stub
				// ����ѡ�е����ݸı�id
				RadioButton rb_1 = (RadioButton) findViewById(checkedId);
				sA1 = rb_1.getText().toString();
				// flag[0]=true;
				flag[0] = 1;

			}
		});
		rg_2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				// TODO Auto-generated method stub
				RadioButton rb_2 = (RadioButton) findViewById(checkedId);
				sA2 = rb_2.getText().toString();
				// flag[1]=true;
				flag[1] = 1;
			}
		});
		rg_3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				// TODO Auto-generated method stub
				RadioButton rb_3 = (RadioButton) findViewById(checkedId);
				sA3 = rb_3.getText().toString();
				// flag[2]=true;
				flag[2] = 1;
			}
		});
		rg_4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				// TODO Auto-generated method stub
				RadioButton rb_4 = (RadioButton) findViewById(checkedId);
				sA4 = rb_4.getText().toString();
				// flag[3]=true;
				flag[3] = 1;
			}
		});
		rg_5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {

				// TODO Auto-generated method stub
				RadioButton rb_5 = (RadioButton) findViewById(checkedId);
				sA5 = rb_5.getText().toString();
				// flag[4]=true;
				flag[4] = 1;
			}
		});
	}

	// ��ѡ���ּ����¼�
	public class MyChecked implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {

			// TODO Auto-generated method stub
			switch (buttonView.getId()) {
			case R.id.checkBox1:
				if (isChecked) {
					sA6_1 = cb_1.getText().toString();
					// fl[0]=true;
					// m++;
				}
				// else
				// {
				// fl[0]=false;
				// }
				break;
			case R.id.checkBox2:
				if (isChecked) {
					sA6_2 = cb_2.getText().toString();
					// fl[1]=true;
					// m++;
				}
				// else
				// {
				// fl[1]=false;
				// }
				break;
			case R.id.checkBox3:
				if (isChecked) {
					sA6_3 = cb_3.getText().toString();
					// fl[2]=true;
					// m++;
				}
				// else
				// {
				// fl[1]=false;
				// }
				break;
			case R.id.checkBox4:
				if (isChecked) {
					sA6_4 = cb_4.getText().toString();
					// fl[3]=true;
					// m++;
				}
				// else
				// {
				// fl[1]=false;
				// }
				break;
			case R.id.checkBox5:
				if (isChecked) {
					sA6_5 = cb_5.getText().toString();
					// fl[4]=true;
					// m++;
				}
				// else
				// {
				// fl[1]=false;
				// }
				break;
			default:
				break;
			}
		}

	}

	// ת�� �ύ��ť�����¼�
	public void onclick(View v) {
		switch (v.getId()) {
		case R.id.button1:

			if (n == 7) {
				Bundle bundle = new Bundle();
				bundle.putString("anwser", sA1 + sA2 + sA3 + sA4 + sA5 + sA6_1
						+ sA6_2 + sA6_3 + sA6_4 + sA6_5);
				Intent intent = new Intent();
				intent.putExtra("Mybundle", bundle);
			} else {
				Toast.makeText(MainActivity.this, "����ɵ�" + n + "������", 500)
						.show();
			}
			break;

		default:
			break;
		}

	}

	// �ж�ҳ���Ƿ�ѡ���
	// public int PanDuan()
	// {
	// // m=0;
	// // for(int i=0;i<fl.length;i++)
	// // {
	// // if(fl[i])
	// // {
	// // m++;
	// // }
	// // }
	// if(m>=3)
	// {
	// n=1;
	// flag[5]=1;
	// for(int i=0;i<flag.length;i++)
	// {
	// if(n==7)
	// {
	// break;
	// }
	// else
	// {
	// if(flag[i]==1)
	// {
	// n++;
	// }
	// else
	// {
	// break;
	//
	// }
	//
	// }
	// }
	// return n;
	// }
	// else
	// {
	// return n=6;
	// }
	// }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
