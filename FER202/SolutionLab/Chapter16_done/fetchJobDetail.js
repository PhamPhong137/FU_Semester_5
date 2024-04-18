import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { fetchJobDetail } from './actions';

const JobDetail = ({ jobId }) => {
  const dispatch = useDispatch();
  const jobDetail = useSelector((state) => state.jobs.jobDetail);
  const loading = useSelector((state) => state.jobs.loading);
  const error = useSelector((state) => state.jobs.error);

  useEffect(() => {
    dispatch(fetchJobDetail(jobId));
  }, [dispatch, jobId]);

  if (loading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return <div>Error: {error}</div>;
  }

  if (!jobDetail) {
    return null;
  }

  return (
    <div>
      <h1>Job Detail</h1>
      <p>{jobDetail.title}</p>
    </div>
  );
};

export default JobDetail;
