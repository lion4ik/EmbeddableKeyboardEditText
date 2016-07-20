package com.github.lion4ik;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;

/**
 * Created by Alexey_Pushkarev1 on 07/14/2016.
 */
public class BackspaceTimer extends CountDownTimer {
    private TimerAction timerAction;

    public BackspaceTimer(long countDownInterval, @NonNull TimerAction timerAction) {
        super(Integer.MAX_VALUE, countDownInterval);
        this.timerAction = timerAction;
    }

    @Override
    public void onTick(long l) {
        timerAction.onTick(l);
    }

    @Override
    public void onFinish() {
        //empty
    }

    public interface TimerAction{
        void onTick(long l);
    }
}
