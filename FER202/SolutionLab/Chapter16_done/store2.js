import { configureStore } from "@reduxjs/toolkit";
import counterReducer from "./counterSlice";
const store2 = configureStore({
    reducer:
    {
        counter: counterReducer
    }
});
export default store2;
