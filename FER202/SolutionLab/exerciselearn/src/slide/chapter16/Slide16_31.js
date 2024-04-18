import { Provider } from 'react-redux';
import store from './store';
import JobDetail from './fetchJobDetail';

const Slide16_31 = () => {
  return (
    <Provider store={store}>
      <JobDetail jobId={1} />
    </Provider>
  );
};

export default Slide16_31;
