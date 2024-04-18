import { Provider } from 'react-redux';
import store2 from './store2';
import Counter from './Counter';
const Slide16_20 = () => {
    return (
        <Provider store={store2}>
            <Counter />
        </Provider>
    );
}
export default Slide16_20;
