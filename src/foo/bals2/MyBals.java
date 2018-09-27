package foo.bals2;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.widget.Toast;
import java.util.ArrayList;

import foo.bals2.R;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.Animation.AnimationListener;

public class MyBals extends Activity implements AnimationListener{
	private MediaPlayer mp1;
	private Vibrator vib;
	private int btnflag;
	private static final int REQUEST = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.main);

        btnflag = 0;
    	Button bButton1 = (Button)findViewById(R.id.bButton1);
    	mp1 = MediaPlayer.create(this, R.raw.mp1);
    	vib = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		Toast.makeText(this, "3���ԑ҂��Ă��I", Toast.LENGTH_SHORT).show();

    	bButton1.setOnClickListener(new OnClickListener() {
    		@Override
            public void onClick(View v) {
    			if (btnflag == 0){
                try {
                    // �C���e���g�쐬
                    // ���͂�����������͂���B
                    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    // free-form speech recognition.
                    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    // �\�������镶����
                    intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"������킹�Ď����������Ă��������B");

                    // �C���e���g�J�n
                    startActivityForResult(intent, REQUEST);
                } catch (ActivityNotFoundException e) {
                    // �A�N�e�B�r�e�B��������Ȃ�����
                    Toast.makeText(MyBals.this,
                    		"�A�N�e�B�r�e�B��������܂���ł����B", Toast.LENGTH_LONG).show();
                }
            }
    		}
    	});
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // �������������C���e���g�ł���Ή�������
        if (requestCode == REQUEST && resultCode == RESULT_OK) {
            String speakedString = "";

            // ���ʕ����񃊃X�g
            // �����̕�����F�������ꍇ�C�������ďo��
            ArrayList<String> speechToChar = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            for (int i = 0; i< speechToChar.size(); i++) {
                speakedString += speechToChar.get(i);
            }

            //�@�������Z�������ꍇ�󔒕����Ńp�f�B���O
            for (int i = (20-speakedString.length()); i>0; i--)
                speakedString += " ";

            // �����I������
        		//�i�f�o�b�O�j�F�����ʂ��_�C�A���O�\��
				//	showDialog(this, "", speakedString);
                if (speakedString.indexOf("�o���X") >= 0){
                	btnflag = 1;
                    AnimationSet set = new AnimationSet(true);

                    AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.0f);
                    RotateAnimation rotate = new RotateAnimation(0, 1800, 100, 100);
                    ScaleAnimation scale = new ScaleAnimation(1.0f, 0, 1.0f, 0, 50, 50);

                    set.addAnimation(alpha);
                    set.addAnimation(rotate);
                    set.addAnimation(scale);

                    set.setDuration(4000);
                    set.setAnimationListener(this);
                    set.setFillAfter(true);
                    Button bButton1 = (Button)findViewById(R.id.bButton1);
                    bButton1.startAnimation(set);

        			mp1.seekTo(0);
        			mp1.start();

        			long[] pattern = {100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,100,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,1500,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,1800,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50,50};
        			vib.vibrate(pattern, -1);

//        	        setContentView(R.layout.sub);
                }else{
        			Toast.makeText(this, "����������Ƒ҂��Ă��", Toast.LENGTH_LONG).show();
                }
                super.onActivityResult(requestCode, resultCode, data);
        }
		}

    public void onAnimationStart(Animation animation){

    }

    public void onAnimationRepeat(Animation animation){

    }

    public void onAnimationEnd(Animation animation){
    	setContentView(R.layout.sub);
		Toast.makeText(this, "�ڂ��A�ڂ����`�I", Toast.LENGTH_LONG).show();
    }

	protected void onStop() {
		super.onStop();
		mp1.release();
		vib.cancel();
	}
}