package MscInvisibleDemo; /**
 * @ClassName MscTest
 * @Author 吴俊淇
 * @Date 2020/4/14 22:04
 * @Version 1.0
 **/

import com.iflytek.cloud.speech.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MscTest {

    private static final String APPID = "5e6729f7";

    private static MscTest mObject;

    private static StringBuffer mResult = new StringBuffer();

    private boolean mIsLoop = true;

    public static void main(String args[]) {
        if (null != args && args.length > 0 && args[0].equals("true")) {
            //在应用发布版本中，请勿显示日志，详情见此函数说明。
            Setting.setShowLog(true);
        }
        SpeechUtility.createUtility("appid=" + APPID);
        getMscObj().loop();
        System.out.println("dhiuagfia");
    }

    private static MscTest getMscObj() {
        if (mObject == null)
            mObject = new MscTest();
        return mObject;
    }

    private boolean onLoop() {
        boolean isWait = true;
        try {
            DebugLog.Log("*********************************");
            DebugLog.Log("Please input the command");
            DebugLog.Log("1:音频流听写    4：退出  ");

//            Scanner in = new Scanner(System.in);
//            int command = in.nextInt();
//
//            DebugLog.Log("You input " + command);
//
//            switch (command) {
//                case 1:
//                    Recognize();
//                    break;
//                case 4:
//                    mIsLoop = false;
//                    isWait = false;
//                    in.close();
//                    break;
////                default:
////                    isWait = false;
////                    break;
//            }
            Recognize();
            mIsLoop = false;
            isWait = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isWait;
    }

    // *************************************音频流听写*************************************

    /**
     * 听写
     */

    private boolean mIsEndOfSpeech = false;

    private void Recognize() {
        if (SpeechRecognizer.getRecognizer() == null)
            SpeechRecognizer.createRecognizer();
        mIsEndOfSpeech = false;
        RecognizePcmfileByte();
    }

    /**
     * 自动化测试注意要点 如果直接从音频文件识别，需要模拟真实的音速，防止音频队列的堵塞
     */
    public void RecognizePcmfileByte() {
        SpeechRecognizer recognizer = SpeechRecognizer.getRecognizer();
        recognizer.setParameter(SpeechConstant.AUDIO_SOURCE, "-1");
        //写音频流时，文件是应用层已有的，不必再保存
//		recognizer.setParameter(SpeechConstant.ASR_AUDIO_PATH,
//				"./iat_test.pcm");
        recognizer.setParameter(SpeechConstant.RESULT_TYPE, "plain");
//        recognizer.setParameter(SpeechConstant.ACCENT,"buzhidao");
        recognizer.startListening(recListener);

        FileInputStream fis = null;
        final byte[] buffer = new byte[64 * 1024];
        try {
            fis = new FileInputStream(new File("./test.pcm"));
            if (0 == fis.available()) {
                mResult.append("no audio avaible!");
                recognizer.cancel();
            } else {
                int lenRead = buffer.length;
                while (buffer.length == lenRead && !mIsEndOfSpeech) {
                    lenRead = fis.read(buffer);
                    recognizer.writeAudio(buffer, 0, lenRead);
                }//end of while
                recognizer.stopListening();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fis) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }//end of try-catch-finally

    }

    /**
     * 听写监听器
     */
    private RecognizerListener recListener = new RecognizerListener() {

        public void onBeginOfSpeech() {
            DebugLog.Log("onBeginOfSpeech enter");
            DebugLog.Log("*************开始录音*************");
        }

        public void onEndOfSpeech() {
            DebugLog.Log("onEndOfSpeech enter");
            mIsEndOfSpeech = true;
        }

        public void onVolumeChanged(int volume) {
            DebugLog.Log("onVolumeChanged enter");
            if (volume > 0)
                DebugLog.Log("*************音量值:" + volume + "*************");

        }

        public void onResult(RecognizerResult result, boolean islast) {
            DebugLog.Log("onResult enter");
            mResult.append(result.getResultString());

            if (islast) {
                System.out.println("ceshi" + Thread.currentThread());
                DebugLog.Log("识别结果为:" + mResult.toString());
                mIsEndOfSpeech = true;
                mResult.delete(0, mResult.length());
                waitupLoop();
            }
        }

        public void onError(SpeechError error) {
            mIsEndOfSpeech = true;
            DebugLog.Log("*************" + error.getErrorCode()
                    + "*************");
            waitupLoop();
        }

        public void onEvent(int eventType, int arg1, int agr2, String msg) {
            DebugLog.Log("onEvent enter");
        }

    };


    private void waitupLoop() {
        synchronized (this) {
            MscTest.this.notify();
        }
    }

    public void loop() {

        while (mIsLoop) {
            try {
                if (onLoop()) {
                    synchronized (this) {
                        this.wait();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
