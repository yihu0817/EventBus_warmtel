package com.warmtel.eventbus;

/**
 * Created by Charlie on 2016/7/29.
 */

public class Event {
    public static class TimeEvent{
        private int[] times;
        public int[] getTimes() {
            return times;
        }
        public TimeEvent(int[] times){
            this.times = times;
        }
    }

    public static class PlayerEvent{
        private boolean isPlaying;

        public boolean isPlaying() {
            return isPlaying;
        }
        public PlayerEvent(boolean isPlaying){
            this.isPlaying = isPlaying;
        }

    }

}