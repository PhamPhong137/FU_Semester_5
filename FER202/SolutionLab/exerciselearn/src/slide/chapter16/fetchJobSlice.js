import { createSlice } from '@reduxjs/toolkit';
import axios from 'axios';
import { fetchJobDetailStart, fetchJobDetailSuccess, fetchJobDetailFailure } from './actions';

const jobSlice = createSlice({
  name: 'jobs',
  initialState: {
    jobDetail: null,
    loading: false,
    error: null,
  },
  reducers: {},
  extraReducers: (builder) => {
    builder
      .addCase(fetchJobDetailStart().type, (state) => {
        state.loading = true;
        state.error = null;
      })
      .addCase(fetchJobDetailSuccess().type, (state, action) => {
        state.loading = false;
        state.jobDetail = action.payload;
      })
      .addCase(fetchJobDetailFailure().type, (state, action) => {
        state.loading = false;
        state.error = action.payload;
      });
  },
});

export default jobSlice.reducer;
