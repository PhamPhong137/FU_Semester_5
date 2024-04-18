import React, { useState, useEffect } from 'react';

const ValidatedInput = ({ validationFunction, errorMessage }) => {
  const [value, setValue] = useState('');
  const [isValid, setIsValid] = useState(true);

  useEffect(() => {
    setIsValid(validationFunction(value));
    // Dependency array bao gồm value và validationFunction để đảm bảo effect chạy mỗi khi chúng thay đổi
  }, [value, validationFunction]);

  return (
    <div>
      <input
        type="text"
        value={value}
        onChange={(e) => setValue(e.target.value)}
        className={isValid ? '' : 'error'}
      />
      {!isValid && <p className="error-message">{errorMessage}</p>}
    </div>
  );
};

export default ValidatedInput;
