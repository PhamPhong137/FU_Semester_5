import React, { useState } from 'react';

const E12_5 = () => {
  const [color, setColor] = useState(''); // Trạng thái lưu trữ màu hiện tại

  const handleChange = (event) => {
    setColor(event.target.value); // Cập nhật màu dựa trên lựa chọn
  };

  return (
    <div>
      <select value={color} onChange={handleChange} style={{ marginBottom: '10px' }}>
        <option value="">Select a color</option>
        <option value="red">Red</option>
        <option value="blue">Blue</option>
        <option value="green">Green</option>
        <option value="yellow">Yellow</option>
      </select>
      <div style={{ width: '100px', height: '100px', backgroundColor: color }}>
        {/* Khối màu sẽ thay đổi dựa trên lựa chọn */}
      </div>
    </div>
  );
};

export default E12_5;
