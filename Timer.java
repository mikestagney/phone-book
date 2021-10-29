package phonebook;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class Timer {

    private long startTime;
    private long endTime; // = System.currentTimeMillis();
    private long timeTaken;// =  endTime - startTime;
    private Duration duration;
    private boolean timerStopped;
    private long minutes;
    private long seconds;
    private long milliseconds;

    Timer() {
        startTime = System.currentTimeMillis();
        timerStopped = false;
    }

    public long getCurrentTime() {
        return startTime - System.currentTimeMillis();
    }

    public void stopTimer() {
        endTime = System.currentTimeMillis();
        timeTaken = startTime - endTime;
        duration = Duration.of(timeTaken, ChronoUnit.MILLIS);
        timerStopped = true;
        minutes = duration.toMinutesPart();
        seconds = duration.toSecondsPart();
        milliseconds = duration.toMillisPart();
    }
    public long getMinutes() {
        return timerStopped ? minutes : 0;
    }
    public long getSeconds() {
        return timerStopped ? seconds : 0;
    }
    public long getMilliseconds() {
        return timerStopped ? milliseconds : 0;
    }

}
