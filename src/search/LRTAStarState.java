package javaff.search;
import javaff.planning.State;

public class LRTAStarState
    {
        protected State state;
        protected double h_value;

        public LRTAStarState (State state, double h_value)
        {
            this.state = state;
            this.h_value = h_value;
        }

        public State getState()
        {
            return state;
        }

        public double getHValue()
        {
            return h_value;
        }

        public void setState(State s)
        {
            state = s;
        }

        public void setHValue(double h)
        {
            h_value = h;
        }

        // public boolean equals(Object obj){
        // if(obj instanceof AStarState){
        //     int s_hash = state.hashCode();
        //     int new_hash = state.hashCode();
        //     return (s_hash == new_hash);
        // }
        // return false;
        // }

    }