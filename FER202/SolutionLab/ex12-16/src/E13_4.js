import React from 'react';
import ValidatedInput from './ValidatedInput';

const E13_4 = () => {
  // Hàm xác minh đơn giản, ví dụ kiểm tra xem giá trị có phải là email hợp lệ hay không
  const validateEmail = (value) => {
    return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(value);
  };

  return (
    <div>
      <ValidatedInput
        validationFunction={validateEmail}
        errorMessage="Please enter a valid email address."
      />
    </div>
  );
};

export default E13_4;
