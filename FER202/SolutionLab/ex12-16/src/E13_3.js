import React, { useState, useEffect } from 'react';

const E13_3 = () => {
  const [windowSize, setWindowSize] = useState({
    width: window.innerWidth,
    height: window.innerHeight
  });

  useEffect(() => {
    const handleResize = () => {
      setWindowSize({ width: window.innerWidth, height: window.innerHeight });
    };

    // Thêm event listener
    window.addEventListener('resize', handleResize);

    // Hàm dọn dẹp khi component bị gỡ bỏ
    return () => {
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  return (
    <p>Kích thước cửa sổ: {windowSize.width} x {windowSize.height}</p>
  );
};

export default E13_3;
