package phonebook;

import java.time.Duration;
import java.time.Instant;
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
        startTime = Instant.now().toEpochMilli();
        timerStopped = false;
    }

    public long getCurrentTime() {
        // make this a duration and convert it to mills and return it


        return Math.abs(startTime - Instant.now().toEpochMilli());
    }

    public void stopTimer() {
        endTime = Instant.now().toEpochMilli();
        timeTaken = startTime - endTime;
        duration = Duration.of(timeTaken, ChronoUnit.MILLIS);
        timerStopped = true;

        minutes = duration.toMinutesPart();
        seconds = duration.toSecondsPart();
        milliseconds = duration.toMillisPart();
    }
    public long getMinutes() {
        return timerStopped ? Math.abs(minutes) : 0;
    }
    public long getSeconds() {
        return timerStopped ? Math.abs(seconds) : 0;
    }
    public long getMilliseconds() {
        return timerStopped ? milliseconds : 0;
    }
    public long getTotalTimeMilli() {
        return timerStopped ? duration.toMillis() : 0;
    }

}