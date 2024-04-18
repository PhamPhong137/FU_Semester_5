import React, { useState } from 'react';

const E12_3 = () => {
  const [isShown, setIsShown] = useState(false);

  const toggleVisibility = () => {
    setIsShown(!isShown);
  };

  return (
    <div style={{ textAlign: 'center', marginTop: '20px', backgroundColor: 'grey', padding: '20px', color: 'white' }}>
      <button onClick={toggleVisibility}>
        {isShown ? 'Hide' : 'Show'}
      </button>
      {isShown && <p>Toggle me!</p>}
    </div>
  );
};

export default E12_3;
