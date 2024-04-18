import axios from 'axios';

export const fetchJobDetailStart = () => ({
  type: 'FETCH_JOB_DETAIL_START',
});

export const fetchJobDetailSuccess = (jobDetail) => ({
  type: 'FETCH_JOB_DETAIL_SUCCESS',
  payload: jobDetail,
});

export const fetchJobDetailFailure = (error) => ({
  type: 'FETCH_JOB_DETAIL_FAILURE',
  payload: error,
});

export const fetchJobDetail = (jobId) => {
  return async (dispatch) => {
    dispatch(fetchJobDetailStart());
    try {
      const response = await axios.get(`https://my-json-server.typicode.com/typicode/demo/posts/${jobId}`);
      const jobDetail = response.data;
      dispatch(fetchJobDetailSuccess(jobDetail));
    } catch (error) {
      dispatch(fetchJobDetailFailure(error.message));
    }
  };
};