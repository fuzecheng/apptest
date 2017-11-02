package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by tongseng on 2016/7/19.
 */
/**
 * log日志统计保存
 *
 * @author way
 *
 */

public class LogCatHelper {
    LogDumper mLogDumper;
    /**
     *
     * 初始化目录
     *
     * */
    public static String msg;
    public LogCatHelper(String package_name) {
         mLogDumper = new LogDumper(package_name);
         }



    public void start() {
        mLogDumper.start();
    }

    public void stop() {
        if (mLogDumper != null) {
            mLogDumper.stopLogs();
            mLogDumper = null;
        }
    }

    private class LogDumper extends Thread {

        private Process logcatProc;
        private BufferedReader mReader = null;
        private boolean mRunning = true;
        String cmds = null;
        private FileOutputStream out = null;

        public LogDumper(String package_name) {
            try {
                File file=new File(System.getProperty("user.dir")+"\\test-output", "APP_log-"
                        + LogcatDate.getFileName()+ ".log");
                out = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            /**
             *
             * 日志等级：*:v , *:d , *:w , *:e , *:f , *:s
             *
             * 显示当前mPID程序的 E和W等级的日志.
             *
             * */

            // cmds = "logcat *:e *:w | grep \"(" + mPID + ")\"";
            cmds = "adb logcat \"ps | grep " + package_name + "\"";//打印所有日志信息
            //cmds = "logcat";//打印所有日志信息
            // cmds = "logcat -s way";//打印标签过滤信息
            //cmds = "logcat *:e *:i | grep \"(" + mPID + ")\"";

        }

        public void stopLogs() {
            mRunning = false;
        }

        @Override
        public void run() {
            try {
                System.out.println(cmds);
                Runtime.getRuntime().exec("adb logcat -c");
                logcatProc = Runtime.getRuntime().exec(cmds);
                mReader = new BufferedReader(new InputStreamReader(
                        logcatProc.getInputStream()), 1024);
                String line = null;
                while (mRunning && (line = mReader.readLine()) != null) {
                    if (!mRunning) {
                        Runtime.getRuntime().exec("adb logcat -c");
                        break;
                    }
                    if (line.length() == 0) {
                        continue;
                    }
                    if (out != null&&!line.contains("appium")) {
                        out.write((LogcatDate.getFileName() + "  " + line + "\n")
                                .getBytes());
                    }
                    if (msg!=null){
                        out.write((msg+ "\n"+ "\n"+ "\n").getBytes());
                        msg=null;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (logcatProc != null) {
                    logcatProc.destroy();
                    logcatProc = null;
                }
                if (mReader != null) {
                    try {
                        mReader.close();
                        mReader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    out = null;
                }

            }

        }

    }


    public static void main(String[] args) throws InterruptedException {
//        String name="com.tct.launcher";
//        LogCatHelper helper= new LogCatHelper(name);
//        helper.start();
//        Thread.sleep(5000);
//        helper.stop();
    }
}