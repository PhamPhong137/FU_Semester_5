import React from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { incrementAsync, decrementAsync } from './actions2';
const Counter = () => {
    const count = useSelector((state) => state.counter);
    const dispatch = useDispatch();
    const handleIncrement = () => {
        dispatch(incrementAsync());
    };
    const handleDecrement = () => {
        dispatch(decrementAsync());
    };
    return (
        <>
            <h2>Count: {count}</h2>
            <button onClick={handleIncrement}>+</button>
            <button onClick={handleDecrement}>&nbsp;-&nbsp;</button>
        </>
    );
};
export default Counter;
