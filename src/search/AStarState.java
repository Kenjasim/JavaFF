package javaff.search;
import javaff.planning.State;

public class AStarState
    {
        protected State state;
        protected double f_value;

        public AStarState (State state, double f_value)
        {
            this.state = state;
            this.f_value = f_value;
        }

        public State getState()
        {
            return state;
        }

        public double getFValue()
        {
            return f_value;
        }

        public void setState(State s)
        {
            state = s;
        }

        public void setFValue(double f)
        {
            f_value = f;
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