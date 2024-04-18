import { configureStore } from '@reduxjs/toolkit';
import fetchReducer from './fetchJobSlice';

const store = configureStore({
  reducer: {
    jobs: fetchReducer,
  },
});

export default store;
