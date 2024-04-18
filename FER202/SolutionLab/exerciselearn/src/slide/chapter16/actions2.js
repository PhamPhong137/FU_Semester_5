import { increment, decrement } from './counterSlice';
export const incrementAsync = () => {
    return (dispatch) => {
        setTimeout(() => {
            dispatch(increment());
        }, 1000);
    };
};
export const decrementAsync = () => {
    return (dispatch) => {
        setTimeout(() => {
            dispatch(decrement());
        }, 1000);
    };
};
