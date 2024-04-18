// EventHandlingDemo.js
import React, { useState } from 'react';

const E16 = () => {
  const [count, setCount] = useState(0);

  const handleButtonClick = () => {
    setCount(prevCount => prevCount + 1);
  };

  return (
    <div>
      <h1>Event Handling Demo</h1>
      <p>Count: {count}</p>
      <button onClick={handleButtonClick}>Increase Count</button>
    </div>
  );
};

export default E16;
