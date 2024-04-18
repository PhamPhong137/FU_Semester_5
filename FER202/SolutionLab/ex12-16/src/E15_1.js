import React, { useReducer } from 'react';

// Hàm reducer để cập nhật trạng thái dựa trên hành động nhận được
const counterReducer = (state, action) => {
  switch (action.type) {
    case 'INCREMENT':
      return { count: state.count + 1 };
    case 'DECREMENT':
      return { count: state.count - 1 };
    case 'RESET':
      return { count: 0 };
    default:
      throw new Error('Loại hành động không hợp lệ');
  }
};

// Component Counter
const E15_1 = () => {
  // Khởi tạo useReducer với hàm reducer và trạng thái ban đầu
  const [state, dispatch] = useReducer(counterReducer, { count: 0 });

  return (
    <div>
      <h2>Số đếm: {state.count}</h2>
      <button onClick={() => dispatch({ type: 'INCREMENT' })}>+</button>
      <button onClick={() => dispatch({ type: 'DECREMENT' })}>-</button>
      <button onClick={() => dispatch({ type: 'RESET' })}>Đặt lại</button>
    </div>
  );
};

export default E15_1;
