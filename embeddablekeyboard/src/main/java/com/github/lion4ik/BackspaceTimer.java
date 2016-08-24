package com.github.lion4ik;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;

/**
 * Created by Alexey_Pushkarev1 on 07/14/2016.
 */
public class BackspaceTimer extends CountDownTimer {
    private TimerAction timerAction;
    private boolean isInProgress = false;
    private int firstActionSkip = 0;

    private static final int SKIP_ON_TICK_TIMES = 5;

    public BackspaceTimer(long countDownInterval, @NonNull TimerAction timerAction) {
        super(Integer.MAX_VALUE, countDownInterval);
        this.timerAction = timerAction;
    }

    @Override
    public void onTick(long l) {
        if(firstActionSkip == 0){
            timerAction.onTick(l);
            firstActionSkip++;
            return;
        }

        if (isInProgress && firstActionSkip == SKIP_ON_TICK_TIMES) {
            timerAction.onTick(l);
        } else {
            if(!isInProgress){
                isInProgress = true;
                firstActionSkip++;
                return;
            }

            if(firstActionSkip < SKIP_ON_TICK_TIMES){
                firstActionSkip++;
            }
        }

    }

    @Override
    public void onFinish() {
        firstActionSkip = 0;
        isInProgress = false;
    }

    public interface TimerAction{
        void onTick(long l);
    }
}
