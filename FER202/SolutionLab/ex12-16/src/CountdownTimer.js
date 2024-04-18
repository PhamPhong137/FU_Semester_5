import React, { useState, useEffect } from 'react';

const CountdownTimer = ({ initialValue }) => {
  const [timeRemaining, setTimeRemaining] = useState(initialValue);

  useEffect(() => {
    // Nếu thời gian đã là 0, không cần thiết lập khoảng thời gian (interval)
    if (timeRemaining <= 0) {
      return;
    }

    // Thiết lập khoảng thời gian để giảm thời gian còn lại mỗi giây
    const timerId = setInterval(() => {
      setTimeRemaining((thoiGianTruoc) => thoiGianTruoc - 1);
    }, 1000);

    // Dọn dẹp khoảng thời gian nếu component bị gỡ bỏ hoặc thời gian còn lại thay đổi
    return () => {
      clearInterval(timerId);
    };
  }, [timeRemaining]);

  return (
    <div>
      Thời gian còn lại: {timeRemaining}
    </div>
  );
};

export default CountdownTimer;
